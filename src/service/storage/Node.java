package service.storage;

import model.AbstractTask;

public class Node {
    private AbstractTask item;
    private Node next;
    private Node last;

    public Node(AbstractTask item, Node next, Node last) {
        this.item = item;
        this.next = next;
        this.last = last;
    }

    public AbstractTask getItem() {
        return item;
    }

    public void setItem(AbstractTask item) {
        this.item = item;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node last) {
        this.last = last;
    }
}
