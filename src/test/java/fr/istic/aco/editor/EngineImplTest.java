package fr.istic.aco.editor;

import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import fr.istic.aco.editor.kernel.Selection;
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
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(), selection.getBeginIndex());
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        engine.insert("car");
        assertEquals("car", engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        engine.insert("hello");
        Selection selection = engine.getSelection();
        selection.setEndIndex(5);
        selection.setBeginIndex(2);
        engine.copySelectedText();

        assertEquals("llo", engine.getClipboardContents());
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
        // given
        engine.insert("Hello");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(1);
        selection.setEndIndex(3);
        // when
        engine.copySelectedText();

        // then
        assertEquals("el", engine.getClipboardContents());
        assertEquals("Hello", engine.getBufferContents());
    }

    @Test
    void pasteClipboard() {
        engine.insert("China is the best");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);

        engine.copySelectedText();
        selection.setEndIndex(selection.getBufferEndIndex());
        selection.setBeginIndex(selection.getBufferEndIndex());
        engine.pasteClipboard();

        assertEquals("China is the bestChina", engine.getBufferContents());
        assertEquals("China", engine.getClipboardContents());


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

    @Test
    void delete_selected_content() {
        engine.insert("hello");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(2);
        selection.setEndIndex(4);

        engine.delete();
        assertEquals("heo", engine.getBufferContents());
    }

    @Test
    void delete_one_character_when_selected_same_index() {
        engine.insert("hello");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(2);
        selection.setEndIndex(2);

        engine.delete();
        assertEquals("hllo", engine.getBufferContents());
    }

    @Test
    void delete_nothing_when_selected_same_index_and_index_is_zero() {
        engine.insert("hello");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(0);

        engine.delete();
        assertEquals("hello", engine.getBufferContents());
    }


}
