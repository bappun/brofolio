package com.bappun.brofolio.api

abstract class AbstractAPI(apiKey: String, apiSecret: String) {

    // Total value of the account in €
    abstract fun getTotal(): Float

    // Get the list of assets available, quantity and price
    abstract fun getAssets(): List<Map<String, String>>
}