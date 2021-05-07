package com.bappun.brofolio.data

data class Asset(val symbol: String, var amount: Float, var price: Float) {
    fun total(): Float {
        return this.amount * this.price
    }
}
