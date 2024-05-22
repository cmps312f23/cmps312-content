package notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CounterNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = NotificationService(context)
        service.showCounterNotification(++Counter.value)
    }
}