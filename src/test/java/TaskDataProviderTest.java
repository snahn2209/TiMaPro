import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataProviderTest {
    @Test
    void insertTaskThatDoesNotExist() throws SQLIntegrityConstraintViolationException {
        Connection con = DBConnection.getConnection();
        Task insertedTask = TaskDataProvider.insertTask(con, "Test-Task", Date.valueOf("2023-03-01"), 1.5, 9, 2, 1);
        System.out.println(insertedTask.toString());
        assertEquals("Test-Task", insertedTask.getName());

        //delete test-task
        TaskDataProvider.deleteTask(con, insertedTask.getId());

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

    @Test
    void deleteTask(){
        //TODO: improve (Task ID should not be found in tasks after deletion)
        Connection con = DBConnection.getConnection();
        boolean executedSuccessfully = TaskDataProvider.deleteTask(con, 7);
        assertTrue(executedSuccessfully);
        DBConnection.disconnect(con);
    }

    @Test
    void selectAllTasksOfProject(){
        int testProjectID = 1;
        Connection con = DBConnection.getConnection();
        List<Task> tasks = TaskDataProvider.selectAllTasksOfProject(con, testProjectID);
        DBConnection.disconnect(con);

        boolean correctness = true;
        for(Task task : tasks){
            if(task.getProject()!=testProjectID){
                correctness = false;
                break;
            }
        }

        assertTrue(correctness);
    }

}