package com.bappun.brofolio.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bappun.brofolio.data.Asset

@ExperimentalFoundationApi
@Composable
fun Home(total: Float, assets: List<Asset>) {
    Column() {
        Row(
            horizontalArrangement = Arrangement.Center,
            // There is a default padding of 16.dp around the screen
            modifier = Modifier.padding(top = 48.dp, bottom = 64.dp).fillMaxWidth()
        ) {
            Text(
                "$total€",
                style = MaterialTheme.typography.h3,
            )
        }

        val gridItems = mutableListOf("Symbol", "Amount", "Price", "Total")
        assets.forEach { asset ->
            gridItems.add(asset.symbol)
            gridItems.add(asset.amount.toString())
            gridItems.add("${asset.price}€")
            gridItems.add("${asset.total()}€")
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(4)
        ) {
            itemsIndexed(gridItems) { item, _ ->
                Text(gridItems[item], modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}
