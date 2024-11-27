package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

public class Start implements Command{
    private final Recorder recorder;

    public Start(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.recorder.start();
    }
}
