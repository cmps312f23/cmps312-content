package football.view

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class LifeCycleObserver(
    private val lifecycle: Lifecycle,
    private val tag: String,
    private val language: String,
    private val screenOrientation: String
) : DefaultLifecycleObserver {
    private val TAG = "LifeCycle->$tag"

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        val msg = "onCreate. Language = $language - Orientation = $screenOrientation - ${lifecycle.currentState}"
        Log.d(TAG, msg)
    }

    override fun onStart(lifecycleOwner: LifecycleOwner) {
        Log.d(TAG, "onStart - ${lifecycle.currentState}")
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
        Log.d(TAG, "\uD83E\uDD29\uD83E\uDD29 onResume \uD83E\uDD29\uD83E\uDD29 - ${lifecycle.currentState}")
    }

    override fun onPause(lifecycleOwner: LifecycleOwner) {
        Log.d(TAG, "onPause")
    }

    override fun onStop(lifecycleOwner: LifecycleOwner) {
        Log.d(TAG, "onStop")
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        Log.d(TAG, "☠☠ onDestroy ☠☠ - ${lifecycle.currentState}")
    }

    init {
        lifecycle.addObserver(this)
    }
}