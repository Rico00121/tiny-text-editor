package fr.istic.aco.editor;

import fr.istic.aco.editor.kernel.SelectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionImplTest {
    private SelectionImpl selection;
    private final String content = "hello";

    @BeforeEach
    void setUp() {
        selection = new SelectionImpl(new StringBuffer(content));
    }

    @Test
    void getBeginIndex() {
        selection.setEndIndex(5);
        selection.setBeginIndex(3);

        assertEquals(3, selection.getBeginIndex());
    }

    @Test
    void getEndIndex() {
        selection.setEndIndex(3);

        assertEquals(3, selection.getEndIndex());
    }

    @Test
    void getBufferBeginIndex() {
        assertEquals(0, selection.getBufferBeginIndex());
    }

    @Test
    void getBufferEndIndex() {
        assertEquals(content.length(), selection.getBufferEndIndex());
    }

    @Test
    void setBeginIndex() {
        selection.setEndIndex(4);
        selection.setBeginIndex(3);

        assertEquals(3, selection.getBeginIndex());
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setBeginIndex(-2));
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setBeginIndex(100));
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setBeginIndex(5));
    }

    @Test
    void setEndIndex() {
        selection.setEndIndex(5);
        selection.setBeginIndex(3);

        assertEquals(5, selection.getEndIndex());
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setEndIndex(-2));
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setEndIndex(100));
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setEndIndex(2));

    }
}