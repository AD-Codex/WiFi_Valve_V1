package com.example.wifi_valve_v01.ui.details

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.wifi_valve_v01.models.ProductItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productItem: ProductItem){
    var nicknameState by remember { mutableStateOf(TextFieldValue(productItem.nickname)) }

    Scaffold {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Product Type: ${productItem.product.name}", style = MaterialTheme.typography.bodyLarge)

            OutlinedTextField(
                value = nicknameState,
                onValueChange = { nicknameState = it },
                label = { Text("Nickname") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Status: ${if (productItem.isOnline) "Online" else "Offline"}",
                style = MaterialTheme.typography.bodyLarge,
                color = if (productItem.isOnline) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}