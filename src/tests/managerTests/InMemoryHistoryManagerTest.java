package tests.managerTests;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.interfaces.HistoryManager;
import service.manager.Manager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private HistoryManager emptyHistoryManager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void setUp() {
        historyManager = Manager.getDefaultHistory();
        emptyHistoryManager = Manager.getDefaultHistory();
        task = new Task("Test Task", "1", 1, Status.NEW, 30, LocalDateTime.now());
        epic = new Epic("Test Epic", "2", 2);
        subtask = new Subtask(epic.getId(), "Test Subtask", "3", 3, Status.NEW, 30, LocalDateTime.now());
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
    }

    @Test
    void getHistory() {
        assertEquals(task, historyManager.getHistory().get(0));
    }

    @Test
    void getEmptyHistory() {
        assertTrue(emptyHistoryManager.getHistory().isEmpty());
    }

    @Test
    void add() {
        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(epic, historyManager.getHistory().get(1));
        assertEquals(subtask, historyManager.getHistory().get(2));
        assertEquals(3, historyManager.getHistory().size());
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
        historyManager.remove(task.getId());

        assertEquals(epic, historyManager.getHistory().get(0));
        assertEquals(subtask, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void removeCentralTask() {
        historyManager.remove(epic.getId());

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(subtask, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void removeLastTask() {
        historyManager.remove(subtask.getId());

        assertEquals(task, historyManager.getHistory().get(0));
        assertEquals(epic, historyManager.getHistory().get(1));
        assertEquals(2, historyManager.getHistory().size());
    }
}