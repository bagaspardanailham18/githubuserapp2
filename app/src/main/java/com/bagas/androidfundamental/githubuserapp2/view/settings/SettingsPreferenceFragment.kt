package com.bagas.androidfundamental.githubuserapp2.view.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.view.settings.AlarmHelper.cancelAlarm
import com.bagas.androidfundamental.githubuserapp2.view.settings.AlarmHelper.setRepeatingAlarm

class SettingsPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var isReminderPreference: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
        configAlarm(requireContext())
    }

    private fun init() {
        REMINDER = resources.getString(R.string.key_reminder)

        isReminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isReminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == REMINDER) {
            if (sharedPreferences != null) {
                isReminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
                configAlarm(requireContext())
            }
        }
    }

    private fun configAlarm(context: Context) {
        if (isReminderPreference.isChecked) {
            setRepeatingAlarm(requireContext(), resources.getString(R.string.daily_notif))
        } else {
            cancelAlarm(requireContext())
        }
    }

}