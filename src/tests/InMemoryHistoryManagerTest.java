package tests;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.interfaces.HistoryManager;
import service.manager.Manager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void startTest() {
        historyManager = Manager.getDefaultHistory();
        task = new Task("Test Task", "1", 101, Status.NEW, 30, LocalDateTime.now());
        epic = new Epic("Test Epic", "2", 102);
        subtask = new Subtask(epic.getId(), "Test Subtask", "3", 103, Status.NEW);
    }

    @Test
    void getHistory() {
        historyManager.add(task);
        assertEquals(task, historyManager.getHistory().get(0));
    }

    @Test
    void getEmptyHistory() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void add() {
        historyManager.add(task);
        historyManager.add(epic);

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(epic, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void addClone() {
        historyManager.add(task);
        historyManager.add(task);

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void removeFirstTask() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(task.getId());

        assertEquals(epic, historyManager.getHistory().get(0));
        assertEquals(subtask, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void removeCentralTask() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(epic.getId());

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(subtask, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void removeLastTask() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(subtask.getId());

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(epic, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }
}