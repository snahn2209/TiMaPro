import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
     * select project with specific
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


    /**
     * select all projects of specific user
     * @param con Connection to dababase
     * @param userName
     * @return list of projects of this user
     */
    //TODO: test
    public static List<Project> selectAllProjectsOfUser(Connection con, String userName){
        if(con!=null && userName!=null){
            List<Project> listOfProjects = new ArrayList<>();
            ResultSet rs = DBConnection.select(con,
            "select projects.projectID, projects.name, projects.deadline ,projectuser.userID "+
                    "from TMproject.projectuser " +
                    "inner join TMproject.projects on projectuser.projectid = projects.projectid " +
                    "inner join TMproject.useraccount on projectuser.userid = useraccount. userid " +
                    "where useraccount.name='"+ userName +"';"
            );

            try{
                if(rs!=null){
                    while (rs.next()){
                        listOfProjects.add(new Project(
                                rs.getInt("projectID"),
                                rs.getString("name"),
                                rs.getDate("deadline")
                                ));
                    }
                    return listOfProjects;
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}