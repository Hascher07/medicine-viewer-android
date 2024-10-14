package com.app.medicineviewer.core.utils

sealed class UIComponent {
    data class Dialog(val title: String, val description: String) : UIComponent()
    data class IDialog(val title: Int, val description: Int) : UIComponent()
    data class None(val message: String) : UIComponent()
    data class INone(val message: Int) : UIComponent()
}
