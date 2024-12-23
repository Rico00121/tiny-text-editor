package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

/**
 * The type Stop.
 */
public class Stop implements Command{
    private final Recorder recorder;

    /**
     * Instantiates a new Stop command.
     *
     * @param recorder the recorder
     */
    public Stop(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.recorder.stop();
    }
}
