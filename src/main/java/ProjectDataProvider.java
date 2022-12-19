import java.sql.*;

public class ProjectDataProvider {


    /**
     * insert the project
     * @param con Connection to DB
     * @param name Name of Project
     * @param deadline Deadline of Project
     * @return inserted Project
     */
    public static Project insertProject(Connection con, String name, Date deadline, int[] memberIDs) throws SQLIntegrityConstraintViolationException {
        if (con != null) {
            ResultSet key = DBConnection.insert(con, "INSERT INTO projects(name,deadline) VALUES('" + name + "','"+ deadline +"')");
            try {
                if(key.next()) {
                    int projectID = key.getInt(1);
                    Project insertedProject = ProjectDataProvider.selectProject(con, projectID);
                    for(int i = 0; i<memberIDs.length; i++){
                        DBConnection.insert(con, "INSERT INTO projectuser(projectID, userID, userpoints) VALUES("+insertedProject.getID()+","+memberIDs[i]+", 0)");
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
     * @param con Connection to DB
     * @param projectID ID of Project
     * @return selected Project
     */
    public static Project selectProject(Connection con, int projectID) {
        if (con != null) {
            ResultSet rs = DBConnection.select(con, "SELECT * FROM projects WHERE projectID='" + projectID + "'");
            try {
                if (rs != null) {
                    if(rs.next()) {
                        return new Project(
                                rs.getInt("projectID"),
                                rs.getString("name"),
                                rs.getDate("deadline")
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}