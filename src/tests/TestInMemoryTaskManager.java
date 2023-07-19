package tests;

import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInMemoryTaskManager extends AbstractTaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
        task = new Task("Test Task", "Test Task description", 30, LocalDateTime.now());
        epic = new Epic("Test Epic", "Test Epic description");
        subtask = new Subtask(1, "Test Epic", "Test Epic description");
    }

    @Test
    void getHistory() {
        int idTask = manager.create(task);
        manager.getTaskById(idTask);
        List<AbstractTask> saveTaskHistory = manager.getHistory();
        assertEquals(task, saveTaskHistory.get(0));
    }
}
