package fr.istic.aco.editor;

import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Recorder;
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
        this.invoker = new Configuration().invoker(engine, recorder);
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
        this.invoker.playCommand(START_RECORD);

        invoker.setSelection(1, 2);
        invoker.playCommand(MOVE_SELECTION);

        this.invoker.playCommand(STOP_RECORD);

        this.invoker.setText(" world");
        this.invoker.playCommand(INSERT);

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

    private void prepareHelloData() {
        invoker.setText("hello");
        invoker.playCommand(INSERT);
    }
}
