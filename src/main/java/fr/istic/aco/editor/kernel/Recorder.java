package fr.istic.aco.editor.kernel;



import java.util.ArrayList;
import java.util.List;

public class Recorder {
    private final List<Pair<CommandOriginator, Memento>> actions;

    public boolean isReplaying() {
        return isReplaying;
    }

    private boolean isReplaying;
    private boolean isRecording;

    public Recorder() {
        this.actions = new ArrayList<>();
    }

    public void save(CommandOriginator originator) {
        if (isRecording) {
            Memento memento = originator.generateMemento();
            this.actions.add(new Pair<>(originator, memento));
        }
    }

    public void start() {
        this.isReplaying = false;
        this.isRecording = true;
    }

    public void stop() {
        this.isRecording = false;
    }

    public void replay() {
        this.isReplaying = true;
        actions.forEach(pair -> {
            Memento memento = pair.second();
            CommandOriginator commandOriginator = pair.first();
            commandOriginator.restoreFrom(memento);
            commandOriginator.execute();
        });
        this.isReplaying = false;
    }
}
