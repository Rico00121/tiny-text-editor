package fr.istic.aco.editor.kernel;


import java.util.Stack;

/**
 * This class manages the undo, redo and store operations.
 */
public class UndoManager {
    private final Stack<EditorSnapshot> pastStates;
    private final Stack<EditorSnapshot> futureStates;
    private final Stack<Pair<CommandOriginator, Memento>> pastCommands;
    private final Stack<Pair<CommandOriginator, Memento>> futureCommands;
    int k = 5; //we store a snapshot after every k commands
    private final Engine engine;
    private boolean isUndoRedo;
    /**
     * constructor for UndoManager
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
     * This method is used to store the commands upon each operation
     */
    public void storeCommand(CommandOriginator originator) {
        if (!isUndoRedo) {
            clearFuture();
            Memento memento = originator.generateMemento();
            pastCommands.push(new Pair<>(originator, memento));
            this.storeSnapshotOrNot();
        }
    }

    private void clearFuture() {
        futureCommands.clear();
        futureStates.clear();
    }

    /**
     * This method is used to store the snapshot of the editor upon each k operations
     */
    private void storeSnapshotOrNot() {
        int totalSize = this.pastCommands.size();
        if ( totalSize % k == 0) {
            pastStates.push(engine.createSnapshot());
        }
    }

    private void backToPreviousState() {
        int totalSize = this.pastCommands.size();
        if ( totalSize % k == 0) {
            futureStates.push(pastStates.pop());
        }
        engine.restoreFrom(pastStates.peek());
    }

    /**
     * This method is used to undo the last operation
     * It will restore the previous state of the editor and use k to apply the last k commands, and then that state becomes the current state
     * we then adjust the past and future states and commands accordingly
     */
    public void undo() {
        isUndoRedo = true;
        if (!pastCommands.isEmpty()) {
            backToPreviousState();

            futureCommands.push(pastCommands.pop());
            for (int i = (pastStates.size() - 1) * k; i <pastCommands.size(); i++) {

                Pair<CommandOriginator, Memento> currentPair = pastCommands.elementAt(i);
                CommandOriginator command = currentPair.first();
                Memento memento = currentPair.second();
                command.restoreFrom(memento);
                command.execute();
            }
        }
        isUndoRedo = false;
    }

    private void moveLastFutureToPastOrNot() {
        int totalSize = this.pastCommands.size();
        if ( totalSize % k == 0) {
            pastStates.push(futureStates.pop());
        }
    }

    public void redo() {
        isUndoRedo = true;
        if (!futureCommands.isEmpty()) {
            Pair<CommandOriginator, Memento> currentPair = futureCommands.pop();
            pastCommands.push(currentPair);
            moveLastFutureToPastOrNot();

            CommandOriginator command = currentPair.first();
            Memento memento = currentPair.second();

            command.restoreFrom(memento);
            command.execute();
        }
        isUndoRedo = false;
    }


}



