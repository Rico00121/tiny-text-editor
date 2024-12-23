package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

/**
 * The Replay concrete command.
 */
public class Replay implements Command{
   private final Recorder recorder;

    /**
     * Instantiates a new Replay command.
     *
     * @param recorder the recorder
     */
    public Replay(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Execute the replay command.
     */
    @Override
    public void execute() {
        this.recorder.replay();
    }
}
