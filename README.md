# Kotlin Multiplatform Project

This is a Kotlin Multiplatform project targeting Android, iOS, and Desktop platforms. The project structure is designed to maximize code sharing while also allowing platform-specific customization.

## Project Structure

- **`/composeApp`**  
  This directory contains code that will be shared across your Compose Multiplatform applications.  
  It is organized into several subfolders:
  - **`commonMain`**: Code that’s common for all platforms (Android, iOS, Desktop).
  - **Platform-Specific Folders**: Kotlin code specific to the respective platform:
    - **`androidMain`**: Android-specific code.
    - **`iosMain`**: iOS-specific code (e.g., calling Apple's CoreCrypto APIs).
    - **`desktopMain`**: Desktop-specific code.

- **`/iosApp`**  
  This folder contains the entry point for your iOS application. Even if you're sharing UI code with Compose Multiplatform, you need this folder for iOS-specific setup.  
  You can also add SwiftUI code for iOS-specific customizations here.

## Setup Instructions

1. **Clone the repository**:
   Clone this project to your local machine using the following command:
   ```bash
   git clone https://github.com/ChunMaruDance/bookpedia.git
