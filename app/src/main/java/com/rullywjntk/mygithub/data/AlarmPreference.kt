package com.rullywjntk.mygithub.data

import android.content.Context

class AlarmPreference(context: Context) {

    private val preference = context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE)

    fun setAlarm(value: Alarm){
        val edit = preference.edit()
        edit.putBoolean(ALARM, value.isChecked)
        edit.apply()
    }

    fun getAlarm(): Alarm{
        val get = Alarm()
        get.isChecked = preference.getBoolean(ALARM, false)
        return get
    }

    companion object {
        const val NAME_PREFERENCE = "name_preference"
        private const val ALARM = "isChecked"
    }
}