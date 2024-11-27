package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;

public class Delete extends AbstractConcreteCommand implements CommandOriginator {
    public Delete(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.delete();
    }

    @Override
    public Memento generateMemento() {
        return null;
    }

    @Override
    public void restoreFrom(Memento memento) {

    }
}
