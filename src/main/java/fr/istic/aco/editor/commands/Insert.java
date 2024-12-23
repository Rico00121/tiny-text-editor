package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.*;

/**
 * The Insert concrete command.
 */
public class Insert extends AbstractConcreteCommand implements CommandOriginator {
    private final Invoker invoker;
    private final Recorder recorder;
    private final UndoManager undoManager;

    /**
     * Instantiates a new Insert command.
     *
     * @param engine      the engine
     * @param invoker     the invoker
     * @param recorder    the recorder
     * @param undoManager the undo manager
     */
    public Insert(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        super(engine);
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    /**
     * Execute the insert command.
     * After insert it saves the command in the recorder and the undo manager.
     */
    @Override
    public void execute() {
        this.engine.insert(invoker.getText());
        this.recorder.save(this);
        this.undoManager.storeCommand(this);
    }

    /**
     * Generate a memento.
     *
     * @return the memento
     */
    @Override
    public Memento generateMemento() {
        return new InsertMemento(invoker.getText());
    }

    /**
     * Restore from a memento.
     *
     * @param memento the memento
     */
    @Override
    public void restoreFrom(Memento memento) {
        InsertMemento insertMemento = (InsertMemento) memento;
        String state = insertMemento.state();
        this.invoker.setText(state);
    }
}
