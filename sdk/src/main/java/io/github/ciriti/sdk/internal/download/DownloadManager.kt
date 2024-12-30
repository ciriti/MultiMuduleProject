package io.github.ciriti.sdk.internal.download

import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.api.SdkEvent
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface DownloadManager {

    fun setClient(client: SdkClient)
    fun clearClient()

    fun loadFiles(
        urls: List<String>,
        scope: CoroutineScope,
        exceptionHandler: CoroutineExceptionHandler
    )

    fun cancelDownload(fileName: String)
    fun cancelAllDownloads()

    companion object
}

internal fun DownloadManager.Companion.create(
    fileDownloader: FileDownloader,
    fileCache: FileCache,
    ioDispatcher: CoroutineDispatcher,
    supervisorJob: Job,
    sdkEventFlow: SdkEventFlow,
): DownloadManager = DownloadManagerImpl(
    fileDownloader,
    fileCache,
    ioDispatcher,
    supervisorJob,
    sdkEventFlow
)

private class DownloadManagerImpl(
    private val fileDownloader: FileDownloader,
    private val fileCache: FileCache,
    private val ioDispatcher: CoroutineDispatcher,
    private val supervisorJob: Job,
    private val sdkEventFlow: SdkEventFlow,
) : DownloadManager {

    private val jobs = mutableMapOf<String, Job>()

    private var sdkClient: SdkClient? = null

    override fun setClient(client: SdkClient) {
        sdkClient = client
        fileDownloader.setClient(client)
    }

    override fun clearClient() {
        sdkClient = null
        fileDownloader.clearClient()
    }

    override fun loadFiles(
        urls: List<String>,
        scope: CoroutineScope,
        exceptionHandler: CoroutineExceptionHandler
    ) {
        scope.launch(supervisorJob + exceptionHandler) {
            urls.forEach { url ->
                val fileName = url.substringAfterLast('/')
                jobs[fileName] = launch(ioDispatcher) {
                    handleFileDownload(fileName, url)
                }
            }
        }
    }

    private suspend fun handleFileDownload(fileName: String, url: String) {
        val fileData: Result<ByteArray> = fileDownloader.downloadFile(url).also {
            sdkEventFlow.emitEvent(SdkEvent.FileDownloadStart(fileName))
            sdkClient?.onFileDownloadStart(fileName)
        }
        fileData
            .onSuccess {
                sdkEventFlow.emitEvent(SdkEvent.FileDownloadSuccess(fileName))
                sdkClient?.onFileDownloadSuccess(fileName)
                fileCache.saveFile(fileName, it)
            }
            .onFailure {
                sdkEventFlow.emitEvent(SdkEvent.FileDownloadError(fileName, it))
                sdkClient?.onFileDownloadError(fileName, it)
            }.also {
                sdkEventFlow.emitEvent(SdkEvent.FileDownloadEnd(fileName))
                sdkClient?.onFileDownloadEnd(fileName)
            }
    }

    override fun cancelDownload(fileName: String) {
        jobs[fileName]?.cancel()
        jobs.remove(fileName)
    }

    override fun cancelAllDownloads() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
    }
}
