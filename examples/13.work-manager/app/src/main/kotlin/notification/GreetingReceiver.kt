package notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/*
A BroadcastReceiver is a component that allows your app to receive
system-wide or app-specific broadcast messages or intents.
It's a fundamental part of the Android system for inter-component communication.
 */
class GreetingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Handle received broadcast
        val greeting = intent.getStringExtra("GREETING").orEmpty()
        Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
        // Process the received data
        val service = NotificationService(context)
        service.showNotification(greeting)
    }
}