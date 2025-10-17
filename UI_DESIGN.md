# GPS Emulator UI Design

## App Interface

The app features a minimal, single-screen interface with a large visual indicator.

### OFF State (Red) 🔴
```
┌─────────────────────────────────────┐
│                                     │
│          GPS Emulator               │
│                                     │
│                                     │
│         ╔═══════════════╗           │
│         ║               ║           │
│         ║               ║           │
│         ║      🔴       ║           │
│         ║     OFF       ║           │
│         ║               ║           │
│         ║  (Red Glow)   ║           │
│         ║               ║           │
│         ╚═══════════════╝           │
│                                     │
│                                     │
│    GPS Mock Location Inactive       │
│                                     │
│                                     │
│      ┌─────────────────┐            │
│      │     START       │            │
│      └─────────────────┘            │
│                                     │
│   Schedule: 8:30 PM - 9:30 AM      │
│                                     │
│  Note: Requires 'Mock Location'    │
│  enabled in Developer Options      │
│                                     │
└─────────────────────────────────────┘
```

### ON State (Green) 🟢
```
┌─────────────────────────────────────┐
│                                     │
│          GPS Emulator               │
│                                     │
│                                     │
│         ╔═══════════════╗           │
│         ║               ║           │
│         ║               ║           │
│         ║      🟢       ║           │
│         ║      ON       ║           │
│         ║               ║           │
│         ║ (Green Glow)  ║           │
│         ║               ║           │
│         ╚═══════════════╝           │
│                                     │
│                                     │
│     GPS Mock Location Active        │
│                                     │
│                                     │
│      ┌─────────────────┐            │
│      │      STOP       │            │
│      └─────────────────┘            │
│                                     │
│   Schedule: 8:30 PM - 9:30 AM      │
│                                     │
│  Note: Requires 'Mock Location'    │
│  enabled in Developer Options      │
│                                     │
└─────────────────────────────────────┘
```

## Visual Features

### Status Indicator
- **Size**: 200dp x 200dp circle
- **OFF State**: 
  - Background: Red (#FF0000)
  - Shadow: 8dp elevation with red spot color
  - Text: "OFF" in white, bold, 48sp
- **ON State**:
  - Background: Green (#00FF00)
  - Shadow: 32dp elevation with green spot color (glowing effect)
  - Text: "ON" in white, bold, 48sp

### Toggle Button
- **Width**: 200dp
- **Height**: 56dp
- **OFF State**: Primary color button with "START" text
- **ON State**: Error/red color button with "STOP" text
- **Text**: Bold, 18sp

### Status Text
- **Font Size**: 20sp
- **Weight**: Medium
- **Text**: 
  - OFF: "GPS Mock Location Inactive"
  - ON: "GPS Mock Location Active"

### Schedule Info
- **Font Size**: 14sp
- **Color**: On-surface variant (gray)
- **Text**: "Schedule: 8:30 PM - 9:30 AM"

### Note Text
- **Font Size**: 12sp
- **Color**: On-surface variant (gray)
- **Alignment**: Center
- **Text**: Multi-line instruction about Developer Options

## Material Design 3

The app follows Material Design 3 principles:
- **Dynamic Colors**: Adapts to system theme
- **Elevation**: Uses shadow to create depth
- **Typography**: Roboto font family
- **Spacing**: Consistent 8dp grid system
- **Colors**: Material color scheme
- **Dark Mode**: Automatically supports dark theme

## Interaction Flow

```
┌──────────────┐
│  App Launch  │
│              │
└──────┬───────┘
       │
       ▼
┌──────────────────────┐
│ Request Permissions  │
│ - Location          │
│ - Notifications     │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│  Show Main Screen   │
│  (Red OFF State)    │
└──────┬───────────────┘
       │
       │ User taps START
       ▼
┌──────────────────────┐
│  Start Service      │
│  Turn Green         │
│  Show Notification  │
└──────┬───────────────┘
       │
       │ Mock location active
       ▼
┌──────────────────────┐
│  Service Running    │
│  GPS mocked         │
└──────┬───────────────┘
       │
       │ User taps STOP
       ▼
┌──────────────────────┐
│  Stop Service       │
│  Turn Red           │
│  Remove Notification│
└──────────────────────┘
```

## Notification

When service is active, a persistent notification is shown:

```
╔════════════════════════════════╗
║ 📍 GPS Emulator                ║
║ Mock location is active        ║
╚════════════════════════════════╝
```

- **Icon**: Map icon (android.R.drawable.ic_dialog_map)
- **Title**: "GPS Emulator"
- **Text**: "Mock location is active"
- **Priority**: Low (doesn't disturb user)
- **Ongoing**: Yes (can't be swiped away)

## Permissions Dialog

On first launch, Android shows permission requests:

```
┌────────────────────────────────────┐
│  Allow GPS Emulator to access      │
│  this device's location?           │
│                                    │
│  [ While using the app ]           │
│  [ Only this time      ]           │
│  [ Don't allow         ]           │
└────────────────────────────────────┘

┌────────────────────────────────────┐
│  Allow GPS Emulator to send you    │
│  notifications?                    │
│                                    │
│  [ Allow      ]                    │
│  [ Don't allow ]                   │
└────────────────────────────────────┘
```

## Developer Options Setup

User needs to manually configure:

```
Settings → System → Developer Options
           ↓
      Select mock location app
           ↓
      Choose "GPS Emulator"
```

## Color Scheme

### Light Mode
- **Primary**: Material Blue (#2196F3)
- **Error**: Material Red (#F44336)
- **Success**: Material Green (#4CAF50)
- **Background**: White (#FFFFFF)
- **Surface**: Light Gray (#F5F5F5)
- **On-Surface**: Dark Gray (#424242)

### Dark Mode
- **Primary**: Light Blue (#64B5F6)
- **Error**: Light Red (#E57373)
- **Success**: Light Green (#81C784)
- **Background**: Very Dark Gray (#121212)
- **Surface**: Dark Gray (#1E1E1E)
- **On-Surface**: Light Gray (#E0E0E0)

## Responsive Design

The app adapts to:
- Different screen sizes (phones, tablets)
- Portrait and landscape orientations
- System font size settings
- Dark/light theme preferences
- System language settings

## Accessibility

- Large touch targets (48dp minimum)
- High contrast colors
- Screen reader support
- Semantic content descriptions
- Readable font sizes

## Animation

- Smooth transitions when toggling on/off
- Elevation change creates depth perception
- Color transition from red to green
- Shadow intensity changes for "glow" effect

---

**This design ensures the app is:**
- ✅ Simple and intuitive
- ✅ Visually clear about status
- ✅ Easy to use with one hand
- ✅ Accessible to all users
- ✅ Following Material Design guidelines
