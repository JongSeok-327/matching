package com.bae.matching.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil
{
    fun applyTheme(themeColor: String) {
        when (themeColor) {
            THEME_LIGHT_MODE -> {
                // Light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            THEME_DARK_MODE -> {
                // Dark
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                // Above Android 10
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // System default
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                // Below Android 10
                else {
                    // Set by battery saver
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}