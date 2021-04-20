package com.philipcutting.localweather


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.philipcutting.localweather.databinding.ActivityMainBinding
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.toScale


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivityLog"

    private lateinit var currentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(
            this
        )

        val request = LocationRequest.create()
        request.apply {
            interval = 1000
            fastestInterval = 100
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED ){
            fusedLocationClient.requestLocationUpdates(
                request,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location: Location? = locationResult.lastLocation
                        if (location != null) {
                            Log.d(TAG, "onLocationResult $location")
                            WeatherRepository.setLocation(location)
                            binding.currentLocationTextview.text =
                                "${location.latitude.toScale(5)}," +
                                        " ${location.longitude.toScale(5)}"
                        }
                    }
                },
                null
            )
        } else {
            Log.d(TAG, "permissions have been denied")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }

        Log.d(TAG, "onCreate, about to call fetchLastLocation()")
        fetchLastlocation()
    }

    private fun checkPermissions(context: Context) {
        val permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED ){
            Log.d(TAG, "permissions have been denied")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }
    }


    private fun fetchLastlocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            return
        }
        val task = fusedLocationClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) currentLocation = location
            Log.d(TAG, "task OnSuccess $currentLocation")
        }
    }
}