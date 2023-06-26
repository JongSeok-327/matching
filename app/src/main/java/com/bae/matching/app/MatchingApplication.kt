package com.bae.matching.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class MatchingApplication: Application()
{
    companion object {
        var DEBUG = false
    }

    override fun onCreate() {
        super.onCreate()
        DEBUG = isDebuggable(this)
    }

    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false
        val pm = context.packageManager
        try {
            val appInfo = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                pm.getApplicationInfo(context.packageName, PackageManager.ApplicationInfoFlags.of(0))
            } else {
                pm.getApplicationInfo(context.packageName, 0)
            }

            debuggable = 0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return debuggable
    }
}