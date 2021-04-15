package com.bagas.androidfundamental.githubuserapp2.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagas.androidfundamental.githubuserapp2.R

class NotificationSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingsPreferenceFragment()).commit()
    }
}