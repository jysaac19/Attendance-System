package attendanceappusers.adminapp.homescreen.attendancemanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.repositories.attendancce.OfflineAttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttendanceManagementViewModel(
    private val offlineAttendanceRepository: OfflineAttendanceRepository
) : ViewModel() {

    // List of attendances
    private val _attendances: MutableStateFlow<List<Attendance>> = MutableStateFlow(emptyList())
    val attendances: StateFlow<List<Attendance>> = _attendances

    init {
        // Load attendances when ViewModel is initialized
        fetchAttendances()
    }


    fun filterAttendancesByAdmin(
        userId: String,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {
            // Call the repository function to filter attendances
            offlineAttendanceRepository.filterAttendancesByAdmin(
                userId,
                startDate,
                endDate
            ).collect() { attendances ->
                // Update the StateFlow with the filtered attendances
                _attendances.value = attendances
            }
        }
    }


    // Function to fetch all attendances
    private fun fetchAttendances() {
        viewModelScope.launch {
            // Call the repository function to get all attendances
            offlineAttendanceRepository.getAllAttendances().collect() { attendances ->
                // Update the StateFlow with the fetched attendances
                _attendances.value = attendances
            }
        }
    }
}