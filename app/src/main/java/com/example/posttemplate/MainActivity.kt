package com.example.posttemplate

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.posttemplate.auth.data.repository.AuthRepository
import com.example.posttemplate.ui.navigation.Route
import com.example.posttemplate.ui.navigation.SetupNavGraph
import com.example.posttemplate.ui.theme.AppTheme
import io.github.ciriti.permissionhandler.PermissionHandler
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.api.setClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val auth by inject<AuthRepository>()

    private val sdk by inject<FileDownloaderSdk>{ parametersOf(this)}

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach { (permission, isGranted) ->
                if (isGranted) {
                    Log.d("Permissions", "$permission granted")
                } else {
                    Log.d("Permissions", "$permission denied")
                }
            }
        }

    private val permissionHandler: PermissionHandler by inject {
        parametersOf(
            this,
            requestPermissionLauncher
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            permissions.forEach { (permission, isGranted) ->
//                if (isGranted) {
//                    Log.d("Permissions", "$permission granted")
//                } else {
//                    Log.d("Permissions", "$permission denied")
//                }
//            }
//        }

        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        enableEdgeToEdge()
        val startDestination =
            if (auth.isUserSignedIn()) Route.Posts.route else Route.Authentication.route

        // test
        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = startDestination,
                    navController = navController
                )
            }
        }






//        sdk.setClient {
//            onProgressFileDownload { fileName, progress ->
//                Log.i("FileDownloaderSdk", "Downloading $fileName: $progress%")
//            }
//            onFileDownloadEnd { fileName ->
//                Log.i("FileDownloaderSdk", "Download of $fileName completed")
//            }
//        }

        sdk.loadFiles()

        lifecycleScope.launch(Dispatchers.Default) {
            sdk.eventFlow.collect {
                Log.i("FileDownloaderSdk", it.toString())
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val requiredPermissions = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        permissionHandler.checkAndRequestPermissions(requiredPermissions)
    }
    private fun resetPermissions(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.clearApplicationUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        requestPermissionLauncher.unregister()
//        resetPermissions(this)
    }

}
