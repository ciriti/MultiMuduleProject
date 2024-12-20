package io.github.ciriti.sdk.internal.downloader

internal interface FileDownloader {
    suspend fun downloadFile(url: String): ByteArray?
}
