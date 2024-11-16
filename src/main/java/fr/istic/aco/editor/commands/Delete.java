package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

public class Delete extends AbstractConcreteCommand implements Command {
    public Delete(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.delete();
    }
}
