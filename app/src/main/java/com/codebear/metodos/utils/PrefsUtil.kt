package com.codebear.metodos.utils

import android.content.Context
import android.content.SharedPreferences
import com.codebear.metodos.R
import com.google.gson.Gson

enum class Step {
    MEZCLADO, AMASADO, REPOSO, MODELADO, MOLDEADO, FERMENTACION, HORNEADO, ENFRIADO, ENVASADO
}

data class DataStep(
    val startMin: String,
    val startSec: String,
    val endMin: String,
    val endSec: String,
    var totalTime: Double = 0.0,
    var name: String? = null,
)

object PrefsUtil {

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    fun saveData(context: Context, step: Step, data: DataStep) {
        val prefs = getPrefs(context)
        val editor = prefs.edit()
        data.name = step.toString()
        val gson = Gson()
        val result = gson.toJson(data)
        with(editor) {
            putString(step.toString(), result)
            commit()
            apply()
        }
    }

    fun getData(context: Context, step: Step): DataStep? {
        val prefs = getPrefs(context)
        val gson = Gson()
        val json = prefs.getString(step.toString(), null)
        return if (json != null)
            gson.fromJson(json, DataStep::class.java)
        else
            null

    }

    fun clearPrefs(context: Context) {
        val prefs = getPrefs(context)
        val editor = prefs.edit()
        with(editor) {
            clear()
            apply()
        }

    }
}