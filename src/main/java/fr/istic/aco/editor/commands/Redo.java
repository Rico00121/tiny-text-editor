package fr.istic.aco.editor.commands;


import fr.istic.aco.editor.kernel.UndoManager;

/**
 * The Redo concrete command.
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

    /**
     * Execute the redo command.
     */
    @Override
    public void execute() {
        this.undoManager.redo();
    }
}