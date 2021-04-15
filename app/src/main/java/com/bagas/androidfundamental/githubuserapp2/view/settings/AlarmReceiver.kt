package com.bagas.androidfundamental.githubuserapp2.view.settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bagas.androidfundamental.githubuserapp2.R
import com.bagas.androidfundamental.githubuserapp2.view.settings.AlarmHelper.showAlarmNotification

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var helper: AlarmHelper

    companion object {
        const val CHANNEL_ID = "channel_1"
        const val CHANNEL_NAME = "AlarmManager Channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = context.resources.getString(R.string.app_name)
        val message = context.resources.getString(R.string.daily_notif)
        showAlarmNotification(context, title, message)
    }
}