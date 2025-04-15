# Easter Egg Animation

A simple demonstration of composable animations in Jetpack Compose using an Easter egg that shakes and responds to user interaction.

## Features

- Animated Easter egg that shakes periodically
- Interactive touch feedback
- Toast messages indicating egg status
- Smooth animations using Jetpack Compose animation APIs
- Gradient backgrounds and styled text

## Technical Highlights

- Uses `Animatable` for controlling rotation, scale, and position
- Implements coroutine-based animation sequences
- Demonstrates state management with `remember` and `mutableStateOf`
- Shows proper composable structure and modifier usage
- Utilizes custom fonts and gradient styling

## Key Components

1. `EasterEggApp`: Main composable that manages the overall UI and state
2. `EasterEgg`: Handles the egg animation logic and user interaction
3. `Toast`: Displays status messages with gradient backgrounds
4. `Title`: Renders the styled "Shaky Egg" header

## Learning Points

- Animation coordination using multiple `LaunchedEffect` blocks
- State management in Compose
- Custom UI styling with gradients and fonts
- Gesture handling with `clickable` modifier
- Coroutine usage for timing and animation control

## Getting Started

- Just run the app and tap the egg when it starts shaking.
- The egg will animate between shaking states, and toast messages will indicate when you can interact with it.

## Demo

https://github.com/user-attachments/assets/81018d50-1cff-4790-a312-be131864a340
