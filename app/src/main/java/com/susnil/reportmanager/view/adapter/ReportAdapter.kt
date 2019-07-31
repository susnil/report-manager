package com.susnil.reportmanager.view.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.susnil.reportmanager.R
import com.susnil.reportmanager.service.model.Report
import com.susnil.reportmanager.service.model.RestrictionType
import java.text.SimpleDateFormat
import java.util.*


class ReportAdapter : BaseFilterAdapter<Report, ReportAdapter.ReportHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.report_view, parent, false)
        return ReportHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportHolder, position: Int) {
        val currentReport = list[position]

        val formatForDate = SimpleDateFormat("dd-MM")
        val formatForTime = SimpleDateFormat("HH:mm")

        val currentDate = formatForDate.format(currentReport.createDate)
        val currentTime = formatForTime.format(currentReport.createDate)

        holder.textViewName.text = currentReport.exportName

        holder.textViewDate.text = currentDate

        holder.textViewTime.text = currentTime

        holder.textViewUser.text = currentReport.userName

        holder.textViewLocal.text = currentReport.locationName
    }

    override fun filterObject(
        filteredList: ArrayList<Report>,
        `report`: Report,
        searchText: String,
        restrictionType: RestrictionType?
    ) {
        when (restrictionType) {
            RestrictionType.DATE -> {
                val date = DateFormat.format("dd-MM", `report`.createDate) as String
                if (date == searchText) {
                    filteredList?.add(`report`)
                }
            }
            RestrictionType.TIME -> {
                val time = DateFormat.format("HH:mm", `report`.createDate) as String
                if (time == searchText) {
                    filteredList?.add(`report`)
                }
            }
            RestrictionType.TEXT_EXPORT ->
                if (`report`.exportName?.toLowerCase()?.startsWith(searchText.toLowerCase())!!) {
                    filteredList?.add(`report`)
                }
            RestrictionType.TEXT_USER ->
                if (`report`.userName?.toLowerCase()?.startsWith(searchText.toLowerCase())!!) {
                    filteredList?.add(`report`)
                }
            RestrictionType.TEXT_LOCAL ->
                if (`report`.locationName?.toLowerCase()?.startsWith(searchText.toLowerCase())!!) {
                    filteredList?.add(`report`)
                }
        }

    }

    class ReportHolder : RecyclerView.ViewHolder {
        val textViewName: TextView
        val textViewDate: TextView
        val textViewTime: TextView
        val textViewUser: TextView
        val textViewLocal: TextView

        constructor(itemView: View) : super(itemView) {
            textViewName = itemView.findViewById(R.id.TextView_name)
            textViewDate = itemView.findViewById(R.id.TextView_date)
            textViewTime = itemView.findViewById(R.id.TextView_time)
            textViewUser = itemView.findViewById(R.id.TextView_user)
            textViewLocal = itemView.findViewById(R.id.TextView_local)
        }
    }
}