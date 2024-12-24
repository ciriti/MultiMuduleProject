package io.github.ciriti.sdk.creation

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import create
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.cache.LruFileCache
import io.github.ciriti.sdk.internal.downloader.FileDownloader
import io.github.ciriti.sdk.internal.sdk.FileDownloaderSdkImpl
import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import io.github.ciriti.sdk.internal.sdk.create
import java.io.File

class FileDownloaderSdkBuilder {
    var client: SdkClient? = null
    var config: FileDownloaderConfig? = null
    var context: Context? = null
    var lifecycleOwner: LifecycleOwner? = null

    fun build(): FileDownloaderSdk {
        requireNotNull(context) { "Context must be set" }
        requireNotNull(config) { "Config must be set" }
        requireNotNull(lifecycleOwner) { "LifecycleOwner must be set" }

        val sdkEventFlow = SdkEventFlow.create()
        val fileDownloader = FileDownloader.create(sdkEventFlow)
        val cache: FileCache by lazy {
            LruFileCache(
                maxSize = config!!.maxCacheSize,
                cacheDir = File("${context!!.cacheDir}/downloader-cache")
            )
        }

        val sdk = FileDownloaderSdkImpl(
            config = config!!,
            fileCache = cache,
            fileDownloader = fileDownloader,
            sdkEventFlow = sdkEventFlow,
            lifecycleOwner = lifecycleOwner!!
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
