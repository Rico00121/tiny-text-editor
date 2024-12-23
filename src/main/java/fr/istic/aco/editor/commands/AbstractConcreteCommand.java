package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

/**
 * The type Abstract concrete command.
 */
public class AbstractConcreteCommand {
    /**
     * The Engine.
     */
    protected Engine engine;

    /**
     * Instantiates a new Abstract concrete command.
     *
     * @param engine the engine
     */
    public AbstractConcreteCommand(Engine engine) {
        this.engine = engine;
    }
}
