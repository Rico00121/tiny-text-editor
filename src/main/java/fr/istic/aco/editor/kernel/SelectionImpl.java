package fr.istic.aco.editor.kernel;

/**
 * The type Selection.
 */
public class SelectionImpl implements Selection {
    private final StringBuffer buffer;
    private final int BUFFER_BEGIN_INDEX = 0;
    private int beginIndex;
    private int endIndex;

    /**
     * Instantiates a new Selection with the specified buffer.
     *
     * @param buffer the buffer to be associated with this selection
     */
    public SelectionImpl(StringBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int getBeginIndex() {
        return this.beginIndex;
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex > this.endIndex || beginIndex < getBufferBeginIndex()) {
            throw new IndexOutOfBoundsException();
        }
        this.beginIndex = beginIndex;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        if (this.beginIndex > endIndex || endIndex > getBufferEndIndex()) {
            throw new IndexOutOfBoundsException();
        }
        this.endIndex = endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return BUFFER_BEGIN_INDEX;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }
}
