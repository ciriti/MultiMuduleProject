package io.github.ciriti.sdk.di

import androidx.lifecycle.LifecycleOwner
import io.github.ciriti.sdk.api.SdkClient
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.config.FileDownloaderOption
import io.github.ciriti.sdk.creation.fileDownloaderSdk
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sdkModule = module {

    single {
        FileDownloaderConfig(
            options = listOf(
                FileDownloaderOption.Option1,
                FileDownloaderOption.Option2,
                FileDownloaderOption.Option3
            ),
            fileUrls = listOf(
                "https://repository.com/file1.png",
                "https://repository.com/file2.png",
                "https://repository.com/file3.png"
            ),
            maxCacheSize = 50 * 1024 * 1024
        )
    }

    // Repository
    single { (lifecycleOwner: LifecycleOwner) ->
        fileDownloaderSdk {
            context = androidContext()
            config = get()
            this.lifecycleOwner = lifecycleOwner
        }
    }
}
