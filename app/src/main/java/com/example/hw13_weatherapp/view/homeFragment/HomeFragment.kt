package com.example.hw13_weatherapp.view.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.databinding.FragmentHomeBinding
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.network.WeatherApiService
import com.example.hw13_weatherapp.repo.WeatherAppRepository
import com.example.hw13_weatherapp.util.NetworkUtil

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var viewModel : HomeViewModel

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
            this,
            HomeViewModelFactory(weatherAppRepository),
        )[HomeViewModel::class.java]


        if (viewModel.weatherData.value == null) {
            viewModel.fetchData()
        }


        initObserve()


        return binding.root
    }

    private fun initObserve(){
        viewModel.weatherData.observe(viewLifecycleOwner){
            initRecyclerView(it)
        }
    }

    private fun initRecyclerView(weatherResponse : WeatherResponse?) {

        val adapter = weatherResponse?.let { weatherRes ->
            WeatherDataAdapter(weatherRes)
        }

        binding.recyclerView.adapter = adapter
    }
}