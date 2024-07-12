package attendanceappusers.adminapp.notification

import androidx.lifecycle.ViewModel
import com.attendanceapp2.data.model.Notifications
import com.attendanceapp2.data.repositories.notification.OnlineNotifRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class NotificationViewModel(
    private val onlineNotifRepository: OnlineNotifRepository
) : ViewModel() {



//    private fun setAllNotifications() {
//        viewModelScope.launch {
//            val onlineNotifications = onlineNotifRepository.getAllNotifications()
//            onlineNotifications.forEach { offlineNotifRepository.insertNotifications(it) }
//        }
//    }
    private val _notifications = MutableStateFlow<List<Notifications>>(emptyList())
    val notifications = _notifications.asStateFlow()

    suspend fun getAllNotifications() {


        try {
            val onlineNotifications = onlineNotifRepository.getAllNotifications()
            _notifications.value = onlineNotifications // Update the MutableStateFlow

        } catch (e: Exception) {
            // Handle the exception, e.g., log it or show an error message
            Timber.e(e, "Failed to get all notifications")
        }
    }

    suspend fun getAllNotificationsByPortals(){

    }
    suspend fun insertNotifications(notifications: Notifications){
        onlineNotifRepository.insertNotification(notifications)
    }


}