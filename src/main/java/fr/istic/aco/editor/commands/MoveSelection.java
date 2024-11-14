package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.Engine;

public class MoveSelection extends AbstractConcreteCommand implements Command {
    private final Invoker invoker;
    public MoveSelection(Engine engine, Invoker invoker) {
        super(engine);
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        this.engine.getSelection().setBeginIndex(invoker.getBeginIndex());
        this.engine.getSelection().setEndIndex(invoker.getEndIndex());
    }
}
