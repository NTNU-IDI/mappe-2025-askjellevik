""""

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/NzRaDbQp)
# Portfolio project IDATT1003
This file uses Mark Down syntax. For more information see [here](https://www.markdownguide.org/basic-syntax/).

[//]: # (TODO: Fill inn your name and student ID)

STUDENT NAME = "André Skjellevik"  
STUDENT ID = "142168"

## Project description

[//]: # (TODO: Write a short description of your project/product here.)

## Project structure

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)
├───src
│   ├───main
│   │   └───java
│   │       └───edu
│   │           └───ntnu
│   │               └───idi
│   │                   └───idatt
│   │                       ├───controller
│   │                       │   ├───exceptions
│   │                       │   ├───utils
│   │                       │   └───validators
│   │                       ├───model
│   │                       │   │
│   │                       │   └───exceptions
│   │                       ├───view
│   │                       │   ├───actions
│   │                       │   └───menus
│   │                       └Main.java
│   └───test                
│       └───java
│           └───edu
│               └───ntnu
│                   └───idi
│                       └───idatt
│                           ├───controller
│                           │   └───validators
│                           └───model
## Link to repository

[//]: # (TODO: Include a link to your GitHub repository here.)
https://github.com/NTNU-IDI/mappe-2025-askjellevik

## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)
The project is set up with an execution plugin that is set to run the Main.java file (main class for starting the application).
To start the project this way, simply enter the following command in the terminal
`mvn exec:java`

However, it is recommended to build clean classes before you run the application. Combine this with the executing command
`mvn clean compile exec:java`

## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)
You can run the tests by running the following command in the terminal
`mvn test`

## References

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)

"""