package com.example.myweather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myweather_app.R
import com.example.myweather_app.viewModel.MainViewModel
import com.example.myweather_app.databinding.MainFragmentBinding
import com.example.myweather_app.model.Weather
import com.example.myweather_app.viewModel.AppState
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null //временная
    private val binding get() = _binding!!  // перопределяем геттер, !!-> это асерт, т.е. если он будет пустой, то будет ошибка
    private val adapter = MainAdapter()  // создаем переменную адаптер
    private var isRussian = true

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
        //СОЗДАЕМ АДАПТЕР ДЛЯ RECYCLER VIEW
        binding.mainRecycleView.adapter = adapter  // адаптер новый создается

        adapter.listener = MainAdapter.OnItemClick { weather ->
            val bundle = Bundle()
            bundle.putParcelable("WEATHER_EXTRA", weather )
            requireActivity()
                .supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }
//        binding.mainRecycleView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner,{ state ->
            render(state)
        })

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
    }

    private fun render(state: AppState) {
        when(state) {
            // формируем метод для его трех состояний
            is AppState.Success<*> -> {

                val weather: List<Weather> = state.data as List<Weather>    // приведение типов
                adapter.setWeather(weather)
                binding.loadingContainer.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE)  // при наличии ошибки, выводим сообщение в снэкбар
                    .setAction("Попробовать снова") {
                 //Запросили новые данные
                    viewModel.getWeatherFromLocalStorageRus()
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