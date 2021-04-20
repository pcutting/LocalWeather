package com.philipcutting.localweather.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.math.RoundingMode
import java.text.DecimalFormat


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


//Formatting for displaying to screen.
private fun Double.toScaleProcessing(precision:Int) =
    this.toBigDecimal()
        .setScale(precision, RoundingMode.HALF_UP)
        .toDouble()

fun Double.toScale(precision: Int = 2) : Double {
    return this.toScaleProcessing(precision)
}

fun Double.toScaleWithFormat(precision: Int = 2) : String {
    val formatter = DecimalFormat("#0.${"".padEnd(precision,'0')}")
    return  formatter.format(this.toScaleProcessing(precision))
}

