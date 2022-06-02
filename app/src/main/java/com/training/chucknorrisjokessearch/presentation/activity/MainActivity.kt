package com.training.chucknorrisjokessearch.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.chucknorrisjokessearch.databinding.ActivityMainBinding
import com.training.chucknorrisjokessearch.presentation.adapter.JokesAdapter
import com.training.chucknorrisjokessearch.presentation.viewmodel.implementation.JokesViewModelImpl

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
    }
}