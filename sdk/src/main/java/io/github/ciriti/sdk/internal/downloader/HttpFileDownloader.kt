package io.github.ciriti.sdk.internal.downloader

import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class HttpFileDownloader(
    private val sdkEventFlow: SdkEventFlow,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FileDownloader {
    override suspend fun downloadFile(url: String): ByteArray? {
        return withContext(ioDispatcher) {
            // Implement file download logic
            null
        }
    }
}
