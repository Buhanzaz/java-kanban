package service;

import model.AbstractTask;

import java.util.List;

public interface HistoryManager {
    void add(AbstractTask Task);

    List<AbstractTask> getHistory();
}
