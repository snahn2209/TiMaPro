import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    @DisplayName("get project name")
    void getName() {
        assertAll(
            () -> assertEquals("Info", new Project("Info").getName()),
            () -> assertEquals("IMI", new Project("IMI").getName())
        );
    }
}