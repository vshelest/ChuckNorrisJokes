package com.training.chucknorrisjokessearch.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.chucknorrisjokessearch.databinding.ActivityMainBinding
import com.training.chucknorrisjokessearch.presentation.adapter.JokesAdapter
import com.training.chucknorrisjokessearch.presentation.viewmodel.implementation.JokesViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: JokesViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val adapter = JokesAdapter(viewModel.jokesFlow)
        val layoutManager = LinearLayoutManager(this)
        binding.tilSearchString.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.setNewSearchString(
                binding.tilSearchString.editText?.text.toString()
            )
        }
        binding.rvJokes.adapter = adapter
        binding.rvJokes.layoutManager = layoutManager

        viewModel.errorFlow.onEach {
            if (it.isNotEmpty()) {
                //TODO: replace hardcoded error String value with actual one
                Toast.makeText(this, "Can't load jokes", Toast.LENGTH_SHORT).show()
            }
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}