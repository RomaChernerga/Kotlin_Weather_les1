package com.example.myweather_app.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.myweather_app.R
import com.example.myweather_app.viewModel.MainViewModel
import com.example.myweather_app.databinding.MainFragmentBinding
import com.example.myweather_app.model.Weather
import com.example.myweather_app.viewModel.AppState
import java.io.IOException
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null //временная
    private val binding get() = _binding!!  // перопределяем геттер, !!-> это асерт, т.е. если он будет пустой, то будет ошибка
    private val adapter = MainAdapter()  // создаем переменную адаптер
    private var isRussian = true

    private val viewModel: MainViewModel by lazy {  // делаем неизменяемой и добавляем делегат by lazy
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION,false
            )
            val coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION,false
            )

            when {
                fineLocationGranted or coarseLocationGranted -> showLocation()
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ->
                    AlertDialog.Builder(requireActivity()).setTitle("Дай доступ")
                        .setMessage("Ну очень надо")
                        .setPositiveButton("Дать доступ") { _, _ -> requestPermission()}
                        .setNegativeButton("Не давать доступ"){ dialog, _ -> dialog.dismiss()}
                        .create()
                        .show()
                else -> requestPermission()
            }
        }

    @SuppressLint("MissingPermission")
    private fun showLocation() {

        requireActivity().startActivity(Intent(requireContext(), MapsActivity::class.java))
    }


    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(requireActivity())
        Thread {
            try {
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                requireActivity().runOnUiThread{
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Я тут был ${Date().time - location.time} Назад")
                        .setMessage(address[0].getAddressLine(0))
                        .show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermission() {
        permissionResult.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //СОЗДАЕМ АДАПТЕР ДЛЯ RECYCLER VIEW
        binding.mainRecycleView.adapter = adapter  // адаптер новый создается

        adapter.listener = MainAdapter.OnItemClick { weather ->

            val bundle = Bundle().apply {
                putParcelable("WEATHER_EXTRA", weather )
            }

            activity?.supportFragmentManager?.apply {
                    beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }

        //подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner) { state ->
            render(state)
        }

        //запросили новые данные
        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.sec)  // замена рисунка на кнопке при изменении языка
            } else {
                viewModel.getWeatherFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.src)  // замена рисунка на кнопке при изменении языка
            }
        }

        binding.historyFAB.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

        binding.locationFAB.setOnClickListener {
            requestPermission()
        }
    }

    private fun render(state: AppState) {
        when(state) {
            // формируем метод для его трех состояний
            is AppState.Success<*> -> {

                val weather: List<Weather> = state.data as List<Weather>    // приведение типов
                adapter.setWeather(weather)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(),
                    "Попробовать снова",
                    {
                         //Запросили новые данные
                    viewModel.getWeatherFromLocalStorageRus()
                    })
            }
            is AppState.Loading ->
                binding.loadingContainer.show()   //отображаем завгрузку
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null  // освобождаем байдинг чтобы небыло утечи данных
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}