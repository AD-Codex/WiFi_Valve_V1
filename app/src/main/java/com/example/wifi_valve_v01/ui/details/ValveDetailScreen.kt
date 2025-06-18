package com.example.wifi_valve_v01.ui.details

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wifi_valve_v01.models.ProductItem
import com.example.wifi_valve_v01.ui.wifi.DeviceConnectDialog
import com.example.wifi_valve_v01.ui.wifi.DeviceDiscoveryDialog

@Composable
fun ValveDetailScreen( productItem: ProductItem) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showWiFiDialog by remember { mutableStateOf(false) }
    var showConnectDialog by remember { mutableStateOf(false) }

    var nickname by remember { mutableStateOf(productItem.nickname) }
    val isOnline = productItem.isOnline

    val sensorValue = productItem.sensorValue

    var isManualOverride by remember { mutableStateOf(false) }
    var isValveOpen by remember { mutableStateOf(productItem.itemState) }
    var upperLimit by remember { mutableStateOf("75") }
    var lowerLimit by remember { mutableStateOf("25") }

    var selectedDevice by remember { mutableStateOf<String?>(null) }
    var scannedDevices by remember { mutableStateOf(listOf<String>()) }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // product details
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 1. Product Type
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Text(
                                text = "Product Type",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = productItem.product.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Divider(color = Color.Gray.copy(alpha = 0.3f))
                    }


                    item {
                        // 2. Nickname
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Nickname",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            TextButton(onClick = { showEditDialog = true }) {
                                Text("Edit")
                            }
                            Text(
                                text = nickname,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Divider(color = Color.Gray.copy(alpha = 0.3f))
                    }

                    item {
                        // 3. Online Status with Indicator Dot
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Connection States",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            val dotColor = if (isOnline) Color.Green else Color.Red
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(dotColor, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isOnline) "Online" else "Offline",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }



            }

            val isSensorValueAvailable = productItem.sensorValue.isNotEmpty()

            // attached sensor details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha( if (isSensorValueAvailable) 1f else 0.5f),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Text(
                                text = "moisture value",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = productItem.sensorValue,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }


            // valve open close
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Valve Controller",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = if (isManualOverride) "Auto" else "Manual")
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = isManualOverride,
                                    onCheckedChange = { if (isSensorValueAvailable) isManualOverride = it }
                                )
                            }
                        }

                        Divider(color = Color.Gray.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Left: Valve State
                                val leftEnabled = !isManualOverride
                                if (!leftEnabled) isValveOpen = false
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp)
                                        .alpha(if (leftEnabled) 1f else 0.4f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Valve State",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = if (isValveOpen) "Open" else "Close")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Switch(
                                            checked = isValveOpen,
                                            onCheckedChange = { if (leftEnabled) isValveOpen = it },
                                            enabled = leftEnabled
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .background(Color.Gray.copy(alpha = 0.3f))
                                )
                                // Right: Valve Auto
                                val rightEnabled = isManualOverride
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp)
                                        .alpha(if (rightEnabled) 1f else 0.4f),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Valve Auto",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = "Upper Limit", modifier = Modifier.weight(1f))
                                            TextField(
                                                value = upperLimit,
                                                onValueChange = { if (rightEnabled) upperLimit = it },
                                                modifier = Modifier.weight(1f),
                                                singleLine = true
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = "Lower Limit", modifier = Modifier.weight(1f))
                                            TextField(
                                                value = lowerLimit,
                                                onValueChange = { if (rightEnabled) lowerLimit = it },
                                                modifier = Modifier.weight(1f),
                                                singleLine = true
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            // wifi authentication setup
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "WiFi Authentication setup",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Button(onClick = { showWiFiDialog = true }) {
                                Text("Connect")
                            }
                        }
                    }
                }
            }


            // offline connection
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Offline Connect",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Button(onClick = { showConnectDialog = true }) {
                                Text("Connect")
                            }
                        }
                    }
                }
            }





        }
    }

    // Dialog to edit nickname
    if (showEditDialog) {
        var tempNickname by remember { mutableStateOf(nickname) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    nickname = tempNickname
                    showEditDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Nickname") },
            text = {
                OutlinedTextField(
                    value = tempNickname,
                    onValueChange = { tempNickname = it },
                    label = { Text("Nickname") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }

    if (showWiFiDialog) {
        WifiSetupDialog(
            onDismiss = { showWiFiDialog = false},
            onConnect = {ssid, password ->
                println("SSID: $ssid, Password: $password")
            }
        )
    }

    if (showConnectDialog) {
        DeviceDiscoveryDialog(
            onDismiss = { showConnectDialog = false },
            deviceList = scannedDevices,
            onFindDevices = {
                // Replace with real scan logic
                scannedDevices = listOf("Device_A", "Device_B", "Device_C")
            },
            onDeviceClick = { device ->
                selectedDevice = device
                showConnectDialog = true
            }
        )
    }
    if (showConnectDialog && selectedDevice != null) {
        DeviceConnectDialog(
            deviceName = selectedDevice!!,
            onDismiss = { showConnectDialog = false },
            onConnect = { password ->
                println("Connecting to $selectedDevice with password $password")
            }
        )
    }


}

