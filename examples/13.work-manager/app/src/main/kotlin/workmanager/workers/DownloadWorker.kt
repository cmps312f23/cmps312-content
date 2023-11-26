package workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DownloadWorker(context: Context, params:WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        return try {
            for (i in 0 ..3000) {
                Log.i("DownloadWorker", "Downloading $i")
            }
            val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa")
            val currentDate = dateFormat.format(Date())
            Log.i("DownloadWorker","Completed $currentDate")
            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }
}