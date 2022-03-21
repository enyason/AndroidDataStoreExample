package com.enyason.androiddatastoreexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enyason.androiddatastoreexample.databinding.ActivityMainBinding
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadConfigs()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            viewModel.getConfigs()
        }
    }
}