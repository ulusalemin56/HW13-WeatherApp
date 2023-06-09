package com.example.hw13_weatherapp.view.homeFragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw13_weatherapp.databinding.FragmentHomeBinding
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.util.sendNotifications
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (checkGPSPermissionGranted())
                getLocationInformation()
            else
                showGPSDisabledAlertToUser()
        } else {
            getAlertDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        if (checkLocationPermission()) {
            if (checkGPSPermissionGranted())
                getLocationInformation()
            else
                showGPSDisabledAlertToUser()
        } else {
            launchRequestPermission()
        }

        initObserve()

        return binding.root
    }


    private fun initObserve() {
        viewModel.weatherData.observe(viewLifecycleOwner) {
            initRecyclerView(it)
            NotificationManagerCompat
                .from(requireContext())
                .sendNotifications(
                    "Current Weather",
                    it?.currentWeather?.temperature.toString(),
                    requireContext()
                )
        }
    }

    private fun initRecyclerView(weatherResponse: WeatherResponse?) {

        val adapter = weatherResponse?.let { weatherRes ->
            WeatherDataAdapter(weatherRes) { position ->
                val time = weatherRes.daily?.time?.get(position) ?: ""

                val maxTemp: Float =
                    weatherRes.daily?.apparentTemperatureMax?.get(position)?.toFloat() ?: 0.0F

                val iconValue = weatherRes.daily?.weathercode?.get(position) ?: -1

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDeatilFragment(
                        time,
                        maxTemp,
                        iconValue
                    )
                )
            }
        }

        binding.recyclerView.adapter = adapter
    }

    private fun getLocationInformation() {
        if (checkLocationPermission() && checkGPSPermissionGranted()) {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
                val lat = location.latitude
                val longt = location.longitude
                viewModel.getCurrentLocationData(lat, longt)
            }
        } else {
            showToastMessage("A random location was shown because location permission was not granted.")
            viewModel.initLogicData()
        }
    }

    private fun checkLocationPermission() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun checkGPSPermissionGranted(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun launchRequestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun getAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.run {
            setTitle("Location Permission Denied!")
            setMessage("We need your location permission to retrieve weather information. Please enable location permission from the settings.")

            setPositiveButton("Yes") { _, _ ->
                launchRequestPermission()
            }

            setNegativeButton("Cancel") { dialog, _ ->
                getLocationInformation()
                dialog.cancel()
            }

            show()
        }

    }

    private fun showGPSDisabledAlertToUser() {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Enable GPS") { _, _ ->
                val callGPSSettingIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                requireActivity().startActivity(callGPSSettingIntent)
                requireActivity().finish()
            }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            getLocationInformation()
            dialog.cancel()
        }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun showToastMessage(message : String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}