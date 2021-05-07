package com.bappun.brofolio.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.bappun.brofolio.data.Account


val services = listOf("binance", "kraken")

@Composable
fun Accounts(accounts: List<Account>?) {
    Column() {
        Text(
            "Accounts",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        accounts?.forEach { account ->
            Text(account.service, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}
