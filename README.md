# 🦖 DinoGameKMP

A cross-platform remake of the classic Chrome Dino game built with **Kotlin Multiplatform**, **Jetpack Compose**, and **Compose Multiplatform**. Run, jump, and dodge cacti on Android and Desktop — all from a single codebase.

![DinoGame](https://github.com/rustembekov/DinoGameKMP/raw/main/assets/screenshot.png)

---

## ✨ Features

- 🧩 **Kotlin Multiplatform (KMP)** – Shared game logic across Android and Desktop
- 🎨 **Jetpack Compose UI** – Declarative UI for Android and Desktop
- 🖼️ **Lottie animation support on Android** (via [Lottie for Android](https://github.com/airbnb/lottie-android))
- 🛠️ **Manual animation implementation for Desktop**
- 🧱 **Modular architecture** – Clean separation of core logic, shared code, and platform-specific features
- 🎮 **Fun gameplay** – Inspired by the classic offline Dino runner

---

## 🖥️ Supported Platforms

- ✅ Android (Lottie-based animated dino)
- ✅ Desktop (manual frame-by-frame animation)

> iOS support could be added in future updates as the codebase is fully multiplatform-ready.

---

## 📦 Tech Stack

- Kotlin Multiplatform (KMP)
- Jetpack Compose (Android + Desktop)
- Gradle Kotlin DSL
- Clean Architecture Principles
- Lottie for Android animations
- Manual sprite animation for Desktop

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Kotlin 1.9+
- Java 17+
- Xcode (optional, for future iOS)

### Clone & Run

```bash
git clone https://github.com/rustembekov/DinoGameKMP.git
cd DinoGameKMP
