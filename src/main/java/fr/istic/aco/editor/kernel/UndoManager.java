package fr.istic.aco.editor.kernel;

import fr.istic.aco.editor.commands.Command;

import java.util.ArrayList;

/**
 * This class manages the undo, redo and store operations.
 *
 */
public class UndoManager {
    private final ArrayList<EditorSnapshot> pastStates;
    private final ArrayList<EditorSnapshot> futureStates;
    private final ArrayList<Pair<CommandOriginator, Memento>> pastCommands;
    private final ArrayList<Pair<CommandOriginator, Memento>> futureCommands;
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
        futureCommands.clear();
    }

    /**
     * This method is used to store the snapshot of the editor upon each operation
     */
    public void storeSnapshot(EditorSnapshot snapshot) {
        pastStates.add(snapshot);
        futureStates.clear();
    }

    /**
     * This method is used to undo the last operation
     * It will restore the previous state of the editor and use k to apply the last k commands, and then that state becomes the current state
     * we then adjust the past and future states and commands accordingly
     */
    public void undo() {
        if (!pastCommands.isEmpty() && !pastStates.isEmpty()) {
            //Assign the past state to the current engine
            EditorSnapshot pastState = pastStates.get(pastStates.size() - 1);
            engine.restoreFrom(pastState);
            //After restoring the state, we apply the last k commands
            int i = 0;
            while (i < k && !pastCommands.isEmpty()) {
                //Execute the k commands since the last snapshot
                Pair<CommandOriginator, Memento> command = pastCommands.get(pastCommands.size() - (k+i));
                command.first().execute();
                i++;

            }
            pastCommands.remove(pastCommands.size() - 1);
            futureCommands.add(pastCommands.get(pastCommands.size() - 1));
        }
    }

    public void redo() {
        if (!futureCommands.isEmpty() && !futureStates.isEmpty()) {
            //Assign the future state to the current engine
            EditorSnapshot futureState = futureStates.get(futureStates.size() - 1);
            engine.restoreFrom(futureState);
            //After restoring the state, we apply the last k commands
            int i = 0;
            while (i < k && !futureCommands.isEmpty()) {
                Pair<CommandOriginator, Memento> command = futureCommands.get(futureCommands.size() - 1);
                command.first().execute();
                i++;
            }
            futureCommands.remove(futureCommands.size() - 1);
            pastCommands.add(futureCommands.get(futureCommands.size() - 1));
        }
    }



}
