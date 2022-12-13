import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void calculateMaxPoints() {
        Task task = new Task(1.0);
        assertEquals(6, task.calculateMaxPoints(1.0));

    }
}