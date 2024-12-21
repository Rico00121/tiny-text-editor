package fr.istic.aco.editor;

import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.UndoManager;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void replay_insert() {
        prepareHelloData();
        this.invoker.playCommand(START_RECORD);

        this.invoker.setText(" world");
        this.invoker.playCommand(INSERT);

        this.invoker.playCommand(STOP_RECORD);
        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("hello world world", this.engine.getBufferContents());
    }

    @Test
    void replay_selection() {
        prepareHelloData();
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
        prepareHelloData();
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
        prepareHelloData();
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
        prepareHelloData();
        this.invoker.playCommand(START_RECORD);
        invoker.playCommand(DELETE);
        // now is hell
        this.invoker.playCommand(STOP_RECORD);

        this.invoker.playCommand(REPLAY_RECORD);

        Assertions.assertEquals("hel", engine.getBufferContents());
    }

    @Test
    void replay_paste() {
        prepareHelloData();
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

    @Test
    void undo() {
        // insert 6 times
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        System.out.println(engine.getBufferContents());
        System.out.println(engine.getSelection().getBeginIndex());
        System.out.println(engine.getSelection().getEndIndex());
        System.out.println(engine.getClipboardContents());


        prepareHelloData();

        invoker.playCommand(UNDO);

        Assertions.assertEquals("hellohellohellohellohello", engine.getBufferContents());
    }

    @Test
    void redo() {
        // insert 6 times
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();
        prepareHelloData();

        invoker.playCommand(UNDO);
        invoker.playCommand(UNDO);
        invoker.playCommand(REDO);

        Assertions.assertEquals("hellohellohellohellohello", engine.getBufferContents());
    }

    private void prepareHelloData() {
        invoker.setText("hello");
        invoker.playCommand(INSERT);
    }
}
