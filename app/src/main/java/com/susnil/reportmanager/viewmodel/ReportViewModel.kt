package com.susnil.reportmanager.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.susnil.reportmanager.service.model.Report
import com.susnil.reportmanager.service.model.RestrictionType
import com.susnil.reportmanager.service.repository.ReportRepository

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReportRepository =
        ReportRepository(application)
    val allReports: LiveData<List<Report>>
    var activeFilter : String? = null
    var restrictionType : RestrictionType? = null
    init {
        allReports = repository.getAllReports()
    }

}