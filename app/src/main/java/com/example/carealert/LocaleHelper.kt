package com.example.carealert

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    fun setLocale(
        context: Context
    ): Context {

        val prefs =
            context.getSharedPreferences(
                "CareAlertPrefs",
                Context.MODE_PRIVATE
            )

        val language =
            prefs.getString(
                "language",
                "en"
            ) ?: "en"

        val locale =
            Locale(language)

        Locale.setDefault(locale)

        val configuration =
            Configuration(
                context.resources.configuration
            )

        configuration.setLocale(locale)

        return context.createConfigurationContext(
            configuration
        )
    }
}