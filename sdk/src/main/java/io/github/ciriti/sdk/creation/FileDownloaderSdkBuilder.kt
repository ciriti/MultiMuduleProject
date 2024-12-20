package io.github.ciriti.sdk.creation

import android.content.Context
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.FileDownloaderSdkImpl

class FileDownloaderSdkBuilder {
    var client: SdkClient? = null
    var config: FileDownloaderConfig? = null
    var context: Context? = null

    fun build(): FileDownloaderSdk {

        requireNotNull(context) { "Context must be set" }
        requireNotNull(config) { "Config must be set" }

        val sdk = FileDownloaderSdkImpl(
            context = context!!,
            config = config!!
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
