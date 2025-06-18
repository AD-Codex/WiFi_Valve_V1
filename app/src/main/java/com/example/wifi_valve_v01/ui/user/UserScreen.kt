package com.example.wifi_valve_v01.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wifi_valve_v01.data.ApiClient
import com.example.wifi_valve_v01.data.user.UpdateUserRequest
import com.example.wifi_valve_v01.data.user.UserRepository
import com.example.wifi_valve_v01.utils.TokenManager
import kotlinx.coroutines.launch

@Composable
fun UserScreen(userViewModel: UserViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        userViewModel.refreshUser()
    }

    val user by userViewModel.user.collectAsState()

    // Editable fields
    var phoneNumberState by remember { mutableStateOf(TextFieldValue(user.phone)) }
    var emailState by remember { mutableStateOf(TextFieldValue(user.email)) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(user) {
//        userViewModel.refreshUser()
        phoneNumberState = TextFieldValue(user.phone)
        emailState = TextFieldValue(user.email)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // User Icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                tint = MaterialTheme.colorScheme.primary
            )

            // User Name (read-only)
            OutlinedTextField(
                value = user.username,
                onValueChange = {},
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false // Make it read-only
            )

            // Phone Number
            OutlinedTextField(
                value = phoneNumberState,
                onValueChange = { phoneNumberState = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            // Email
            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth()
            )

            // Update Button
            Button(
                onClick = {
                    if (user.id != -1) {
                        userViewModel.updateUser(
                            email = emailState.text,
                            phone = phoneNumberState.text
                        ) { success ->
                            scope.launch {
                                val message = if (success) "User updated" else "Failed to update"
                                snackbarHostState.showSnackbar(message)
                            }
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen()
}