package com.wafflestudio.snugo.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.showPermissionDialog() {
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

fun ComponentActivity.getLocationPermission() {
    val locationPermissionRequest =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            val coarseLocationPermitted =
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    false,
                )

            val fineLocationPermitted =
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    false,
                )
            val backgroundLocationPermitted =
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    false,
                )

            Log.d(
                "aaaa",
                "$coarseLocationPermitted $fineLocationPermitted $backgroundLocationPermitted",
            )

            if (fineLocationPermitted && backgroundLocationPermitted) {
                // pass
            } else {
                showPermissionDialog()
            }
        }

    val fineLocationPermissionGranted =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

    if (fineLocationPermissionGranted) {
        startService(Intent(this, LocationService::class.java))
    } else {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        ) {
            // TODO: Show UI
            Log.d("aaaa", "rationale")
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            ),
        )
    }
}
