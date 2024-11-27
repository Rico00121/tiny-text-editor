package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.kernel.Recorder;

public class Replay implements Command{
   private final Recorder recorder;

    public Replay(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        this.recorder.replay();
    }
}
