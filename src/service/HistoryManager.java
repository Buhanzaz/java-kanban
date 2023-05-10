package service;

import model.AbstractTask;

import java.util.List;

public interface HistoryManager {
    byte LAST_INDEX = 10;
    byte FIRST_INDEX = 0;

    void add(AbstractTask Task);

    List<AbstractTask> getHistory();
}
