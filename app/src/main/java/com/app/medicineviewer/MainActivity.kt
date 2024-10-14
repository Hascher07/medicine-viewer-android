package com.app.medicineviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.app.medicineviewer.presentation.medicine.MedicineViewModel
import com.app.medicineviewer.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val viewModel: MedicineViewModel = hiltViewModel()
                AppNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}


