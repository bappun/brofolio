package com.bappun.brofolio.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.bappun.brofolio.screens.services

class Account(val service: String) {

    val assets: List<Asset> = listOf(Asset("BTC", 1.0F, 1000.0F), Asset("ETH", 4.0F, 500.0F))

    companion object Factory {
        fun create(context: Context, service: String, apiKey: String, apiSecret: String): Account? {
            if (!services.contains(service)) throw error("Service not supported.")

            // Create a pref to store the service credentials
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val sharedPreferences = EncryptedSharedPreferences.create(
                service,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            val editor = sharedPreferences.edit()
            editor.putString("apiKey", apiKey)
            editor.putString("apiSecret", apiSecret)
            editor.apply()

            // Edit the list of connected services
            val defaultSharedPreferences = getDefaultSharedPreferences(context)
            val connectedServices = getConnectedServices(defaultSharedPreferences)

            // If the service already exists in the index we do not need to add it
            if (connectedServices.contains(service)) return null

            // Otherwise add the service to the index and return the service
            connectedServices.add(service)
            val defaultEditor = defaultSharedPreferences.edit()
            defaultEditor.putStringSet("connectedServices", connectedServices)
            defaultEditor.apply()

            return Account(service)
        }

        fun getAccounts(context: Context): List<Account> {
            val defaultSharedPreferences = getDefaultSharedPreferences(context)
            val connectedServices = getConnectedServices(defaultSharedPreferences)
            val accounts = mutableListOf<Account>()
            connectedServices.forEach { service ->
                accounts.add(Account(service))
            }
            return accounts.toList()
        }
    }

    fun getTotal(): Float {
        return 0.0F
//        return this.service.getTotal()
    }
}

fun getDefaultSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun getConnectedServices(sharedPreferences: SharedPreferences): MutableSet<String> {
    return sharedPreferences.getStringSet("connectedServices", null) ?: mutableSetOf()
}