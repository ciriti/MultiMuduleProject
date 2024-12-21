package io.github.ciriti.permissionhandler.di

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import io.github.ciriti.permissionhandler.PermissionHandler
import io.github.ciriti.permissionhandler.create
import org.koin.dsl.module

val permissionHandlerModule = module {
    single<PermissionHandler> { (context: Context, launcher: ActivityResultLauncher<Array<String>>) ->
        PermissionHandler.create(context, launcher)
    }
}
