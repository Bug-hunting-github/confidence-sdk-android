package com.spotify.confidence

import android.content.Context
import android.os.Build
import com.spotify.confidence.CommonContextKeys.APP_BUILD_KEY
import com.spotify.confidence.CommonContextKeys.APP_NAMESPACE_KEY
import com.spotify.confidence.CommonContextKeys.APP_NAME_KEY
import com.spotify.confidence.CommonContextKeys.APP_VERSION_KEY
import com.spotify.confidence.CommonContextKeys.DEVICE_MANUFACTURER_KEY
import com.spotify.confidence.CommonContextKeys.DEVICE_MODEL_KEY
import com.spotify.confidence.CommonContextKeys.DEVICE_NAME_KEY
import com.spotify.confidence.CommonContextKeys.DEVICE_TYPE_KEY
import com.spotify.confidence.CommonContextKeys.OS_NAME_KEY
import com.spotify.confidence.CommonContextKeys.OS_VERSION_KEY
import com.spotify.confidence.CommonContextKeys.SCREEN_DENSITY_KEY
import com.spotify.confidence.CommonContextKeys.SCREEN_HEIGHT_KEY
import com.spotify.confidence.CommonContextKeys.SCREEN_WIDTH_KEY

internal fun Confidence.addCommonContext(context: Context): Confidence {
    with(this) {
        // OS and OS Version
        putContext(OS_NAME_KEY, ConfidenceValue.String("Android"))
        if (Build.VERSION.RELEASE != null) {
            putContext(OS_VERSION_KEY, ConfidenceValue.String(Build.VERSION.RELEASE))
        }
        // Screen
        val displayMetrics = context.resources.displayMetrics
        putContext(
            SCREEN_DENSITY_KEY,
            ConfidenceValue.Double(displayMetrics.density.toDouble())
        )
        putContext(
            SCREEN_HEIGHT_KEY,
            ConfidenceValue.Double(displayMetrics.heightPixels.toDouble())
        )
        putContext(SCREEN_WIDTH_KEY, ConfidenceValue.Integer(displayMetrics.widthPixels))
        // Package
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        putContext(
            APP_NAME_KEY,
            ConfidenceValue.String(
                packageInfo.applicationInfo.loadLabel(packageManager).toString()
            )
        )
        putContext(APP_VERSION_KEY, ConfidenceValue.String(packageInfo.versionName))
        putContext(APP_NAMESPACE_KEY, ConfidenceValue.String(packageInfo.packageName))

        val appBuild = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toString()
        }
        putContext(APP_BUILD_KEY, ConfidenceValue.String(appBuild))

        // Device ID
        putContext(DEVICE_MANUFACTURER_KEY, ConfidenceValue.String(Build.MANUFACTURER))
        putContext(DEVICE_MODEL_KEY, ConfidenceValue.String(Build.MODEL))
        putContext(DEVICE_NAME_KEY, ConfidenceValue.String(Build.DEVICE))
        putContext(DEVICE_TYPE_KEY, ConfidenceValue.String("android"))
    }

    return this
}