# GPS Emulator App

A simple Android app for mocking GPS location on your Pixel 6 Pro.

## Features

- Simple ON/OFF toggle with visual indicator (red = OFF, green = ON)
- Mock GPS location to a specific coordinate
- Runs as a foreground service
- Scheduled to activate between 8:30 PM - 9:30 AM (configurable)
- No ads, minimal UI

## Requirements

- Android Studio (latest version recommended)
- Android device with Developer Options enabled
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)

## Setup Instructions

### 1. Open in Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to this project directory and open it
4. Wait for Gradle sync to complete (this may take a few minutes on first load)

### 2. Enable Developer Options on Your Device

1. Go to **Settings** > **About phone**
2. Tap **Build number** 7 times to enable Developer Options
3. Go back to **Settings** > **System** > **Developer options**
4. Enable **USB debugging**
5. Enable **Allow mock locations** or **Select mock location app** and choose "gpuemulator"

### 3. Connect Your Device

1. Connect your Pixel 6 Pro via USB
2. Accept the USB debugging prompt on your device
3. In Android Studio, select your device from the device dropdown

### 4. Run the App

1. Click the green "Run" button (▶) in Android Studio
2. Or press `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac)
3. The app will build and install on your device

## Using the App

1. **Grant Permissions**: On first launch, grant location permissions
2. **Toggle Service**: Tap the START button to activate mock location
   - The indicator will turn GREEN when active
   - The indicator will be RED when inactive
3. **Stop Service**: Tap the STOP button to deactivate

## Important Notes

### Mock Location Setup

For Android 6.0 and above:
1. Go to **Settings** > **System** > **Developer options**
2. Find **Select mock location app**
3. Select **gpuemulator** from the list

Without this setting, the app cannot mock your GPS location.

### Customizing Mock Location

To change the mock GPS coordinates, edit `LocationMockService.kt`:

```kotlin
private val mockLatitude = 25.002070   // Your latitude
private val mockLongitude = 121.544402  // Your longitude
```

### Customizing Schedule

To change the active hours, edit `ScheduledLocationReceiver.kt`:

```kotlin
val startTime = 20 * 60 + 30  // 8:30 PM (20:30 in 24-hour format)
val endTime = 9 * 60 + 30      // 9:30 AM (09:30 in 24-hour format)
```

## Troubleshooting

### Build Issues

If you encounter build errors:
1. Go to **File** > **Invalidate Caches / Restart**
2. Clean the project: **Build** > **Clean Project**
3. Rebuild: **Build** > **Rebuild Project**

### Gradle Sync Issues

If Gradle sync fails:
1. Check your internet connection
2. Ensure you have the latest version of Android Studio
3. Try **File** > **Sync Project with Gradle Files**

### App Crashes

If the app crashes:
1. Ensure all permissions are granted
2. Verify mock location is enabled for this app in Developer Options
3. Check Logcat in Android Studio for error messages

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/gpuemulator/
│   │   ├── MainActivity.kt              # Main UI with toggle
│   │   ├── LocationMockService.kt       # Background service for mock location
│   │   └── ScheduledLocationReceiver.kt # Time-based scheduler
│   ├── AndroidManifest.xml              # App configuration and permissions
│   └── res/                             # Resources (layouts, strings, etc.)
```

## License

This is a personal project for educational purposes.
