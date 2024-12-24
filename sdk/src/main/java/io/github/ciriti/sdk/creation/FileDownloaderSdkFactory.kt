import io.github.ciriti.sdk.internal.downloader.FileDownloader
import io.github.ciriti.sdk.internal.downloader.HttpFileDownloader
import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal fun FileDownloader.Companion.create(
    sdkEventFlow: SdkEventFlow,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): FileDownloader = HttpFileDownloader(sdkEventFlow, ioDispatcher)
