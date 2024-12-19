package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.*;

public class MoveSelection extends AbstractConcreteCommand implements CommandOriginator {
    private final Invoker invoker;
    private final Recorder recorder;
    private final UndoManager undoManager;

    public MoveSelection(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        super(engine);
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

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

    @Override
    public Memento generateMemento() {
        return new SelectMemento(this.invoker.getBeginIndex(), this.invoker.getEndIndex());
    }
    @Override
    public void restoreFrom(Memento memento) {
        SelectMemento selectMemento = (SelectMemento) memento;
        this.invoker.setSelection(selectMemento.beginIndex(), selectMemento.endIndex());
    }
}
