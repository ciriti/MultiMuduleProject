package io.github.ciriti.sdk.internal.download

import io.github.ciriti.sdk.api.SdkClient

internal interface FileDownloader {
    fun setClient(client: SdkClient)
    fun clearClient()
    suspend fun downloadFile(url: String): Result<ByteArray>

    companion object
}
