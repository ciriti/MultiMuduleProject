package com.example.posttemplate

import android.app.Application
import com.example.posttemplate.data.di.dataModule
import com.example.posttemplate.di.appModule
import com.example.posttemplate.auth.di.authModule
import com.example.posttemplate.posts.di.postsModule
import com.example.posttemplate.profile.di.profileModule
import io.github.ciriti.permissionhandler.di.permissionHandlerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule)
            modules(authModule)
            modules(profileModule)
            modules(postsModule)
            modules(appModule)
            modules(permissionHandlerModule)
        }
    }
}
