import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataProviderTest {
    @Test
    void insertTaskThatDoesNotExist() throws SQLIntegrityConstraintViolationException {
        //NOTE: use another name before running the test
        Connection con = DBConnection.getConnection();
        //TODO: assertEquals("Task2", TaskDataProvider.insertTask(con, "Task2", Date.valueOf("2022-12-31"), 1.5, 9, 2, 2).getName());
        DBConnection.disconnect(con);
    }

    @Test
    void insertTaskThatExists(){
        Connection con = DBConnection.getConnection();
        //TODO: assertThrows(SQLIntegrityConstraintViolationException.class, () -> TaskDataProvider.insertTask(con,"hallo", Date.valueOf("2022-12-16"), 1.5, 3, 1, 1));
        DBConnection.disconnect(con);
    }

    @Test
    void selectTaskThatExists() {
        Connection con = DBConnection.getConnection();
        Task selectedTask = TaskDataProvider.selectTask(con, 1);
        System.out.println(selectedTask.toString());
        assertEquals(1, selectedTask.getId());
        DBConnection.disconnect(con);
    }
    @Test
    void selectTaskThatDoesntExist() {
        Connection con = DBConnection.getConnection();
        assertNull(TaskDataProvider.selectTask(con, 10000));
        DBConnection.disconnect(con);
    }



}