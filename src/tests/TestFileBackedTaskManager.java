package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        task = new Task("Test Task", "Test Task description", 30, LocalDateTime.now());
        epic = new Epic("Test Epic", "Test Epic description");
        subtask = new Subtask(1, "Test Epic", "Test Epic description");
    }

    @Test
    void loadFromFileTest() {
        int idTask = manager.create(new Task("Task", "Task", 30, LocalDateTime.now()));
        int idEpic = manager.create(new Epic("Epic", "Epic"));
        int idSubtask = manager.create(new Subtask(idEpic, "Subtask", "Subtask"));

        manager.getTaskById(idTask);
        manager.getEpicById(idEpic);
        manager.getSubtaskById(idSubtask);


        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager.getHistory().get(0), newManager.getHistory().get(0));
        assertEquals(manager.getHistory().get(1), newManager.getHistory().get(1));
        assertEquals(manager.getHistory().get(2), newManager.getHistory().get(2));
        assertEquals(manager.getTaskById(idTask), newManager.getTaskById(idTask));
        assertEquals(manager.getEpicById(idEpic), newManager.getEpicById(idEpic));
        assertEquals(manager.getSubtaskById(idSubtask), newManager.getSubtaskById(idSubtask));
    }

    //TODO Переделать тест так чтобы он вылавливал ошибку что файл пуст
   /* @Test
    void loadFromEmptyFileTest() {
        manager = FileBackedTasksManager.loadFromFile(emptyFile);

        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getEpics().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());
        assertTrue(manager.getHistory().isEmpty());
    }*/

    @Test
    void loadFromEpicFileTest() {
        int epicId = manager.create(epic);
        manager = FileBackedTasksManager.loadFromFile(file);

        assertEquals(epic, manager.getEpicById(epicId));
        assertFalse(manager.getEpics().isEmpty());
        assertFalse(manager.getHistory().isEmpty());
        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());
        assertTrue(manager.getSubtasksInEpic(epicId).isEmpty());
    }
}
