package fr.istic.aco.editor.kernel;

/**
 * Provides access to selection control operations
 *
 * @author plouzeau
 * @version 1.0
 */
public interface Selection {

    /**
     * Provides the index of the first character designated
     * by the selection.
     *
     * @return begin index
     */
    int getBeginIndex();

    /**
     * Provides the index of the first character
     * after the last character designated
     * by the selection.
     *
     * @return the end index
     */
    int getEndIndex();

    /**
     * Provides the index of the first character in the buffer
     *
     * @return the buffer's begin index
     */
    int getBufferBeginIndex();

    /**
     * Provides the index of the first "virtual" character
     * after the end of the buffer
     *
     * @return the post end buffer index
     */
    int getBufferEndIndex();

    /**
     * Changes the value of the begin index of the selection
     *
     * @param beginIndex the begin index
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    void setBeginIndex(int beginIndex);

    /**
     * Changes the value of the end index of the selection
     *
     * @param endIndex the end index
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    void setEndIndex(int endIndex);


}
