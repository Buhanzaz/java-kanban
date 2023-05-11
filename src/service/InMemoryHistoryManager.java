package service;

import model.AbstractTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final byte LAST_INDEX = 10;
    private final LinkedList<AbstractTask> viewTask = new LinkedList<>();

    @Override
    public List<AbstractTask> getHistory() {
        return new ArrayList<>(viewTask);
    }

    @Override
    public void add(AbstractTask object) {
        if (object != null) {
            viewTask.add(object);
            if (viewTask.size() >= LAST_INDEX) {
                viewTask.removeFirst();
            }
        }
    }
}
