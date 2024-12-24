package io.github.ciriti.sdk.api

class DslSdkClient : SdkClient {

    private var onProcessStart: (() -> Unit)? = null
    private var onProcessEnd: (() -> Unit)? = null
    private var onProgressFileDownload: ((fileName: String, progress: Int) -> Unit)? = null
    private var onFileDownloadError: ((fileName: String, error: Throwable) -> Unit)? = null
    private var onFileDownloadCancel: ((fileName: String) -> Unit)? = null
    private var onFileDownloadEnd: ((fileName: String) -> Unit)? = null
    private var onFileDownloadStart: ((fileName: String) -> Unit)? = null
    private var onFileDownloadSuccess: ((fileName: String) -> Unit)? = null

    fun onProcessStart(block: () -> Unit) {
        onProcessStart = block
    }

    override fun onProcessStart() {
        onProcessStart?.invoke()
    }

    fun onProcessEnd(block: () -> Unit) {
        onProcessEnd = block
    }

    override fun onProcessEnd() {
        onProcessEnd?.invoke()
    }

    fun onProgressFileDownload(block: (fileName: String, progress: Int) -> Unit) {
        onProgressFileDownload = block
    }

    override fun onProgressFileDownload(fileName: String, progress: Int) {
        onProgressFileDownload?.invoke(fileName, progress)
    }

    fun onFileDownloadError(block: (fileName: String, error: Throwable) -> Unit) {
        onFileDownloadError = block
    }

    override fun onFileDownloadError(fileName: String, error: Throwable) {
        onFileDownloadError?.invoke(fileName, error)
    }

    fun onFileDownloadSuccess(block: (fileName: String) -> Unit) {
        onFileDownloadSuccess = block
    }

    override fun onFileDownloadSuccess(fileName: String) {
        onFileDownloadSuccess?.invoke(fileName)
    }

    fun onFileDownloadStart(block: (fileName: String) -> Unit) {
        onFileDownloadStart = block
    }

    override fun onFileDownloadStart(fileName: String) {
        onFileDownloadStart?.invoke(fileName)
    }

    fun onFileDownloadEnd(block: (fileName: String) -> Unit) {
        onFileDownloadEnd = block
    }

    override fun onFileDownloadEnd(fileName: String) {
        onFileDownloadEnd?.invoke(fileName)
    }

    fun onFileDownloadCancel(block: (fileName: String) -> Unit) {
        onFileDownloadCancel = block
    }

    override fun onFileDownloadCancel(fileName: String) {
        onFileDownloadCancel?.invoke(fileName)
    }
}

fun FileDownloaderSdk.setClient(block: DslSdkClient.() -> Unit) {
    val dsl = DslSdkClient()
    dsl.block()
    setClient(dsl)
}
