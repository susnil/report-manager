package com.susnil.reportmanager.service.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.susnil.reportmanager.service.ReportDao
import com.susnil.reportmanager.config.AppDatabase
import com.susnil.reportmanager.service.model.Report


class ReportRepository {
    private val reportDao: ReportDao
    private val allReports: LiveData<List<Report>>

    constructor(application: Application) {
        val database = AppDatabase.getAppDataBase(application)
        reportDao = database!!.reportDao()
        allReports = reportDao.getAllReports()
    }

    fun getAllReports(): LiveData<List<Report>> {
        return allReports
    }

}