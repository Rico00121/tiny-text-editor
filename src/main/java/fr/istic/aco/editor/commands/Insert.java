package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.Engine;

public class Insert extends AbstractConcreteCommand implements Command {
    private final Invoker invoker;

    public Insert(Engine engine, Invoker invoker) {
        super(engine);
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        this.engine.insert(invoker.getText());
    }
}
