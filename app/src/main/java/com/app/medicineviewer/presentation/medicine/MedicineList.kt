package com.app.medicineviewer.presentation.medicine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.medicineviewer.R
import com.app.medicineviewer.data.Medicine

@Composable
fun MedicineList(viewModel: MedicineViewModel = hiltViewModel(), onMedicineClick: (Medicine) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(MedicineEvent.FetchMedicines)
    }
    val medicineDataState by viewModel.medicineDataState.collectAsState()

    LazyColumn {
        items(medicineDataState.medicines) { medicine ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onMedicineClick(medicine) }
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = medicine.imageUrl,
                        contentDescription = "Medicine Image",
                        placeholder = painterResource(id = R.drawable.image),
                        error = painterResource(id = R.drawable.error),
                        modifier = Modifier
                            .size(70.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column {
                        Text(text = "Name: ${medicine.name}")
                        Text(text = "Dose: ${medicine.dose}")
                        Text(text = "Strength: ${medicine.strength}")
                    }
                }
            }
        }
    }
}

