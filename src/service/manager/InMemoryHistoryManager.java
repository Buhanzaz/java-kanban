package service.manager;

import model.AbstractTask;
import service.interfaces.HistoryManager;
import service.storage.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node> idToTasksHistory = new HashMap<>();
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
            tail.setNext(newNode);
        }
        idToTasksHistory.put(taskId, newNode);
    }

    private List<AbstractTask> getTasks() {
        List<AbstractTask> tasks = new ArrayList<>();

        for (Node x = head; x != null; x = x.getNext()) {
            if (x.getItem() != null) {
                tasks.add(x.getItem());
            }
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node != null) {
            Node nextNode = node.getNext();
            Node lastNode = node.getLast();

            if (node == head) {
                head = nextNode;
                tail = null;
            } else if (node == tail) {
                tail = lastNode;
                tail.setNext(null);
            } else {
                //nextNode.setLast(lastNode);
                lastNode.setNext(nextNode);
            }
        }
    }
}
