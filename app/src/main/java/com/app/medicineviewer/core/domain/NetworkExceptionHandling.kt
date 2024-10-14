package com.app.medicineviewer.core.domain

import com.app.medicineviewer.response.ErrorResource
import com.app.medicineviewer.core.utils.Logger
import com.app.medicineviewer.R
import com.app.medicineviewer.core.utils.UIComponent
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkExceptionHandling @Inject constructor(val logger: Logger) {

    companion object {
        private const val TAG = "NetworkException"
        private const val DESC_KEY = "description"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    fun <T> execute(e: Exception): Resource<T> {
        logger.log(e.toString())
        return when (e) {
            is HttpException -> {
                when {
                    e.code() == 401 -> {
                        Resource.Error(
                            UIComponent.Dialog(
                                "Session Expired",
                                "Session Expired"
                            ), ErrorType.SESSION_EXPIRED
                        )
                    }
                    else -> {
                        getErrorMessage(e.response()?.errorBody())
                    }
                }
            }
            is ConnectException -> {
                Resource.Error(
                    UIComponent.None(
                        e.localizedMessage?.toString() ?: "Not Able to Connect to Host"
                    ), ErrorType.NETWORK
                )
            }
            is SocketTimeoutException -> {
                Resource.Error(
                    UIComponent.IDialog(
                        R.string.error_timeout_title,
                        R.string.error_timeout_desc
                    ), ErrorType.TIMEOUT
                )
            }
            is IOException -> {
                Resource.Error(UIComponent.INone(R.string.please_check_internet), ErrorType.NETWORK)
            }
            else -> {
                Resource.Error(UIComponent.INone(R.string.please_try_again), ErrorType.UNKNOWN)
            }
        }
    }

    fun <T> getErrorMessage(responseBody: ResponseBody?): Resource<T> {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            val errorResource = Gson().fromJson(jsonObject.toString(), ErrorResource::class.java)
            return when (errorResource.errorCode) {
                ErrorCodes.INTERNAL_ERROR.value -> Resource.Error(
                    UIComponent.None(errorResource.description!!),
                    ErrorType.NETWORK
                )
                ErrorCodes.INVALID_MISSING.value -> Resource.Error(
                    UIComponent.None(errorResource.description!!),
                    ErrorType.NETWORK
                )
                else -> {
                    val message = when {
                        jsonObject.has(DESC_KEY) -> jsonObject.getString(DESC_KEY)
                        jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                        jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                        else -> "Something went wrong"
                    }
                    Resource.Error(
                        UIComponent.None(
                            if (message.length > 400) "Please Try Again"
                            else message
                        ),
                        ErrorType.NETWORK
                    )
                }
            }
        } catch (e: Exception) {
            Resource.Error(UIComponent.INone(R.string.something_went_wrong), ErrorType.NETWORK)
        }
    }

    enum class ErrorCodes(val value: String) {
        INTERNAL_ERROR("21000"),
        INVALID_MISSING("21001")
    }
}
