package attendanceappusers.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import com.attendanceapp2.data.repositories.subject.OfflineSubjectRepository
import com.attendanceapp2.data.repositories.usersubjectcossref.OfflineUserSubjectCrossRefRepository
import com.attendanceapp2.appviewmodel.screenviewmodel.SubjectViewModel


class StudentSubjectViewModel (
    private val offlineUserSubjectCrossRefRepository: OfflineUserSubjectCrossRefRepository,
    private val offlineSubjectRepository: OfflineSubjectRepository,
    private val offlineAttendanceRepository: OfflineAttendanceRepository,
    private val subjectViewModel: SubjectViewModel
) : ViewModel() {

}
