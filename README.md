# Aurexa India Simulator

Aurexa India is a fictional, offline-first Android finance simulator. It is not affiliated with, endorsed by, or connected to American Express, any bank, card network, or payment provider. All balances, cards, and transaction examples are simulated and have no monetary value.

## Step 02 included
- Native Android application written in Java
- Five-tab navigation: Home, Cards, Activity, Insights, Profile
- Black and antique-gold visual identity
- Metallic fictional virtual card preview
- Dark/light theme switch in Profile
- Sample-only dashboard, activity, and insights
- GitHub Actions workflow for building a debug APK

## Open and run
1. Install Android Studio with JDK 17 support.
2. Open the `android` folder as the project.
3. Allow Gradle to sync and download dependencies.
4. Select an emulator or connect an Android device with USB debugging enabled.
5. Press Run.

To build an APK in Android Studio, select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Build with GitHub Actions
Push the repository to GitHub, open **Actions**, select **Android APK build**, and run the workflow. The debug APK will be available under the workflow run's Artifacts section.

## Requirements
- Android Studio with Android SDK 35
- JDK 17
- Minimum Android version: Android 6.0 (API 23)

## Next steps
Step 03 will improve the dashboard. Later steps add local SQLite storage and editable fictional financial controls.
