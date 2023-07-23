package tests.modelTest;

import model.Status;
import model.Task;
import model.TypeTasks;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class TaskTest extends AbstractTaskTest {
    @BeforeEach
    void setUp() {
        task = new Task("Test Name", "Test Description", 0, Status.NEW, 30, LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1));
        type = TypeTasks.TASKS;
    }
}
