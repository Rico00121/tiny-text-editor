# Tiny Text Editor - Frontend Architecture

## Overview
The front-end of `Tiny Text Editor` is a text editor application based on Electron and React, designed to provide users with basic text manipulation functions, including insert, delete, copy, paste, cut, undo and redo operations. Additionally, it supports recording and replaying user actions. The UI is styled using Material-UI components and custom themes.

![Frontend Architecture](./img/frontend-arc.png "Frontend Architecture")

## Component Structure

### Main Components
- **ThemeProvider**: Applies the custom hacker theme (`hackerTheme`) to the entire application.
- **CssBaseline**: Ensures consistent baseline styles across different browsers.
- **Container**: Provides a responsive fixed-width container for the layout.
- **Box**: A flexible layout component used to structure the page.
- **Typography**: Used for displaying headings and text content.
- **TextField**: Input fields for user interaction.
- **Button**: Action buttons for various operations.
- **Paper**: Container for sections of the UI, providing elevation and padding.
- **Grid2**: Layout grid system to arrange elements in rows and columns.
- **List & ListItem**: Used to display the editor history log.

### State Management
The `TextEditor` component manages its state using React's `useState` hook:
- **inputText**: Holds the text entered by the user to be inserted into the editor.
- **outputText**: Displays the current content of the editor.
- **clipboardContent**: Stores the content copied to the clipboard.
- **history**: Logs of editor actions.
- **isRecording**: Tracks whether the editor is currently recording user actions.
- **beginIndex & endIndex**: Indices for selection range.
- **beginIndexError & endIndexError**: Flags to indicate errors in index input.
- **currentBeginIndex & currentEndIndex**: Current selection indices.

### Event Handlers
Event handlers are defined to manage user interactions:
- **handleBeginIndexChange & handleEndIndexChange**: Validate and update the selection indices.
- **updateStatus**: Updates the editor state based on the result from service calls.
- **handleRefresh**: Moves the selection to the end of the text.
- **handleInsert**: Inserts the input text at the current cursor position.
- **handleDelete**: Deletes the selected text.
- **handleMoveSelection**: Moves the selection to specified indices.
- **handleCopy, handlePaste, handleCut**: Perform clipboard operations.
- **handleStartRecord, handleStopRecord, handleReplayRecord**: Manage recording and replaying user actions.
- **handleUndo & handleRedo**: Undo or redo the last action.

## Service Integration
The `TextEditor` component interacts with the `editor-service` module to perform core editing operations such as:
- **copy, cut, deleteText, insertText, moveSelection, paste, redo, replayRecord, startRecord, stopRecord, undo**

## Styling
The application uses Material-UI's `ThemeProvider` to apply a custom theme (`hackerTheme`). The theme defines colors, typography, and other visual properties. Specific styles are applied to individual components to achieve the desired look and feel.

## Layout
The layout is divided into three main sections:
1. **Console Section**:
    - Buttons for starting/stopping recording, playing back recorded actions, and performing text manipulations.
    - Input fields for specifying selection indices and inserting new text.

2. **Editor State Section**:
    - Displays the current selection indices and provides a refresh button.
    - Shows the current content of the editor and the clipboard content.

3. **Editor History Section**:
    - Logs all editor actions in a scrollable list.

![Frontend Screenshot](./img/frontend-screenshot.png "Frontend Screenshot")

## Dependencies
- **React**: For building the UI components.
- **Material-UI**: For styling and layout components.
- **Axios**: Provides the core functionality for text manipulation and recording using Axios to communicate with the backend.
- **Electron**: For building cross-platform desktop applications using web technologies.
