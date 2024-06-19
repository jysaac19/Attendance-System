package com.attendanceapp2.data.model.attendance

data class AttendanceToExport(
    val userId: Int,
    val firstname: String,
    val lastname: String,
    val presentCount: Int,
    val absentCount: Int,
    val lateCount: Int,
    val totalCount: Int,
    val attendances: List<Attendance>
)

object AttendanceToExportListHolder {
    private val attendanceToExportList = mutableListOf<AttendanceToExport>()

    fun getAttendanceToExportList(): List<AttendanceToExport> {
        return attendanceToExportList.toList()
    }

    fun addSummary(summary: AttendanceToExport) {
        attendanceToExportList.add(summary)
    }

    fun clearSummaryList() {
        attendanceToExportList.clear()
    }

    fun removeSummary(summary: AttendanceToExport) {
        attendanceToExportList.remove(summary)
    }

    fun updateSummary(summary: AttendanceToExport) {
        val index = attendanceToExportList.indexOfFirst { it.userId == summary.userId }
        if (index != -1) {
            attendanceToExportList[index] = summary
        }
    }

    fun setAttendanceToExportList(list: List<AttendanceToExport>) {
        attendanceToExportList.clear()
        attendanceToExportList.addAll(list)
    }
}

object AttendanceToExportListHolderForFaculty {
    private val attendanceToExportListForFaculty = mutableListOf<AttendanceToExport>()

    fun getAttendanceToExportList(): List<AttendanceToExport> {
        return attendanceToExportListForFaculty.toList()
    }

    fun addSummary(summary: AttendanceToExport) {
        attendanceToExportListForFaculty.add(summary)
    }

    fun clearSummaryList() {
        attendanceToExportListForFaculty.clear()
    }

    fun removeSummary(summary: AttendanceToExport) {
        attendanceToExportListForFaculty.remove(summary)
    }

    fun updateSummary(summary: AttendanceToExport) {
        val index = attendanceToExportListForFaculty.indexOfFirst { it.userId == summary.userId }
        if (index != -1) {
            attendanceToExportListForFaculty[index] = summary
        }
    }

    fun setAttendanceToExportList(list: List<AttendanceToExport>) {
        attendanceToExportListForFaculty.clear()
        attendanceToExportListForFaculty.addAll(list)
    }
}