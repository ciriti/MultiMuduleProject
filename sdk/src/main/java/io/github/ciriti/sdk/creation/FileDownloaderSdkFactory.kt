import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.internal.FileDownloaderSdkImpl

fun FileDownloaderSdk.Companion.create(): FileDownloaderSdk {
    return FileDownloaderSdkImpl()
}
