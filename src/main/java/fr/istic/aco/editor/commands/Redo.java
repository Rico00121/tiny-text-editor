package fr.istic.aco.editor.commands;


import fr.istic.aco.editor.kernel.UndoManager;

public class Redo implements Command{
    private final UndoManager undoManager;

    public Redo(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        this.undoManager.redo();
    }
}