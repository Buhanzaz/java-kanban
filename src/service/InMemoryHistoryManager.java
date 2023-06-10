package service;

import model.AbstractTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> idToTasksHistory = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public List<AbstractTask> getHistory() {
        return new ArrayList<>(getTasks());
    }

    @Override
    public void add(AbstractTask task) {
        remove(task.getId());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node n = idToTasksHistory.get(id);
        removeNode(n);
        idToTasksHistory.remove(id);
    }

    private void linkLast(AbstractTask task) {
        int taskId = task.getId();
        final Node tail = this.tail;
        final Node newNode = new Node(task, null, tail);
        this.tail = newNode;

        if (tail == null) {
            this.head = newNode;
        } else {
            tail.next = newNode;
        }
        idToTasksHistory.put(taskId, newNode);
    }

    private List<AbstractTask> getTasks() {
        List<AbstractTask> tasks = new ArrayList<>();

        for (Node x = head; x != null; x = x.next) {
            if (x.item != null) {
                tasks.add(x.item);
            }
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node != null) {
            Node nextNode = node.next;
            Node lastNode = node.last;

            if (node == head) {
                head = nextNode;
            } else if (node == tail) {
                tail = lastNode;
                tail.next = null;
            } else {
                nextNode.last = lastNode;
                lastNode.next = nextNode;
            }
        }
    }
}
