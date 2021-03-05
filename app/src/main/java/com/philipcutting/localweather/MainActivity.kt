package com.philipcutting.localweather


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.philipcutting.localweather.databinding.ActivityMainBinding
import com.philipcutting.localweather.models.CurrentWeather
import com.philipcutting.localweather.networking.NetworkCurrentWeather


class MainActivity : AppCompatActivity() {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var mCurrentLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private val locationRequestCode = 1000

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
       //binding.primaryFragmentForWeatherFragments.

        var currentWeather:CurrentWeather? = null

        NetworkCurrentWeather.getCurrentWeather{weather ->
            currentWeather = weather
        }
        Log.d("MainActivity", "currentWeather: ${currentWeather.toString()}")

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest?.interval = (10 * 1000).toLong() // 10 seconds
        locationRequest?.fastestInterval = (5 * 1000).toLong() // 5 seconds

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        mCurrentLocation = location
                        if (mFusedLocationClient != null) {
                            mFusedLocationClient?.removeLocationUpdates(locationCallback)
                        }
                    }
                }
            }
        }

        //Check user permission at run time
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    locationRequestCode)

        }
        else
        {
            requestLocation()
        }

        binding.buttonLocation.setOnClickListener {
            var location = getCurrentLocation()
            binding.locationTextView.text = "Updated : ${ location?.latitude.toString()}  ${location?.longitude.toString()}"
        }
    }

    //request location
    private fun requestLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mFusedLocationClient?.lastLocation?.addOnSuccessListener(this) { location ->
            if (location != null) {
                mCurrentLocation = location
            } else {
                mFusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }
    }

    //on request permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation()

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCurrentLocation(): Location? {
        return mCurrentLocation ?: null
    }
}