package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.Attendance
import com.attendanceapp2.data.model.Subject
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.model.UserSubjectCrossRef
import com.attendanceapp2.data.model.LoggedInUserHolder

class NBSAttendanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Define your list of embedded users here
        val embeddedUsers = listOf(
            User(101, "JE", "YSAAC", "k", "123", "Student", "ComSci"),
            User(201, "KARA", "COLUMBA", "j", "123", "Admin", "ComSci"),
            User(301, "KENNETH", "BONAAGUA", "s", "123", "Faculty", "ComSci")
        )

        val embeddedSubjects = listOf(
            Subject(id = 1, code = "CS101", name = "Introduction to Computer Science", room = "Room A", faculty = "John Doe", day = "Monday", start = "9:00 AM", end = "11:00 AM"),
            Subject(id = 2, code = "ENG201", name = "English Literature", room = "Room B", faculty = "Jane Smith", day = "Tuesday", start = "10:00 AM", end = "12:00 PM"),
            Subject(id = 3, code = "MATH301", name = "Advanced Mathematics", room = "Room C", faculty = "Alice Johnson", day = "Wednesday", start = "11:00 AM", end = "1:00 PM"),
            Subject(id = 4, code = "PHY401", name = "Physics", room = "Room D", faculty = "Robert Brown", day = "Thursday", start = "1:00 PM", end = "3:00 PM"),
            Subject(id = 5, code = "CHEM501", name = "Chemistry", room = "Room E", faculty = "Emily Wilson", day = "Friday", start = "2:00 PM", end = "4:00 PM"),
            Subject(id = 6, code = "BIO601", name = "Biology", room = "Room F", faculty = "Michael Johnson", day = "Monday", start = "1:00 PM", end = "3:00 PM"),
            Subject(id = 7, code = "HIST701", name = "History", room = "Room G", faculty = "Sarah Adams", day = "Wednesday", start = "9:00 AM", end = "11:00 AM")
        )

        val embeddedUserSubjectCrossRefs = listOf(
            UserSubjectCrossRef(101, 1),
            UserSubjectCrossRef(101, 2),
            UserSubjectCrossRef(101, 3),
            UserSubjectCrossRef(101, 4),
            UserSubjectCrossRef(101, 5),
            UserSubjectCrossRef(101, 6),
            UserSubjectCrossRef(101, 7),
            UserSubjectCrossRef(201, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(201, 5),
            UserSubjectCrossRef(201, 6),
            UserSubjectCrossRef(201, 7),
            UserSubjectCrossRef(301, 1),
            UserSubjectCrossRef(301, 2),
            UserSubjectCrossRef(301, 3),
            UserSubjectCrossRef(301, 4),
            UserSubjectCrossRef(301, 5),
            UserSubjectCrossRef(301, 6),
            UserSubjectCrossRef(301, 7)
        )

        val embeddedAttendances = listOf(
            Attendance(1, 101, "JE", "YSAAC", 1, "Introduction to Computer Science", "CS101", "2024-04-01", "09:00 AM", "Present"),
            Attendance(2, 301, "KENNETH", "BONAAGUA", 1, "Introduction to Computer Science", "CS101", "2024-04-02", "09:00 AM", "Present"),
            Attendance(3, 201, "KARA", "COLUMBA", 1, "Introduction to Computer Science", "CS101", "2024-04-05", "09:00 AM","Present"),
            Attendance(4, 101, "JE", "YSAAC", 1, "Introduction to Computer Science", "CS101", "2024-04-07", "09:00 AM","Present"),
            Attendance(5, 301, "KENNETH", "BONAAGUA", 1, "Introduction to Computer Science", "CS101", "2024-04-08", "09:00 AM","Present"),
            Attendance(6, 101, "JE", "YSAAC", 1, "Introduction to Computer Science", "CS101", "2024-04-15", "09:00 AM","Present"),
            Attendance(7, 201, "KARA", "COLUMBA", 1, "Introduction to Computer Science", "CS101", "2024-04-18", "09:00 AM","Present"),
            Attendance(8, 301, "KENNETH", "BONAAGUA", 1, "Introduction to Computer Science", "CS101", "2024-04-24", "09:00 AM","Present"),
            Attendance(9, 101, "JE", "Ysaac", 1, "Introduction to Computer Science", "CS101", "2024-04-28", "09:00 AM","Present"),
            Attendance(10, 301, "KENNETH", "BONAAGUA", 1, "Introduction to Computer Science", "CS101", "2024-04-30", "09:00 AM","Present"),

            // English Literature
            Attendance(11, 101, "JE", "YSAAC", 2, "English Literature", "ENG201", "2024-04-02", "10:00 AM","Present"),
            Attendance(12, 301, "KENNETH", "BONAAGUA", 2, "English Literature", "ENG201", "2024-04-03", "10:00 AM","Present"),
            Attendance(13, 301, "KENNETH", "BONAAGUA", 2, "English Literature", "ENG201", "2024-04-08", "10:00 AM","Present"),
            Attendance(14, 201, "KARA", "COLUMBA", 2, "English Literature", "ENG201", "2024-04-10", "10:00 AM","Present"),
            Attendance(15, 101, "JE", "YSAAC", 2, "English Literature", "ENG201", "2024-04-11", "10:00 AM","Present"),
            Attendance(16, 201, "KARA", "COLUMBA", 2, "English Literature", "ENG201", "2024-04-18", "10:00 AM","Present"),
            Attendance(17, 101, "JE", "YSAAC", 2, "English Literature", "ENG201", "2024-04-21", "10:00 AM","Present"),
            Attendance(18, 301, "KENNETH", "BONAAGUA", 2, "English Literature", "ENG201", "2024-04-25", "10:00 AM","Present"),
            Attendance(19, 101, "JE", "YSAAC", 2, "English Literature", "ENG201", "2024-04-29", "10:00 AM","Present"),
            Attendance(20, 101, "JE", "YSAAC", 2, "English Literature", "ENG201", "2024-04-30", "10:00 AM","Present"),

            // Advanced Mathematics
            Attendance(21, 101, "JE", "YSAAC", 3, "Advanced Mathematics", "MATH301", "2024-04-06", "11:00 AM","Present"),
            Attendance(22, 301, "KENNETH", "BONAAGUA", 3, "Advanced Mathematics", "MATH301", "2024-04-08", "11:00 AM","Present"),
            Attendance(23, 301, "KENNETH", "BONAAGUA", 3, "Advanced Mathematics", "MATH301", "2024-04-10", "11:00 AM","Present"),
            Attendance(24, 101, "JE", "YSAAC", 3, "Advanced Mathematics", "MATH301", "2024-04-12", "11:00 AM","Present"),
            Attendance(25, 201, "KARA", "COLUMBA", 3, "Advanced Mathematics", "MATH301", "2024-04-13", "11:00 AM","Present"),
            Attendance(26, 301, "KENNETH", "BONAAGUA", 3, "Advanced Mathematics", "MATH301", "2024-04-26", "11:00 AM","Present"),
            Attendance(27, 101, "JE", "YSAAC", 3, "Advanced Mathematics", "MATH301", "2024-04-27", "11:00 AM","Present"),
            Attendance(28, 301, "KENNETH", "BONAAGUA", 3, "Advanced Mathematics", "MATH301", "2024-04-28", "11:00 AM","Present"),
            Attendance(29, 201, "KARA", "COLUMBA", 3, "Advanced Mathematics", "MATH301", "2024-04-29", "11:00 AM","Present"),
            Attendance(30, 101, "JE", "YSAAC", 3, "Advanced Mathematics", "MATH301", "2024-04-30", "11:00 AM","Present"),
        )

        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers, embeddedSubjects, embeddedAttendances, embeddedUserSubjectCrossRefs)
    }
}