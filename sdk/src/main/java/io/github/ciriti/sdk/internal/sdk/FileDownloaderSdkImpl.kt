package io.github.ciriti.sdk.internal.sdk

import android.content.Context
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.downloader.FileDownloader

internal class FileDownloaderSdkImpl(
    private val context: Context,
    private val config: FileDownloaderConfig,
    private val fileCache: FileCache,
    private val fileDownloader: FileDownloader
) : FileDownloaderSdk {

    
    override fun loadFiles(urls: List<String>) {
    }

    override fun clearFiles() {
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
        return 0
    }

    override fun setClient(client: SdkClient) {
    }
    
}
