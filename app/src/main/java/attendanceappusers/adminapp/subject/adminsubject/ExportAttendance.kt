package attendanceappusers.adminapp.subject.adminsubject

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.attendanceapp2.data.model.attendance.AttendanceSummaryListHolder
import com.attendanceapp2.data.model.subject.SelectedSubjectHolder
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun exportAttendanceSummariesAsExcel(context: Context) {
    val attendanceSummaries = AttendanceSummaryListHolder.getSummaryList()
    val subject = SelectedSubjectHolder.getSelectedSubject()
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("${subject?.name}")

    val headerRow = sheet.createRow(0)
    headerRow.createCell(0).setCellValue("Student ID")
    headerRow.createCell(1).setCellValue("Name")
    headerRow.createCell(2).setCellValue("Present")
    headerRow.createCell(3).setCellValue("Absent")
    headerRow.createCell(4).setCellValue("Late")
    headerRow.createCell(5).setCellValue("Total Records")

    val currentDate = LocalDate.now()
    val startDate = currentDate.withDayOfMonth(1)
    val endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth())

    val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val dates = generateSequence(startDate) { date ->
        date.plusDays(1).takeIf { it <= endDate }
    }.map { it.format(dateFormatter) }.toList()

    dates.forEachIndexed { index, date ->
        headerRow.createCell(index + 6).setCellValue(date)
    }

    var rowNum = 1
    attendanceSummaries.forEach { summary ->
        val row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue(summary.userId.toString())
        row.createCell(1).setCellValue("${summary.firstname} ${summary.lastname}")
        row.createCell(2).setCellValue(summary.presentCount.toDouble())
        row.createCell(3).setCellValue(summary.absentCount.toDouble())
        row.createCell(4).setCellValue(summary.lateCount.toDouble())
        row.createCell(5).setCellValue(summary.totalCount.toDouble())

        dates.forEachIndexed { index, date ->
            val attendance = summary.attendances.find { it.date == date }
            val statusSymbol = when (attendance?.status) {
                "Present" -> "✔"
                "Absent" -> "✘"
                "Late" -> "━"
                else -> ""
            }
            row.createCell(index + 6).setCellValue(statusSymbol)
        }
    }

    val monthName = currentDate.month.name.toUpperCase()
    val fileName = "${subject?.name} ATTENDANCES RECORD FOR THE MONTH OF $monthName.xlsx"

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