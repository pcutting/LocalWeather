package com.philipcutting.localweather

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.philipcutting.localweather.databinding.ActivityMainBinding
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.showCoordinate
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivityLog"
    private lateinit var currentLocation: Location
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var request : LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(
            this
        )
        request = LocationRequest.create()
        //Set very short for testing.
        request.apply {
            interval = 18000  // Should be like 15 min updates, not 6 second.
            fastestInterval = 6000  //Can't  be faster than 1 min, api restrictions.
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        checkPermissions(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ){
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    if (it!= null) {
                        Log.d(TAG, "used LastLocation ${it.showCoordinate(6)}")
                        WeatherRepository.setLocation(it)
                        viewModel.getCurrentWeather()
                        binding.currentLocationTextview.text = it.showCoordinate(5)
                    } else {
                        Log.d(TAG, "lastLocation didn't have any valid location: $it")
                    }
                }
        } else {
            Log.d(TAG, "permissions have been denied")
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
        getRefreshLocationService(this)
    }

    fun getRefreshLocationService(context: Context){
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ){
            fusedLocationClient.requestLocationUpdates(
                request,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location: Location? = locationResult.lastLocation
                        if (location != null) {
                            Log.d(TAG, "onLocationResult $location")
                            WeatherRepository.setLocation(location)
                            viewModel.getCurrentWeather()
                            binding.currentLocationTextview.text = location.showCoordinate(5)
                        }
                    }
                }, null
            )
        } else {
            Log.d(TAG, "permissions have been denied")
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun checkPermissions(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ){
            Log.d(TAG, "permissions have not been granted. requesting them.")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ){
            Log.d(TAG, "permissions have not been granted. requesting them.")
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fetchLastLocation()
    }

    private fun fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        val task = fusedLocationClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                WeatherRepository.setLocation(location)
                viewModel.getCurrentWeather()
                binding.currentLocationTextview.text = location.showCoordinate(7)
            }
            Log.d(TAG, "task OnSuccess $location")
        }
    }
}