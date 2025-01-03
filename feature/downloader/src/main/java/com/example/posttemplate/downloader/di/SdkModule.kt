package com.example.posttemplate.downloader.di

import androidx.lifecycle.LifecycleOwner
import com.example.posttemplate.downloader.ui.DownloadsEffect
import com.example.posttemplate.downloader.ui.DownloadsIntent
import com.example.posttemplate.downloader.ui.DownloadsState
import com.example.posttemplate.downloader.ui.DownloadsViewModel
import com.example.posttemplate.ui.components.BaseViewModel
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.config.FileDownloaderOption
import io.github.ciriti.sdk.creation.fileDownloaderSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
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
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk1.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk2.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk3.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk4.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk5.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk6.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk7.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk8.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk9.apk",
                "https://github.com/ciriti/data_folder/raw/refs/heads/master/TestApk10.apk",
            ),
            maxCacheSize = 100 * 1024 * 1024
        )
    }

    // Repository
    factory { (lifecycleOwner: LifecycleOwner) ->
        fileDownloaderSdk {
            context = androidContext()
            config = get()
            this.lifecycleOwner = lifecycleOwner
        }
    }

    viewModel<BaseViewModel<DownloadsState, DownloadsEffect, DownloadsIntent>> { DownloadsViewModel() }
}
