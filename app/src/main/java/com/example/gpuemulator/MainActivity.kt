package com.example.gpuemulator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gpuemulator.ui.theme.GpuemulatorTheme

class MainActivity : ComponentActivity() {
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            checkMockLocationSettings()
        } else {
            Toast.makeText(
                this,
                "Location permissions are required for this app",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        requestNecessaryPermissions()
        
        setContent {
            GpuemulatorTheme {
                MainScreen()
            }
        }
    }
    
    private fun requestNecessaryPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            checkMockLocationSettings()
        }
    }
    
    private fun checkMockLocationSettings() {
        // Check if mock location is enabled in developer options
        try {
            val mockLocationEnabled = Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.ALLOW_MOCK_LOCATION,
                0
            ) != 0
            
            if (!mockLocationEnabled && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Toast.makeText(
                    this,
                    "Please enable 'Allow mock locations' in Developer Options",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            // On Android 6.0+, mock location is set per-app in Developer Options
            // The user needs to manually set this app as the mock location app
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var isServiceRunning by remember { mutableStateOf(LocationMockService.isServiceRunning()) }
    
    // Update service status
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        isServiceRunning = LocationMockService.isServiceRunning()
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Status indicator
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(
                            elevation = if (isServiceRunning) 32.dp else 8.dp,
                            shape = CircleShape,
                            spotColor = if (isServiceRunning) Color.Green else Color.Red
                        )
                        .background(
                            color = if (isServiceRunning) Color.Green else Color.Red,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isServiceRunning) "ON" else "OFF",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Text(
                    text = if (isServiceRunning) 
                        "GPS Mock Location Active" 
                    else 
                        "GPS Mock Location Inactive",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Toggle button
                Button(
                    onClick = {
                        if (isServiceRunning) {
                            // Stop service
                            val intent = Intent(context, LocationMockService::class.java)
                            context.stopService(intent)
                            isServiceRunning = false
                        } else {
                            // Start service
                            val intent = Intent(context, LocationMockService::class.java)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(intent)
                            } else {
                                context.startService(intent)
                            }
                            isServiceRunning = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isServiceRunning) 
                            MaterialTheme.colorScheme.error 
                        else 
                            MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .width(200.dp)
                ) {
                    Text(
                        text = if (isServiceRunning) "STOP" else "START",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Schedule: 8:30 PM - 9:30 AM",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Note: This app requires 'Mock Location' to be enabled\nin Developer Options for this app.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }
}
