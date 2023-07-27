package tests.modelTest;

import model.Status;
import model.Subtask;
import model.TypeTasks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SubtaskTest extends AbstractTaskTest<Subtask> {
    @BeforeEach
    void setUp() {
        task = new Subtask(1, "Test Name", "Test Description", 0, Status.NEW,
                30, LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1));
        type = TypeTasks.SUBTASK;
    }

    @Test
    void getEpicId() {
        int id = 1;
        Subtask subtask = task;
        assertEquals(id, subtask.getEpicId());
    }
}
