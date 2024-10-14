package com.app.medicineviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey val id: Int,
    val name: String,
    val dose: String,
    val strength: String,
    val imageUrl: String,
    val details: String
)
