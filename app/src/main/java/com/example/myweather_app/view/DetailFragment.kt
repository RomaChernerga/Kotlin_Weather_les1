package com.example.myweather_app.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.myweather_app.R
import com.example.myweather_app.databinding.DetailFragmentBinding
import com.example.myweather_app.viewModel.MainViewModel
import com.example.myweather_app.databinding.MainFragmentBinding
import com.example.myweather_app.model.*

import java.lang.NullPointerException

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?) : DetailFragment {  // возвращаем DetailFragment
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listener = Repository.OnLoadListener {
        RepositoryImpl.getWeatherFromServer()?.let { weather ->
            binding.weatherConditions.text = weather.condition
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()

//            binding.weatherImage.load("https://picsum.photos/300/300") {
//                crossfade(true)
//                placeholder(R.drawable.icon_close)
//                transformations(CircleCropTransformation())
//            }

            Log.d("Debug","https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg" )
            val request = ImageRequest.Builder(requireContext())
                .data("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                .target(binding.weatherImage)
                .build()

            ImageLoader.Builder(requireContext())
                .componentRegistry{ add(SvgDecoder(requireContext())) }
                .build()
                .enqueue(request)

        }  ?: Toast.makeText(context, "ОШИБКА", Toast.LENGTH_LONG).show()
    }
    private var _binding: DetailFragmentBinding? = null //временная
    private val binding get() = _binding!!  // перопределяем геттер, !!-> это асерт, т.е. если он будет пустой, то будет ошибка


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RepositoryImpl.addLoadedListener(listener)    // подписываемся на обновления при старте приложения

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather ->

            binding.cityName.text = weather.city.name
            binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"

            requireActivity().startService(Intent(requireContext(), MainIntentService::class.java).apply {
                putExtra("WEATHER_EXTRA", weather)
            })

//            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener  {
//                override fun onLoaded(weatherDTO: WeatherDTO) {
//                    weatherDTO.fact?.let { fact ->
//                        binding.weatherConditions.text = fact.condition
//                        binding.temperatureValue.text = fact.temp?.toString()
//                        binding.feelsLikeValue.text = fact.feels_like?.toString()
//                    }
//                }
//                override fun onFailed(throwable: Throwable) {
//                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
//                }
//            })
        }?: throw NullPointerException("Weather is null")

        //для клавиши "назад" метод
        binding.mainBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RepositoryImpl.removeLoaderListener(listener) // отписываемся от обновлений
        _binding = null  // освобождаем байдинг чтобы небыло утечи данных
    }


}