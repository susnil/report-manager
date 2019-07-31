package com.susnil.reportmanager.view.ui

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susnil.reportmanager.R
import com.susnil.reportmanager.UtilMethods.Companion.formatDateFromDatePicker
import com.susnil.reportmanager.UtilMethods.Companion.formatDateFromTimePicker
import com.susnil.reportmanager.config.AppDatabase
import com.susnil.reportmanager.service.model.Report
import com.susnil.reportmanager.service.model.RestrictionType
import com.susnil.reportmanager.view.adapter.ReportAdapter
import com.susnil.reportmanager.viewmodel.ReportViewModel
import java.util.*
import androidx.recyclerview.widget.DividerItemDecoration




class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var adapter: ReportAdapter? = null
    private lateinit var reportViewModel: ReportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewHeaderName = findViewById<TextView>(R.id.Header_name)
        val textViewHeaderDate = findViewById<TextView>(R.id.Header_Date)
        val textViewHeaderTime = findViewById<TextView>(R.id.Header_Time)
        val textViewHeaderUser = findViewById<TextView>(R.id.Header_User)
        val textViewHeaderLocal = findViewById<TextView>(R.id.Header_Local)

        textViewHeaderName.setOnClickListener {
            showDialog(RestrictionType.TEXT_EXPORT)
        }
        textViewHeaderDate.setOnClickListener {
            showDialog(RestrictionType.DATE)
        }
        textViewHeaderTime.setOnClickListener {
            showDialog(RestrictionType.TIME)
        }
        textViewHeaderUser.setOnClickListener {
            showDialog(RestrictionType.TEXT_USER)
        }
        textViewHeaderLocal.setOnClickListener {
            showDialog(RestrictionType.TEXT_LOCAL)
        }

        db = AppDatabase.getAppDataBase(context = this)
        val recyclerView = findViewById<RecyclerView>(com.susnil.reportmanager.R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        adapter = ReportAdapter()
        recyclerView.adapter = adapter

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        reportViewModel.allReports.observe(this,
            Observer<List<Report>> { reports ->
                adapter?.setItems(reports as ArrayList<Report>)
                adapter?.restrictionType = reportViewModel.restrictionType
                if (reportViewModel.activeFilter != null) adapter?.filter?.filter(reportViewModel.activeFilter)
            })
    }

    private fun showDialog(restrictionType: RestrictionType) {
        val builder = AlertDialog.Builder(this)
        var input: View?

        when (restrictionType) {
            RestrictionType.DATE -> {
                input = DatePicker(this)
                builder.setView(input)
            }
            RestrictionType.TIME -> {
                input = TimePicker(this)
                input.setIs24HourView(true)
                builder.setView(input)
            }
            else -> {
                builder.setTitle("Filtr: ${restrictionType.message}")
                input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)
            }
        }


        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            var inputText: String = when (restrictionType) {
                RestrictionType.DATE -> formatDateFromDatePicker(input as DatePicker)
                RestrictionType.TIME -> formatDateFromTimePicker(input as TimePicker)
                else -> (input as EditText).text.toString()
            }

            Toast.makeText(
                applicationContext,
                resources.getString(R.string.search_for, restrictionType.message),
                Toast.LENGTH_SHORT
            ).show()

            runFilter(restrictionType, inputText)
        }
        builder.show()
    }


    private fun runFilter(restrictionType: RestrictionType, inputText: String) {
        adapter?.restrictionType = restrictionType
        reportViewModel.activeFilter = inputText
        reportViewModel.restrictionType = restrictionType
        adapter?.filter?.filter(inputText)
    }


}
