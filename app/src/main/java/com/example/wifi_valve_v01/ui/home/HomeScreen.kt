package com.example.wifi_valve_v01.ui.home

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wifi_valve_v01.models.Product
import com.example.wifi_valve_v01.models.ProductItem
import com.example.wifi_valve_v01.navigation.Screens
import java.util.UUID


@Composable
fun HomeScreen( navController: NavController) {
    var showAddDialog by remember { mutableStateOf(false) }
    var productItems by remember { mutableStateOf(emptyList<ProductItem>()) }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Product",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // <-- Important for proper insets
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(productItems) { item ->
                    ProductRow(
                        productItem = item,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("productItem", item)
                            navController.navigate(Screens.ProductDetail.route)

                        }
                    )
                }
            }
        }

        if (showAddDialog) {
            AddProductDialog(
                onDismiss = { showAddDialog = false },
                onAddProduct = { product ->
                    val newItem = ProductItem(
                        id = UUID.randomUUID().toString(),
                        product = product,
                        nickname = ""
                    )
                    productItems = productItems + newItem
                    showAddDialog = false
                }
            )
        }
    }


}



@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)

}


@Composable
fun ProductRow(productItem: ProductItem, onClick: () -> Unit) {
    val product = productItem.product

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image (scaled up)
            Image(
                painter = painterResource(id = product.imageId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(96.dp)
                    .padding(end = 16.dp)
            )

            // Name & Nickname stacked vertically
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (productItem.nickname.isNotEmpty()) productItem.nickname else "No nickname",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


