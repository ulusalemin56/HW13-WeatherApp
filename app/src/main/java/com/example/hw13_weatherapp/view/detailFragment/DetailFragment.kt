package com.example.hw13_weatherapp.view.detailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hw13_weatherapp.databinding.FragmentDeatilBinding
import com.example.hw13_weatherapp.util.addCelcius

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDeatilBinding

    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeatilBinding.inflate(inflater)

        binding.apply {
            tvTitle.text = args.time

            ivWeatherIcon.setImageResource(args.iconValue)

            tvTemperature.text = args.maxTemp.toString().addCelcius()
        }

        return binding.root
    }

}