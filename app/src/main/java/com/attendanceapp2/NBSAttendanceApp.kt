package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.User
import com.attendanceapp2.authentication.LoggedInUserHolder
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.UserSubjectCrossRef

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

        val embeddedSubjects = listOf(
            Subject(
                code = "CS101",
                name = "Introduction to Computer Science",
                room = "Room A",
                faculty = "John Doe",
                day = "Monday",
                start = "9:00 AM",
                end = "11:00 AM"
            ),
            Subject(
                code = "ENG201",
                name = "English Literature",
                room = "Room B",
                faculty = "Jane Smith",
                day = "Tuesday",
                start = "10:00 AM",
                end = "12:00 PM"
            ),
            Subject(
                code = "MATH301",
                name = "Advanced Mathematics",
                room = "Room C",
                faculty = "Alice Johnson",
                day = "Wednesday",
                start = "11:00 AM",
                end = "1:00 PM"
            ),
            Subject(
                code = "PHY401",
                name = "Physics",
                room = "Room D",
                faculty = "Robert Brown",
                day = "Thursday",
                start = "1:00 PM",
                end = "3:00 PM"
            ),
            Subject(
                code = "CHEM501",
                name = "Chemistry",
                room = "Room E",
                faculty = "Emily Wilson",
                day = "Friday",
                start = "2:00 PM",
                end = "4:00 PM"
            )
        )

        val embeddedUserSubjectCrossRefs = listOf(
            // User 101 is enrolled in CS101, ENG201, and MATH301
            UserSubjectCrossRef(
                userId = 101,
                subjectId = 1 // Assuming the subjectId for CS101 is 1
            ),
            UserSubjectCrossRef(
                userId = 101,
                subjectId = 2 // Assuming the subjectId for ENG201 is 2
            ),
            UserSubjectCrossRef(
                userId = 101,
                subjectId = 3 // Assuming the subjectId for MATH301 is 3
            ),
            // User 201 is enrolled in ENG201, PHY401, and CHEM501
            UserSubjectCrossRef(
                userId = 201,
                subjectId = 2 // Assuming the subjectId for ENG201 is 2
            ),
            UserSubjectCrossRef(
                userId = 201,
                subjectId = 4 // Assuming the subjectId for PHY401 is 4
            ),
            UserSubjectCrossRef(
                userId = 201,
                subjectId = 5 // Assuming the subjectId for CHEM501 is 5
            ),
            // User 301 is enrolled in CS101, MATH301, and PHY401
            UserSubjectCrossRef(
                userId = 301,
                subjectId = 1 // Assuming the subjectId for CS101 is 1
            ),
            UserSubjectCrossRef(
                userId = 301,
                subjectId = 3 // Assuming the subjectId for MATH301 is 3
            ),
            UserSubjectCrossRef(
                userId = 301,
                subjectId = 4 // Assuming the subjectId for PHY401 is 4
            )
        )

        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers, embeddedSubjects, embeddedUserSubjectCrossRefs)
    }
}