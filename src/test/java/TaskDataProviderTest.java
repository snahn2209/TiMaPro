import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataProviderTest {
    @Test
    void selectTaskThatExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                () -> assertEquals(1, TaskDataProvider.selectTask(con, 1).getId())
        );
        DBConnection.disconnect(con);
    }

}