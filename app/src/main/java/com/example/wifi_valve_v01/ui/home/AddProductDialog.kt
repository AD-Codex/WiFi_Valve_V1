package com.example.wifi_valve_v01.ui.home


import android.app.appsearch.Migrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.wifi_valve_v01.models.Product
import androidx.compose.ui.unit.dp
import com.example.wifi_valve_v01.R


@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAddProduct: (Product) -> Unit
) {
    val availableProducts = remember {
        listOf(
            Product(
                productId = "v001",
                name = "WiFi valve v1",
                description = "Basic motorize valve control over WiFi",
                imageId = R.drawable.v001
            ),
            Product(
                productId = "v002",
                name = "WiFi valve v2",
                description = "Motorize valve and Soil moisture sensors control over WiFi",
                imageId = R.drawable.v002
            ),
            Product(
                productId = "C001",
                name = "Controller Unit",
                description = "Universal Controller unit",
                imageId = R.drawable.c001)
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ){
                        Text(
                            text = "Add Products",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.align(Alignment.TopEnd))
                        {
                            Icon(Icons.Default.Close, contentDescription = "close")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(availableProducts) { product ->
                        ProductItem(
                            product = product,
                            onAddClick = { onAddProduct(product)}
                        )
                    }
                }


            }
        }
    }

}



@Composable
private fun ProductItem(
    product: Product,
    onAddClick: () -> Unit
) {
    Card(
        onClick = onAddClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource( id = product.imageId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }

    }
}