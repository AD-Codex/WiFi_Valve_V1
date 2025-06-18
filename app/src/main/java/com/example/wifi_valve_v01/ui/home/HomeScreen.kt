package com.example.wifi_valve_v01.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.wifi_valve_v01.models.ProductItem
import com.example.wifi_valve_v01.navigation.Screens
import com.example.wifi_valve_v01.data.productItem.toProductItem
import java.util.UUID


@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: ProductViewModel = viewModel()
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var productItems by remember { mutableStateOf(emptyList<ProductItem>()) }

    LaunchedEffect(Unit) {
        userViewModel.fetchProductItems()
    }

    val productItemsDB by userViewModel.productItems.collectAsState()
    Log.d("Homepage", productItemsDB.toString())
    productItems = remember(productItemsDB) {
        productItemsDB.map { it.toProductItem() }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                onClick = {
                    showAddDialog = true
                }
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
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(productItems) { item ->
                    ProductRow(
                        productItem = item,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("productItem", item)
                            when (item.product.productId) {
                                "1001"  -> navController.navigate(Screens.ValveDetail.route)
                                "1002"  -> navController.navigate(Screens.ValveDetail.route)
                                "2001" -> navController.navigate(Screens.ProductDetail.route)
                            }

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Main Row content (Image + Text)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image
                Image(
                    painter = rememberImagePainter(product.fullImageUrl),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                // Texts
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (productItem.nickname.isNotEmpty()) productItem.nickname else "No nickname",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Status bar aligned to end (right edge)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd) // align to right center of card
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(if (productItem.isOnline) Color.Green else Color.Red)
            )
        }
    }
}


