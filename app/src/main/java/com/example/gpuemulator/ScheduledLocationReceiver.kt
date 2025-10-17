package com.example.gpuemulator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

class ScheduledLocationReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent?) {
        // Check if current time is within the scheduled window (8:30 PM - 9:30 AM)
        if (shouldActivateMockLocation()) {
            // Start the mock location service
            val serviceIntent = Intent(context, LocationMockService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        } else {
            // Stop the mock location service
            val serviceIntent = Intent(context, LocationMockService::class.java)
            context.stopService(serviceIntent)
        }
    }
    
    companion object {
        fun shouldActivateMockLocation(): Boolean {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            
            val currentTimeInMinutes = hour * 60 + minute
            val startTime = 20 * 60 + 30  // 8:30 PM = 20:30
            val endTime = 9 * 60 + 30      // 9:30 AM = 09:30
            
            // The window spans across midnight, so we need special logic
            return if (startTime > endTime) {
                // Spans midnight: active from 20:30 to 23:59 and from 00:00 to 09:30
                currentTimeInMinutes >= startTime || currentTimeInMinutes <= endTime
            } else {
                // Normal case (doesn't span midnight)
                currentTimeInMinutes in startTime..endTime
            }
        }
    }
}
