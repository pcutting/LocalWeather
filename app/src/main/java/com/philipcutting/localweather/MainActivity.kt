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
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.philipcutting.localweather.databinding.ActivityMainBinding
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.showToast
import com.philipcutting.localweather.utilities.toScale
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivityLog"

    private lateinit var currentLocation: Location

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewModel : CurrentWeatherViewModel by viewModels()

        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        checkPermissions(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(
            this
        )

        val request = LocationRequest.create()
        //Set very short for testing.
        request.apply {
            interval = 6000  // Should be like 15 min updates, not 6 second.
            fastestInterval = 1000  //Can't  be faster than 1 min, api restrictions.
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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
                            Log.d(TAG, "onLocationResult location from repo before: ${WeatherRepository.getLocation()}")
                            WeatherRepository.setLocation(location)
                            viewModel.getCurrentWeather()
                            Log.d(TAG, "onLocationResult location from repo after update: ${WeatherRepository.getLocation()}")
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
                0
            )
        }

        Log.d(TAG, "onCreate, about to call fetchLastLocation()")
        fetchLastLocation()
    }

    private fun checkPermissions(context: Context) {
        val permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED ){
            Log.d(TAG, "permissions have not been granted. requestiong them.")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )

            if (permission != PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "checkPermissions() user denied location permission again.")
                context.showToast("""This app cannot be used without location permission.  
                    |Please go into location settings and grant permissions for Local Weather. """.trimMargin())
            } else {
                Log.d(TAG, "checkPermissions() user has granted location permissions")
            }
        } else {
            // log permission present:
            Log.d(TAG, "checkPermissions() has permission")
        }
    }


    private fun fetchLastLocation() {
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
            if (location != null) {
                currentLocation = location
                WeatherRepository.setLocation(location)
                viewModel.getCurrentWeather()
                binding.currentLocationTextview.text = "${location.latitude.toScale(7)}," +
                        " ${location.longitude.toScale(7)}"
            }
            Log.d(TAG, "task OnSuccess $location ?: loading...")
        }
    }
}