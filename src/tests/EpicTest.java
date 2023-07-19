package tests;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.*;
import service.interfaces.TaskManager;
import service.manager.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    //TODO Сделать абстрактный клас со всеми тестами на задачи
    private static TaskManager taskManager;
    private static int idEpic;

    @BeforeEach
    public void startTestsParamNone() {
        taskManager = Manager.getDefault();
        idEpic = taskManager.create(new Epic("Test Epic", "Description Epic Test"));
    }

    @Test
    public void emptyEpicTest() {
        List<Integer> testSubtaskId = new ArrayList<>();
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        assertEquals(testSubtaskId, testSubtaskId2);
        Assertions.assertEquals(Status.NEW, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtaskStatusNewEpicTest() {
        int idSubtask = taskManager.create(new Subtask(idEpic, "Test Subtask", "Description Subtask Test"));
        List<Integer> testSubtaskId = List.of(idSubtask);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.NEW, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtaskStatusDoneEpicTest() {
        int idSubtask = taskManager.create(new Subtask(idEpic, "Test Subtask", "Description Subtask Test"));
        List<Integer> testSubtaskId = List.of(idSubtask);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask, Status.DONE));

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.DONE, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtasksStatusNewAndDoneEpicTest() {
        int idSubtask = taskManager.create(new Subtask(idEpic, "Test Subtask", "Description Subtask Test"));
        int idSubtask2 = taskManager.create(new Subtask(idEpic, "Test Subtask 2", "Description Subtask Test 2"));
        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask2, Status.DONE));

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtasksStatusInProgressEpicTest() {
        int idSubtask = taskManager.create(new Subtask(idEpic, "Test Subtask", "Description Subtask Test"));
        int idSubtask2 = taskManager.create(new Subtask(idEpic, "Test Subtask 2", "Description Subtask Test 2"));

        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask, Status.IN_PROGRESS));
        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask2, Status.IN_PROGRESS));

        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();
        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(idEpic).getStatus());
    }
}