package attendanceappusers.adminapp.profile

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.LoggedInUserHolder

class AdminProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
    }
}