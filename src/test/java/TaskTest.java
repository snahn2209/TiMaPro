import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void calculateMaxPoints() {
        assertEquals(6, Task.calculateMaxPoints(1.0));

    }
}