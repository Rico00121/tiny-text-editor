package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Engine;

public class Paste extends AbstractConcreteCommand implements Command {
    public Paste(Engine engine) {
        super(engine);
    }

    @Override
    public void execute() {
        this.engine.pasteClipboard();
    }
}
