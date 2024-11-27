package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.*;

public class MoveSelection extends AbstractConcreteCommand implements CommandOriginator {
    private final Invoker invoker;
    private final Recorder recorder;

    public MoveSelection(Engine engine, Invoker invoker, Recorder recorder) {
        super(engine);
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.engine.getSelection().setBeginIndex(invoker.getBeginIndex());
        this.engine.getSelection().setEndIndex(invoker.getEndIndex());
        this.recorder.save(this);
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
