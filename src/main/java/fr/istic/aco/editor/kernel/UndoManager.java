package fr.istic.aco.editor.kernel;


import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the undo, redo and store operations.
 */
public class UndoManager {
    private final List<EditorSnapshot> pastStates;
    private final List<EditorSnapshot> futureStates;
    private final List<Pair<CommandOriginator, Memento>> pastCommands;
    private final List<Pair<CommandOriginator, Memento>> futureCommands;
    int k = 5; //we store a snapshot after every k commands
    private final Engine engine;
    private boolean isUndoRedo;
    /**
     * constructor for UndoManager
     */
    public UndoManager(Engine engine) {
        this.engine = engine;
        pastStates = new ArrayList<>();
        pastStates.add(new EditorSnapshot(this.engine.getBufferContents(),
                engine.getSelection().getBeginIndex(),
                engine.getSelection().getEndIndex(),
                engine.getClipboardContents()));
        futureStates = new ArrayList<>();
        pastCommands = new ArrayList<>();
        futureCommands = new ArrayList<>();
    }

    /**
     * This method is used to store the commands upon each operation
     */
    public void storeCommand(CommandOriginator originator) {
        if (!isUndoRedo) {
            clearFuture();
            Memento memento = originator.generateMemento();
            pastCommands.add(new Pair<>(originator, memento));
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
            pastStates.add(engine.createSnapshot());
        }
    }

    private void backToPreviousState() {
        int totalSize = this.pastCommands.size();
        if ( totalSize % k == 0) {
            futureStates.add(pastStates.remove(getEndIndex(pastStates)));
        }
        engine.restoreFrom(pastStates.get(getEndIndex(pastStates)));
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

            futureCommands.add(pastCommands.remove(getEndIndex(pastCommands)));
            for (int i = (pastStates.size() - 1) * k; i <pastCommands.size(); i++) {
                Pair<CommandOriginator, Memento> currentPair = pastCommands.get(i);
                CommandOriginator command = currentPair.first();
                Memento memento = currentPair.second();
                command.restoreFrom(memento);
                command.execute();
            }
        }
        isUndoRedo = false;
    }

    private int getEndIndex(List<?> list) {
        return list.size() - 1;
    }

    private void moveLastFutureToPastOrNot() {
        int totalSize = this.pastCommands.size();
        if ( totalSize % k == 0) {
            pastStates.add(futureStates.remove(getEndIndex(pastStates)));
        }
    }

    public void redo() {
        isUndoRedo = true;
        if (!futureCommands.isEmpty()) {
            Pair<CommandOriginator, Memento> currentPair = futureCommands.remove(getEndIndex(futureCommands));
            pastCommands.add(currentPair);
            moveLastFutureToPastOrNot();

            CommandOriginator command = currentPair.first();
            Memento memento = currentPair.second();

            command.restoreFrom(memento);
            command.execute();
        }
        isUndoRedo = false;
    }


}



