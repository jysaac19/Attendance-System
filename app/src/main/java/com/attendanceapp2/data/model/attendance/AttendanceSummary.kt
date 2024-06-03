package com.attendanceapp2.data.model.attendance

data class AttendanceSummary(
    val userId: Int,
    val firstname: String,
    val lastname: String,
    val presentCount: Int,
    val absentCount: Int,
    val lateCount: Int,
    val totalCount: Int,
    val attendances: List<Attendance>
)

object AttendanceSummaryListHolder {
    private val summaryList = mutableListOf<AttendanceSummary>()
    fun getSummaryList(): List<AttendanceSummary> {
        return summaryList
    }

    fun addSummary(summary: AttendanceSummary) {
        summaryList.add(summary)
    }

    fun clearSummaryList() {
        summaryList.clear()
    }

    fun removeSummary(summary: AttendanceSummary) {
        summaryList.remove(summary)
    }

    fun updateSummary(summary: AttendanceSummary) {
        val index = summaryList.indexOfFirst { it.userId == summary.userId }
        if (index != -1) {
            summaryList[index] = summary
        }
    }

    fun setSummaryList(list: List<AttendanceSummary>) {
        summaryList.clear()
        summaryList.addAll(list)
    }
}