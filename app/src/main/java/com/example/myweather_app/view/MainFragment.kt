package com.example.myweather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myweather_app.viewModel.MainViewModel
import com.example.myweather_app.databinding.MainFragmentBinding
import com.example.myweather_app.viewModel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {



    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null //временная
    private val binding get() = _binding!!  // перопределяем геттер, !!-> это асерт, т.е. если он будет пустой, то будет ошибка

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)
                    .get(MainViewModel::class.java)

        //подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner,{ state ->
            render(state)
        })

        //запросили новые данные
        viewModel.getWeather()

    }

    private fun render(state: AppState) {
        when(state) {
            // формируем метод для его трех состояний
            is AppState.Success -> {
                binding.loadingContainer.visibility = View.GONE  // если все успешно, скрываем загрузку

                binding.cityName.text = state.weather.city
                binding.temperature.text = state.weather.temperature.toString() // париводим к типу температуру

            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE)  // при наличии ошибки, выводим сообщение в снэкбар
                    .setAction("Попробовать снова") {
                 //Запросили новые данные
                    viewModel.getWeather()
                }.show()  // команда запуска
            }
            is AppState.Loading ->
                binding.loadingContainer.visibility = View.VISIBLE   //отображаем завгрузку
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