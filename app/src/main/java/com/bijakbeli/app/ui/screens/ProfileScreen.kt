package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: BijakBeliViewModel,
    onLogoutClick: () -> Unit
) {
    var showBudgetDialog by remember { mutableStateOf(false) }
    var budgetInput by remember { mutableStateOf(viewModel.monthlyBudget.doubleValue.toString()) }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)) {
            // Header
            item {
                Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd)))) {
                    Column(modifier = Modifier.padding(top = 40.dp, bottom = 36.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(modifier = Modifier.size(100.dp), shape = CircleShape,
                            color = Color.White.copy(alpha = 0.12f),
                            border = BorderStroke(2.dp, Color.White.copy(alpha = 0.25f))) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(viewModel.userName.value, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        Text(viewModel.userEmail.value, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(shape = RoundedCornerShape(8.dp), color = AccentGold) {
                            Text("PREMIUM MEMBER", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }
            // Budget card
            item {
                Surface(modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-24).dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 8.dp) {
                    Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Monthly Budget", fontSize = 12.sp, color = TextSecondary)
                            Text("RM${String.format("%.2f", viewModel.monthlyBudget.doubleValue)}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = DeepTeal)
                        }
                        Button(onClick = { showBudgetDialog = true }, shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TealSurface)) {
                            Text("Edit Limit", color = DeepTeal, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
            // Settings
            item {
                PrSettingsGroup("Account Settings") {
                    PrSettingsItem("Personal Information", Icons.Outlined.Person)
                    PrSettingsItem("Payment Methods", Icons.Outlined.CreditCard)
                    PrSettingsItem("Order History", Icons.Outlined.Receipt)
                }
                PrSettingsGroup("App Preferences") {
                    PrSettingsItem("Notifications", Icons.Outlined.Notifications)
                    PrSettingsItem("Theme", Icons.Outlined.Palette)
                    PrSettingsItem("Language", Icons.Outlined.Language)
                }
                PrSettingsGroup("Support") {
                    PrSettingsItem("Help Center", Icons.Outlined.HelpOutline)
                    PrSettingsItem("Privacy Policy", Icons.Outlined.Security)
                }
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, WarmCoral),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WarmCoral)
                ) {
                    Icon(Icons.Outlined.Logout, contentDescription = null, tint = WarmCoral, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out Account", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (showBudgetDialog) {
            AlertDialog(
                onDismissRequest = { showBudgetDialog = false },
                title = { Text("Set Monthly Budget", fontWeight = FontWeight.Bold) },
                text = {
                    OutlinedTextField(value = budgetInput, onValueChange = { budgetInput = it },
                        label = { Text("Budget (RM)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DeepTeal))
                },
                confirmButton = {
                    Button(onClick = {
                        budgetInput.toDoubleOrNull()?.let { viewModel.monthlyBudget.doubleValue = it }
                        showBudgetDialog = false
                    }, colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)) { Text("Save") }
                },
                dismissButton = { TextButton(onClick = { showBudgetDialog = false }) { Text("Cancel", color = TextSecondary) } }
            )
        }
    }
}

@Composable
private fun PrSettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextSecondary, modifier = Modifier.padding(start = 4.dp, bottom = 12.dp))
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Column(content = content)
        }
    }
}

@Composable
private fun PrSettingsItem(label: String, icon: ImageVector) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.size(36.dp), shape = RoundedCornerShape(10.dp), color = TealSurface) {
            Box(contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(18.dp)) }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontWeight = FontWeight.Medium, fontSize = 15.sp, color = TextPrimary, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
    }
}
