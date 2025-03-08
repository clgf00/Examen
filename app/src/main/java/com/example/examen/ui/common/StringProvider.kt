package com.example.examen.ui.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun getString(@StringRes stringResId: Int) = context.getString(stringResId)
}