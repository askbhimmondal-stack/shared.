# Shared Money — GitHub Android APK Project

## Features
- Admin login: `Admin` / `Neelam143`
- Shared Money / Cost Tracker UI
- LocalStorage persistence for expenses, income, debits, opening balance and budget
- Passbook with running balance
- **Save Passbook PDF** button
- On Android, the PDF button opens the system print dialog where the user can choose **Save as PDF**
- Browser fallback uses the browser print dialog
- GitHub Actions workflow for Android APK build

## GitHub APK build
1. Upload the project contents to a GitHub repository.
2. Push to the `main` branch.
3. Open **Actions**.
4. Run/open **Build Android APK**.
5. Download the generated APK artifact.

## PDF
Open **Passbook → Save Passbook PDF**. On the APK, Android's print dialog opens. Select **Save as PDF**, choose the destination, and save the passbook.

## Local data
The HTML app stores its application data in browser/WebView `localStorage`. The Android WebView is configured with DOM storage enabled, so the data remains on the device between app launches unless app data is cleared/uninstalled.
