package com.app.medicineviewer.response

import android.app.Activity

class ErrorResource {

    @JvmField
    var status: String? = null

    private val externalErrorCode: String? = null

    val errorCode: String? = null

    @JvmField
    var message: String? = ""
    @JvmField
    var description: String? = ""
    @JvmField
    var title: String? = ""
    var errors: List<FieldError>? = null
    @JvmField
    var data: Any? = null
    fun show(activity: Activity?) {
        Error(title)
    }

    class FieldError(var field: String, var message: String)
}
