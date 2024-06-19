package attendanceappusers.adminapp.subject.adminsubject

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.attendanceapp2.data.model.attendance.AttendanceSummaryListHolder
import com.attendanceapp2.data.model.attendance.AttendanceToExport
import com.attendanceapp2.data.model.attendance.AttendanceToExportListHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun exportAttendanceSummariesAsExcel(context: Context, period: String) {
    val attendanceSummaries = AttendanceToExportListHolder.getAttendanceToExportList()
    val subject = SelectedSubjectHolder.getSelectedSubject()
    val workbook = XSSFWorkbook()

    // Check if there are attendance records
    if (attendanceSummaries.isEmpty()) {
        Toast.makeText(context, "No Attendance Record for this Criteria", Toast.LENGTH_SHORT).show()
        return
    }

    // Determine date ranges
    val currentDate = LocalDate.now()
    val (startDate, endDate, formattedPeriod) = when (period) {
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

    if (period == "Whole Year") {
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

fun createSheet(workbook: XSSFWorkbook, sheetName: String, attendanceSummaries: List<AttendanceToExport>, presentDates: List<String>) {
    val sheet = workbook.createSheet(sheetName)
    val headerRow = sheet.createRow(0)

    val centerAlignmentStyle = workbook.createCellStyle()
    centerAlignmentStyle.alignment = HorizontalAlignment.CENTER

    val leftAlignmentStyle = workbook.createCellStyle()
    leftAlignmentStyle.alignment = HorizontalAlignment.LEFT

    val studentIdHeaderCell = headerRow.createCell(0)
    studentIdHeaderCell.setCellValue("Student ID")
    studentIdHeaderCell.cellStyle = centerAlignmentStyle

    val nameHeaderCell = headerRow.createCell(1)
    nameHeaderCell.setCellValue("Name")
    nameHeaderCell.cellStyle = centerAlignmentStyle

    headerRow.createCell(2).setCellValue("Present")
    headerRow.createCell(3).setCellValue("Absent")
    headerRow.createCell(4).setCellValue("Late")
    headerRow.createCell(5).setCellValue("Total Records")

    for (i in 2 until headerRow.lastCellNum) {
        headerRow.getCell(i).cellStyle = centerAlignmentStyle
    }

    presentDates.forEachIndexed { index, date ->
        headerRow.createCell(index + 6).setCellValue(date)
    }

    var rowNum = 1
    attendanceSummaries.forEach { summary ->
        val row = sheet.createRow(rowNum++)
        val studentIdCell = row.createCell(0)
        studentIdCell.setCellValue(summary.userId.toString())
        studentIdCell.cellStyle = centerAlignmentStyle

        val nameCell = row.createCell(1)
        nameCell.setCellValue("${summary.firstname} ${summary.lastname}")
        nameCell.cellStyle = leftAlignmentStyle

        row.createCell(2).setCellValue(summary.presentCount.toDouble())
        row.createCell(3).setCellValue(summary.absentCount.toDouble())
        row.createCell(4).setCellValue(summary.lateCount.toDouble())
        row.createCell(5).setCellValue(summary.totalCount.toDouble())

        presentDates.forEachIndexed { index, date ->
            val attendance = summary.attendances.find { it.date == date }
            val statusSymbol = when (attendance?.status) {
                "Present" -> "✔"
                "Absent" -> "✘"
                "Late" -> "━"
                else -> ""
            }
            row.createCell(index + 6).setCellValue(statusSymbol)
        }

        for (i in 2 until row.lastCellNum) {
            row.getCell(i).cellStyle = centerAlignmentStyle
        }

        for (i in 0 until headerRow.lastCellNum) {
            val maxColumnWidth = (0 until sheet.physicalNumberOfRows).mapNotNull { sheet.getRow(it)?.getCell(i) }
                .maxByOrNull { it.toString().length }?.toString()?.length ?: 0
            sheet.setColumnWidth(i, (maxColumnWidth + 4) * 256)
        }
    }
}