package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

public class Copy extends AbstractConcreteCommand implements Command {
    public Copy(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.copySelectedText();
    }
}
