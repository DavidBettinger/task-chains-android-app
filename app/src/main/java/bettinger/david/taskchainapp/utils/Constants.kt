package bettinger.david.taskchainapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Constants {


    const val BASE_URL: String = "http://192.168.178.117:8080/mobileApi/v1/"


    fun formatDate(date: String): String {
        //TODO Format date
        return date.substring(0..9)

    }
}