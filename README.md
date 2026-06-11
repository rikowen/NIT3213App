# NIT3213 - Android Application Development

A three-screen Android application built with Kotlin demonstrating API integration, dependency injection, and Android development best practices.

## Screens

| Screen | Description |
|---|---|
| **Login** | Authenticates using first name and student ID via the VU NIT3213 API |
| **Dashboard** | Displays a list of entities from the API in a RecyclerView |
| **Details** | Shows the full description of a selected entity |

## Architecture

This app follows MVVM architecture:

```
Activity → Fragment → ViewModel → Repository → ApiService (Retrofit) → API
```

- **MVVM** — ViewModels expose LiveData UI state to Fragments
- **Hilt** — Dependency injection for ApiService, Repository, and ViewModels
- **Retrofit + Moshi** — HTTP networking and JSON parsing
- **Navigation Component** — Fragment navigation with SafeArgs
- **Coroutines** — Async API calls using viewModelScope
- **Repository Pattern** — Single source of truth for data

## How to Build and Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- Minimum device API 24

### Steps

1. Clone the repository:
```bash
   git clone https://github.com/rikowen/NIT3213App.git
```
2. Open the project in Android Studio
3. Wait for Gradle sync to complete
4. Click **Run ▶** or press `Shift + F10`

### Login Credentials
- **Username:** `Riko`
- **Password:** `s8117371`
- **API Base URL:** `https://nit3213api.onrender.com/`
- **Endpoint:** `/sydney/auth`

> Note: The API is hosted on a free plan and may take 30-60 seconds on first request.

## Running Unit Tests

```bash
./gradlew test
```

| Test File | Tests |
|---|---|
| `LoginViewModelTest` | Valid login, empty username, empty password, API failure, reset state |
| `DashboardViewModelTest` | Success state, error state, loading state |

## Dependencies

| Library | Purpose |
|---|---|
| Hilt 2.50 | Dependency injection |
| Retrofit 2.9.0 | HTTP networking |
| Moshi 1.15.0 | JSON parsing |
| Navigation Component 2.7.7 | Fragment navigation |
| Coroutines 1.7.3 | Async operations |
| JUnit 4 + Mockito | Unit testing |

## Student Information
- **Name:** Riko
- **Student ID:** s8117371
- **Course:** NIT3213 Mobile Application Development
- **University:** Victoria University Sydney
