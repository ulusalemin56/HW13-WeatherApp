package com.example.hw13_weatherapp.view.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw13_weatherapp.databinding.FragmentHomeBinding
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.util.sendNotifications
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    //private lateinit var viewModel: HomeViewModel

    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

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

}