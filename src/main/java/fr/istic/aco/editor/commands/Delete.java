package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

/**
 * The Delete concrete command.
 */
public class Delete extends AbstractConcreteCommand implements CommandOriginator {
    private final Recorder recorder;

    /**
     * Instantiates a new Delete command.
     *
     * @param engine   the engine
     * @param recorder the recorder
     */
    public Delete(Engine engine, Recorder recorder) {
        super(engine);
        this.recorder = recorder;
    }

    /**
     * Execute the delete command.
     */
    @Override
    public void execute() {
        this.engine.delete();
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
