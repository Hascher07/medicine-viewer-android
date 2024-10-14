package com.app.medicineviewer.response.getmedicines

data class Medicine(
    val details: String,
    val dose: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val strength: String
)
