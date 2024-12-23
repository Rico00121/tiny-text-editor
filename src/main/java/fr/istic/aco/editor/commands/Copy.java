package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

/**
 * The Copy concrete command.
 */
public class Copy extends AbstractConcreteCommand implements CommandOriginator {
    private final Recorder recorder;

    /**
     * Instantiates a new Copy command.
     *
     * @param engine   the engine
     * @param recorder the recorder
     */
    public Copy(Engine engine, Recorder recorder) {
        super(engine);
        this.recorder = recorder;
    }

    /**
     * Execute the copy command.
     */
    @Override
    public void execute() {
        this.engine.copySelectedText();
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
