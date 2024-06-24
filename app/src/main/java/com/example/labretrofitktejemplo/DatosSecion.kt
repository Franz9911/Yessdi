package com.example.labretrofitktejemplo

import android.content.Context
import android.content.SharedPreferences


object DatosSecion {
    private const val PREF_NAME = "user_session"
    private const val KEY_NAME = "name"
    private const val KEY_PASSWORD = "password"
    private const val IDUSER="user_id"

    fun saveUserSession(context: Context, name: String, password: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PASSWORD, password)
        //editor.putString(IDUSER,user_id)

        editor.apply()
    }

    fun getUserSession(context: Context):  Pair<String?, String?> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = sharedPreferences.getString(KEY_NAME, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        val user_id= sharedPreferences.getString(IDUSER,null)
        return Pair(name, password)
    }

    fun clearUserSession(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.clear()
        editor.apply()
    }
}