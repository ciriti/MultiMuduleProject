package io.github.ciriti.sdk.internal.downloader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class HttpFileDownloader : FileDownloader {
    override suspend fun downloadFile(url: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            // Implement file download logic
            null
        }
    }
}
