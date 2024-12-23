package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

/**
 * The type Paste.
 */
public class Paste extends AbstractConcreteCommand implements CommandOriginator {
    private final Recorder recorder;

    /**
     * Instantiates a new Paste command.
     *
     * @param engine   the engine
     * @param recorder the recorder
     */
    public Paste(Engine engine, Recorder recorder) {
        super(engine);
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.engine.pasteClipboard();
        this.recorder.save(this);
    }

    @Override
    public Memento generateMemento() {
        return null;
    }

    @Override
    public void restoreFrom(Memento memento) {

    }
}