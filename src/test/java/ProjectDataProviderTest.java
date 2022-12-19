import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDataProviderTest {

    @Test
    void insertProject() throws SQLIntegrityConstraintViolationException {
        //NOTE: look for a name that isnt already in the db before running this test
        Connection con = DBConnection.getConnection();
        Project insertedProject = ProjectDataProvider.insertProject(con,"Project3", Date.valueOf("2025-05-24"), new int[]{1, 2});
        System.out.println(insertedProject.toString());
        assertEquals("Project3", insertedProject.getName());
        DBConnection.disconnect(con);

        //TODO: remove name after test
    }

    @Test
    void selectProjectThatExists() {
        Connection con = DBConnection.getConnection();
        Project selectedProject = ProjectDataProvider.selectProject(con, 1);
        System.out.println(selectedProject.toString());
        assertEquals("Project1", selectedProject.getName());
        DBConnection.disconnect(con);
    }

    @Test
    void selectProjectThatDoesntExists() {
        Connection con = DBConnection.getConnection();
        assertNull(ProjectDataProvider.selectProject(con,100000));
        DBConnection.disconnect(con);
    }
}