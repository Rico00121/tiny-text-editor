package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;

public class Copy extends AbstractConcreteCommand implements CommandOriginator {
    public Copy(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.copySelectedText();
    }

    @Override
    public Memento generateMemento() {
        return null;
    }

    @Override
    public void restoreFrom(Memento memento) {

    }
}
