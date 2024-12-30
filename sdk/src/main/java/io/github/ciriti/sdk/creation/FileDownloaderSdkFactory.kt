import io.github.ciriti.sdk.internal.download.FileDownloader
import io.github.ciriti.sdk.internal.download.HttpFileDownloader
import io.github.ciriti.sdk.internal.sdk.SdkEventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal fun FileDownloader.Companion.create(
    sdkEventFlow: SdkEventFlow,
    okHttpClient: okhttp3.OkHttpClient = okhttp3.OkHttpClient(),
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): FileDownloader = HttpFileDownloader(sdkEventFlow, okHttpClient, ioDispatcher)
