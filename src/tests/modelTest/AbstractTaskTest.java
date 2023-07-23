package tests.modelTest;

import model.AbstractTask;
import model.Status;
import model.TypeTasks;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTaskTest<T extends AbstractTask> {
    T task;
    TypeTasks type;

    @Test
    void getName() {
        String testName = "Test Name";
        assertEquals(testName, task.getName());
    }

    @Test
    void setName() {
        String testName = "Test new Name";
        task.setName(testName);
        assertEquals(testName, task.getName());
    }

    @Test
    void getDescription() {
        String testDescription = "Test Description";
        assertEquals(testDescription, task.getDescription());
    }

    @Test
    void setDescription() {
        String testDescription = "Test new Description";
        task.setDescription(testDescription);
        assertEquals(testDescription, task.getDescription());
    }

    @Test
    void getId() {
        int id = 0;
        assertEquals(id, task.getId());
    }

    @Test
    void setId() {
        int newId = 2;
        task.setId(newId);
        assertEquals(newId, task.getId());
    }

    @Test
    void getStatus() {
        Status status = Status.NEW;
        assertEquals(status, task.getStatus());
    }

    @Test
    void setStatus() {
        Status newStatus = Status.DONE;
        task.setStatus(newStatus);
        assertEquals(newStatus, task.getStatus());
    }

    @Test
    void getStartTime() {
        LocalDateTime startTime = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1);
        assertEquals(startTime, task.getStartTime());
    }

    @Test
    void getEndTime() {
        LocalDateTime endTime = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1).plusMinutes(30);
        assertEquals(endTime, task.getEndTime());
    }

    @Test
    void setStartTime() {
        LocalDateTime startTime = LocalDateTime.of(2001, 1, 1, 1, 1, 1, 1);
        task.setStartTime(startTime);
        assertEquals(startTime, task.getStartTime());
    }

    @Test
    void getDuration() {
        int duration = 30;
        assertEquals(duration, task.getDuration());
    }

    @Test
    void setDuration() {
        int duration = 60;
        task.setDuration(duration);
        assertEquals(duration, task.getDuration());
    }


    @Test
    void getType() {
        assertEquals(type, task.getType());
    }
}