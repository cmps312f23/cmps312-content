package workmanager.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import workmanager.workers.CompressWorker
import workmanager.workers.Constants
import workmanager.workers.DownloadWorker
import workmanager.workers.FilterWorker
import workmanager.workers.ProgressWorker
import workmanager.workers.UploadWorker
import java.util.concurrent.TimeUnit

class WorkerViewModel(val appContext: Application) : AndroidViewModel(appContext) {

    private val _workInfoFlow: MutableStateFlow<WorkInfo?> = MutableStateFlow(null)
    val workInfoFlow: StateFlow<WorkInfo?> = _workInfoFlow.asStateFlow()

    public fun setOneTimeWorkRequest(onJobStateChange: (String) -> Unit) {
        val workManager = WorkManager.getInstance(appContext)

        val inputData = workDataOf(Constants.COUNT_VALUE to 125)

        val constraints = Constraints.Builder()
            .setRequiresDeviceIdle(true)
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //1000, 2000, 3000
        val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .addTag(Constants.UPLOAD_TAG)
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                1000,
                TimeUnit.SECONDS
            )
            .build()

        val filterRequest = OneTimeWorkRequestBuilder<FilterWorker>().build()
        val compressRequest = OneTimeWorkRequestBuilder<CompressWorker>().build()
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>().build()
        //WorkManager.getInstance(applicationContext).enqueue(downloadRequest)

        val parallelWorks = listOf(downloadRequest, filterRequest)
        /* workManager.beginWith(parallelWorks)
                    .then(compressRequest)
                    .then(uploadRequest)
                    .enqueue()*/

        workManager.enqueue(uploadRequest)
        val workInfo = workManager.getWorkInfosByTag(Constants.UPLOAD_TAG).get()[0]
        if (workInfo.state.isFinished) {
            println(">> Debug: ${workInfo.state}")
            onJobStateChange("${workInfo.state}")

            val date = workInfo.outputData.getString(Constants.COMPLETED_DATE).orEmpty()
            onJobStateChange("Completed at $date")
        }
    }

    public suspend fun setProgressWorker(onJobStateChange: (String) -> Unit) {
        // Observing Worker Progress
        val request = OneTimeWorkRequestBuilder<ProgressWorker>().build()
        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueue(request)

        workManager.getWorkInfoByIdFlow(request.id).collect { workInfo ->
            val progressValue = workInfo.progress.getInt(Constants.PROGRESS, 0)
            _workInfoFlow.emit(workInfo)
            onJobStateChange("$progressValue % complete")

            if (workInfo.state.isFinished) {
                val date = workInfo.outputData.getString(Constants.COMPLETED_DATE).orEmpty()
                onJobStateChange("Completed at $date")
            }
        }
    }

    public fun setPeriodicWorkRequest(context: Context) {
        // Create a periodic work request with 30 mins as repeat interval
        val repeatInterval = 30L
        val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(repeatInterval, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }

    private fun getWorkerConstraints(): Constraints {
        return Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(false)
            .setRequiresStorageNotLow(false)
            .build()
    }
}

    /*
    class MyApp: Application() {
        override fun onCreate() {
            super.onCreate()
            val backupWorkRequest = PeriodicWorkRequestBuilder<BackupWorker>(8, TimeUnit.HOURS)
                                            .build()
            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                    "BackupWork",
                    ExistingPeriodicWorkPolicy.KEEP,
                    backupWorkRequest)

            val workManager = WorkManager.getInstance(applicationContext)
            workManager.getWorkInfosForUniqueWorkLiveData("BackupWork")
        }
    }
    */

    /*
    val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInitialDelay(10, TimeUnit.MINUTES)
            .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS)
            .setInputData(workDataOf(KEY_IMAGE_URI to imageUriString))
            .setConstraints(constraints)
            .addTag("cleanup")
            .build()
    val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

     */

    /*
    WorkManager.getInstance(applicationContext).getWorkInfosByTag("Sync")
    WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWork("MyUniqueName")
    WorkManager.getInstance(applicationContext).getWorkInfoById(uploadRequest.id)
    WorkManager.getInstance(applicationContext).getWorkInfosByTag(TAG_SAVE)
    WorkManager.getInstance(applicationContext).getWorkInfosByTagLiveData(TAG_SAVE)

    WorkManager.getInstance(applicationContext).cancelAllWork()
    */

//Source: developer.android.com/topic/libraries/architecture/workmanager/how-to/cancel-stop-work
    /*
    val saveImageWorkRequest = OneTimeWorkRequestBuilder<SaveImageWorker>()
            .addTag(TAG_SAVE)
            .build()

    WorkManager.getInstance(applicationContext).cancelWorkById(saveImageWorkRequest.id)
    WorkManager.getInstance(applicationContext).cancelAllWorkByTag(TAG_SAVE) */