package fr.istic.aco.editor;

import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.UndoManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static fr.istic.aco.editor.Configuration.*;


public class InvokerTest {
    private Engine engine;
    private Invoker invoker;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.engine = new EngineImpl();
        Recorder recorder = new Recorder();
        UndoManager undoManager = new UndoManager(engine);
        this.invoker = new Configuration().invoker(engine, recorder, undoManager);
    }

    @Test
    void insert() {
        invoker.setText("hello");
        invoker.playCommand(INSERT);

        Assertions.assertEquals("hello", engine.getBufferContents());
    }

    @Test
    void moveSelection() {
        prepareHellodData();

        invoker.setSelection(1, 2);
        invoker.playCommand(MOVE_SELECTION);


        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(2, engine.getSelection().getEndIndex());
    }

    @Test
    void copy() {
        prepareHellodData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(COPY);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(3, engine.getSelection().getEndIndex());
        Assertions.assertEquals("el", engine.getClipboardContents());

    }

    @Test
    void cut() {
        prepareHellodData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(CUT);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(1, engine.getSelection().getEndIndex());
        Assertions.assertEquals("el", engine.getClipboardContents());
    }

    @Test
    void delete() {
        prepareHellodData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(DELETE);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(1, engine.getSelection().getEndIndex());
        Assertions.assertEquals("hlo", engine.getBufferContents());

    }

    @Test
    void paste() {
        prepareHellodData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(CUT);
        invoker.playCommand(PASTE);

        Assertions.assertEquals(3, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(3, engine.getSelection().getEndIndex());
        Assertions.assertEquals("hello", engine.getBufferContents());
    }

    @Test
    void replay_insert() {
        prepareHellodData();
        this.invoker.playCommand(START_RECORD);

        this.invoker.setText(" world");
        this.invoker.playCommand(INSERT);

        this.invoker.playCommand(STOP_RECORD);
        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("hello world world", this.engine.getBufferContents());
    }

    @Test
    void replay_selection() {
        prepareHellodData();
        //start record
        this.invoker.playCommand(START_RECORD);

        invoker.setSelection(1, 2);
        invoker.playCommand(MOVE_SELECTION);
        //stop record
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.setText(" world");
        this.invoker.playCommand(INSERT);
        //engine content: "hello world"
        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(2, engine.getSelection().getEndIndex());
    }

    @Test
    void replay_copy() {
        prepareHellodData();
        this.invoker.playCommand(START_RECORD);
        invoker.playCommand(COPY);
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.setSelection(1,2);
        this.invoker.playCommand(MOVE_SELECTION);
        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("e", engine.getClipboardContents());
    }

    @Test
    void replay_cut() {
        prepareHellodData();
        // hello
        this.invoker.playCommand(START_RECORD);
        invoker.playCommand(CUT);
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.setSelection(1,2);
        this.invoker.playCommand(MOVE_SELECTION);

        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("e", engine.getClipboardContents());
        Assertions.assertEquals("hllo", engine.getBufferContents());
    }

    @Test
    void replay_delete() {
        prepareHellodData();
        this.invoker.playCommand(START_RECORD);
        invoker.playCommand(DELETE);
        // now is hell
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("hel", engine.getBufferContents());
    }

    @Test
    void replay_paste() {
        prepareHellodData();
        this.invoker.setSelection(1, 2);
        this.invoker.playCommand(MOVE_SELECTION);
        this.invoker.playCommand(COPY);

        this.invoker.playCommand(START_RECORD);
        invoker.playCommand(PASTE);
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.setSelection(5, 5);
        this.invoker.playCommand(MOVE_SELECTION);

        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("helloe", engine.getBufferContents());
    }

    @Nested
    class UndoTest {
        @Test
        void should_not_undo_when_past_commands_size_is_0() {

            invoker.playCommand(UNDO);

            Assertions.assertEquals("", engine.getBufferContents());
        }
        @Test
        void undo_once_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHellodData();

            invoker.playCommand(UNDO);

            Assertions.assertEquals("", engine.getBufferContents());
        }
        @Test
        void undo_multiple_times_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");

            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);

            Assertions.assertEquals("hello", engine.getBufferContents());
        }

        @Test
        void undo_once_when_past_snapshot_size_is_2_and_future_snapshot_size_is_0() {
            // 7 hellos
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");
            prepareCustomizedData(" afghanistan");
            prepareCustomizedData(" south africa");
            prepareCustomizedData(" south korea");

            invoker.playCommand(UNDO);

            Assertions.assertEquals("hello pakistan china india afghanistan south africa", engine.getBufferContents());
        }

        @Test
        void undo_once_when_past_snapshot_size_is_1_and_future_snapshot_size_is_1() {
            // 7 hellos
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");
            prepareCustomizedData(" afghanistan");
            prepareCustomizedData(" south africa");
            prepareCustomizedData(" south korea");

            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);

            Assertions.assertEquals("hello pakistan china india", engine.getBufferContents());
        }
    }

    @Nested
    class RedoTest {
        @Test
        void should_not_redo_when_future_commands_size_is_0() {
            invoker.playCommand(REDO);

            Assertions.assertEquals("", engine.getBufferContents());
        }
        @Test
        void redo_once_when_past_snapshot_is_1_and_future_snapshot_is_0() {
            prepareHellodData();
            invoker.playCommand(UNDO);

            invoker.playCommand(REDO);

            Assertions.assertEquals("hello", engine.getBufferContents());
        }

        @Test
        void redo_multiple_times_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);


            invoker.playCommand(REDO);
            invoker.playCommand(REDO);
            invoker.playCommand(REDO);


            Assertions.assertEquals("hello pakistan china india", engine.getBufferContents());
        }

        @Test
        void redo_multiple_times_when_past_snapshot_size_is_1_and_future_snapshot_size_is_1() {
            //7 hellos
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");
            prepareCustomizedData(" afghanistan");
            prepareCustomizedData(" south africa");
            prepareCustomizedData(" south korea");
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);


            invoker.playCommand(REDO);
            invoker.playCommand(REDO);
            invoker.playCommand(REDO);


            Assertions.assertEquals("hello pakistan china india afghanistan south africa south korea", engine.getBufferContents());
        }
    }

    @Nested
    class UndoRedoTest {
        @Test
        void undo_redo_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");

            invoker.playCommand(UNDO);
            invoker.playCommand(REDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(REDO);

            Assertions.assertEquals("hello pakistan china", engine.getBufferContents());
        }

        @Test
        void undo_redo_when_past_snapshot_size_is_1_and_future_snapshot_size_is_1() {
            //7 hellos
            prepareHellodData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");
            prepareCustomizedData(" india");
            prepareCustomizedData(" afghanistan");
            prepareCustomizedData(" south africa");
            prepareCustomizedData(" south korea");

            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);
            invoker.playCommand(UNDO);

            invoker.playCommand(UNDO);
            invoker.playCommand(REDO);

            Assertions.assertEquals("hello pakistan china india", engine.getBufferContents());
        }
    }

    private void prepareCustomizedData(String text) {
        invoker.setText(text);
        invoker.playCommand(INSERT);
    }

    private void prepareHellodData() {
        prepareCustomizedData("hello");
    }
}
