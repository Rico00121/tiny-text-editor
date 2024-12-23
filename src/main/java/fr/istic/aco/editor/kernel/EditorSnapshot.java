package fr.istic.aco.editor.kernel;


/**
 * This class stores a snapshot of the editor's state.
 */
public class EditorSnapshot {
    private final String bufferContent;
    private final int beginIndex;
    private final int endIndex;
    private final String clipboard;

    /**
     * Constructor for EditorMemento.
     *
     * @param bufferContent The content of the buffer.
     * @param beginIndex    The beginning index of the selection.
     * @param endIndex      The ending index of the selection.
     * @param clipboard     The content of the clipboard.
     */
    public EditorSnapshot(String bufferContent, int beginIndex, int endIndex, String clipboard) {
        this.bufferContent = bufferContent;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.clipboard = clipboard;
    }

    /**
     * Gets buffer contents.
     *
     * @return the buffer contents
     */
// Getter methods to retrieve the state
    public String getBufferContents() {
        return bufferContent;
    }

    /**
     * Gets begin index.
     *
     * @return the begin index
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Gets end index.
     *
     * @return the end index
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Gets clipboard contents.
     *
     * @return the clipboard contents
     */
    public String getClipboardContents() {
        return clipboard;
    }
}
