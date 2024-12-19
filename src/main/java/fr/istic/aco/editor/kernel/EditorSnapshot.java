package fr.istic.aco.editor.kernel;


/**
 * This class stores a snapshot of the editor's state.
 */
public class EditorSnapshot {
    private StringBuffer bufferContent;
    private int beginIndex;
    private int endIndex;
    private String clipboard;

    /**
     * Constructor for EditorMemento.
     *
     * @param bufferContent The content of the buffer.
     * @param beginIndex    The beginning index of the selection.
     * @param endIndex      The ending index of the selection.
     * @param clipboard     The content of the clipboard.
     */
    public EditorSnapshot(StringBuffer bufferContent, int beginIndex, int endIndex, String clipboard) {
        this.bufferContent = bufferContent;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.clipboard = clipboard;
    }

    // Getter methods to retrieve the state
    public StringBuffer getBufferContents() {
        return bufferContent;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getClipboardContents() {
        return clipboard;
    }
}
