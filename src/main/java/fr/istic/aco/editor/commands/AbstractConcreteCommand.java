package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

public class AbstractConcreteCommand {
    protected Engine engine;

    public AbstractConcreteCommand(Engine engine) {
        this.engine = engine;
    }
}
