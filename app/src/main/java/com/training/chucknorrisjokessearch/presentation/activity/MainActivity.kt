package com.training.chucknorrisjokessearch.presentation.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.training.chucknorrisjokessearch.databinding.ActivityMainBinding
import com.training.chucknorrisjokessearch.presentation.viewmodel.abstraction.JokesViewModel
import com.training.chucknorrisjokessearch.presentation.viewmodel.implementation.JokesViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Delayed

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
        binding.tilSearchString.editText?.doOnTextChanged { text, start, before, count ->
            updateSearchStringDelayed()
        }
    }

    private fun updateSearchStringDelayed() {
        CoroutineScope(IO).launch {
            delay(500)
            viewModel.setNewSearchString(
                binding.tilSearchString.editText?.text.toString()
            )
        }
    }
}