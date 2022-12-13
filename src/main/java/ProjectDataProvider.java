import java.sql.*;

public class ProjectDataProvider {
    //TODO: insert Project
    public static Project insertProject(Connection con, String name, Date deadline) throws SQLIntegrityConstraintViolationException {

        if (con != null) {
            try {
                DBConnection.insert(con, "INSERT INTO projects(name,deadline) VALUES('" + name + "','"+ deadline +"')");
                return ProjectDataProvider.selectProject(con, name);
            } catch (SQLIntegrityConstraintViolationException e) {
                throw e;
            }
        }
        return null;
    }

    //TODO: select Project
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
