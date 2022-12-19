import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDataProviderTest {

    @Test
    void insertProjectThatDoesntExist() {
        //NOTE: look for a name that isnt already in the db before running this test
        Connection con = DBConnection.getConnection();
        //assertEquals("Project34", ProjectDataProvider.insertProject(con,"Project34", Date.valueOf("2025-05-24"), new int[]{7, 9}).getName())
        DBConnection.disconnect(con);
        //TODO: remove name after test
    }
    @Test
    void insertProjectThatDoesAlreadyExist(){
        Connection con = DBConnection.getConnection();
        //assertThrows(SQLIntegrityConstraintViolationException.class,() -> ProjectDataProvider.insertProject(con,"Project1", Date.valueOf("2022-12-31"), new int[]{7, 9}));
        DBConnection.disconnect(con);
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