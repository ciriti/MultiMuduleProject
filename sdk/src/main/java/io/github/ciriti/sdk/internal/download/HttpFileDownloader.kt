package io.github.ciriti.sdk.internal.download

import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.api.SdkEvent
import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException

internal class HttpFileDownloader(
    private val sdkEventFlow: SdkEventFlow,
    private val okHttpClient: OkHttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FileDownloader {

    private var sdkClient: SdkClient? = null

    override fun setClient(client: SdkClient) {
        sdkClient = client
    }

    override fun clearClient() {
        sdkClient = null
    }

    override suspend fun downloadFile(url: String): Result<ByteArray> {
        return withContext(ioDispatcher) {
            val fileName = url.substringAfterLast('/')

            val result = kotlin.runCatching {
                val request = Request.Builder().url(url).build()
                val response = okHttpClient.newCall(request).execute()
                if (!response.isSuccessful) throw IOException("Failed to download file: $url")

                val body: ResponseBody = response.body ?: throw IOException("Empty response body")
                val contentLength = body.contentLength()
                val byteArray = ByteArray(contentLength.toInt())
                var bytesRead: Long = 0
                var progress: Int

                body.byteStream().use { inputStream ->
                    var read: Int
                    while (inputStream.read(
                            byteArray,
                            bytesRead.toInt(),
                            byteArray.size - bytesRead.toInt()
                        ).also { read = it } != -1
                    ) {
                        bytesRead += read
                        progress = (bytesRead * 100 / contentLength).toInt()
                        sdkEventFlow.emitEvent(
                            SdkEvent.ProgressFileDownload(
                                fileName,
                                progress
                            )
                        ) // Emit progress event
                        sdkClient?.onProgressFileDownload(fileName, progress)
                    }
                }
                byteArray
            }
            result
        }
    }
}
