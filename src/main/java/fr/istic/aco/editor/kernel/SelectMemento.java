package fr.istic.aco.editor.kernel;

/**
 * The type Select memento.
 */
public record SelectMemento(int beginIndex, int endIndex) implements Memento {
}
