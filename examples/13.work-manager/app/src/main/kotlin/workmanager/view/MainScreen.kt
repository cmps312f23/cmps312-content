package workmanager.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkInfo
import kotlinx.coroutines.launch
import notification.Counter
import notification.CounterNotificationService
import workmanager.viewmodel.WorkerViewModel
import workmanager.workers.Constants

@Composable
fun MainScreen() {
    val notificationService = CounterNotificationService(LocalContext.current)

    //val context = LocalContext.current
    var jobState by remember { mutableStateOf("") }

    val workerViewModel = viewModel<WorkerViewModel>()
    val coroutineScope = rememberCoroutineScope()

    val workInfoFlow = workerViewModel.workInfoFlow.collectAsStateWithLifecycle().value

    fun onStartTaskClicked () {
       workerViewModel.setOneTimeWorkRequest(onJobStateChange = {
            jobState = it
        })
    }
    fun onStartTaskWithProgressClicked () {
        coroutineScope.launch {
            workerViewModel.setProgressWorker(onJobStateChange = {
                jobState = it
            })
            //setPeriodicWorkRequest()
        }
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = jobState)

            workInfoFlow?.let {
                var progressValue = it.progress.getInt(Constants.PROGRESS, 0)
                if (it.state == WorkInfo.State.SUCCEEDED)
                    progressValue = 100

                val msg = "${it.state.name} - $progressValue % complete"
                Text(text = msg)
            }

            Button(onClick = {
                onStartTaskClicked()
            }) {
                Text(text = "Start Task")
            }

            Button(onClick = {
                onStartTaskWithProgressClicked()
            }) {
                Text(text = "Start Task with progress")
            }

            Button(onClick = {
                notificationService.showNotification(Counter.value)
            }) {
                Text(text = "Show notification")
            }
        }
    }
}

