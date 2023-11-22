package qu.lingosnacks.view.games.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
// for using this bottom sheet,
// you have to add a "boolean state variable named isSheetOpen" to keep track of the state closed/open of the sheet like:
// val isSheetOpen = remember { mutableStateOf(false) }
// then wrap the BottomSheet composable with an if statement like so: if(isSheetOpen) { BottomSheet(...) }
// and you have to create the sheetState using "val sheetState = rememberModalBottomSheetState()"
fun BottomSheet(sheetState : SheetState,
                isSheetOpen : Boolean,
                updateShowBottomSheet : (Boolean) -> Unit,
                content : @Composable () -> Unit) {
    // bottom sheet component below
    ModalBottomSheet(
        // state of the bottom sheet
        sheetState = sheetState,
        // function
        onDismissRequest = { updateShowBottomSheet(false) },
    )
    {
        content()
    }
}