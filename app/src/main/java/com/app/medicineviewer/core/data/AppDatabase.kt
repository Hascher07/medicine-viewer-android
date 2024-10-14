package com.app.medicineviewer.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.medicineviewer.data.Medicine
import com.app.medicineviewer.data.MedicineDao

@Database(entities = [Medicine::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}
