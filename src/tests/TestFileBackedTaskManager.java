package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.exception.ManagerSaveException;
import service.manager.FileBackedTasksManager;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileBackedTaskManager extends AbstractTaskManagerTest<FileBackedTasksManager> {

    File file;
    File emptyFile;


    @BeforeEach
    void setUp() {
        file = new File("FileBacked.csv");
        emptyFile = new File("test.csv");
        manager = new FileBackedTasksManager(file);
        task = new Task("Test Task", "Test Task", 30, LocalDateTime.of(2023,12,12,12,12,12,12));
        epic = new Epic("Test Epic", "Test Epic");
        taskId = manager.create(task);
        epicId = manager.create(epic);
        savedEpic = manager.getEpicById(epicId);
        subtask = new Subtask(epicId, "Test Subtask", "Test Subtask", 30, LocalDateTime.of(2023,12,13,12,12,12,12));
        subtaskId = manager.create(subtask);
        savedSubtask = manager.getSubtaskById(subtaskId);
        savedTask = manager.getTaskById(taskId);
    }

   @Test
    void loadFromFileTest() {
        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager.getHistory().get(0), newManager.getHistory().get(0));
        assertEquals(manager.getHistory().get(1), newManager.getHistory().get(1));
        assertEquals(manager.getHistory().get(2), newManager.getHistory().get(2));
        assertEquals(manager.getTaskById(taskId), newManager.getTaskById(taskId));
        assertEquals(manager.getEpicById(epicId), newManager.getEpicById(epicId));
        assertEquals(manager.getSubtaskById(subtaskId), newManager.getSubtaskById(subtaskId));
    }

    //TODO Переделать тест так чтобы он вылавливал ошибку что файл пуст
   @Test
    void loadFromEmptyFileTest() {
        ManagerSaveException exception = assertThrows(ManagerSaveException.class,
                () -> FileBackedTasksManager.loadFromFile(emptyFile));

        assertNull(exception.getMessage());
    }

    @Test
    void loadFromEpicFileTest() {
        deleteTasksTest();
        deleteSubtask();
        manager = FileBackedTasksManager.loadFromFile(file);

        assertEquals(epic, manager.getEpicById(epicId));
        assertFalse(manager.getEpics().isEmpty());
        assertFalse(manager.getHistory().isEmpty());
        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());
        assertTrue(manager.getSubtasksInEpic(epicId).isEmpty());
    }
}
