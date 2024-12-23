# Mini Text Editor

### Authors:

**1: Muhammad Ihtisham Alam Khan**  
**2: Ruikang Tao**

## Description:

Project for Object-Oriented Analysis and Design (ACO) 2024 M1 Informatique at the University of Rennes 1 ISTIC under the
supervision of **Adrian Le Roch**.

We have provided the list of the editor's [features](#Features), [commands](#Commands), and how
to [switch to different versions](#switch-to-different-versions), and instructions
on [building the editor from source code](#building-the-editor-from-source-code). Further documentation can be found
here:

- [Application design](./docs/design.md)
- [Application architecture](./docs/architecture.md)
- [Javadoc](./docs/javadoc/index.html)

PDF prints of the documentation can be found [here](./docs/pdf).

### Features

- **Tests**:
    - Test Driven Design (TDD) approach was followed.
    - Over 40 Unit tests as well as integration tests for all the features of the editor using JUnit 5.
    - Exception handling is tested for all the features of the editor.
  
  | Package                        | Class        | Method         | Line             |
  |--------------------------------|--------------|----------------|------------------|
  | all classes                    | 100% (27/27) | 94.8% (91/96)  | 	97.9% (233/238) |
  | fr.istic.aco.editor            | 100% (3/3)   | 73.3% (11/15)  | 	87.1% (27/31)   |
  | fr.istic.aco.editor.commands   | 100% (12/12) | 100% (35/35)   | 	100% (66/66)    |
  | fr.istic.aco.editor.controller | 	100% (4/4)  | 	100% (5/5)    | 	100% (21/21)    |
  | fr.istic.aco.editor.kernel     | 	100% (8/8)  | 	97.6% (40/41) | 	99.2% (119/120) |

- **Modular Backend**:
    - The application is built with Spring Boot, the industry standard for production-grade Java applications.
    - The backend is seperated into three main parts: Invoker, the kernel, and the controller layer.
    - The invoker has access to different commands and is used to perform different operations on the kernel.
    - The kernel is the main part of the application that holds the text and performs operations on it.
    - The controller layer is used to interact with the frontend.
    - The backend is designed to be computing resource friendly, efficient, easily extendable and maintainable.
    - The Command Design Pattern is used for invoking commands.
    - The Memento Design Pattern is used for the recording and undo/redo functionality.
- **REST API**:
    - We used REST API to communicate between the frontend and the backend.
    - This design was chosen because it is stateless, interoperable, scalable, decouples the client and server, and easy
      to maintain.
    - All endpoints are managed by the engine controller for easy API maintenance and extension.
- **Frontend**:
    - The frontend is build with electron and react for a modern and responsive user interface.
    - The frontend is designed to be user-friendly, easy to use, and visually appealing.
    - The frontend can insert, cut, copy, paste, delete, move selection or cursor, start recording, stop recording, play
      recording ,undo and redo.
    - Actions can be recorded and replayed.
    - Every action is logged in the frontend to give the user feedback on changes to the engine's state.

## Commands

| **Command** | **Description**                                                                          |
|-------------|------------------------------------------------------------------------------------------|
| Insert      | Type any single character                                                                |
| Select      | `SHIFT` + `→`, `SHIFT` + `←` or use the mouse                                            |
| Select all  | `SHIFT` + `A`                                                                            |
| Delete      | `Backspace`                                                                              |
| Copy        | `CTRL` + `C`                                                                             |
| Cut         | `CTRL` + `X`                                                                             |
| Paste       | `CTRL` + `V`                                                                             |
| Undo        | `CTRL` + `Z`                                                                             |
| Redo        | `CTRL` + `shift` + `z`                                                                   |
| Record      | Click the "Start Recording" button to start and click "Stop Recording" to stop recording |
| Replay      | Click the "Play Recording" button to replay all the actions                              |

### Environment Requirements

- Node.js v22 or later
- Java openjdk 17 or later
- Yarn v1.22 or later

## Building the editor from source code

### Clone the repository

```shell
git clone https://github.com/Rico00121/tiny-text-editor.git
```

### Start the server

> Make sure you are under the *editor2020* directory

```shell
./gradlew build
./gradlew bootRun
```

### Start the client app

```shell
cd tiny-editor-app
yarn install
yarn start
```

After running the above commands a window will open and user will be able to use our desktop client.

### Switch to different versions

We used tags to mark different versions of the editor.

```shell
git tag
```

This will show all the tags available.  
We have created four tags. v0,v1,v2 and v3 for convenience.

To go to v0 run the following command.

```shell
git checkout v0
```

To go to v1 run the following command.

```shell
git checkout v1
```

To go to v2 run the following command.

```shell
git checkout v2
```

To go to v3 run the following command.

```shell
git checkout v3
```