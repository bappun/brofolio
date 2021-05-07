package com.bappun.brofolio.api

class Binance(apiKey: String, apiSecret: String): AbstractAPI(apiKey, apiSecret) {

    private val name: String = "binance"

    override fun getTotal(): Float {
        TODO("Not yet implemented")
    }

    override fun getAssets(): List<Map<String, String>> {
        TODO("Not yet implemented")
    }

}