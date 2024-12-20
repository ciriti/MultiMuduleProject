package io.github.ciriti.sdk.internal

internal interface FileDownloader {
    suspend fun downloadFile(url: String): ByteArray?
}
