package com.susnil.reportmanager.config

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import androidx.annotation.NonNull
import com.susnil.reportmanager.service.DateTypeConverter
import com.susnil.reportmanager.service.ReportDao
import com.susnil.reportmanager.service.model.Report
import java.util.concurrent.Executors


@Database(entities = [Report::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE =
                        buildDatabase(context)
                }
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "myDB"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadScheduledExecutor()
                            .execute {
                                getAppDataBase(context)?.reportDao()?.insertAll(Report.populateData())
                            }
                    }
                })
                .build()
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}