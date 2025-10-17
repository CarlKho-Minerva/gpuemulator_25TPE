# Developer Guide - GPS Emulator App

## What Was Implemented

This Android app provides GPS mock location functionality with the following features:

### 1. **Simple Toggle UI** ✓
- Big circular indicator that glows RED when inactive, GREEN when active
- Simple START/STOP button
- Status text showing current state
- Schedule information display
- Clean, minimal design with no ads

### 2. **GPS Mock Location Service** ✓
- Foreground service that runs continuously when active
- Mocks GPS location to specific coordinates (default: Google HQ)
- Updates location every 2 seconds
- Notification shown when service is running
- Properly handles Android version differences

### 3. **Time-Based Scheduling** ✓
- Scheduler component that checks if current time is within active window
- Default: 8:30 PM - 9:30 AM
- Handles midnight crossing correctly
- Can be triggered automatically or manually

### 4. **Permission Handling** ✓
- Requests location permissions at runtime
- Handles Android 13+ notification permission
- Checks for mock location settings
- User-friendly error messages

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                        MainActivity                         │
│  - UI with Compose                                          │
│  - Permission handling                                      │
│  - Service control                                          │
└────────────────┬────────────────────────────────────────────┘
                 │
                 │ starts/stops
                 ▼
┌─────────────────────────────────────────────────────────────┐
│                   LocationMockService                       │
│  - Foreground service                                       │
│  - Creates notification                                     │
│  - Adds test provider (GPS_PROVIDER)                        │
│  - Updates mock location every 2 seconds                    │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│               ScheduledLocationReceiver                     │
│  - BroadcastReceiver for scheduled activation               │
│  - Checks time window (8:30 PM - 9:30 AM)                   │
│  - Starts/stops service automatically                       │
└─────────────────────────────────────────────────────────────┘
```

## File Structure

### Core Files

1. **MainActivity.kt** (241 lines)
   - Entry point of the app
   - Handles UI rendering with Jetpack Compose
   - Manages permissions (location, notifications)
   - Controls service lifecycle
   - Shows visual status indicator

2. **LocationMockService.kt** (167 lines)
   - Android Foreground Service
   - Creates notification channel for Android 8.0+
   - Manages GPS test provider
   - Updates mock location periodically
   - Handles cleanup on destroy

3. **ScheduledLocationReceiver.kt** (46 lines)
   - BroadcastReceiver for time-based activation
   - Calculates if current time is in active window
   - Handles midnight crossing logic
   - Can trigger service start/stop

### Configuration Files

1. **AndroidManifest.xml**
   - Declares required permissions:
     - `ACCESS_FINE_LOCATION`
     - `ACCESS_COARSE_LOCATION`
     - `ACCESS_MOCK_LOCATION` (special permission)
     - `FOREGROUND_SERVICE`
     - `FOREGROUND_SERVICE_LOCATION`
     - `POST_NOTIFICATIONS` (Android 13+)
   - Registers MainActivity, Service, and Receiver

2. **build.gradle.kts** (app level)
   - Android Gradle Plugin: 8.2.2
   - Kotlin: 1.9.22
   - Compose BOM: 2024.02.00
   - Min SDK: 24 (Android 7.0)
   - Target SDK: 34 (Android 14)
   - Compile SDK: 34

3. **libs.versions.toml**
   - Centralized version catalog
   - All dependency versions in one place

## Key Permissions Explained

### 1. Location Permissions
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```
Required to access and mock GPS location.

### 2. Mock Location Permission
```xml
<uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION" />
```
Special permission that allows the app to provide fake GPS coordinates. Must also be enabled in Developer Options.

### 3. Foreground Service Permissions
```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
```
Required to run a foreground service that handles location. Android 14+ requires specific foreground service type.

## How Mock Location Works

1. **Enable Test Provider**
   ```kotlin
   locationManager?.addTestProvider(
       LocationManager.GPS_PROVIDER,
       false, false, false, false, true, true, true, 3, 2
   )
   ```
   Creates a mock GPS provider that overrides real GPS.

2. **Set Mock Location**
   ```kotlin
   val location = Location(LocationManager.GPS_PROVIDER).apply {
       latitude = mockLatitude
       longitude = mockLongitude
       accuracy = 1f
       time = System.currentTimeMillis()
       elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
   }
   locationManager?.setTestProviderLocation(LocationManager.GPS_PROVIDER, location)
   ```
   Provides fake GPS coordinates to the system.

3. **Continuous Updates**
   - Timer runs every 2 seconds
   - Updates location each time
   - Keeps GPS "active" so apps read mock location

## User Instructions

### First Time Setup in Android Studio

1. **Open Project**
   - Launch Android Studio
   - Open this project folder
   - Wait for Gradle sync (may take 5-10 minutes first time)

2. **Enable Developer Options on Device**
   - Settings → About Phone
   - Tap "Build number" 7 times
   - Go back to Settings → System → Developer Options
   - Enable "USB Debugging"

3. **Set Mock Location App**
   - Developer Options → Select mock location app
   - Choose "GPS Emulator"

4. **Connect Device**
   - Connect Pixel 6 Pro via USB
   - Accept USB debugging prompt
   - Select device in Android Studio toolbar

5. **Run App**
   - Click green Run button (▶)
   - Or press Shift+F10
   - Grant permissions when prompted

### Customizing Coordinates

Edit `LocationMockService.kt` lines 23-24:
```kotlin
private val mockLatitude = 37.4219999   // Replace with your latitude
private val mockLongitude = -122.0840575 // Replace with your longitude
```

### Customizing Schedule

Edit `ScheduledLocationReceiver.kt` lines 27-28:
```kotlin
val startTime = 20 * 60 + 30  // 20:30 = 8:30 PM
val endTime = 9 * 60 + 30      // 09:30 = 9:30 AM
```

## Troubleshooting

### "Mock location is not enabled"
- Go to Developer Options
- Find "Select mock location app"
- Choose "GPS Emulator"

### Service doesn't start
- Check Logcat for errors
- Verify all permissions are granted
- Ensure foreground service permission is granted

### Location not mocking
- Other apps might need restart to pick up mock location
- Try toggling mock location off and on
- Check if Google Play Services respects mock locations

### Build fails in Android Studio
- File → Invalidate Caches / Restart
- Build → Clean Project
- Build → Rebuild Project
- Check internet connection (for Gradle dependencies)

## Testing Checklist

- [ ] App installs successfully
- [ ] Permissions are requested on first launch
- [ ] START button turns indicator green
- [ ] STOP button turns indicator red
- [ ] Notification appears when service is active
- [ ] Mock location works in Google Maps or other GPS apps
- [ ] Service persists when app is minimized
- [ ] Service stops properly when STOP is pressed

## Future Enhancements (Optional)

- [ ] Add ability to input custom coordinates in UI
- [ ] Add route simulation (moving along a path)
- [ ] Add AlarmManager integration for true scheduled activation
- [ ] Add ability to save multiple preset locations
- [ ] Add speed simulation for walking/driving
- [ ] Add battery optimization handling
- [ ] Add widget for quick toggle

## Known Limitations

1. **Mock Location Detection**: Some apps (banking, security apps) detect mock locations and may refuse to work
2. **Battery Usage**: Foreground service uses battery, but should be minimal
3. **Android Version Differences**: Some features work differently on different Android versions
4. **Developer Options Required**: User must enable mock location in Developer Options

## References

- [Android Location Documentation](https://developer.android.com/training/location)
- [Foreground Services](https://developer.android.com/develop/background-work/services/foreground-services)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Mock Location Testing](https://developer.android.com/training/location/location-testing)
