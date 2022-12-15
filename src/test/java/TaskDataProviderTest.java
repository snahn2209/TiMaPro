import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataProviderTest {
    @Test
    void insertTaskThatDoesNotExist() throws SQLIntegrityConstraintViolationException {
        Connection con = DBConnection.getConnection();
        assertEquals("ToDo3", TaskDataProvider.insertTask(con, "ToDo3", Date.valueOf("2022-12-17"), 1.0, 7, 1, 1).getName());
        DBConnection.disconnect(con);
    }

    @Test
    void insertTaskThatExists(){
        Connection con = DBConnection.getConnection();
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> TaskDataProvider.insertTask(con,"hallo", Date.valueOf("2022-12-16"), 1.5, 3, 1, 1));
        DBConnection.disconnect(con);
    }

    @Test
    void selectTaskThatExists() {
        Connection con = DBConnection.getConnection();
        assertEquals("hallo", TaskDataProvider.selectTask(con, "hallo").getName());
        DBConnection.disconnect(con);
    }
    @Test
    void selectTaskThatDoesntExist() {
        Connection con = DBConnection.getConnection();
        assertNull(TaskDataProvider.selectTask(con, "gibtsnicht"));
        DBConnection.disconnect(con);
    }



}