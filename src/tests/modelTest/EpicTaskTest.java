package tests.modelTest;

import model.*;
import org.junit.jupiter.api.*;
import service.interfaces.TaskManager;
import service.manager.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest extends AbstractTaskTest {

    private TaskManager taskManager;
    private int idEpic;
    private int idSubtask;
    private int idSubtask2;


    @BeforeEach
    public void setUp() {
        type = TypeTasks.EPIC;
        task = new Epic("Test Name", "Test Description");
        taskManager = Manager.getDefault();
        idEpic = taskManager.create(new Epic("Test Epic 1", "Test Epic Description 1"));
        idSubtask = taskManager.create(new Subtask(idEpic, "Test Subtask 2", "Description Subtask Test 2",
                30, LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1)));
        idSubtask2 = taskManager.create(new Subtask(idEpic, "Test Subtask 2", "Description Subtask Test 2",
                30, LocalDateTime.of(2001, 1, 1, 1, 1, 1, 1)));
    }

    @Override
    @Test
    void getStartTime() {
        assertNull(task.getStartTime());
    }

    @Override
    @Test
    void getEndTime() {
        assertNull(task.getEndTime());
    }

    @Override
    @Test
    void getDuration() {
        assertEquals(0, task.getDuration());
    }

    @Test
    public void emptyEpicTest() {
        taskManager.deleteSubtask();
        List<Integer> testSubtaskId = new ArrayList<>();
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        assertEquals(testSubtaskId, testSubtaskId2);
        Assertions.assertEquals(Status.NEW, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtaskStatusNewEpicTest() {
        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.NEW, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtaskStatusDoneEpicTest() {
        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask, Status.DONE, 30, LocalDateTime.MIN));
        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask2, Status.DONE, 30, LocalDateTime.MAX.minusMinutes(30)));

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.DONE, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtasksStatusNewAndDoneEpicTest() {
        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();

        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask2, Status.DONE, 30, LocalDateTime.now()));

        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(idEpic).getStatus());
    }

    @Test
    public void allSubtasksStatusInProgressEpicTest() {
        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask, Status.IN_PROGRESS, 30, LocalDateTime.now()));
        taskManager.update(new Subtask(idEpic, "Test Subtask", "Description Subtask Test", idSubtask2, Status.IN_PROGRESS, 30, LocalDateTime.now()));

        List<Integer> testSubtaskId = List.of(idSubtask, idSubtask2);
        List<Integer> testSubtaskId2 = taskManager.getEpicById(idEpic).getSubtasksId();
        assertEquals(testSubtaskId, testSubtaskId2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(idEpic).getStatus());
    }
}
