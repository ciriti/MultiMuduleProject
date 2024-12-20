package io.github.ciriti.sdk.api

interface SdkClient {
    fun onProcessStart()
    fun onProcessEnd()

    fun onProgressFileDownload(fileName: String, progress: Int)
    fun onFileDownloadError(fileName: String, error: Throwable)
    fun onFileDownloadSuccess(fileName: String)
    fun onFileDownloadStart(fileName: String)
    fun onFileDownloadEnd(fileName: String)
    fun onFileDownloadCancel(fileName: String)
}
