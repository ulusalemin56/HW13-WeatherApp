package com.example.hw13_weatherapp.view.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.databinding.FragmentHomeBinding
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.network.WeatherApiService
import com.example.hw13_weatherapp.repo.WeatherAppRepository
import com.example.hw13_weatherapp.util.NetworkUtil
import com.example.hw13_weatherapp.util.sendNotifications

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        val weatherApiService = WeatherApiService.create(requireContext())
        val weatherDB = WeatherDB.getInstance(requireContext())
        val hasInternet = NetworkUtil.isInternetAvailable(requireContext())
        val weatherAppRepository = WeatherAppRepository(weatherApiService, weatherDB, hasInternet)

        viewModel = ViewModelProvider(
            requireActivity(),
            HomeViewModelFactory(weatherAppRepository),
        )[HomeViewModel::class.java]

        if (viewModel.weatherData.value == null) {
            viewModel.fetchData()
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

                val iconValue = weatherRes.icons[position]

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

}