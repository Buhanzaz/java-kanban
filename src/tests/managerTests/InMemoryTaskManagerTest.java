package tests.managerTests;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;

import java.time.LocalDateTime;


public class InMemoryTaskManagerTest extends AbstractTaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
        task = new Task("Test Task", "Test Task",
                30, LocalDateTime.of(2023, 12, 12, 12, 12, 12, 12));
        epic = new Epic("Test Epic", "Test Epic");
        taskId = manager.create(task);
        savedTask = manager.getTaskById(taskId);
        epicId = manager.create(epic);
        savedEpic = manager.getEpicById(epicId);
        subtask = new Subtask(epicId, "Test Subtask", "Test Subtask",
                30, LocalDateTime.of(2023, 12, 13, 12, 12, 12, 12));
        subtaskId = manager.create(subtask);
        savedSubtask = manager.getSubtaskById(subtaskId);
    }

    @Test
    void updateEpicTime() {

    }

    @Test
    void addPrioritizedTasks() {

    }
}