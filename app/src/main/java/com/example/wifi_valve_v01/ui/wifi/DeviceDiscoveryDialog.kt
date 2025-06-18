package com.example.wifi_valve_v01.ui.wifi

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeviceDiscoveryDialog(
    onDismiss: () -> Unit,
    deviceList: List<String>,
    onFindDevices: () -> Unit,
    onDeviceClick: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Available Devices", style = MaterialTheme.typography.titleMedium)

                Button(onClick = onFindDevices, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Find Device")
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (deviceList.isEmpty()) {
                    Text("No devices found", style = MaterialTheme.typography.bodyMedium)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        deviceList.forEach { deviceName ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onDeviceClick(deviceName) },
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                tonalElevation = 2.dp,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = deviceName,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
                    Text("Close")
                }
            }
        }
    }

}