package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.CommandOriginator;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Memento;
import fr.istic.aco.editor.kernel.Recorder;

/**
 * The type Cut.
 */
public class Cut extends AbstractConcreteCommand implements CommandOriginator {
    private final Recorder recorder;

    /**
     * Instantiates a new Cut command.
     *
     * @param engine   the engine
     * @param recorder the recorder
     */
    public Cut(Engine engine, Recorder recorder) {
        super(engine);
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.engine.cutSelectedText();
        this.recorder.save(this);
    }

    @Override
    public Memento generateMemento() {
        return null;
    }

    public void restoreFrom(Memento memento) {
        
    }
}
