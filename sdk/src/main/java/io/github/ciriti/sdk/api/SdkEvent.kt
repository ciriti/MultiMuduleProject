package io.github.ciriti.sdk.api

sealed class SdkEvent {
    /**
     * Data object provides additional features like:
     * - toString,
     * - equals, and
     * - hashCode methods,
     * which are not available in a regular object.
     */
    data object ProcessStart : SdkEvent()
    data object ProcessEnd : SdkEvent()
    data class ProgressFileDownload(val fileName: String, val progress: Int) : SdkEvent()
    data class FileDownloadError(val fileName: String, val error: Throwable) : SdkEvent()
    data class FileDownloadSuccess(val fileName: String) : SdkEvent()
    data class FileDownloadStart(val fileName: String) : SdkEvent()
    data class FileDownloadEnd(val fileName: String) : SdkEvent()
    data class FileDownloadCancel(val fileName: String) : SdkEvent()
}
