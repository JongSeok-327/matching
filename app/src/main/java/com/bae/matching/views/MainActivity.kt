package com.bae.matching.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bae.matching.R
import com.bae.matching.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
            val navController = navHostFragment.navController

            navigation.setupWithNavController(navController)
        }
    }
}