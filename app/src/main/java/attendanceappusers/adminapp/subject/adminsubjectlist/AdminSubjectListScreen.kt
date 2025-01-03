package attendanceappusers.adminapp.subject.adminsubjectlist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.subject.SelectedSubject
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminSubject
import com.attendanceapp2.screenuniversalcomponents.subjectuicomponents.SubjectCard
import java.time.Month

@Composable
fun AdminSubjectListScreen (
    navController: NavController,
    viewModel : AdminSubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val subjects by viewModel.subjects.collectAsState()
    val currentMonth = Month.entries[java.time.LocalDate.now().monthValue - 1]
    val schoolYearText = when (currentMonth) {
        in Month.SEPTEMBER..Month.DECEMBER -> {
            "S.Y. ${java.time.LocalDate.now().year} - ${java.time.LocalDate.now().year + 1}"
        }
        in Month.JANUARY..Month.JUNE -> {
            "S.Y. ${java.time.LocalDate.now().year - 1} - ${java.time.LocalDate.now().year}"
        }
        else -> {
            "Summer Class"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Subjects",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            schoolYearText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.width(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(subjects) { subject ->
                SubjectCard(subject = subject) {
                    SelectedSubjectHolder.setSelectedSubject(
                        SelectedSubject(
                            subject.id,
                            subject.code,
                            subject.name,
                            subject.room,
                            subject.facultyName,
                            subject.subjectStatus,
                            subject.joinCode
                        )
                    )
                    Log.d("SelectedSubject", "Selected subject: ${SelectedSubjectHolder.getSelectedSubject()}")
                    navController.navigate(  AdminSubject.SubjectScreen.name )
                }
            }
        }
    }
}