package com.training.chucknorrisjokessearch.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.chucknorrisjokessearch.application.exception.RequestFailure
import com.training.chucknorrisjokessearch.databinding.ActivityMainBinding
import com.training.chucknorrisjokessearch.application.system.JokesApplication
import com.training.chucknorrisjokessearch.presentation.adapter.JokesAdapter
import com.training.chucknorrisjokessearch.presentation.viewmodel.implementation.JokesViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: JokesViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDatabase()
        initViews()
    }

    private fun initDatabase() {
        viewModel.setDatabase((application as JokesApplication).database)
    }

    private fun initViews() {
        val adapter = JokesAdapter(viewModel.jokesFlow)
        val layoutManager = LinearLayoutManager(this)
        binding.tilSearchString.editText?.doAfterTextChanged {
            viewModel.setNewSearchString(
                binding.tilSearchString.editText?.text.toString()
            )
        }
        binding.rvJokes.adapter = adapter
        binding.rvJokes.layoutManager = layoutManager

        viewModel.errorFlow.onEach {
            //TODO: replace hardcoded error String value with actual one
            when (it) {
                RequestFailure.NetworkFailure -> {
                    Toast.makeText(
                        this,
                        "Network failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                RequestFailure.IncorrectParamsFailure -> {
                    Toast.makeText(
                        this,
                        "Can't load jokes from server. Loaded from local database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.Main))

        lifecycleScope.launchWhenStarted {
            adapter.longClickedJokeFlow.collectLatest {action->
                viewModel.handleJokeAction(action)
            }
        }
    }
}