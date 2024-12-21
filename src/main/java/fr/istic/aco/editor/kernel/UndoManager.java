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

    /**
     * constructor for UndoManager
     */
    public UndoManager(Engine engine) {
        this.engine = engine;
        pastStates = new ArrayList<>();
        futureStates = new ArrayList<>();
        pastCommands = new ArrayList<>();
        futureCommands = new ArrayList<>();
    }

    /**
     * This method is used to store the commands upon each operation
     */
    public void storeCommand(CommandOriginator originator) {
        Memento memento = originator.generateMemento();
        pastCommands.add(new Pair<>(originator, memento));
        int totalSize = this.pastCommands.size() + this.futureCommands.size();
        if (totalSize == 1 || totalSize % k == 0) {
            this.storeSnapshot(engine.createSnapshot());
        }
    }

    /**
     * This method is used to store the snapshot of the editor upon each operation
     */
    public void storeSnapshot(EditorSnapshot snapshot) {
        pastStates.add(snapshot);
    }

    /**
     * This method is used to undo the last operation
     * It will restore the previous state of the editor and use k to apply the last k commands, and then that state becomes the current state
     * we then adjust the past and future states and commands accordingly
     */
    public void undo() {
        if (!pastCommands.isEmpty() && !pastStates.isEmpty()) {
            //Assign the past state to the current engine
            //if the snapshot was captured at the current stage, we go back to the previous state and recover all the steps from there
            //if the snapshot was captured at the previous stage, we go back to the previous state and recover the last k steps from there
            int loopSize;

            //Check if the snapshot was captured at the current stage
            if ((pastCommands.size() % k) == 0) {
                //Remove the current state and add the current state to the future states
                futureStates.add(pastStates.remove(getEndIndex(pastStates)));

                //Assign the past state to the current engine
                EditorSnapshot pastState = pastStates.get(getEndIndex(pastStates));
                engine.restoreFrom(pastState);

                loopSize = (getEndIndex(pastCommands) % k);

            } else {
                EditorSnapshot pastState = pastStates.get(getEndIndex(pastStates));
                engine.restoreFrom(pastState);
                loopSize = (pastCommands.size() % k);
            }

            for (int i = loopSize; i > 1; i--) {
                Pair<CommandOriginator, Memento> pair = pastCommands.get(getEndIndex(pastCommands));
                CommandOriginator command = pair.first();
                Memento memento = pair.second();
                command.restoreFrom(memento);
                command.execute();
                pastCommands.remove(getEndIndex(pastCommands));
            }
            pastCommands.remove(getEndIndex(pastCommands));
            futureCommands.add(pastCommands.get(getEndIndex(pastCommands)));
        }
    }

    private int getEndIndex(List<?> list) {
        return list.size() - 1;
    }

    public void redo() {
        if (!futureCommands.isEmpty() && !futureStates.isEmpty()) {
            //Execute the first command in the future commands
            Pair<CommandOriginator, Memento> pair = futureCommands.get(0);
            CommandOriginator command = pair.first();
            Memento memento = pair.second();
            command.restoreFrom(memento);
            command.execute();
            //Add the command to the past commands
            pastCommands.add(futureCommands.get(0));
            //Remove the command from the future commands
            futureCommands.remove(0);
        }
    }
}



