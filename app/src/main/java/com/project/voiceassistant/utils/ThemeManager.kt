package com.project.voiceassistant.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
    }
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _themeFlow = MutableStateFlow(isSystemDarkTheme())
    val themeFlow: StateFlow<Boolean> = _themeFlow

    init {
        _themeFlow.value = prefs.getBoolean(KEY_IS_DARK_THEME, isSystemDarkTheme())
    }

    fun getTheme(defaultIsDark: Boolean): Boolean {
        return prefs.getBoolean(KEY_IS_DARK_THEME, defaultIsDark)
    }

    fun saveTheme(isDark: Boolean) {
        prefs.edit { putBoolean(KEY_IS_DARK_THEME, isDark) }
        _themeFlow.value = isDark
    }

    private fun isSystemDarkTheme(): Boolean {
        return true
    }
}