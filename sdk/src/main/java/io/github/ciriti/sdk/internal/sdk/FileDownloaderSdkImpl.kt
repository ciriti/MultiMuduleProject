package io.github.ciriti.sdk.internal.sdk

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.api.SdkEvent
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.downloader.FileDownloader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class FileDownloaderSdkImpl(
    private val config: FileDownloaderConfig,
    private val fileCache: FileCache,
    private val fileDownloader: FileDownloader,
    private val lifecycleOwner: LifecycleOwner,
    private val sdkEventFlow: SdkEventFlow,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FileDownloaderSdk, LifecycleEventObserver {

    private var client: SdkClient? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun loadFiles(urls: List<String>) {
        lifecycleOwner.lifecycleScope.launch {

        }
    }

    override fun clearFiles() {
        lifecycleOwner.lifecycleScope.launch(ioDispatcher) {
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
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> disposeSdk()
            else -> {
            }
        }
    }

    private fun disposeSdk() {
        lifecycleOwner.lifecycle.removeObserver(this)
        client = null
    }
}
