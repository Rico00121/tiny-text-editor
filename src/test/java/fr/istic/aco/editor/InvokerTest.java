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
        prepareHelloData();

        invoker.setSelection(1, 2);
        invoker.playCommand(MOVE_SELECTION);


        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(2, engine.getSelection().getEndIndex());
    }

    @Test
    void copy() {
        prepareHelloData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(COPY);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(3, engine.getSelection().getEndIndex());
        Assertions.assertEquals("el", engine.getClipboardContents());

    }

    @Test
    void cut() {
        prepareHelloData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(CUT);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(1, engine.getSelection().getEndIndex());
        Assertions.assertEquals("el", engine.getClipboardContents());
    }

    @Test
    void delete() {
        prepareHelloData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(DELETE);

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(1, engine.getSelection().getEndIndex());
        Assertions.assertEquals("hlo", engine.getBufferContents());

    }

    @Test
    void paste() {
        prepareHelloData();

        invoker.setSelection(1, 3);
        invoker.playCommand(MOVE_SELECTION);
        invoker.playCommand(CUT);
        invoker.playCommand(PASTE);

        Assertions.assertEquals(3, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(3, engine.getSelection().getEndIndex());
        Assertions.assertEquals("hello", engine.getBufferContents());
    }

    @Nested
    class ReplayTest {
        @Test
        void replay_insert() {
            prepareHelloData();
            invoker.playCommand(START_RECORD);

            invoker.setText(" world");
            invoker.playCommand(INSERT);

            invoker.playCommand(STOP_RECORD);
            invoker.playCommand(REPLAY_RECORD);

            Assertions.assertEquals("hello world world", engine.getBufferContents());
        }

        @Test
        void replay_selection() {
            prepareHelloData();
            //start record
            invoker.playCommand(START_RECORD);

            invoker.setSelection(1, 2);
            invoker.playCommand(MOVE_SELECTION);
            //stop record
            invoker.playCommand(STOP_RECORD);

            invoker.setText(" world");
            invoker.playCommand(INSERT);
            //engine content: "hello world"
            invoker.playCommand(REPLAY_RECORD);

            Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
            Assertions.assertEquals(2, engine.getSelection().getEndIndex());
        }

        @Test
        void replay_copy() {
            prepareHelloData();
            invoker.playCommand(START_RECORD);
            invoker.playCommand(COPY);
            invoker.playCommand(STOP_RECORD);

            invoker.setSelection(1,2);
            invoker.playCommand(MOVE_SELECTION);
            invoker.playCommand(REPLAY_RECORD);

            Assertions.assertEquals("e", engine.getClipboardContents());
        }

        @Test
        void replay_cut() {
            prepareHelloData();
            // hello
            invoker.playCommand(START_RECORD);
            invoker.playCommand(CUT);
            invoker.playCommand(STOP_RECORD);

            invoker.setSelection(1,2);
            invoker.playCommand(MOVE_SELECTION);

            invoker.playCommand(REPLAY_RECORD);
            Assertions.assertEquals("e", engine.getClipboardContents());
            Assertions.assertEquals("hllo", engine.getBufferContents());
        }

        @Test
        void replay_delete() {
            prepareHelloData();
            invoker.playCommand(START_RECORD);
            invoker.playCommand(DELETE);
            // now is hell
            invoker.playCommand(STOP_RECORD);

            invoker.playCommand(REPLAY_RECORD);

            Assertions.assertEquals("hel", engine.getBufferContents());
        }

        @Test
        void replay_paste() {
            prepareHelloData();
            invoker.setSelection(1, 2);
            invoker.playCommand(MOVE_SELECTION);
            invoker.playCommand(COPY);

            invoker.playCommand(START_RECORD);
            invoker.playCommand(PASTE);
            invoker.playCommand(STOP_RECORD);

            invoker.setSelection(5, 5);
            invoker.playCommand(MOVE_SELECTION);

            invoker.playCommand(REPLAY_RECORD);

            Assertions.assertEquals("helloe", engine.getBufferContents());
        }

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
            prepareHelloData();

            invoker.playCommand(UNDO);

            Assertions.assertEquals("", engine.getBufferContents());
        }
        @Test
        void undo_multiple_times_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHelloData();
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
            prepareHelloData();
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
            prepareHelloData();
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
            prepareHelloData();
            invoker.playCommand(UNDO);

            invoker.playCommand(REDO);

            Assertions.assertEquals("hello", engine.getBufferContents());
        }

        @Test
        void redo_multiple_times_when_past_snapshot_size_is_1_and_future_snapshot_size_is_0() {
            prepareHelloData();
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
            prepareHelloData();
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
            prepareHelloData();
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
            prepareHelloData();
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

        @Test
        void should_cannot_redo_when_happen_any_operation_during_redo_undo_processing() {
            prepareHelloData();
            prepareCustomizedData(" pakistan");
            prepareCustomizedData(" china");

            invoker.playCommand(UNDO);
            // hello pakistan
            prepareCustomizedData(" india");
            // hello pakistan india
            invoker.playCommand(REDO); // should happen nothing

            Assertions.assertEquals("hello pakistan india", engine.getBufferContents());
        }
    }

    private void prepareCustomizedData(String text) {
        invoker.setText(text);
        invoker.playCommand(INSERT);
    }

    private void prepareHelloData() {
        prepareCustomizedData("hello");
    }
}
