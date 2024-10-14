package com.app.medicineviewer.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.medicineviewer.core.domain.ProgressBarState

@Composable
fun ProgressBar(progressBarState: ProgressBarState) {
    when (progressBarState) {
        is ProgressBarState.Loading -> CircularProgressIndicator()
        is ProgressBarState.Idle -> Box(modifier = Modifier.height(0.dp))
    }
}
