package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

public class Stop implements Command{
    private final Recorder recorder;

    public Stop(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.recorder.stop();
    }
}
