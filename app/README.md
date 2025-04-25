# CS 477 Lab #9 – BroadcastReceiver and Notifications

## Overview
This Android app demonstrates the use of a **BroadcastReceiver** to detect incoming **SMS messages** and display:
- A **Toast** message: "Text received"
- A **Notification** using the Android notification system

The app dynamically requests the necessary runtime permissions, supports API 26+, and includes compatibility for Android 13+ (with `POST_NOTIFICATIONS` permission).


---

### When a text (SMS) comes into the phone/emulator with the app installed:
1. Android System Broadcasts an Intent. Android sends a system-wide broadcast automatically.
2. MyReceiver (BroadcastReceiver) is triggered. This happens immediately even if the app is in the background or not open.

## File Descriptions

### `MainActivity.java`
- Requests runtime permissions (`RECEIVE_SMS` and `POST_NOTIFICATIONS` on API 33+).
- Registers the `BroadcastReceiver` dynamically if permissions are granted.
- Displays a simple UI with a `TextView`.
- `checkAndRequestPermissions()`: Verifies and requests required permissions.
- `onRequestPermissionsResult()`: Handles the result of permission requests and registers the receiver if allowed.

### `MyReceiver.java`
- The Android system sends out broadcasts when events like SMS arrival happen. It cannot be directly detected from a Activity. 
- Custom `BroadcastReceiver` to handle `SMS_RECEIVED` broadcasts.
- Register to listen for the "SMS_RECEIVED" intent.
- Define what should happen when the event is received.
- It still works in the background even when the app is not opened.
- Displays a `Toast` when an SMS is received.
- Creates a notification channel (on Android 8.0+).
- Shows a system notification with a title and message.
- `onReceive(Context context, Intent intent)`: Triggered when SMS is received.
- `createNotificationChannel(Context context)`: Initializes the channel required for notifications.
- `showNotification(Context context)`: Builds and sends a notification to the system tray.

---

### `AndroidManifest.xml`

- Declares:
    - Permissions:
        - `RECEIVE_SMS` (for receiving messages)
        - `POST_NOTIFICATIONS` (required on API 33+)
    - Features:
        - `telephony` hardware support (optional)
    - BroadcastReceiver:
        - Declares `MyReceiver` with intent filter for `SMS_RECEIVED`

### `activity_main.xml`

- Displays a simple `TextView` with the message.


