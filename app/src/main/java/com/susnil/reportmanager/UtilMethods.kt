package com.susnil.reportmanager

import android.widget.DatePicker
import android.widget.TimePicker
import java.util.*
import kotlin.random.Random

class UtilMethods {

    companion object {
        private val nameList = listOf("Jan", "Joanna", "Karol", "Krystyna", "Wiktoria", "Wiktor", "Zofia", "Zygmunt")
        private val surnameList = listOf("Nowak", "Wójcik", "Woźniak", "Mazur", "Zając", "Król", "Pawlak")
        private val exportsList = listOf("Test", "API")
        private val cityList = listOf("Łódź", "Warszawa", "Kraków", "Szczecin", "Gdynia")
        private val streetList = listOf("Przybyszewskiego", "Narutowicza", "Piotrkowska", "Rojna", "Rokicińska", "Dolna", "Kusa")

        fun addHoursToDate(date: Date, hours: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.HOUR_OF_DAY, hours)
            return calendar.time
        }

        fun getRandomCity(): String {
            return cityList[Random.nextInt(0, cityList.size)]
        }
        fun getRandomStreet(): String {
            return streetList[Random.nextInt(0, cityList.size)]
        }
        fun getRandomExport(): String {
            return exportsList[Random.nextInt(0, exportsList.size)]
        }
        fun getRandomName(): String {
            return nameList[Random.nextInt(0, nameList.size)] + " "+ surnameList[Random.nextInt(0, surnameList.size)]
        }

        fun formatDateFromDatePicker(datePicker: DatePicker): String {
            val month = datePicker.month + 1
            var formattedMonth = "" + month
            var formattedDayOfMonth = "" + datePicker.dayOfMonth

            if (month < 10) {
                formattedMonth = "0$month"
            }
            if (datePicker.dayOfMonth < 10) {
                formattedDayOfMonth = "0${datePicker.dayOfMonth}"
            }
            return "$formattedDayOfMonth-$formattedMonth"
        }

        fun formatDateFromTimePicker(timePicker: TimePicker): String {
            val hour = timePicker.hour
            var formattedMinute = "" + timePicker.minute
            var formattedHour = "" + hour

            if (hour < 10) {
                formattedHour = "0$hour"
            }
            if (timePicker.minute < 10) {
                formattedHour = "0${timePicker.minute}"
            }
            return "$formattedHour:$formattedMinute"
        }
    }
}