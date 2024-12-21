package io.github.ciriti.sdk.internal.sdk

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.downloader.FileDownloader
import kotlinx.coroutines.launch

internal class FileDownloaderSdkImpl(
    private val context: Context,
    private val config: FileDownloaderConfig,
    private val fileCache: FileCache,
    private val fileDownloader: FileDownloader,
    private val lifecycleOwner: LifecycleOwner
) : FileDownloaderSdk {

    var client: SdkClient? = null

    override fun loadFiles(urls: List<String>) {
        lifecycleOwner.lifecycleScope.launch {
        }
    }

    override fun clearFiles() {
        lifecycleOwner.lifecycleScope.launch {
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
}
