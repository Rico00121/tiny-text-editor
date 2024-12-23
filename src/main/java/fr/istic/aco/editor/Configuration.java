package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.UndoManager;
import org.springframework.context.annotation.Bean;

/**
 * Configuration class for defining and initializing components of the application.
 * This class includes command constants and dependency injection configurations.
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    /**
     * The constant INSERT.
     */
    public static final String INSERT = "insert";
    /**
     * The constant MOVE_SELECTION.
     */
    public static final String MOVE_SELECTION = "moveSelection";

    /**
     * The constant COPY.
     */
    public static final String COPY = "copy";

    /**
     * The constant CUT.
     */
    public static final String CUT = "cut";

    /**
     * The constant DELETE.
     */
    public static final String DELETE = "delete";
    /**
     * The constant PASTE.
     */
    public static final String PASTE = "paste";

    /**
     * The constant START_RECORD.
     */
    public static final String START_RECORD = "startRecord";

    /**
     * The constant STOP_RECORD.
     */
    public static final String STOP_RECORD = "stopRecord";

    /**
     * The constant REPLAY_RECORD.
     */
    public static final String REPLAY_RECORD = "replayRecord";

    /**
     * The constant UNDO.
     */
    public static final String UNDO = "undo";
    /**
     * The constant REDO.
     */
    public static final String REDO = "redo";

    /**
     * Creates and configures the Engine bean.
     *
     * @return the configured Engine instance
     */
    @Bean
    public Engine engine() {
        return new EngineImpl();
    }

    /**
     * Creates and configures the Recorder bean.
     *
     * @return the configured Recorder instance
     */
    @Bean
    public Recorder recorder() {
        return new Recorder();
    }

    /**
     * Creates and configures the UndoManager bean.
     *
     * @param engine the Engine instance to manage undo operations
     * @return the configured UndoManager instance
     */
    @Bean
    public UndoManager undoManager(Engine engine) {
        return new UndoManager(engine);
    }


    /**
     * Creates and configures the Invoker bean, registering all available commands.
     *
     * @param engine      the Engine instance for executing commands
     * @param recorder    the Recorder instance for managing command recordings
     * @param undoManager the UndoManager instance for managing undo/redo operations
     * @return the configured Invoker instance
     */
    @Bean
    public Invoker invoker(Engine engine, Recorder recorder, UndoManager undoManager) {
        Invoker invoker = new Invoker();
        invoker.addCommand(INSERT, new Insert(engine, invoker, recorder, undoManager));
        invoker.addCommand(MOVE_SELECTION, new MoveSelection(engine, invoker, recorder, undoManager));
        invoker.addCommand(COPY, new Copy(engine, recorder));
        invoker.addCommand(CUT, new Cut(engine, recorder));
        invoker.addCommand(DELETE, new Delete(engine, recorder));
        invoker.addCommand(PASTE, new Paste(engine, recorder));
        invoker.addCommand(START_RECORD, new Start(recorder));
        invoker.addCommand(STOP_RECORD, new Stop(recorder));
        invoker.addCommand(REPLAY_RECORD, new Replay(recorder));
        invoker.addCommand(UNDO, new Undo(undoManager));
        invoker.addCommand(REDO, new Redo(undoManager));

        return invoker;
    }

}
