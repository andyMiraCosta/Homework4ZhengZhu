package com.Homework4ZhengZhu.hw4.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Homework4ZhengZhu.hw4.data.Bitcoin

@Composable
fun BitcoinList(
    onBitcoinClicked: (Bitcoin) -> Unit,
    bitcoins: List<Bitcoin>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(bitcoins) { bitcoin ->
            PetListItem(
                bitcoin = bitcoin,
                onBitcoinClicked = onBitcoinClicked
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetListItem(bitcoin: Bitcoin, onBitcoinClicked: (Bitcoin) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                    onBitcoinClickedClicked(bitcoin)
                }
        ) {
            AsyncImage(
                model = "https://cataas.com/cat/${bitcoin.id}",
                contentDescription = "Cute cat",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth
            )
            FlowRow(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                repeat(bitcoin.tags.size) {
                    SuggestionChip(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp),
                        onClick = { },
                        label = {
                            Text(text = bitcoin.tags[it])
                        }
                    )
                }
            }
        }
    }
}