package tests.managerTests;

import model.*;

import org.junit.jupiter.api.*;
import service.interfaces.TaskManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTaskManagerTest<T extends TaskManager> {

    T manager;
    Task task;
    Epic epic;
    Subtask subtask;
    int taskId;
    int epicId;
    int subtaskId;
    Task savedTask;
    Epic savedEpic;
    Subtask savedSubtask;

    @Test
    void createTaskTest() {
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void createEmptyTaskTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.create((Task) null));
        assertNull(exception.getMessage());
    }

    @Test
    void createEpicTest() {
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void createEmptyEpicTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.create((Epic) null));
        assertNull(exception.getMessage());
    }

    @Test
    void createSubtaskTest() {
        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void createEmptySubtaskTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.create((Subtask) null));
        assertNull(exception.getMessage());
    }

    @Test
    void updateTaskTest() {
        savedTask.setStatus(Status.DONE);

        manager.update(savedTask);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateEmptyTaskTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.update((Task) null));
        assertNull(exception.getMessage());
    }

    @Test
    void updateEpicTest() {
        manager.update(new Epic("Test Epic", "Test Epic description Test", epicId));

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateEmptyEpicTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.update((Epic) null));
        assertNull(exception.getMessage());
    }

    @Test
    void updateSubtaskAndEpicTest() {
        savedSubtask.setStatus(Status.DONE);
        manager.update(subtask);

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
        assertEquals(manager.getEpicById(epicId).getStatus(), savedSubtask.getStatus());
    }

    @Test
    void updateEmptySubtaskAndEpicTest() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.update((Epic) null));
        assertNull(exception.getMessage());
        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> manager.update((Subtask) null));
        assertNull(exception2.getMessage());
    }

    @Test
    void getTasksTest() {
        manager.create(task);

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
        assertEquals(task, tasks.get(1), "Задачи не совпадают.");
    }

    @Test
    void getEmptyTaskTest() {
        manager.deleteTasks();

        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void getEpicsTest() {
        manager.create(epic);

        final List<Epic> epics = manager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(2, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void getEmptyEpicsTest() {
        manager.deleteEpics();

        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void getSubtasksTest() {
        manager.create(subtask);

        List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(2, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getEmptySubtaskTest() {
        manager.deleteSubtask();

        assertTrue(manager.getSubtasks().isEmpty());
    }


    @Test
    void getTaskById() {
        assertEquals(task, manager.getTaskById(taskId));
    }

    @Test
    void getEmptyTaskById() {
        assertNull(manager.getTaskById(Integer.MAX_VALUE));
    }

    @Test
    void getEpicById() {
        assertEquals(epic, manager.getEpicById(epicId));
    }

    @Test
    void getEmptyEpicById() {
        assertNull(manager.getEpicById(Integer.MAX_VALUE));
    }

    @Test
    void getSubtaskById() {
        assertEquals(subtask, manager.getSubtaskById(subtaskId));
    }

    @Test
    void getEmptySubtaskById() {
        assertNull(manager.getSubtaskById(Integer.MAX_VALUE));
    }

    @Test
    void getSubtasksInEpic() {
        Subtask subtask1 = new Subtask(epicId, "test", "test", 30, LocalDateTime.now());
        manager.create(subtask1);
        List<Subtask> testSubtask = List.of(subtask, subtask1);

        List<Subtask> saveSubtasks = manager.getSubtasksInEpic(epicId);

        assertEquals(testSubtask.get(0), saveSubtasks.get(0));
        assertEquals(testSubtask.get(1), saveSubtasks.get(1));
    }

    @Test
    void getEmptySubtasksInEpic() {
        manager.deleteSubtask();
        manager.deleteEpics();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.getSubtasksInEpic(Integer.MAX_VALUE));
        assertNull(exception.getMessage());
    }

    @Test
    void deleteTasksTest() {
        manager.create(task);
        manager.create(task);
        manager.deleteTasks();


        assertTrue(manager.getTasks().isEmpty());
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    void deleteEpics() {
        manager.deleteEpics();

        NullPointerException exception = assertThrows(NullPointerException.class, () -> manager.getSubtasksInEpic(epicId));
        assertTrue(manager.getEpics().isEmpty());
        assertNull(exception.getMessage());
        assertEquals(0, manager.getEpics().size());
    }

    @Test
    void deleteSubtask() {
        manager.create(subtask);
        manager.create(subtask);
        manager.deleteSubtask();

        assertTrue(manager.getSubtasks().isEmpty());
        assertEquals(0, manager.getSubtasks().size());
    }

    @Test
    void removeTaskById() {
        manager.removeTaskById(taskId);

        assertNull(manager.getTaskById(taskId));
    }

    @Test
    void removeEpicById() {
        manager.removeEpicById(epicId);

        assertNull(manager.getEpicById(epicId));
    }

    @Test
    void removeSubtaskById() {
        manager.removeSubtaskById(subtaskId);
        assertNull(manager.getSubtaskById(subtaskId));
    }
}