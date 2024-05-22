package workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date

// Reporting Worker Progress
class ProgressWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        delay(2000L)
        setProgress(workDataOf(Constants.PROGRESS to 25))
        //...
        delay(2000L)
        setProgress(workDataOf(Constants.PROGRESS to 50))
        //...
        delay(2000L)

        setProgress(workDataOf(Constants.PROGRESS to 100))
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa")
        val currentDate = dateFormat.format(Date())

        val outputData = workDataOf(Constants.COMPLETED_DATE to currentDate)
        return Result.success(outputData)
    }
}