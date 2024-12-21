package fr.istic.aco.editor.kernel;

public class EngineImpl implements Engine {
    private final StringBuffer buffer;
    private String clipboard;

    private final Selection selection;

    public EngineImpl() {
        this.buffer = new StringBuffer();
        this.selection = new SelectionImpl(this.buffer);
        this.clipboard = "";
    }

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return this.clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        this.copySelectedText();
        int beginIndex = this.selection.getBeginIndex();
        int endIndex = this.selection.getEndIndex();
        buffer.delete(beginIndex, endIndex);
        selection.setEndIndex(beginIndex);
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        this.clipboard = buffer.substring(selection.getBeginIndex(), selection.getEndIndex());
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {

        insert(clipboard);
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        buffer.replace(selection.getBeginIndex(), selection.getEndIndex(), s);
        selection.setEndIndex(selection.getBeginIndex() + s.length());
        selection.setBeginIndex(selection.getEndIndex());
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        int beginIndex = this.selection.getBeginIndex();
        int endIndex = this.selection.getEndIndex();

        if (beginIndex == endIndex && beginIndex > 0) {
            buffer.delete(beginIndex - 1, beginIndex);
            selection.setBeginIndex(beginIndex - 1);
            selection.setEndIndex(beginIndex - 1);
        } else {
            buffer.delete(beginIndex, endIndex);
            selection.setEndIndex(beginIndex);
        }

    }

    /**
     * It recovers editor using the previous snapshot, which is stored in the memento.
     *
     */
    public void restoreFrom(EditorSnapshot memento) {
        this.buffer.replace(0, this.buffer.length(), memento.getBufferContents());
        this.selection.setBeginIndex(memento.getBeginIndex());
        this.selection.setEndIndex(memento.getEndIndex());
        this.clipboard = memento.getClipboardContents();

    }

    /**
     * It creates a snapshot of the editor's state.
     *
     * @return the memento
     */
    @Override
    public EditorSnapshot createSnapshot() {
        return new EditorSnapshot(this.getBufferContents(), this.selection.getBeginIndex(), this.selection.getEndIndex(), this.clipboard);
    }
}

