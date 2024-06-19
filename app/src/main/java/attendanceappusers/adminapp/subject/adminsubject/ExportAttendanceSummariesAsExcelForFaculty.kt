package attendanceappusers.adminapp.subject.adminsubject

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.attendanceapp2.data.model.attendance.AttendanceToExport
import com.attendanceapp2.data.model.attendance.AttendanceToExportListHolderForFaculty
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun exportAttendanceSummariesAsExcelForFaculty(context: Context, period: String) {
    val attendanceSummaries = AttendanceToExportListHolderForFaculty.getAttendanceToExportList()
    if (attendanceSummaries.isEmpty()) {
        Toast.makeText(context, "No Attendance Record for this Criteria", Toast.LENGTH_SHORT).show()
        return
    }

    val subject = SelectedSubjectHolder.getSelectedSubject()
    val workbook = XSSFWorkbook()

    // Determine date ranges
    val currentDate = LocalDate.now()
    val (startDate, endDate, formattedPeriod) = when (period) {
        "Previous Day" -> {
            val previousDay = currentDate.minusDays(1)
            Triple(previousDay, previousDay, "Previous Day")
        }
        "Current Day" -> {
            Triple(currentDate, currentDate, "Current Day")
        }
        "Previous Month" -> {
            val previousMonth = currentDate.minusMonths(1)
            val startOfPreviousMonth = previousMonth.withDayOfMonth(1)
            val endOfPreviousMonth = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth())
            Triple(startOfPreviousMonth, endOfPreviousMonth, "${previousMonth.month.name.toLowerCase().capitalize()} ${previousMonth.year}")
        }
        "Current Month" -> {
            val startOfCurrentMonth = currentDate.withDayOfMonth(1)
            val endOfCurrentMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
            Triple(startOfCurrentMonth, endOfCurrentMonth, "${currentDate.month.name.toLowerCase().capitalize()} ${currentDate.year}")
        }
        "Whole Year" -> {
            val startOfYear = currentDate.withDayOfYear(1)
            val endOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear())
            Triple(startOfYear, endOfYear, "Year ${currentDate.year}")
        }
        else -> Triple(currentDate, currentDate, "")
    }

    val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val allDates = generateSequence(startDate) { date ->
        date.plusDays(1).takeIf { it <= endDate }
    }.map { it.format(dateFormatter) }.toList()

    val datesWithAttendance = attendanceSummaries.flatMap { it.attendances.map { it.date } }.toSet()
    val presentDates = allDates.filter { it in datesWithAttendance }

    if (presentDates.isEmpty()) {
        Toast.makeText(context, "No Attendance Record for this Criteria", Toast.LENGTH_SHORT).show()
        return
    }

    if (period == "Previous Day" || period == "Current Day") {
        createStudentListSheet(workbook, formattedPeriod, attendanceSummaries, startDate.format(dateFormatter))
    } else if (period == "Whole Year") {
        // Create a summary sheet for the whole year
        createSheet(workbook, "Yearly Summary", attendanceSummaries, presentDates)

        // Create individual sheets for each month
        var currentMonth = startDate.withDayOfMonth(1)
        while (currentMonth.isBefore(endDate) || currentMonth.isEqual(endDate)) {
            val monthEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth())
            val monthDates = generateSequence(currentMonth) { date ->
                date.plusDays(1).takeIf { it <= monthEnd && it <= endDate }
            }.map { it.format(dateFormatter) }.filter { it in datesWithAttendance }.toList()
            if (monthDates.isNotEmpty()) {
                createSheet(workbook, "${currentMonth.month.name.toLowerCase().capitalize()} ${currentMonth.year}", attendanceSummaries, monthDates)
            }
            currentMonth = currentMonth.plusMonths(1)
        }
    } else {
        createSheet(workbook, formattedPeriod, attendanceSummaries, presentDates)
    }

    val fileName = "${subject?.name} ATTENDANCES RECORD FOR $formattedPeriod.xlsx"
    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    if (directory != null && !directory.exists()) {
        if (!directory.mkdirs()) {
            Toast.makeText(context, "Can't create directory", Toast.LENGTH_SHORT).show()
            return
        } else {
            Toast.makeText(context, "Directory created", Toast.LENGTH_SHORT).show()
        }
    }
    val file = File(directory, fileName)
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        workbook.write(fileOutputStream)
        workbook.close()
        Toast.makeText(context, "Excel file created successfully", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
    } finally {
        fileOutputStream?.close()
        workbook.close()
    }
}

private fun createStudentListSheet(workbook: XSSFWorkbook, sheetName: String, attendanceSummaries: List<AttendanceToExport>, targetDate: String) {
    val sheet = workbook.createSheet(sheetName)
    var rowNum = 0
    val headerRow = sheet.createRow(rowNum++)
    headerRow.createCell(0).setCellValue("Student Name")
    headerRow.createCell(1).setCellValue("Attendance Status")

    for (attendance in attendanceSummaries) {
        val attendanceRecord = attendance.attendances.find { it.date == targetDate }
        if (attendanceRecord != null) {
            val row = sheet.createRow(rowNum++)
            row.createCell(0).setCellValue("${attendance.firstname} ${attendance.lastname}")
            row.createCell(1).setCellValue(attendanceRecord.status)
        }
    }
}