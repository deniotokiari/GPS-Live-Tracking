package by.deniotokiari.gpslivetracking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import by.deniotokiari.gpslivetracking.extension.kodein
import org.kodein.di.generic.instance

class LocationService : LifecycleService() {

    private val locationLiveData: LiveData<Location> by kodein.instance()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("LocationService")
            .setContentText("Find location")
            .setSmallIcon(R.drawable.ic_marker_current)
            .build()

        startForeground(1, notification)

        locationLiveData.observe(this, Observer {
            val notification: Notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("LocationService")
                .setSmallIcon(R.drawable.ic_marker_current)
                .setContentText("Location: ${it.longitude}, ${it.latitude}")
                .build()

            NotificationManagerCompat.from(this).notify(1, notification)
        })

        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

}