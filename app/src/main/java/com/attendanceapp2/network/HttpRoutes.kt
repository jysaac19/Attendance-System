package com.attendanceapp2.network

object HttpRoutes {

    private const val BASE_URL = "http://192.168.158.240:5555"

    //LOCAL API
    const val ATTENDANCES = "${BASE_URL}/attendance"
    const val ADD_ATTENDANCE = "${BASE_URL}/addAttendance"
    const val UPDATE_ATTENDANCE = "${BASE_URL}/updateAttendance"
    const val DELETE_ATTENDANCE = "${BASE_URL}/deleteAttendance"
    const val ATTENDANCE_BY_USER_ID_SUBJECT_ID_DATE = "${BASE_URL}/attendanceByUserIdSubjectIdDate"




    const val SUBJECTS = "${BASE_URL}/subject"
    const val ADD_SUBJECT = "${BASE_URL}/addSubject"
    const val UPDATE_SUBJECT = "${BASE_URL}/updateSubject"
    const val DELETE_SUBJECT = "${BASE_URL}/deleteSubject"
    const val GET_SUBJECT_BY_IDS = "${BASE_URL}/subjectsByIds"
    const val GET_SUBJECT_BY_CODE = "${BASE_URL}/subjectByCode"
    const val GET_SUBJECT_BY_NAME = "${BASE_URL}/subjectByName"
    const val GET_SUBJECT_BY_JOIN_CODE = "${BASE_URL}/subjectByJoinCode"




    const val USERS = "${BASE_URL}/user"
    const val ADDUSER = "${BASE_URL}/addUser"
    const val UPDATE_USER = "${BASE_URL}/updateUser"
    const val DELETE_USER = "${BASE_URL}/deleteUser"
    const val GET_USER_BY_EMAIL_PASSWORD = "$BASE_URL/getUserByEmailPassword"
    const val GET_USER_BY_EMAIL = "$BASE_URL/getUserByEmail"
    const val GET_USER_BY_FULL_NAME = "$BASE_URL/getUserByFullName"


    const val SCHEDULES = "${BASE_URL}/subjectschedules"
    const val ADD_SCHEDULE = "${BASE_URL}/addSchedule"
    const val UPDATE_SCHEDULE = "${BASE_URL}/updateSchedule"
    const val DELETE_SCHEDULE = "${BASE_URL}/deleteSchedule"
    const val SCHEDULE_BY_SUBJECT_ID = "${BASE_URL}/subjectschedules"


    const val USER_SUBJECT_CROSS_REF = "${BASE_URL}/usersubjectcrossref"
    const val ADD_USER_SUBJECT_CROSS_REF = "${BASE_URL}/addUserSubjectCrossRef"
    const val DELETE_USER_SUBJECT_CROSS_REF = "${BASE_URL}/deleteUserSubjectCrossRef"
    const val GET_USER_SUBJECT_CROSS_REF_BY_USER_ID = "${BASE_URL}/usersubjectcrossrefByUserId"
    const val GET_USER_SUBJECT_CROSS_REF_BY_USER_ID_SUBJECT_ID = "${BASE_URL}/usersubjectcrossrefBySubjectAndUser"

    const val NOTIFICATIONS = "${BASE_URL}/notifications"
    const val ADD_NOTIFICATIONS = "${BASE_URL}/addNotifications"

}