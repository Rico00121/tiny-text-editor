package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

public class Delete extends AbstractConcreteCommand implements CommandOriginator {
    private final Recorder recorder;
    public Delete(Engine engine, Recorder recorder) {
        super(engine);
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.engine.delete();
        this.recorder.save(this);
    }

    @Override
    public Memento generateMemento() {
        return null;
    }

    @Override
    public void restoreFrom(Memento memento) {

    }
}
