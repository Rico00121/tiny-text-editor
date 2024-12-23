package fr.istic.aco.editor.commands;


import fr.istic.aco.editor.kernel.UndoManager;

/**
 * The type Redo.
 */
public class Redo implements Command{
    private final UndoManager undoManager;

    /**
     * Instantiates a new Redo command.
     *
     * @param undoManager the undo manager
     */
    public Redo(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        this.undoManager.redo();
    }
}