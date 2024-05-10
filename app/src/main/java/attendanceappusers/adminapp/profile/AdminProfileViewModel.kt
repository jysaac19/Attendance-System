package attendanceappusers.adminapp.profile

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.ResultsManager
import com.attendanceapp2.data.model.user.LoggedInUserHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder

class AdminProfileViewModel () : ViewModel() {
    fun logout() {
        LoggedInUserHolder.clearLoggedInUser()
        SelectedSubjectHolder.clearSelectedSubject()
        ResultsManager.clear()
    }
}