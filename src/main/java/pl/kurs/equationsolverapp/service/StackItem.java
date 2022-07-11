package pl.kurs.equationsolverapp.service;

public class StackItem<E> {
    public E value;
    public StackItem<E> next;

    public StackItem(E x) {
        value = x;
        next = null;
    }
}
