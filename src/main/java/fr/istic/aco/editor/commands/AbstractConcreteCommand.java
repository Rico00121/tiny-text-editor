package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

/**
 * The Abstract concrete command.
 * This is a class that provides the engine instance to the concrete commands.
 */
public class AbstractConcreteCommand {
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
