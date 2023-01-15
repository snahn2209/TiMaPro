import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class    UserDataProvider {


    /**
     * adds new user to database
     * @param con Connection to database
     * @param name name of inserted user as String
     * @return inserted UserAccount or null if user already exists
     * @throws SQLIntegrityConstraintViolationException when user already exists
     */
    public static UserAccount insertUser(Connection con, String name) throws SQLIntegrityConstraintViolationException {
        int totalPoints = 0; //new users don't have points yet

        if(con!=null){
            if(selectUser(con, name)==null){
                DBConnection.insert(con, "INSERT INTO useraccount(name,totalpoints) VALUES('"+ name +"',"+ totalPoints +")");
                return UserDataProvider.selectUser(con, name);
            }
        }
        return null;
    }

    /**
     * selects user with specific name
     * @param con Connection to database
     * @param name name of inserted user as String
     * @return selected UserAccount
     */
    public static UserAccount selectUser(Connection con, String name) {
        if(con!=null){
            ResultSet rs = DBConnection.select(con, "SELECT * FROM useraccount WHERE name='"+ name +"'");
            try{
                if(rs!=null){
                    while (rs.next()){
                        return new UserAccount(
                                rs.getInt("userID"),
                                rs.getString("name"),
                                rs.getInt("totalpoints")
                        );
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static UserAccount selectUserByID(Connection con, int userID) {
        if(con!=null){
            ResultSet rs = DBConnection.select(con, "SELECT * FROM useraccount WHERE userID='"+ userID +"'");
            try{
                if(rs!=null){
                    while (rs.next()){
                        return new UserAccount(
                                rs.getInt("userID"),
                                rs.getString("name"),
                                rs.getInt("totalpoints")
                        );
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * deletes user with specific ID
     * @param con Connection to DB
     * @param userID unique ID of user
     * @return whether the deletion was successfully
     */
    public static boolean deleteUser(Connection con, int userID){
        //TODO: update all tasks linked to this user

        if(con!=null){
            //delete user from all projects
            boolean success = DBConnection.delete(con, "DELETE FROM projectuser WHERE userID = '"+ userID + "'");
            return success;
        }
        return false;
    }

    /**
     * returns List of UserAccounts which are members of a certain Project
     * @param con Connection to db
     * @param projectID unique ID of project
     * @return List of UserAccounts
     */
    //TODO: test this method
    public static List<UserAccount> selectAllUsersOfProject(Connection con, int projectID) {
        if(con!=null ){
            List<UserAccount> members = new ArrayList<>();
            ResultSet rs = DBConnection.select(con, "SELECT * FROM TMproject.projectuser WHERE projectID="+projectID+";");
            try {
                if (rs != null) {
                    while (rs.next()) {
                        UserAccount memberOfProject = UserDataProvider.selectUserByID(con, rs.getInt("userID"));
                        if(memberOfProject!=null){
                            //return only points earned in this project
                            memberOfProject.setTotalPoints(rs.getInt("userpoints"));
                            members.add(memberOfProject);
                        }
                    }
                    return members;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static UserAccount earnPoints(Connection con, int userID, int points){
        if(con!=null){
            Boolean success = DBConnection.update(con, "UPDATE TMproject.useraccount SET totalpoints=totalpoints+"+points+" WHERE userID='"+userID+"';");
            if (success){
                return UserDataProvider.selectUserByID(con, userID);
            }
        }
        return null;
    }
}
