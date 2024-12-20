package io.github.ciriti.sdk.creation

import android.content.Context
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.cache.LruFileCache
import io.github.ciriti.sdk.internal.downloader.HttpFileDownloader
import io.github.ciriti.sdk.internal.sdk.FileDownloaderSdkImpl
import java.io.File

class FileDownloaderSdkBuilder {
    var client: SdkClient? = null
    var config: FileDownloaderConfig? = null
    var context: Context? = null

    private val cache: FileCache by lazy {
        LruFileCache(
            maxSize = config!!.maxCacheSize,
            cacheDir = File("${context!!.cacheDir}/downloader-cache")
        )
    }

    private val fileDownloader by lazy { HttpFileDownloader() }

    fun build(): FileDownloaderSdk {

        requireNotNull(context) { "Context must be set" }
        requireNotNull(config) { "Config must be set" }

        val sdk = FileDownloaderSdkImpl(
            context = context!!,
            config = config!!,
            fileCache = cache,
            fileDownloader = fileDownloader
        )
        client?.let { sdk.setClient(it) }
        // Apply other configurations as needed
        return sdk
    }
}

fun fileDownloaderSdk(block: FileDownloaderSdkBuilder.() -> Unit): FileDownloaderSdk {
    val dsl = FileDownloaderSdkBuilder()
    dsl.block()
    return dsl.build()
}
