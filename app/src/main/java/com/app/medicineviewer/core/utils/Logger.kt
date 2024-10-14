package com.app.medicineviewer.core.utils

import android.util.Log

class Logger(private val tag: String) {

    fun log(msg: String) {
        printLogD(tag, msg)
    }

    companion object Factory {
        fun build(tag: String): Logger {
            return buildDebug(tag)
        }

        fun buildDebug(tag: String): Logger {
            return Logger(tag = tag)
        }

        fun buildRelease(tag: String): Logger {
            return Logger(tag = tag)
        }
    }
}

fun printLogD(tag: String, message: String) {
    Log.d(tag, message)
}
