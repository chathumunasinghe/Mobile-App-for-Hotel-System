# 🏝️ LuxVista Resort — Android App

A native Android app for a resort/hotel booking system, backed by
**Firebase Realtime Database** and **Firebase Auth**. It has both a guest
side (browse rooms, offers, promotions, services, book a room, pay) and
an admin side (manage rooms, offers, promotions, services).

## Features

- **Guest**
  - Sign up / log in (Firebase Auth)
  - Browse rooms, offers, promotions, and services (each backed by its
    own Firebase Realtime Database node, with a `RecyclerView` + adapter)
  - Book a room and go through a payment flow
- **Admin**
  - Separate admin login
  - Add / edit rooms, offers, promotions, and services

## Tech stack

- Java, Android SDK (compileSdk 35, minSdk 24)
- Firebase: Realtime Database, Firestore, Auth
- Gradle (Kotlin DSL)
- RecyclerView, CardView, Glide (images), CircleImageView

## Project structure

```
LuxVistaResort/
├── app/src/main/java/com/example/myapplication/   # Activities, adapters, models
├── app/src/main/res/                                # Layouts, drawables, strings
├── database/firebase-realtime-db-export.json        # Sample DB export (for reference)
├── app/google-services.json.example                 # Template - see Setup below
└── build.gradle.kts / settings.gradle.kts
```

## Getting started

### Prerequisites

- Android Studio (Koala or newer recommended)
- JDK 17
- A Firebase project with Realtime Database, Firestore, and
  Authentication (Email/Password) enabled

### Setup

1. **Create a Firebase project** at https://console.firebase.google.com,
   add an Android app with package name `com.example.myapplication`, and
   download its `google-services.json`.

2. **Add your config file.** Copy your downloaded file to
   `app/google-services.json` (this filename is gitignored on purpose —
   see [Notes on this codebase](#notes-on-this-codebase) below).
   `app/google-services.json.example` shows the expected shape.

3. **(Optional) Seed the database.** `database/firebase-realtime-db-export.json`
   is a snapshot of the original data (Rooms, Offers, Promotions,
   Services, payments). You can import it via the Firebase console
   (Realtime Database → ⋮ → Import JSON) to get sample data to develop
   against.

4. **Set your database security rules.** The Realtime Database defaults
   to locked-down rules for new projects — you'll need rules that at
   least allow authenticated reads/writes for the app to work, and you
   should scope them appropriately for guest vs. admin access rather
   than leaving the database fully open.

5. **Open in Android Studio** and run. Gradle will sync automatically.

## Notes on this codebase

This started as an academic MAD (Mobile Application Development)
coursework project. Before publishing:

- **Removed committed build artifacts.** `app/build/`, `.gradle/`, and
  `.idea/` (generated caches, ~100MB) were being tracked; these are now
  gitignored and excluded, since they're regenerated automatically and
  bloat the repo.
- **Removed the live `google-services.json`.** It contained a real
  Firebase project ID and API key tied to the original developer's
  Firebase project. It's now gitignored, with `.example` template added
  instead — see Setup above.
- **Removed a stray unused image** (`pexels-photo-261411.jpeg`) that had
  been dropped in `app/` directly rather than a proper `res/drawable`
  location, and wasn't referenced anywhere in code.
- Firebase Realtime Database references (`FirebaseDatabase.getInstance()
  .getReference("Rooms")`, etc.) are already read from the configured
  project rather than hardcoded URLs — that part was already done well.

## Possible next steps

- Enforce Firebase Realtime Database security rules that distinguish
  guest vs. admin access, rather than relying on the app UI alone
- Add input validation on the booking/payment forms
- Consolidate the many numbered `Activity` classes (`FifthActivity`,
  `SixthActivity`, ...) into more descriptively named ones as the app
  grows
