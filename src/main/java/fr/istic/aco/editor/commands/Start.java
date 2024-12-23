package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

/**
 * The type Start.
 */
public class Start implements Command{
    private final Recorder recorder;

    /**
     * Instantiates a new Start command.
     *
     * @param recorder the recorder
     */
    public Start(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.recorder.start();
    }
}
