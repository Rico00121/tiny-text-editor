package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

/**
 * The Paste concrete command.
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

    /**
     * Execute the paste command.
     * After pasting to the engine it saves the command in the recorder.
     */
    @Override
    public void execute() {
        this.engine.pasteClipboard();
        this.recorder.save(this);
    }

    /**
     * Generate a memento.
     *
     * @return the memento
     */
    @Override
    public Memento generateMemento() {
        return null;
    }

    /**
     * Restore from a memento.
     *
     * @param memento the memento
     */
    @Override
    public void restoreFrom(Memento memento) {

    }
}