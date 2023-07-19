package tests;

import model.*;

import org.junit.jupiter.api.Test;
import service.interfaces.TaskManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTaskManagerTest<T extends TaskManager> {

    protected T manager;
    Task task;
    Epic epic;
    Subtask subtask;

    @Test
    void createTaskTest() {
        int taskId = manager.create(task);

        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void createEpicTest() {
        int epicId = manager.create(epic);

        final Epic savedEpic = manager.getEpicById(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void createSubtaskTest() {
        int epicId = manager.create(epic);
        subtask.setEpicId(epicId);
        int subtaskId = manager.create(subtask);

        final Subtask savedSubtask = manager.getSubtaskById(subtaskId);

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateTaskTest() {
        int taskId = manager.create(task);

        final Task savedTask = manager.getTaskById(taskId);

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
    void updateEpicTest() {
        int epicId = manager.create(epic);
        Epic epic1 = new Epic("Test Epic", "Test Epic description Test", epicId);

        final Epic savedEpic = manager.getEpicById(epicId);

        manager.update(new Epic("Test Epic", "Test Epic description Test", epicId));

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic1, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic1, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateSubtaskAndEpicTest() {
        int epicId = manager.create(epic);
        subtask.setEpicId(epicId);
        int subtaskId = manager.create(subtask);

        final Subtask savedSubtask = manager.getSubtaskById(subtaskId);

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
    void getTasksTest() {
        manager.create(task);
        manager.create(task);

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
        assertEquals(task, tasks.get(1), "Задачи не совпадают.");
    }

    @Test
    void getEpicsTest() {
        int epicId = manager.create(epic);
        subtask.setEpicId(epicId);
        manager.create(subtask);

        final List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getTaskById() {
        int idTask = manager.create(task);

        Task saveTask = manager.getTaskById(idTask);
        assertEquals(task, saveTask);
    }

    @Test
    void getEpicById() {
        int idEpic = manager.create(epic);

        Epic saveEpic = manager.getEpicById(idEpic);
        assertEquals(epic, saveEpic);
    }

    @Test
    void getSubtaskById() {
        int idEpic = manager.create(epic);
        subtask.setEpicId(idEpic);
        int idSubtask = manager.create(subtask);

        Subtask saveSubtask = manager.getSubtaskById(idSubtask);
        assertEquals(subtask, saveSubtask);
    }

    @Test
    void getSubtasksInEpic() {
        int idEpic = manager.create(epic);
        Subtask subtask1 = new Subtask(idEpic, "test", "test");
        Subtask subtask2 = new Subtask(idEpic, "test", "test");
        int idSubtask1 = manager.create(subtask1);
        int idSubtask2 = manager.create(subtask2);
        subtask1.setId(idSubtask1);
        subtask2.setId(idSubtask2);
        List<Subtask> testSubtask = List.of(subtask1, subtask2);

        List<Subtask> saveSubtasks = manager.getSubtasksInEpic(idEpic);

        assertEquals(testSubtask.get(0), saveSubtasks.get(0));
        assertEquals(testSubtask.get(1), saveSubtasks.get(1));
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
        manager.create(epic);
        manager.create(epic);
        manager.deleteEpics();

        //TODO Дописать проверку на удаление подзадач

        assertTrue(manager.getEpics().isEmpty());
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
        int idTask = manager.create(task);

        manager.removeTaskById(idTask);

        assertTrue(manager.getTasks().isEmpty());
        assertEquals(0, manager.getTasks().size());
    }

    @Test
    void removeEpicById() {
        int idTask = manager.create(epic);

        manager.removeEpicById(idTask);
//TODO Дописать проверку на удаление подзадач
        assertTrue(manager.getEpics().isEmpty());
        assertEquals(0, manager.getEpics().size());
    }

    @Test
    void removeSubtaskById() {
        int idEpic = manager.create(epic);
        subtask.setEpicId(idEpic);
        int idTask = manager.create(subtask);
        assertNotNull(manager.getEpicById(manager.getSubtaskById(idTask).getEpicId()));
        manager.removeSubtaskById(idTask);


        assertTrue(manager.getSubtasks().isEmpty());
        assertEquals(0, manager.getSubtasks().size());
    }

    @Test
    void getHistory() {

    }
}
