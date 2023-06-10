package service;

import model.AbstractTask;

public class Node {
    protected AbstractTask item;
    protected Node next;
    protected Node last;

    public Node(AbstractTask item, Node next, Node last) {
        this.item = item;
        this.next = next;
        this.last = last;
    }
}
