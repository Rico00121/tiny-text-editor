package fr.istic.aco.editor.kernel;


import java.util.Stack;

/**
 * This class manages the undo, redo, and store operations for the editor.
 * It uses stacks to keep track of past and future states and commands.
 */
public class UndoManager {
    private final Stack<EditorSnapshot> pastStates;
    private final Stack<EditorSnapshot> futureStates;
    private final Stack<Pair<CommandOriginator, Memento>> pastCommands;
    private final Stack<Pair<CommandOriginator, Memento>> futureCommands;
    private final Engine engine;
    /**
     * Step size k, we store a snapshot after every k commands
     */
    int k = 5;
    private boolean isUndoRedo;

    /**
     * Constructor for UndoManager.
     *
     * @param engine the engine associated with this undo manager
     */
    public UndoManager(Engine engine) {
        this.engine = engine;
        pastStates = new Stack<>();
        pastStates.add(new EditorSnapshot(this.engine.getBufferContents(),
                engine.getSelection().getBeginIndex(),
                engine.getSelection().getEndIndex(),
                engine.getClipboardContents()));
        futureStates = new Stack<>();
        pastCommands = new Stack<>();
        futureCommands = new Stack<>();
    }

    /**
     * Stores the command upon each operation.
     *
     * @param originator the command originator to store
     */
    public void storeCommand(CommandOriginator originator) {
        if (!isUndoRedo) {
            clearFuture();
            Memento memento = originator.generateMemento();
            pastCommands.push(new Pair<>(originator, memento));
            this.storeSnapshotOrNot();
        }
    }

    /**
     * Clears the future states and commands.
     */
    private void clearFuture() {
        futureCommands.clear();
        futureStates.clear();
    }

    /**
     * Stores a snapshot of the editor if the number of commands is a multiple of k.
     */
    private void storeSnapshotOrNot() {
        int totalSize = this.pastCommands.size();
        if (totalSize % k == 0) {
            pastStates.push(engine.createSnapshot());
        }
    }

    /**
     * Restores the editor to the previous state.
     */
    private void backToPreviousState() {
        int totalSize = this.pastCommands.size();
        if (totalSize % k == 0) {
            futureStates.push(pastStates.pop());
        }
        engine.restoreFrom(pastStates.peek());
    }

    /**
     * This method is used to undo the last operation
     * It will restore the previous state of the editor and use k to apply the last k commands, and then the previous state becomes the current state
     * we then adjust the past and future states and commands accordingly
     */
    public void undo() {
        isUndoRedo = true;
        if (!pastCommands.isEmpty()) {
            backToPreviousState();

            futureCommands.push(pastCommands.pop());
            for (int i = (pastStates.size() - 1) * k; i < pastCommands.size(); i++) {

                Pair<CommandOriginator, Memento> currentPair = pastCommands.elementAt(i);
                CommandOriginator command = currentPair.first();
                Memento memento = currentPair.second();
                command.restoreFrom(memento);
                command.execute();
            }
        }
        isUndoRedo = false;
    }

    /**
     * Moves the nearest future state to the past if the number of commands is a multiple of k.
     */
    private void moveNearestFutureToPastOrNot() {
        int totalSize = this.pastCommands.size();
        if (totalSize % k == 0) {
            pastStates.push(futureStates.pop());
        }
    }

    /**
     * Redoes the last undo operation.
     */
    public void redo() {
        isUndoRedo = true;
        if (!futureCommands.isEmpty()) {
            Pair<CommandOriginator, Memento> currentPair = futureCommands.pop();
            pastCommands.push(currentPair);
            moveNearestFutureToPastOrNot();

            CommandOriginator command = currentPair.first();
            Memento memento = currentPair.second();

            command.restoreFrom(memento);
            command.execute();
        }
        isUndoRedo = false;
    }


}



