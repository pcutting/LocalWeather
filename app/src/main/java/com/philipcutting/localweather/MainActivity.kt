package com.philipcutting.localweather

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.philipcutting.localweather.databinding.ActivityMainBinding
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.CurrentLocationComponent
import com.philipcutting.localweather.utilities.hasPermission
import com.philipcutting.localweather.utilities.showToast


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"

    private lateinit var currentLocationComponent: CurrentLocationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //converted to DataBinding lifecycleOwner
        
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.lifecycleOwner = this

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.lifecycleOwner = this


        currentLocationComponent = CurrentLocationComponent(this,
            {
                WeatherRepository.setLocation(it)
            },
            {
                showToast("onCreate: $it")
            }
        )
        lifecycle.addObserver(currentLocationComponent)

        checkLocationPermission()
    }


    private fun checkLocationPermission() {
        if(hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getCurrentLocation()
        } else {
            locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    private fun getCurrentLocation() {
        showToast("Loading location")
        currentLocationComponent.getCurrentLocation()
    }


    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts
            .RequestMultiplePermissions())
        {
            if (it.values.contains(false)) {
                val toast = Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG)
                toast.show()
            } else {
                getCurrentLocation()
            }
        }

    private fun checkShouldShowSettingsDialog() {
        if(!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",
                BuildConfig.APPLICATION_ID,
                null
            )
            intent.data = uri
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            showToast("Allow location permission from settings")
        }
    }










}