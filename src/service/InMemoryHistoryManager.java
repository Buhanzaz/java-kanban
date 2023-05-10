package service;

import model.AbstractTask;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected static List<AbstractTask> viewTask = new LinkedList<>();

    @Override
    public List<AbstractTask> getHistory() {
        return viewTask;
    }

    @Override
    public void add(AbstractTask object) {
        if (object != null) {
            if (viewTask.size() < LAST_INDEX) {
                viewTask.add(object);

            } else {
                viewTask.add(object);
                viewTask.remove(FIRST_INDEX);
            }
        }
    }
}
