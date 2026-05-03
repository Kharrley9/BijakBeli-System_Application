package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(GradientHeaderStart, GradientHeaderEnd)
                        )
                    )
            ) {
                // Background decoration
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.04f),
                        radius = 120.dp.toPx(),
                        center = Offset(size.width * 0.85f, size.height * 0.3f)
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.03f),
                        radius = 80.dp.toPx(),
                        center = Offset(size.width * 0.1f, size.height * 0.8f)
                    )
                }
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Join BijakBeli",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Start your smart shopping journey today",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 15.sp
                    )
                }
            }
        }

        // Registration Form Card
        item {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .offset(y = (-28).dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 12.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Create Account",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    RegisterInputField(
                        value = name,
                        onValueChange = { name = it; nameError = null },
                        label = "Full Name",
                        icon = Icons.Outlined.Person,
                        isError = nameError != null,
                        errorText = nameError
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RegisterInputField(
                        value = email,
                        onValueChange = { email = it; emailError = null },
                        label = "Email Address",
                        icon = Icons.Outlined.Email,
                        isError = emailError != null,
                        errorText = emailError
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RegisterInputField(
                        value = password,
                        onValueChange = { password = it; passwordError = null },
                        label = "Password",
                        icon = Icons.Outlined.Lock,
                        isError = passwordError != null,
                        errorText = passwordError,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RegisterInputField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it; confirmError = null },
                        label = "Confirm Password",
                        icon = Icons.Outlined.Lock,
                        isError = confirmError != null,
                        errorText = confirmError,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RegisterInputField(
                        value = budget,
                        onValueChange = { budget = it },
                        label = "Monthly Budget (RM)",
                        icon = Icons.Outlined.ShoppingCart,
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            var hasError = false
                            if (name.isBlank()) { nameError = "Name is required"; hasError = true }
                            if (!email.contains("@")) { emailError = "Invalid email"; hasError = true }
                            if (password.length < 6) { passwordError = "Min 6 characters"; hasError = true }
                            if (confirmPassword != password) { confirmError = "Passwords don't match"; hasError = true }
                            if (!hasError) onRegisterClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 2.dp)
                    ) {
                        Text("Create My Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Bottom Link
        item {
            Row(
                modifier = Modifier.padding(bottom = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already a member?", color = TextSecondary)
                TextButton(onClick = onLoginClick) {
                    Text("Log In Here", color = DeepTeal, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isError: Boolean = false,
    errorText: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            leadingIcon = {
                Icon(icon, contentDescription = null, tint = if (isError) ErrorRed else DeepTeal)
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = DividerColor,
                focusedBorderColor = DeepTeal,
                errorBorderColor = ErrorRed,
                unfocusedContainerColor = SurfaceVariant.copy(alpha = 0.3f),
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            isError = isError,
            singleLine = true
        )
        if (isError && errorText != null) {
            Text(
                errorText,
                color = ErrorRed,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}
