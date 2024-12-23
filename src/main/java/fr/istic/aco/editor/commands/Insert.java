package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.*;

/**
 * The type Insert.
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
    @Override
    public void execute() {
        this.engine.insert(invoker.getText());
        this.recorder.save(this);
        this.undoManager.storeCommand(this);
    }

    @Override
    public Memento generateMemento() {
        return new InsertMemento(invoker.getText());
    }

    @Override
    public void restoreFrom(Memento memento) {
        InsertMemento insertMemento = (InsertMemento) memento;
        String state = insertMemento.state();
        this.invoker.setText(state);
    }
}
