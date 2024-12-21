package io.github.ciriti.permissionhandler

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

internal class PermissionHandlerImpl(
    private val context: Context,
    private val activityResultLauncher: ActivityResultLauncher<Array<String>>
) : PermissionHandler {

    override fun checkAndRequestPermissions(permissions: List<String>) {
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
        if (permissionsToRequest.isNotEmpty()) {
            activityResultLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

fun PermissionHandler.Companion.create(
    context: Context,
    activityResult: ActivityResultLauncher<Array<String>>
): PermissionHandler = PermissionHandlerImpl(context, activityResult)
