package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.universaldata.LoggedInUserHolder
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
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
            User(101, "Je", "Ysaac", "k", "123", "Student", "ComSci"),
            User(201, "Admin", "Ysaac", "j", "123", "Admin", "ComSci"),
            User(301, "Faculty", "Ysaac", "s", "123", "Faculty", "ComSci")
        )

        val embeddedSubjects = listOf(
            Subject(code = "CS101", name = "Introduction to Computer Science", room = "Room A", faculty = "John Doe", day = "Monday", start = "9:00 AM", end = "11:00 AM"),
            Subject(code = "ENG201", name = "English Literature", room = "Room B", faculty = "Jane Smith", day = "Tuesday", start = "10:00 AM", end = "12:00 PM"),
            Subject(code = "MATH301", name = "Advanced Mathematics", room = "Room C", faculty = "Alice Johnson", day = "Wednesday", start = "11:00 AM", end = "1:00 PM"),
            Subject(code = "PHY401", name = "Physics", room = "Room D", faculty = "Robert Brown", day = "Thursday", start = "1:00 PM", end = "3:00 PM"),
            Subject(code = "CHEM501", name = "Chemistry", room = "Room E", faculty = "Emily Wilson", day = "Friday", start = "2:00 PM", end = "4:00 PM"),
            Subject(code = "BIO601", name = "Biology", room = "Room F", faculty = "Michael Johnson", day = "Monday", start = "1:00 PM", end = "3:00 PM"),
            Subject(code = "HIST701", name = "History", room = "Room G", faculty = "Sarah Adams", day = "Wednesday", start = "9:00 AM", end = "11:00 AM")
        )

        val embeddedUserSubjectCrossRefs = listOf(
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(101, 3),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(201, 5),
            UserSubjectCrossRef(301, 1),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(101, 3),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(201, 5),
            UserSubjectCrossRef(301, 1),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(301, 4),
        )

        val embeddedAttendances = listOf(
            Attendance(1, 101, "Firstname1", "Lastname1", "CS101", "Introduction to Computer Science", "CS101", "2024-04-01", "09:00 AM"),
            Attendance(2, 102, "Firstname2", "Lastname2", "CS101", "Introduction to Computer Science", "CS101", "2024-04-02", "10:00 AM"),
            Attendance(3, 103, "Firstname3", "Lastname3", "ENG201", "English Literature", "ENG201", "2024-04-03", "11:00 AM"),
            Attendance(4, 104, "Firstname4", "Lastname4", "MATH301", "Advanced Mathematics", "MATH301", "2024-04-04", "12:00 PM"),
            Attendance(5, 105, "Firstname5", "Lastname5", "PHY401", "Physics", "PHY401", "2024-04-05", "01:00 PM"),
            Attendance(6, 106, "Firstname6", "Lastname6", "CHEM501", "Chemistry", "CHEM501", "2024-04-06", "02:00 PM"),
            Attendance(7, 107, "Firstname7", "Lastname7", "BIO601", "Biology", "BIO601", "2024-04-07", "03:00 PM"),
            Attendance(8, 108, "Firstname8", "Lastname8", "HIST701", "History", "HIST701", "2024-04-08", "04:00 PM"),
            Attendance(9, 101, "Firstname9", "Lastname9", "CS101", "Introduction to Computer Science", "CS101", "2024-04-09", "09:00 AM"),
            Attendance(10, 102, "Firstname10", "Lastname10", "ENG201", "English Literature", "ENG201", "2024-04-10", "10:00 AM"),
            Attendance(11, 103, "Firstname11", "Lastname11", "MATH301", "Advanced Mathematics", "MATH301", "2024-04-11", "11:00 AM"),
            Attendance(12, 104, "Firstname12", "Lastname12", "PHY401", "Physics", "PHY401", "2024-04-12", "12:00 PM"),
            Attendance(13, 105, "Firstname13", "Lastname13", "CHEM501", "Chemistry", "CHEM501", "2024-04-13", "01:00 PM"),
            Attendance(14, 106, "Firstname14", "Lastname14", "BIO601", "Biology", "BIO601", "2024-04-14", "02:00 PM"),
            Attendance(15, 107, "Firstname15", "Lastname15", "HIST701", "History", "HIST701", "2024-04-15", "03:00 PM"),
            Attendance(16, 108, "Firstname16", "Lastname16", "CS101", "Introduction to Computer Science", "CS101", "2024-04-16", "09:00 AM"),
            Attendance(17, 101, "Firstname17", "Lastname17", "ENG201", "English Literature", "ENG201", "2024-04-17", "10:00 AM"),
            Attendance(18, 102, "Firstname18", "Lastname18", "MATH301", "Advanced Mathematics", "MATH301", "2024-04-18", "11:00 AM"),
            Attendance(19, 103, "Firstname19", "Lastname19", "PHY401", "Physics", "PHY401", "2024-04-19", "12:00 PM"),
            Attendance(20, 104, "Firstname20", "Lastname20", "CHEM501", "Chemistry", "CHEM501", "2024-04-20", "01:00 PM")
        )

        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers, embeddedSubjects, embeddedAttendances, embeddedUserSubjectCrossRefs)
    }
}