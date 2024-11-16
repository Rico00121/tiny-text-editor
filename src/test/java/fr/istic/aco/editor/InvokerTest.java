package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static fr.istic.aco.editor.Configuration.*;


public class InvokerTest {
    private Engine engine;
    private Invoker invoker;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.engine = new EngineImpl();
        this.invoker = new Invoker();
        invoker.addCommand(INSERT, new Insert(engine, invoker));
        invoker.addCommand(MOVE_SELECTION, new MoveSelection(engine, invoker));
        invoker.addCommand(COPY, new Copy(engine));
        invoker.addCommand(CUT, new Cut(engine));
        invoker.addCommand(DELETE, new Delete(engine));
        invoker.addCommand(PASTE, new Paste(engine));
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

    private void prepareHelloData() {
        invoker.setText("hello");
        invoker.playCommand(INSERT);
    }
}
