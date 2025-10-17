# 🚀 Getting Started - GPS Emulator

## Welcome!

Your GPS Emulator Android app is **ready to run from Android Studio**! 

This guide will walk you through opening and running the app on your Pixel 6 Pro.

---

## 📋 Prerequisites

Before you begin, make sure you have:

- [ ] **Android Studio** installed (latest version recommended)
- [ ] **Pixel 6 Pro** with USB cable
- [ ] **Internet connection** (for initial Gradle sync)
- [ ] **15 minutes** for first-time setup

---

## 🎯 Quick Start (5 Steps)

### Step 1: Open Project in Android Studio

1. Launch **Android Studio**
2. Click **"Open"** or **"File → Open"**
3. Navigate to the project folder: `gpuemulator_25TPE`
4. Click **"OK"**

### Step 2: Wait for Gradle Sync

⏳ **First time will take 5-10 minutes** - Android Studio is downloading dependencies

You'll see at the bottom:
```
Gradle sync in progress...
```

Wait until you see:
```
✅ Gradle sync finished
```

### Step 3: Enable Developer Options on Your Pixel 6 Pro

1. Open **Settings** on your phone
2. Scroll to **About phone**
3. Find **Build number**
4. **Tap it 7 times** rapidly
5. You'll see: "You are now a developer!"

### Step 4: Enable Mock Location for This App

1. Go back to **Settings**
2. Tap **System** → **Developer options**
3. Scroll down to find **"Select mock location app"**
4. Tap it and select **"GPS Emulator"**

### Step 5: Run the App!

1. Connect your Pixel 6 Pro via USB
2. Unlock your phone
3. Accept the **USB debugging** prompt (if shown)
4. In Android Studio, select your device from the dropdown
5. Click the green **▶ Run** button
6. Wait for the app to install and launch

---

## 🎨 Using the App

### First Launch

When the app opens for the first time, it will ask for permissions:

1. **Location Permission** → Tap **"While using the app"**
2. **Notification Permission** → Tap **"Allow"**

### The Interface

You'll see a simple screen with:

```
┌─────────────────────────┐
│                         │
│    GPS Emulator         │
│                         │
│      ┌───────┐          │
│      │  🔴   │          │  ← Big indicator
│      │  OFF  │          │
│      └───────┘          │
│                         │
│  GPS Mock Location      │
│      Inactive           │
│                         │
│    [  START  ]          │  ← Tap this!
│                         │
│  Schedule: 8:30PM-9:30AM│
└─────────────────────────┘
```

### To Start Mock Location

1. Tap the **"START"** button
2. The indicator turns **🟢 GREEN** and glows
3. You'll see a notification: "Mock location is active"
4. Your GPS is now mocked!

### To Stop Mock Location

1. Tap the **"STOP"** button
2. The indicator turns **🔴 RED**
3. The notification disappears
4. Normal GPS resumes

---

## 🔧 Troubleshooting

### Problem: Gradle Sync Fails

**Solution 1: Check Internet Connection**
- Make sure you're connected to the internet
- Gradle needs to download dependencies

**Solution 2: Invalidate Caches**
1. Go to **File → Invalidate Caches / Restart**
2. Click **"Invalidate and Restart"**

**Solution 3: Update Android Studio**
- Make sure you have the latest version

### Problem: Device Not Showing in Android Studio

**Solution 1: Check USB Debugging**
1. Disconnect and reconnect USB cable
2. On phone, go to **Developer Options**
3. Make sure **USB debugging** is ON
4. Accept the prompt on your phone

**Solution 2: Try Different USB Cable/Port**
- Use the official cable that came with your phone
- Try a different USB port on your computer

### Problem: "Allow mock locations" Not Found

**On Android 6.0+, it's called "Select mock location app":**
1. Settings → System → Developer options
2. Scroll down to **"Select mock location app"**
3. Choose **"GPS Emulator"**

### Problem: Mock Location Not Working

**Solution 1: Restart the App**
1. Force stop GPS Emulator
2. Open it again
3. Tap START

**Solution 2: Restart Other Apps**
- Apps like Google Maps need to be restarted to detect mock location
- Close and reopen the app that uses GPS

**Solution 3: Check Permissions**
1. Settings → Apps → GPS Emulator → Permissions
2. Make sure Location is set to "Allow all the time" or "Allow only while using the app"

### Problem: Build Errors in Android Studio

**Solution: Clean and Rebuild**
1. **Build → Clean Project**
2. Wait for it to finish
3. **Build → Rebuild Project**

---

## 🎓 Understanding the App

### What Does It Do?

The app creates a **fake GPS signal** that tricks other apps into thinking you're at a different location.

### How Does It Work?

1. **Test Provider**: The app registers itself as a mock GPS provider
2. **Continuous Updates**: It sends fake location updates every 2 seconds
3. **Foreground Service**: Runs in the background so it doesn't get killed

### What Location Does It Mock?

By default, it mocks **Google Headquarters**:
- Latitude: 37.4219999
- Longitude: -122.0840575

### Can I Change the Location?

Yes! See **"Customization"** section in `DEVELOPER_GUIDE.md`

### What's the Schedule For?

The app has built-in scheduling support for **8:30 PM to 9:30 AM**. This is the framework - you can extend it to auto-start/stop during these hours.

---

## 📚 Documentation

We've included comprehensive documentation:

### For Users:
- **README.md** - Setup instructions and basic usage
- **GETTING_STARTED.md** - This file!

### For Developers:
- **DEVELOPER_GUIDE.md** - Code architecture and customization
- **PROJECT_SUMMARY.md** - Feature overview
- **UI_DESIGN.md** - Visual design specifications

---

## ⚠️ Important Notes

### Mock Location Detection

Some apps can detect mock locations:
- Banking apps
- Security apps
- Some games

These apps may refuse to work when mock location is active.

### Battery Usage

The app uses a foreground service, which consumes some battery. However, it's optimized to be minimal.

### Privacy

The app runs locally on your device. It doesn't send your location anywhere.

---

## ✅ Success Checklist

After following this guide, you should have:

- [ ] Android Studio open with project loaded
- [ ] Gradle sync completed successfully
- [ ] Developer Options enabled on phone
- [ ] USB debugging enabled
- [ ] Mock location app set to "GPS Emulator"
- [ ] App installed on phone
- [ ] Permissions granted
- [ ] App running and showing red/green indicator
- [ ] Successfully tested START and STOP

---

## 🎉 You're Ready!

Your GPS Emulator app is now up and running!

### Next Steps:

1. **Test it**: Open Google Maps and tap START to see your location change
2. **Customize it**: Edit the coordinates in the code (see DEVELOPER_GUIDE.md)
3. **Use it**: Enable mock location when you need it

### Need Help?

Check the other documentation files:
- Technical issues → **DEVELOPER_GUIDE.md**
- Features → **PROJECT_SUMMARY.md**
- UI questions → **UI_DESIGN.md**

---

**Happy mocking! 🚀📍**
