package attendanceappusers.adminapp.homescreen.usermanagement.updateuser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import attendanceappusers.adminapp.homescreen.ConfirmDialog
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.data.model.Results
import com.attendanceapp2.data.model.user.UpdateUser
import com.attendanceapp2.data.model.user.UpdatingUserHolder
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.navigation.approutes.admin.AdminHomeScreen
import com.attendanceapp2.screenuniversalcomponents.attendanceuicomponents.UniversalDropDownMenu
import kotlinx.coroutines.launch

@Composable
fun UpdateUserScreen(
 navController: NavController,
 viewModel: UpdateUserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
 val coroutineScope = rememberCoroutineScope()
 var updateUser by remember { mutableStateOf(UpdatingUserHolder.getSelectedUser() ?: UpdateUser(0, "", "", "", "", "", "", "")) }
 var showDialog by remember { mutableStateOf(false) }
 var result by remember { mutableStateOf(Results.UpdateUserResult()) }

 Column(
  modifier = Modifier
   .fillMaxSize()
   .padding(16.dp),
  horizontalAlignment = Alignment.CenterHorizontally
 ) {
  LazyColumn(
   modifier = Modifier
    .fillMaxSize(),
   horizontalAlignment = Alignment.CenterHorizontally
  ) {
   item {
    Text(
     "Update User",
     fontSize = 24.sp,
     modifier = Modifier.padding(bottom = 16.dp)
    )
   }

   item {
    OutlinedTextField(
     value = updateUser.firstname,
     onValueChange = { updateUser = updateUser.copy(firstname = it.toUpperCase()) },
     singleLine = true,
     label = { Text("First Name") },
     modifier = Modifier.fillMaxWidth(),
     shape = RoundedCornerShape(20.dp)
    )
   }

   item {
    OutlinedTextField(
     value = updateUser.lastname,
     onValueChange = { updateUser = updateUser.copy(lastname = it.toUpperCase()) },
     singleLine = true,
     label = { Text("Last Name") },
     modifier = Modifier.fillMaxWidth(),
     shape = RoundedCornerShape(20.dp)
    )
   }

   item {
    OutlinedTextField(
     value = updateUser.email,
     onValueChange = { updateUser = updateUser.copy(email = it) },
     singleLine = true,
     label = { Text("Email") },
     modifier = Modifier.fillMaxWidth(),
     shape = RoundedCornerShape(20.dp)
    )
   }

   item {
    OutlinedTextField(
     value = updateUser.password,
     onValueChange = { updateUser = updateUser.copy(password = it) },
     singleLine = true,
     label = { Text("Password") },
     modifier = Modifier.fillMaxWidth(),
     shape = RoundedCornerShape(20.dp)
    )
   }

   // Replace the OutlinedTextField with UniversalDropDownMenu for User Type
   item {
    UniversalDropDownMenu(
     label = "User Type",
     items = listOf("Admin", "Student", "Faculty"),
     selectedItem = updateUser.usertype,
     onItemSelected = { updateUser = updateUser.copy(usertype = it) }
    )
   }

   // Replace the OutlinedTextField with UniversalDropDownMenu for Department
   item {
    UniversalDropDownMenu(
     label = "Department",
     items = listOf("BSCS", "BSA", "BSE", "BSAIS", "BSTM"),
     selectedItem = updateUser.department,
     onItemSelected = { updateUser = updateUser.copy(department = it) }
    )
   }

   // Replace the OutlinedTextField with UniversalDropDownMenu for Status
   item {
    UniversalDropDownMenu(
     label = "Status",
     items = listOf("Active", "Inactive"),
     selectedItem = updateUser.status,
     onItemSelected = { updateUser = updateUser.copy(status = it) }
    )
   }

   item {
    Text(
     text = result.failureMessage ?: "",
     color = Color.Red,
     fontSize = 12.sp,
     modifier = Modifier.padding(bottom = 8.dp)
    )
   }

   item {
    Row(
     verticalAlignment = Alignment.Bottom,
     horizontalArrangement = Arrangement.SpaceAround,
     modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 50.dp)
    ) {
     FloatingActionButton(
      onClick = {
       navController.navigate(AdminHomeScreen.UserManagement.name)
      },
      modifier = Modifier
       .padding(8.dp)
       .weight(1f)
     ) {
      Row(
       horizontalArrangement = Arrangement.spacedBy(16.dp),
       verticalAlignment = Alignment.CenterVertically
      ) {
       Icon(
        Icons.Default.Clear,
        contentDescription = "Cancel"
       )

       Text(
        text = "Cancel",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
       )
      }
     }

     FloatingActionButton(
      onClick = {
       result = viewModel.validateFields(
        User(
         updateUser.id,
         updateUser.firstname,
         updateUser.lastname,
         updateUser.email,
         updateUser.password,
         updateUser.usertype,
         updateUser.department,
         updateUser.status
        )
       )
       if (result.failureMessage == null) {
        coroutineScope.launch {
         showDialog = true
        }
       }
      },
      modifier = Modifier
       .padding(8.dp)
       .weight(1f)
     ) {
      Row(
       horizontalArrangement = Arrangement.spacedBy(16.dp),
       verticalAlignment = Alignment.CenterVertically
      ) {
       Text(
        text = "Save",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
       )

       Icon(
        Icons.Default.Check,
        contentDescription = "Save"
       )
      }
     }
    }
   }
  }
 }

 if (showDialog) {
  ConfirmDialog(
   title = "Confirm Action",
   message = "Are you sure you want to perform this action?",
   onConfirm = {
    coroutineScope.launch {
     viewModel.updateUser(
      User(
       updateUser.id,
       updateUser.firstname,
       updateUser.lastname,
       updateUser.email,
       updateUser.password,
       updateUser.usertype,
       updateUser.department,
       updateUser.status
      )
     )
     navController.navigate(AdminHomeScreen.UserManagement.name)
    }
   },
   onDismiss = {
    showDialog = false
   },
   showDialog = showDialog
  )
 }
}