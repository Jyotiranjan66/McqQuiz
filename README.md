# 🧠 MCQ Quiz

A modern Android Quiz application built using **Jetpack Compose**, **Clean Architecture**, **MVI**, and **Hilt**.

This project demonstrates scalable Android architecture, state-driven UI, smooth animations, and modern Android development best practices.

---

# ✨ Features

- Splash Screen
- Local JSON Parsing using Kotlin Serialization
- Clean Architecture
- MVI Architecture
- Hilt Dependency Injection
- Jetpack Compose UI
- Material 3
- Animated Quiz Flow
- Streak System
- Skip Question
- Automatic Question Transition
- Animated Result Screen
- Responsive UI
- Dark Theme
- Edge-to-Edge Support

---

# 🏗️ Architecture

```
Presentation (Compose + MVI)
            │
            ▼
       ViewModel
            │
            ▼
        Use Cases
            │
            ▼
      Repository
            │
            ▼
      Local JSON Assets
```

---

# 📂 Project Structure

```
com.demo.mcqquiz

├── data
│   ├── datasource
│   ├── mapper
│   ├── model
│   └── repository
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── di
│
└── presentation
    ├── splash
    ├── quiz
    ├── result
    ├── navigation
    └── theme
```

---

# 🧰 Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Hilt
- StateFlow
- MVI
- Kotlin Serialization
- Navigation Compose
- Coroutines
- Clean Architecture

---

# 📦 Data Source

The application loads questions from a local JSON file located in:

```
app/src/main/assets/questions.json
```

The JSON is parsed using Kotlin Serialization into a `List<Question>`.

---

# 🎯 Quiz Flow

- Load questions from local JSON
- Display one question at a time
- Select an answer
- Reveal the correct answer
- Automatically move to the next question after 2 seconds
- Skip question support
- Track consecutive correct answers
- Highlight streak after 3 consecutive correct answers
- Display quiz summary after the last question

---

# 🔥 Streak Logic

- Correct answer → Increase streak
- Wrong answer → Reset streak
- Highest streak tracked throughout the quiz
- Animated streak badge for better user engagement

---

# 🎨 UI Highlights

- Animated Progress Header
- Animated Question Card
- Animated Option Cards
- Animated Streak Badge
- Animated Result Screen
- Animated Splash Screen
- Material 3 Components
- Smooth Screen Transitions
- Responsive Layout

---

# 👨‍💻 Author

**Jyotiranjan Mishra**

GitHub:
https://github.com/Jyotiranjan66
