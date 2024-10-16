package fr.istic.aco.editor;

public class SelectionImpl implements Selection{
    private int beginIndex;
    private int endIndex;
    private final StringBuffer buffer;

    private final int BUFFER_BEGIN_INDEX = 0;

    public SelectionImpl(StringBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int getBeginIndex() {
        return this.beginIndex;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return BUFFER_BEGIN_INDEX;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        // TODO: make sure begin index is not bigger than end index.
        this.beginIndex = beginIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        if (this.beginIndex > this.endIndex) {
            throw new IndexOutOfBoundsException();
        }
        this.endIndex = endIndex;
    }
}
