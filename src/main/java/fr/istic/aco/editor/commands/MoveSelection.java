package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.*;

/**
 * The Move selection concrete command.
 */
public class MoveSelection extends AbstractConcreteCommand implements CommandOriginator {
    private final Invoker invoker;
    private final Recorder recorder;
    private final UndoManager undoManager;

    /**
     * Instantiates a new Move Selection command.
     *
     * @param engine      the engine
     * @param invoker     the invoker
     * @param recorder    the recorder
     * @param undoManager the undo manager
     */
    public MoveSelection(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        super(engine);
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    /**
     * Execute the move selection command.
     * After moving the selection it saves the command in the recorder and the undo manager.
     */
    @Override
    public void execute() {
        if (this.invoker.getEndIndex() < this.engine.getSelection().getBeginIndex() ){
            this.engine.getSelection().setBeginIndex(this.invoker.getBeginIndex());
            this.engine.getSelection().setEndIndex(this.invoker.getEndIndex());
        } else {
            this.engine.getSelection().setEndIndex(this.invoker.getEndIndex());
            this.engine.getSelection().setBeginIndex(this.invoker.getBeginIndex());
        }
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
        return new SelectMemento(this.invoker.getBeginIndex(), this.invoker.getEndIndex());
    }

    /**
     * Restore from a memento.
     *
     * @param memento the memento
     */
    @Override
    public void restoreFrom(Memento memento) {
        SelectMemento selectMemento = (SelectMemento) memento;
        this.invoker.setSelection(selectMemento.beginIndex(), selectMemento.endIndex());
    }
}
