package com.philipcutting.localweather.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat


fun Context.showToast(message: String) {
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
}

fun Context.hasPermission(permission: String): Boolean {
    if (permission == Manifest.permission.ACCESS_COARSE_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ){

    return true
    }

    return ActivityCompat.checkSelfPermission(this,permission) ==
            PackageManager.PERMISSION_GRANTED

}
