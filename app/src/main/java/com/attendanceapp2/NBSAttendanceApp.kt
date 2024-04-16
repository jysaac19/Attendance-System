package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.model.UserSubjectCrossRef
import com.attendanceapp2.universal.data.LoggedInUserHolder

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
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(301, 5)
        )


        val embeddedAttendances = listOf(

            Attendance(101, "Firstname1", "Lastname1", 101L, "Introduction to Computer Science", "CS101", "2024-04-01", "09:00 AM"),
            Attendance(102, "Firstname2", "Lastname2", 101L, "Introduction to Computer Science", "CS101", "2024-04-01", "10:00 AM"),
            Attendance(103, "Firstname3", "Lastname3", 201L, "English Literature", "ENG201", "2024-04-02", "11:00 AM"),
            Attendance(104, "Firstname4", "Lastname4", 301L, "Advanced Mathematics", "MATH301", "2024-04-02", "12:00 PM"),
            Attendance(105, "Firstname5", "Lastname5", 401L, "Physics", "PHY401", "2024-04-03", "01:00 PM"),
            Attendance(106, "Firstname6", "Lastname6", 501L, "Chemistry", "CHEM501", "2024-04-03", "02:00 PM"),
            Attendance(107, "Firstname7", "Lastname7", 601L, "Biology", "BIO601", "2024-04-04", "03:00 PM"),
            Attendance(108, "Firstname8", "Lastname8", 701L, "History", "HIST701", "2024-04-04", "04:00 PM"),
            Attendance(101, "Firstname9", "Lastname9", 101L, "Introduction to Computer Science", "CS101", "2024-04-05", "09:00 AM"),
            Attendance(102, "Firstname10", "Lastname10", 201L, "English Literature", "ENG201", "2024-04-05", "10:00 AM"),
            Attendance(103, "Firstname11", "Lastname11", 301L, "Advanced Mathematics", "MATH301", "2024-04-06", "11:00 AM"),
            Attendance(104, "Firstname12", "Lastname12", 401L, "Physics", "PHY401", "2024-04-06", "12:00 PM"),
            Attendance(105, "Firstname13", "Lastname13", 501L, "Chemistry", "CHEM501", "2024-04-07", "01:00 PM"),
            Attendance(106, "Firstname14", "Lastname14", 601L, "Biology", "BIO601", "2024-04-07", "02:00 PM"),
            Attendance(107, "Firstname15", "Lastname15", 701L, "History", "HIST701", "2024-04-08", "03:00 PM"),
            Attendance(108, "Firstname16", "Lastname16", 101L, "Introduction to Computer Science", "CS101", "2024-04-08", "04:00 PM"),
            Attendance(101, "Firstname17", "Lastname17", 201L, "English Literature", "ENG201", "2024-04-09", "10:00 AM"),
            Attendance(102, "Firstname18", "Lastname18", 301L, "Advanced Mathematics", "MATH301", "2024-04-09", "11:00 AM"),
            Attendance(103, "Firstname19", "Lastname19", 401L, "Physics", "PHY401", "2024-04-10", "12:00 PM"),
            Attendance(104, "Firstname20", "Lastname20", 501L, "Chemistry", "CHEM501", "2024-04-10", "01:00 PM"),
            Attendance(105, "Firstname21", "Lastname21", 101L, "Introduction to Computer Science", "CS101", "2024-04-11", "09:00 AM"),
            Attendance(106, "Firstname22", "Lastname22", 201L, "English Literature", "ENG201", "2024-04-11", "10:00 AM"),
            Attendance(107, "Firstname23", "Lastname23", 301L, "Advanced Mathematics", "MATH301", "2024-04-12", "11:00 AM"),
            Attendance(108, "Firstname24", "Lastname24", 401L, "Physics", "PHY401", "2024-04-12", "12:00 PM"),
            Attendance(101, "Firstname25", "Lastname25", 501L, "Chemistry", "CHEM501", "2024-04-13", "01:00 PM"),
            Attendance(102, "Firstname26", "Lastname26", 601L, "Biology", "BIO601", "2024-04-13", "02:00 PM"),
            Attendance(103, "Firstname27", "Lastname27", 701L, "History", "HIST701", "2024-04-14", "03:00 PM"),
            Attendance(104, "Firstname28", "Lastname28", 101L, "Introduction to Computer Science", "CS101", "2024-04-14", "04:00 PM"),
            Attendance(105, "Firstname29", "Lastname29", 201L, "English Literature", "ENG201", "2024-04-15", "10:00 AM"),
            Attendance(106, "Firstname30", "Lastname30", 301L, "Advanced Mathematics", "MATH301", "2024-04-15", "11:00 AM"),
            Attendance(107, "Firstname31", "Lastname31", 401L, "Physics", "PHY401", "2024-04-16", "12:00 PM"),
            Attendance(108, "Firstname32", "Lastname32", 501L, "Chemistry", "CHEM501", "2024-04-16", "01:00 PM"),
            Attendance(101, "Firstname33", "Lastname33", 101L, "Introduction to Computer Science", "CS101", "2024-04-17", "09:00 AM"),
            Attendance(102, "Firstname34", "Lastname34", 201L, "English Literature", "ENG201", "2024-04-17", "10:00 AM"),
            Attendance(103, "Firstname35", "Lastname35", 301L, "Advanced Mathematics", "MATH301", "2024-04-18", "11:00 AM"),
            Attendance(104, "Firstname36", "Lastname36", 401L, "Physics", "PHY401", "2024-04-18", "12:00 PM"),
            Attendance(105, "Firstname37", "Lastname37", 501L, "Chemistry", "CHEM501", "2024-04-19", "01:00 PM"),
            Attendance(106, "Firstname38", "Lastname38", 601L, "Biology", "BIO601", "2024-04-19", "02:00 PM"),
            Attendance(107, "Firstname39", "Lastname39", 701L, "History", "HIST701", "2024-04-20", "03:00 PM"),
            Attendance(108, "Firstname40", "Lastname40", 101L, "Introduction to Computer Science", "CS101", "2024-04-20", "04:00 PM")

        )
        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers, embeddedSubjects, embeddedAttendances, embeddedUserSubjectCrossRefs)
    }
}