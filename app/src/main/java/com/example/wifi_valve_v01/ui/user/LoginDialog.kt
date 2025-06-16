package com.example.wifi_valve_v01.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person

@Composable
fun LoginDialog(
    onDismiss: () -> Unit,
    onLogin: (username: String, password: String, onResult: (Boolean) -> Unit) -> Unit,
    isLoggedIn: Boolean,
    currentUsername: String?,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp, max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (isLoggedIn) "Logged in as" else "Login",
                    style = MaterialTheme.typography.titleLarge
                )

                if (isLoggedIn && currentUsername != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // User Icon
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User Icon",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Username
                        Text(
                            text = currentUsername ?: "Guest",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Buttons Row
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { onDismiss() }) {
                                Text("Close")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                onLogout()
                                onDismiss()
                            }) {
                                Text("Logout")
                            }
                        }
                    }

                } else {
                    // Login Form
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            error = null
                        },
                        label = { Text("Username") },
                        singleLine = true,
                        isError = error?.contains("username") == true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            error = null
                        },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        isError = error?.contains("password") == true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                when {
                                    username.isBlank() -> error = "Username is required"
                                    password.isBlank() -> error = "Password is required"
                                    else -> {
                                        isLoading = true
                                        onLogin(username, password) { success ->
                                            isLoading = false
                                            if (success) {
                                                onDismiss()
                                            } else {
                                                error = "Invalid credentials"
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text("Login")
                            }
                        }
                    }
                }
            }
        }
    }
}