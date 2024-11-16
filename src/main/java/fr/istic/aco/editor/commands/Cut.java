package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

public class Cut extends AbstractConcreteCommand implements Command {

    public Cut(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.cutSelectedText();
    }
}
