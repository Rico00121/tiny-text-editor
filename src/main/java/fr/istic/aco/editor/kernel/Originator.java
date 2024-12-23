package fr.istic.aco.editor.kernel;

/**
 * The interface Originator, designed for the Memento design pattern.
 */
public interface Originator {
    /**
     * Generate memento.
     *
     * @return the memento
     */
    Memento generateMemento();

    /**
     * Restore state from a Memento object.
     *
     * @param memento the memento
     */
    void restoreFrom(Memento memento);
}
