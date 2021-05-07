package com.bappun.brofolio.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bappun.brofolio.data.Account
import com.bappun.brofolio.data.Asset

class PortfolioViewModel: ViewModel() {

//    private val invested: Float = 0.0F

    private val _accounts = MutableLiveData(listOf<Account>())
    val accounts: LiveData<List<Account>> = _accounts

    private val _assets = MutableLiveData(listOf<Asset>())
    val assets: LiveData<List<Asset>> = _assets

    private val _total = MutableLiveData(0.0F)
    val total: LiveData<Float> = _total

//    fun getProfit(): Float {
//        return this.invested - this.getTotal()
//    }

    fun loadAccounts(context: Context) {
        this._accounts.value = Account.getAccounts(context)
    }
    fun addAccount(account: Account) {
        val currentAccounts = this._accounts.value ?: listOf()
        this._accounts.value = listOf(account, *currentAccounts.toTypedArray())
    }

    fun updateAssets() {
        val assets = mutableListOf<Asset>()
        this._accounts.value?.forEach { account -> assets += account.assets }
        this._assets.value = assets
    }

    fun updateTotal() {
        this._total.value = _assets.value?.fold(0.0F) { acc, asset -> acc + asset.total() }
    }

    fun update() {
        this.updateAssets()
        this.updateTotal()
    }
}


