import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDataProviderTest {

    @Test
    void insertProject() throws SQLIntegrityConstraintViolationException {
        Connection con = DBConnection.getConnection();
        Project insertedProject = ProjectDataProvider.insertProject(con,"Project3", Date.valueOf("2025-05-24"), new int[]{1, 2});
        System.out.println(insertedProject.toString());
        assertEquals("Project3", insertedProject.getName());

        //delete test-project
        ProjectDataProvider.deleteProject(con, insertedProject.getID());

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

    @Test
    void deleteProject() {
        //NOTE: look for a user ID that exists
        Connection con = DBConnection.getConnection();
        boolean executedSuccessfully = ProjectDataProvider.deleteProject(con, 6);
        DBConnection.disconnect(con);
        assertTrue(executedSuccessfully);
    }

    @Test
    void selectAllProjectsOfUser() {
        //1. select all Projects of Text User Pia
        String username = "Pia";

        Connection con = DBConnection.getConnection();

        UserAccount user = UserDataProvider.selectUser(con, username);
        int testUserID = user.getID();

        List<Project> projects = ProjectDataProvider.selectAllProjectsOfUser(con, username);

        //2. justify that Pia is member of all Projects
        Boolean areAllProjectsOfUser = true;
        //get all user-project combinations from db
        ResultSet rs = DBConnection.select(con, "SELECT * FROM projectuser");
        try {
            if (rs != null && projects!=null) {
                while (rs.next()) { //goes through all user-project combinations
                    int projectID = rs.getInt("projectID");
                    int userID = rs.getInt("userID");
                    if(userID==testUserID){ //if testUser is involved look for project
                        Boolean foundProject = false;
                        for(Project project : projects){
                            if(project.getID()==projectID){
                                foundProject=true;
                                break;
                            }
                        }
                        if(foundProject=false){
                            areAllProjectsOfUser=false;
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(areAllProjectsOfUser);

        DBConnection.disconnect(con);
    }
}