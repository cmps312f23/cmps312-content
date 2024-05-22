package workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FilterWorker(context: Context, params:WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        return try {

            for (i in 0 ..3000) {
                Log.i("FilterWorker", "Filtering $i")
            }

            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }
}