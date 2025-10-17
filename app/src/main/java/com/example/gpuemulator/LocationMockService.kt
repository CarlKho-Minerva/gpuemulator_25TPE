package com.example.gpuemulator

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import java.util.Timer
import java.util.TimerTask

class LocationMockService : Service() {
    
    private var locationManager: LocationManager? = null
    private var timer: Timer? = null
    
    // Default mock location - No. 81號, Jingfeng St, Wenshan District, Taipei City, 116
    private val mockLatitude = 25.002070
    private val mockLongitude = 121.544402
    
    companion object {
        const val CHANNEL_ID = "LocationMockServiceChannel"
        const val NOTIFICATION_ID = 1
        
        private var isRunning = false
        
        fun isServiceRunning(): Boolean = isRunning
    }
    
    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        
        isRunning = true
        startMockingLocation()
        
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopMockingLocation()
        isRunning = false
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Location Mock Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
    
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("GPS Emulator")
            .setContentText("Mock location is active")
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .build()
    }
    
    private fun startMockingLocation() {
        try {
            // Enable mock location for GPS provider
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager?.addTestProvider(
                    LocationManager.GPS_PROVIDER,
                    false,
                    false,
                    false,
                    false,
                    true,
                    true,
                    true,
                    3,
                    2
                )
                locationManager?.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
                
                // Update location every 2 seconds
                timer = Timer()
                timer?.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        setMockLocation(mockLatitude, mockLongitude)
                    }
                }, 0, 2000)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun stopMockingLocation() {
        try {
            timer?.cancel()
            timer = null
            
            locationManager?.setTestProviderEnabled(LocationManager.GPS_PROVIDER, false)
            locationManager?.removeTestProvider(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun setMockLocation(latitude: Double, longitude: Double) {
        try {
            val location = Location(LocationManager.GPS_PROVIDER).apply {
                this.latitude = latitude
                this.longitude = longitude
                accuracy = 1f
                time = System.currentTimeMillis()
                elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    bearingAccuracyDegrees = 0.1f
                    verticalAccuracyMeters = 0.1f
                    speedAccuracyMetersPerSecond = 0.01f
                }
            }
            
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager?.setTestProviderLocation(LocationManager.GPS_PROVIDER, location)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
