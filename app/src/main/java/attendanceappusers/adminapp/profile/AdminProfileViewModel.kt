package attendanceappusers.adminapp.profile

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.LoggedInUserHolder
import com.attendanceapp2.data.model.SelectedSubjectHolder

class AdminProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
        SelectedSubjectHolder.clearSelectedSubject()
    }
}