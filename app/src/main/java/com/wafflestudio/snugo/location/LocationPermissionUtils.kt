package com.wafflestudio.snugo.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun ComponentActivity.getLocationPermissions() {
    val requiredPermissions =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        )

    val permissionRequest =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            val allPermissionGranted =
                requiredPermissions.all {
                    permissions.getOrDefault(it, false)
                }

            if (allPermissionGranted.not()) {
                showPermissionDialog()
            }
        }

    val allPermissionGranted =
        requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

    if (allPermissionGranted.not()) {
        val shouldShowRationale =
            requiredPermissions.any {
                ActivityCompat.shouldShowRequestPermissionRationale(this, it)
            }

        if (shouldShowRationale) {
            // TODO: Show UI
        } else {
            permissionRequest.launch(requiredPermissions)
        }
    }
}

private fun Context.showPermissionDialog() {
    AlertDialog.Builder(this)
        .setMessage("이 앱을 사용하려면 위치 권한이 필요합니다. 설정에서 권한을 활성화하십시오.")
        .setPositiveButton("설정으로 이동") { _, _ ->
            val intent =
                Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                }
            startActivity(intent)
        }
        .setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        .setCancelable(false)
        .show()
}
