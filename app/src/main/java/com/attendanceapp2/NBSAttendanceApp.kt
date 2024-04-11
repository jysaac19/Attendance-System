package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.User
import com.attendanceapp2.authentication.LoggedInUserHolder

class NBSAttendanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Define your list of embedded users here
        val embeddedUsers = listOf(
            User(
                101,
                "Je",
                "Ysaac",
                "k",
                "123",
                "Student",
                "ComSci"
            ),
            User(
                201,
                "Admin",
                "Ysaac",
                "j",
                "123",
                "Admin",
                "ComSci"
            ),
            User(
                301,
                "Faculty",
                "Ysaac",
                "s",
                "123",
                "Faculty",
                "ComSci"
            )
        )
        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers)
    }
}