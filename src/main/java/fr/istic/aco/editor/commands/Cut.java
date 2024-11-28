package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;

public class Cut extends AbstractConcreteCommand implements Command {

    public Cut(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.cutSelectedText();
    }

    public void restoreFrom(Memento memento) {
        
    }
}
