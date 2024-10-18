package fr.istic.aco.editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineImplTest {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    private void todo() {
        fail("Unimplemented test");
    }
    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(),selection.getBeginIndex());
        assertEquals("",engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        engine.insert("car");
        assertEquals("car",engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        engine.copySelectedText();
    }

    @Test
    void cutSelectedText() {
        engine.insert("hello");
        engine.getSelection().setBeginIndex(2);
        engine.getSelection().setEndIndex(4);

        engine.cutSelectedText();

        assertEquals("heo", engine.getBufferContents());
        assertEquals("ll", engine.getClipboardContents());
    }

    @Test
    void copySelectedText() {
        todo();
    }

    @Test
    void pasteClipboard() {
        todo();
    }

    @Test
    void insert() {
        engine.insert("hello");
        assertEquals("hello", engine.getBufferContents());

        Selection selection = engine.getSelection();
        selection.setBeginIndex(2);
        selection.setEndIndex(4);

        engine.insert("aaa");
        assertEquals("heaaao", engine.getBufferContents());

    }
}
