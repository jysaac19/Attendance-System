package com.attendanceapp2

import android.app.Application
import com.attendanceapp2.data.AppContainer
import com.attendanceapp2.data.AppDataContainer
import com.attendanceapp2.data.model.attendance.Attendance
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import com.attendanceapp2.data.model.subject.UserSubjectCrossRef
import com.attendanceapp2.data.model.user.LoggedInUserHolder

class NBSAttendanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Define your list of embedded users here
        val embeddedUsers = listOf(
            User(101, "JE", "YSAAC", "k", "123", "Student", "ComSci", "Active"),
            User(201, "KARA", "COLUMBA", "j", "123", "Admin", "ComSci", "Active"),
            User(301, "KENNETH", "BONAAGUA", "s", "123", "Faculty", "ComSci", "Active"),
            User(605, "VINCENT", "DAIS", "n", "123", "Faculty", "ComSci", "Active")
        )

        val embeddedSubjects = listOf(
            Subject(id = 1, code = "CS101", name = "Introduction to Computer Science", room = "Room A", faculty = "John Doe", "Active", "CS101AB1"),
            Subject(id = 2, code = "ENG201", name = "English Literature", room = "Room B", faculty = "Jane Smith", "Active", "ENG201CD2"),
            Subject(id = 3, code = "MATH301", name = "Advanced Mathematics", room = "Room C", faculty = "Alice Johnson", "Active", "MATH301EF3"),
            Subject(id = 4, code = "PHY401", name = "Physics", room = "Room D", faculty = "Robert Brown", "Active", "PHY401GH4"),
            Subject(id = 5, code = "CHEM501", name = "Chemistry", room = "Room E", faculty = "Emily Wilson", "Active", "CHEM501IJ5"),
            Subject(id = 6, code = "BIO601", name = "Biology", room = "Room F", faculty = "Michael Johnson", "Active", "BIO601KL6"),
            Subject(id = 7, code = "HIST701", name = "History", room = "Room G", faculty = "Sarah Adams", "Archive", "HIST701MN7")
        )

        val embeddedUserSubjectCrossRefs = listOf(
            UserSubjectCrossRef(201, 1),
            UserSubjectCrossRef(201, 2),
            UserSubjectCrossRef(201, 3),
            UserSubjectCrossRef(201, 4),
            UserSubjectCrossRef(201, 5),
            UserSubjectCrossRef(201, 6),
            UserSubjectCrossRef(201, 7),
        )

        val embeddedAttendances = listOf(Attendance(
                1,
                101,
                "JE",
                "YSAAC",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-01-2024",
                "09:00 AM",
                "Present",
                "Student"
            ), Attendance(
                2,
                301,
                "KENNETH",
                "BONAAGUA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-02-2024",
                "09:00 AM",
                "Present",
                "Faculty"
            ), Attendance(
                3,
                201,
                "KARA",
                "COLUMBA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-05-2024",
                "09:00 AM",
                "Present",
            "Admin"
            ), Attendance(
                4,
                101,
                "JE",
                "YSAAC",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-07-2024",
                "09:00 AM",
                "Present",
                "Student"
            ), Attendance(
                5,
                301,
                "KENNETH",
                "BONAAGUA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-08-2024",
                "09:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                6,
                101,
                "JE",
                "YSAAC",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-15-2024",
                "09:00 AM",
                "Present",
                "Student"
            ), Attendance(
                7,
                201,
                "KARA",
                "COLUMBA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-18-2024",
                "09:00 AM",
                "Present",
            "Admin"
            ), Attendance(
                8,
                301,
                "KENNETH",
                "BONAAGUA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-24-2024",
                "09:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                9,
                101,
                "JE",
                "Ysaac",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-28-2024",
                "09:00 AM",
                "Present",
                "Student"
            ), Attendance(
                10,
                301,
                "KENNETH",
                "BONAAGUA",
                1,
                "Introduction to Computer Science",
                "CS101",
                "04-30-2024",
                "09:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                11,
                101,
                "JE",
                "YSAAC",
                2,
                "English Literature",
                "ENG201",
                "04-02-2024",
                "10:00 AM",
                "Present",
                "Student"
            ), Attendance(
                12,
                301,
                "KENNETH",
                "BONAAGUA",
                2,
                "English Literature",
                "ENG201",
                "04-03-2024",
                "10:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                13,
                301,
                "KENNETH",
                "BONAAGUA",
                2,
                "English Literature",
                "ENG201",
                "04-08-2024",
                "10:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                14,
                201,
                "KARA",
                "COLUMBA",
                2,
                "English Literature",
                "ENG201",
                "04-10-2024",
                "10:00 AM",
                "Present",
            "Admin"
            ), Attendance(
                15,
                101,
                "JE",
                "YSAAC",
                2,
                "English Literature",
                "ENG201",
                "04-11-2024",
                "10:00 AM",
                "Present",
                "Student"
            ), Attendance(
                16,
                201,
                "KARA",
                "COLUMBA",
                2,
                "English Literature",
                "ENG201",
                "04-18-2024",
                "10:00 AM",
                "Present",
            "Admin"
            ), Attendance(
                17,
                101,
                "JE",
                "YSAAC",
                2,
                "English Literature",
                "ENG201",
                "04-21-2024",
                "10:00 AM",
                "Present",
                "Student"
            ), Attendance(
                18,
                301,
                "KENNETH",
                "BONAAGUA",
                2,
                "English Literature",
                "ENG201",
                "04-25-2024",
                "10:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                19,
                101,
                "JE",
                "YSAAC",
                2,
                "English Literature",
                "ENG201",
                "04-29-2024",
                "10:00 AM",
                "Present",
                "Student"
            ), Attendance(
                20,
                101,
                "JE",
                "YSAAC",
                2,
                "English Literature",
                "ENG201",
                "04-30-2024",
                "10:00 AM",
                "Present",
                "Student"
            ), Attendance(
                21,
                101,
                "JE",
                "YSAAC",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-06-2024",
                "11:00 AM",
                "Present",
                "Student"
            ), Attendance(
                22,
                301,
                "KENNETH",
                "BONAAGUA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-08-2024",
                "11:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                23,
                301,
                "KENNETH",
                "BONAAGUA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-10-2024",
                "11:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                24,
                101,
                "JE",
                "YSAAC",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-12-2024",
                "11:00 AM",
                "Present",
                "Student"
            ), Attendance(
                25,
                201,
                "KARA",
                "COLUMBA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-13-2024",
                "11:00 AM",
                "Present",
            "Admin"
            ), Attendance(
                26,
                301,
                "KENNETH",
                "BONAAGUA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-26-2024",
                "11:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                27,
                101,
                "JE",
                "YSAAC",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-27-2024",
                "11:00 AM",
                "Present",
                "Student"
            ), Attendance(
                28,
                301,
                "KENNETH",
                "BONAAGUA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-28-2024",
                "11:00 AM",
                "Present",
            "Faculty"
            ), Attendance(
                29,
                201,
                "KARA",
                "COLUMBA",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-29-2024",
                "11:00 AM",
                "Present",
                "Admin"
            ), Attendance(
                30,
                101,
                "JE",
                "YSAAC",
                3,
                "Advanced Mathematics",
                "MATH301",
                "04-30-2024",
                "11:00 AM",
                "Present",
                "Student"
            ))

        LoggedInUserHolder.init(this)
        container = AppDataContainer(this, embeddedUsers, embeddedSubjects, embeddedAttendances, embeddedUserSubjectCrossRefs)
    }
}