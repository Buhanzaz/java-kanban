package service;

import model.AbstractTask;

import java.util.List;

import static service.InMemoryTaskManager.viewTask;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public List<AbstractTask> getHistory() {
        return viewTask;
    }

    @Override
    public void add(AbstractTask object) {
        byte maxValue = 10;
        byte firstIndex = 0;

        if (object != null) {
            if (viewTask.size() < maxValue) {
                viewTask.add(object);

            } else {
                viewTask.add(object);
                viewTask.remove(firstIndex);
            }
        }
    }
}
