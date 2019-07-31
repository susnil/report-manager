package com.susnil.reportmanager.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.susnil.reportmanager.service.model.Report


@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(reports: Array<Report>)

    @Query("SELECT * FROM Report ORDER BY id ASC")
    fun getAllReports(): LiveData<List<Report>>

}