package io.github.ciriti.permissionhandler

interface PermissionHandler {
    fun checkAndRequestPermissions(permissions: List<String>)
    companion object
}
