package attendanceappusers.adminapp.subject.adminsubjectlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.SelectedSubject
import com.attendanceapp2.data.model.SelectedSubjectHolder
import com.attendanceapp2.navigation.approutes.admin.AdminSubject
import com.attendanceapp2.screenuniversalcomponents.subjectuicomponents.SubjectCard

@Composable
fun AdminSubjectListScreen (
    navController:NavController,
    viewModel : AdminSubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val subjects = viewModel.subjects


    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(subjects) { subject ->
                SubjectCard(
                    subject = subject,
                    onClick = {
                        SelectedSubjectHolder.setSelectedSubject(
                            SelectedSubject(
                                id = subject.id,
                                code = subject.code,
                                name = subject.name,
                                room = subject.room,
                                faculty = subject.faculty
                            )
                        )
                        Log.d("SelectedSubject", "Selected subject: ${SelectedSubjectHolder.getSelectedSubject()}")
                        navController.navigate(  AdminSubject.SubjectScreen.name )
                    }
                )
            }
        }
    }
}