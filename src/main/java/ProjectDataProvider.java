import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDataProvider {

    /**
     * insert the project
     * 
     * @param con      Connection to DB
     * @param name     Name of Project
     * @param deadline Deadline of Project
     * @return inserted Project
     */
    public static Project insertProject(Connection con, String name, Date deadline, int[] memberIDs)
            throws SQLIntegrityConstraintViolationException {
        if (con != null) {
            ResultSet key = DBConnection.insert(con,
                    "INSERT INTO projects(name,deadline) VALUES('" + name + "','" + deadline + "')");
            try {
                if (key.next()) {
                    int projectID = key.getInt(1);
                    Project insertedProject = ProjectDataProvider.selectProject(con, projectID);
                    for (int i = 0; i < memberIDs.length; i++) {
                        DBConnection.insert(con, "INSERT INTO projectuser(projectID, userID, userpoints) VALUES("
                                + insertedProject.getID() + "," + memberIDs[i] + ", 0)");
                    }
                    return insertedProject;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * select the project
     * 
     * @param con       Connection to DB
     * @param projectID ID of Project
     * @return selected Project
     */
    public static Project selectProject(Connection con, int projectID) {
        if (con != null) {
            ResultSet rs = DBConnection.select(con, "SELECT * FROM projects WHERE projectID='" + projectID + "'");
            try {
                if (rs != null) {
                    if (rs.next()) {
                        Project.ProjectBuilder builder = new Project.ProjectBuilder();
                        Project newProject = builder.setID(rs.getInt("projectID")).
                                setName(rs.getString("name")).
                                setDeadline(rs.getDate("deadline")).
                                createProject();
                        return newProject;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean deleteProject(Connection con, int projectID){
        if(con!=null){
            //select task all tasks of this project and delete them
            DBConnection.delete(con, "DELETE FROM tasks WHERE projectid = '"+ projectID + "'");

            //delete all rows of projectUser of this project
            DBConnection.delete(con, "DELETE FROM projectuser WHERE projectID = '"+ projectID + "'");

            return DBConnection.delete(con, "DELETE FROM projects WHERE projectID = '"+ projectID + "'");
        }
        return false;
    }

    /**
     * select all projects of specific user
     * 
     * @param con      Connection to dababase
     * @param userName
     * @return list of projects of this user
     */
    // TODO: test
    public static List<Project> selectAllProjectsOfUser(Connection con, String userName) {
        if (con != null && userName != null) {
            List<Project> listOfProjects = new ArrayList<>();
            ResultSet rs = DBConnection.select(con,
                    "select projects.projectID, projects.name, projects.deadline ,projectuser.userID " +
                            "from TMproject.projectuser " +
                            "inner join TMproject.projects on projectuser.projectid = projects.projectid " +
                            "inner join TMproject.useraccount on projectuser.userid = useraccount. userid " +
                            "where useraccount.name='" + userName + "';");

            try {
                if (rs != null) {
                    while (rs.next()) {
                        Project.ProjectBuilder builder = new Project.ProjectBuilder();
                        Project newProject = builder.setID(rs.getInt("projectID")).
                                                    setName(rs.getString("name")).
                                                    setDeadline(rs.getDate("deadline")).
                                                    createProject();
                        listOfProjects.add(newProject);
                    }
                    return listOfProjects;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}