package com.susnil.reportmanager.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.susnil.reportmanager.UtilMethods
import com.susnil.reportmanager.UtilMethods.Companion.getRandomCity
import com.susnil.reportmanager.UtilMethods.Companion.getRandomExport
import com.susnil.reportmanager.UtilMethods.Companion.getRandomName
import com.susnil.reportmanager.UtilMethods.Companion.getRandomStreet
import java.util.*

@Entity
data class Report(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "export_name") val exportName: String?,
    @ColumnInfo(name = "create_date") val createDate: Date?,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "location_name") val locationName: String?
) {
    companion object {
        fun populateData(): Array<Report> {
            val mutableListOf = mutableListOf<Report>()
            for (i in 1..20) {
                val date = UtilMethods.addHoursToDate(Date(), -i * 8)
                var report1 = Report(
                    id = i,
                    exportName = getRandomExport(),
                    createDate = date,
                    userName = getRandomName(),
                    locationName = "${getRandomCity()} ${getRandomStreet()} $i"
                )
                mutableListOf.add(report1)
            }
            return mutableListOf.toTypedArray()
        }
    }
}
