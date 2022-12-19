import java.sql.*;

public class ProjectDataProvider {


    /**
     * insert the project
     * @param con
     * @param name
     * @param deadline
     * @return inserted Project
     * @throws SQLIntegrityConstraintViolationException
     */
    public static Project insertProject(Connection con, String name, Date deadline, int[] memberIDs) throws SQLIntegrityConstraintViolationException {

        if (con != null) {
            try {
                DBConnection.insert(con, "INSERT INTO projects(name,deadline) VALUES('" + name + "','"+ deadline +"')");
                Project insertedProject = ProjectDataProvider.selectProject(con, name);
                for(int i = 0; i<memberIDs.length; i++){
                    DBConnection.insert(con, "INSERT INTO projectuser(projectID, userID, userpoints) VALUES("+insertedProject.getID()+","+memberIDs[i]+", 0)");
                }
                return insertedProject;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw e;
            }
        }
        return null;
    }



    /**
     * select the project
     * @param con
     * @param name
     * @return selected Project
     */
    public static Project selectProject(Connection con, String name) {
        if (con != null) {
            ResultSet rs = DBConnection.select(con, "SELECT * FROM projects WHERE name='" + name + "'");
            try {
                if (rs != null) {
                    while (rs.next()) {
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