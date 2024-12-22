# **Architecture**

The architecture of the **Text Editor** is implemented with a modular architecture, utilizing the Command and Memento design patterns.  
It is built in a modular way. The frontend handles user interaction and communicates with the backend through the REST API. The backend, written in Java, handles the core logic and state management, while the REST API serves as the interface for communication between the frontend and backend.

This section explains the overall architectural structure of the application, explaining the key components, their interactions, and the design pattern implementations used to ensure maintainability, scalability, and performance.

Click here to go back to the [README](../README.md).

## **Table of Contents**

1. [Frontend](#frontend)
    - [HTML and CSS components](#html-and-css-components)
    - [Engine components](#engine-components)
    - [Logging](#logging)
2. [Backend](#backend)
    - [Components](#components)
        - [Engine](#engine)
        - [Invoker](#invoker)
        - [Selection](#selection)
        - [Command](#command)
        - [Memento](#memento)
    - [Service and Controller Layers](#service-and-controller-layers)
        - [EngineService](#engineservice)
        - [EngineController](#enginecontroller)
3. [Component relationships](#component-relationships)

---

## **Backend**
## **Components**

### **Engine**

The **Engine** is responsible for managing the buffer (which is basically the entire text in the editor), and clipboard operations. It interacts with **SelectionImpl** class via **Selection** interface to handle all the operations related to selection. It is the heart of the text editor, handling the core logic for editing text.

- **Interface**: `Engine`
    - Defines the methods needed for the engine of a simple text editor.

- **Class**: `EngineImpl`
    - Implements the `Engine` interface to interact with the engine state.
    - Manages the **text buffer**, where the main text is stored.
    - Handles **clipboard operations**, such as copy, cut, paste and delete.
    - Maintains an instance of **Selection**, the currently selected portion of the text in the buffer.
    - Supports operations like `insert`, `delete`, `copySelectedText`, `cutSelectedText`, and `pasteClipboard`.
    - In v3, it is used as an originator for the Memento design pattern. And is used to save and when needed, restore, a snapshot of an engine state. This is part of the implementation for the redo and undo operations.

### **Invoker**
- **Class**: `Invoker`
    - Responsible for invoking commands, decoupling the actual execution of commands from the application logic.
    - Part of the command design pattern implementation.
    - It is able to execute commands and add new commands.

### **Selection**
The **Selection** component has information about the start and end indices of the selected text. It is used to manage the selection of text in the editor.
This component interacts with `EngineImpl` to perform operations on the selected text.

- **Interface**: `Selection`
    - Abstracts the concept of selecting text with methods to get and set selection indices.

- **Class**: `SelectionImpl`
    - Implements the `Selection` interface to manage the start (`beginIndex`) and end (`endIndex`) of the selection.

- **Class**: `SelectionDto`
    - Defines a class consisting of primitive types for parsing the `beginIndex` and the `endIndex` when updating the selection via the API.
