package attendanceappusers.facultyapp.qrscreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import kotlinx.coroutines.delay

/**
 * Composable function to display the QR code generator screen.
 *
 * @param navController The navigation controller.
 * @param qrGeneratorVM The ViewModel for QR code generation.
 * @param subjectVM The ViewModel for handling subjects.
 */
@Composable
fun QRGeneratorScreen(
    navController: NavHostController,
    qrGeneratorVM: FacultyQRGeneratorViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectVM: SubjectViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    // Get the currently selected subject from the SelectedSubjectHolder
    val selectedSubject = SelectedSubjectHolder.getSelectedSubject()

    // Mutable state to hold the generated QR code bitmap
    var qrCodeBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Fetch subjects when the screen is launched
    LaunchedEffect(key1 = true) {
        subjectVM.fetchActiveSubjectsForLoggedInUser()
        subjectVM.fetchArchivedSubjectsForLoggedInUser()
    }

    // Generate QR code and update it when selectedSubject changes
    LaunchedEffect(selectedSubject) {
        selectedSubject?.let {
            qrCodeBitmap = qrGeneratorVM.generateQrCodeBitmap(it)
        }
    }

    // Use a coroutine to update the QR code every 1 minute
    LaunchedEffect(true) {
        while (true) {
            selectedSubject?.let {
                qrCodeBitmap = qrGeneratorVM.generateQrCodeBitmap(it)
            }
            delay(1000L) // Update every seconds
        }
    }

    // Column composable to arrange children vertically
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 75.dp)
    ) {
        // Box composable to contain the QR code image
        Box(
            modifier = Modifier
                .padding(top = 70.dp, bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            // Display the QR code image if it's not null
            qrCodeBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Generated QR Code",
                    modifier = Modifier.size(400.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Display QRCode SubjectCode and SubjectName
        selectedSubject?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = it.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = it.code,
                    fontSize = 16.sp
                )
            }
        }
    }
}