package fr.istic.aco.editor.commands;


import fr.istic.aco.editor.kernel.UndoManager;

/**
 * The undo concrete command.
 */
public class Undo implements Command{
    private final UndoManager undoManager;

    /**
     * Instantiates a new Undo command.
     *
     * @param undoManager the undo manager
     */
    public Undo(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    /**
     * Execute the undo command.
     */
    @Override
    public void execute() {
       this.undoManager.undo();
    }
}
