package notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import work.manager.R

class NotificationService(
    private val context: Context
) {
    /*
    Here, a NotificationManager is being initialized using the application context.
    The NotificationManager is a system service that allows the app to handle notifications.
     */
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /*
    showNotification is responsible for creating and displaying the notification
     */
    fun showNotification(message : String) {
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_info)
            .setContentTitle("Greeting")
            .setContentText(message)
            .build()

        notificationManager.notify(1, notification)
    }

    /*
    showNotification is responsible for creating and displaying the notification
     */
    fun showCounterNotification(counter: Int) {
        /*
        Intent = a message that's used to request an action from another component,
        such as an activity, or a broadcast receiver.
        Intents are the backbone of Android's inter-component communication system,
        enabling various parts of an app to work together, pass data, and trigger action
         */
        /*
        A PendingIntent is created for a Broadcast Receiver to increment the counter,
        utilizing the CounterNotificationReceiver
        */
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        /*
        A NotificationCompat.Builder is used to construct the notification.
        It sets various attributes of the notification, including icon, title, content text,
        associated intents, and an action button for incrementing the counter
        */
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_info)
            .setContentTitle("Increment counter")
            .setContentText("The count is $counter")
            .addAction(
                R.drawable.ic_increment,
                "Increment",
                incrementIntent
            )
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}