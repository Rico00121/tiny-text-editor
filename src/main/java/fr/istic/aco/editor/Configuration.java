package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.UndoManager;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    public static final String INSERT = "insert";
    public static final String MOVE_SELECTION = "moveSelection";

    public static final String COPY="copy";

    public static final String CUT="cut";

    public static final String DELETE="delete";
    public static final String PASTE = "paste";

    public static final String START_RECORD = "startRecord";

    public static final String STOP_RECORD = "stopRecord";

    public static final String REPLAY_RECORD = "replayRecord";

    public static final String UNDO = "undo";
    public static final String REDO = "redo";

    @Bean
    public Engine engine() {
        return new EngineImpl();
    }

    @Bean
    public Recorder recorder() {
        return new Recorder();
    }

    @Bean
    public UndoManager undoManager(Engine engine) {
        return new UndoManager(engine);
    }



    @Bean
    public Invoker invoker(Engine engine, Recorder recorder, UndoManager undoManager) {
        Invoker invoker = new Invoker();
        invoker.addCommand(INSERT, new Insert(engine, invoker, recorder));
        invoker.addCommand(MOVE_SELECTION, new MoveSelection(engine, invoker, recorder));
        invoker.addCommand(COPY, new Copy(engine, recorder));
        invoker.addCommand(CUT, new Cut(engine, recorder));
        invoker.addCommand(DELETE, new Delete(engine, recorder));
        invoker.addCommand(PASTE, new Paste(engine, recorder));
        invoker.addCommand(START_RECORD, new Start(recorder));
        invoker.addCommand(STOP_RECORD, new Stop(recorder));
        invoker.addCommand(REPLAY_RECORD, new Replay(recorder));
        invoker.addCommand(UNDO,new Undo(undoManager));
        invoker.addCommand(REDO, new Redo(undoManager));

        return invoker;
    }

}
