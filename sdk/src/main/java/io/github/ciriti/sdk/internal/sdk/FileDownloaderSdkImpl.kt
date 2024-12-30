package io.github.ciriti.sdk.internal.sdk

import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.api.SdkEvent
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.download.DownloadManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class FileDownloaderSdkImpl(
    private val config: FileDownloaderConfig,
    private val fileCache: FileCache,
    private val scope: CoroutineScope,
    private val downloadManager: DownloadManager,
    lifecycleHandler: LifecycleHandler,
    private val sdkEventFlow: SdkEventFlow,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FileDownloaderSdk {

    private var client: SdkClient? = null

    init {
        lifecycleHandler.onDispose {
            client = null
            downloadManager.clearClient()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    override val eventFlow: Flow<SdkEvent>
        get() = sdkEventFlow.events

    override fun loadFiles() {
        downloadManager.loadFiles(config.fileUrls, scope, exceptionHandler)
    }

    override fun loadFiles(urls: List<String>) {
        downloadManager.loadFiles(urls, scope, exceptionHandler)
    }

    override fun clearFiles() {
        scope.launch(ioDispatcher) {
            fileCache.clear()
        }
    }

    override fun getFiles(): List<ByteArray> {
        return fileCache.loadFiles()
    }

    override fun getFile(name: String): ByteArray? {
        return fileCache.getFile(name)
    }

    override fun getFilesCount(): Int {
        return fileCache.getFilesCount()
    }

    override fun getFilesSize(): Int {
        return fileCache.getCurrentSize()
    }

    override fun setClient(client: SdkClient) {
        this.client = client
        downloadManager.setClient(client)
    }

    override fun cancelDownload(name: String) {
        downloadManager.cancelDownload(name)
    }

    override fun cancelAllDownloads() {
        downloadManager.cancelAllDownloads()
    }
}
