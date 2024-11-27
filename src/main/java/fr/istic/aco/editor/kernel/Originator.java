package fr.istic.aco.editor.kernel;

public interface Originator {
    Memento generateMemento();
    void restoreFrom(Memento memento);
}
