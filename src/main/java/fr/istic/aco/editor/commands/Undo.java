package fr.istic.aco.editor.commands;


import fr.istic.aco.editor.kernel.UndoManager;

public class Undo implements Command{
    private final UndoManager undoManager;

    public Undo(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
       this.undoManager.undo();
    }
}
