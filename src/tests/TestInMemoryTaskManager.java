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
        task = new Task("Test Task", "Test Task", 30, LocalDateTime.now());
        epic = new Epic("Test Epic", "Test Epic");
        taskId = manager.create(task);
        savedTask = manager.getTaskById(taskId);
        epicId = manager.create(epic);
        savedEpic = manager.getEpicById(epicId);
        subtask = new Subtask(epicId, "Test Subtask", "Test Subtask", 30, LocalDateTime.now());
        subtaskId = manager.create(subtask);
        savedSubtask = manager.getSubtaskById(subtaskId);
    }


}
