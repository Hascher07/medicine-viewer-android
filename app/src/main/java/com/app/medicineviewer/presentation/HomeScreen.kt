package com.app.medicineviewer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.medicineviewer.R
import com.app.medicineviewer.data.Medicine
import com.app.medicineviewer.presentation.medicine.MedicineList
import com.app.medicineviewer.presentation.medicine.MedicineViewModel
import java.time.LocalDateTime

@Composable
fun HomeScreen(username: String, viewModel: MedicineViewModel, onMedicineClick: (Medicine) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Icon",
                modifier = Modifier.size(100.dp)
            )
        }
        Text(
            text = getGreetingMessage(username),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        MedicineList(viewModel = viewModel, onMedicineClick = onMedicineClick)
    }
}

private fun getGreetingMessage(username: String): String {
    val greeting = when (LocalDateTime.now().hour) {
        in 5..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    return "$greeting $username!"
}
