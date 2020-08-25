# Task-Chain Android App

An app to work on and complete tasks in a fixed order.

## Introduction

The purpose of this app is to work together with other people on completing lists (chains)

of tasks. These task-chains can be created with my [Task-Chains web app](https://github.com/DavidBettinger/task-chains) built with Java Spring Boot.

My aim for this project is primarily to learn how to build good android apps with kotlin.

### Examples of use

A task-chain could represent the things that need to be done in connection with the opening of a new branch. Such as:

1. To sign the rental agreement
2. Read the eletricity meter (at the new branch)
3. Register the new branch with the electricity provider (at the main office)
4. ...

### Planned and implemented functionalities

At the moment this project is in a very early development stage. So far you can:

- Use the "fake" login (real login is not yet implemented on the server side)
- Load the avaible task-chains from the server
- Navigate between the task-chains and tasks
- Add comments to tasks and save them on the device

There are many more features I plan to add to the app. At the moment the most important ones are:

- Improve the UI of the app
- Save changes (comments / completed tasks) on the server
- Add options/setting 
- Add a new comment indicator



### Technologies

- Kotlin
- Gradle
- Retrofit2
- RxJava / RxKotlin
- Android Room
- Hilt for dependency injection



### Other information

I started this project with a simple MVC architecture and then decided to switch to MVVM with Clean

Architecture. At the moment the refactoring is still not completly finished, so I am sure some code will

be moved around in the future.

Also, this is my first real android app and my first time using Kotlin and RxJava, so there are certainly

many things to improve. My goal is to make the code cleaner, better and add more tests as I work on the poject.
