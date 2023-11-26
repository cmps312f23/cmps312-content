package workmanager.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.text.SimpleDateFormat
import java.util.Date

class UploadWorker(context: Context, params:WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(Constants.COUNT_VALUE, 0)
            for (i in 0 until count) {
                Log.i("UploadWorker", "Uploading $i")
            }

            val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa")
            val currentDate = dateFormat.format(Date())

            val outputData = workDataOf(Constants.COMPLETED_DATE to currentDate)
            Result.success(outputData)
        } catch (e: Exception) {
            Result.retry()
        }
    }
}