package fr.istic.aco.editor.kernel;


import java.util.ArrayList;
import java.util.List;

/**
 * The Recorder class is responsible for recording and replaying commands in the text editor.
 * It uses the Memento design pattern to save and restore the state of commands.
 */
public class Recorder {
    private final List<Pair<CommandOriginator, Memento>> actions;
    private boolean isReplaying;
    private boolean isRecording;

    /**
     * Instantiates a new Recorder.
     */
    public Recorder() {
        this.actions = new ArrayList<>();
    }

    /**
     * Checks if the recorder is currently replaying actions.
     *
     * @return true if replaying, false otherwise
     */
    public boolean isReplaying() {
        return isReplaying;
    }

    /**
     * Saves the current state of a command originator if isRecording flag is enabled.
     *
     * @param originator the command originator to save
     */
    public void save(CommandOriginator originator) {
        if (isRecording) {
            Memento memento = originator.generateMemento();
            this.actions.add(new Pair<>(originator, memento));
        }
    }

    /**
     * Starts recording actions.
     */
    public void start() {
        this.isReplaying = false;
        this.isRecording = true;
    }

    /**
     * Stops recording actions.
     */
    public void stop() {
        this.isRecording = false;
    }


    /**
     * Replays all recorded actions.
     */
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
