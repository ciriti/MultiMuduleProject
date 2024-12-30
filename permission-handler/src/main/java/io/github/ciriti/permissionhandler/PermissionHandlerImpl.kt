package io.github.ciriti.permissionhandler

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

internal class PermissionHandlerImpl(
    context: Context,
    activityResultLauncher: ActivityResultLauncher<Array<String>>
) : PermissionHandler {

    private val ctx: WeakReference<Context> = WeakReference(context)
    private val actResultLauncher: WeakReference<ActivityResultLauncher<Array<String>>> =
        WeakReference(activityResultLauncher)

    override fun checkAndRequestPermissions(permissions: List<String>) {
        val permissionsToRequest = permissions.filter {
            ctx.get()?.let { context ->
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            } ?: false
        }
        if (permissionsToRequest.isNotEmpty()) {
            actResultLauncher.get()?.launch(permissionsToRequest.toTypedArray())
        }
    }

}

fun PermissionHandler.Companion.create(
    context: Context,
    activityResult: ActivityResultLauncher<Array<String>>
): PermissionHandler = PermissionHandlerImpl(context, activityResult)
