import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class    UserDataProvider {


    /**
     * adds new user to database
     * @param con Connection to database
     * @param name name of inserted user as String
     * @return inserted UserAccount
     * @throws SQLIntegrityConstraintViolationException when user already exists
     */
    public static UserAccount insertUser(Connection con, String name) throws SQLIntegrityConstraintViolationException {
        int totalPoints = 0; //new users don't have points yet

        if(con!=null){
            try {
                DBConnection.insert(con, "INSERT INTO useraccount(name,totalpoints) VALUES('"+ name +"',"+ totalPoints +")");
                return UserDataProvider.selectUser(con, name);
            } catch (SQLIntegrityConstraintViolationException e) {
                //thrown when user already exists
                throw e;
            }
            //return null;
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
    
    public static List<UserAccount> selectAllUsers(Connection con){
        List<UserAccount> listOfUsers = new ArrayList<>();
        if(con!=null){
            ResultSet rs = DBConnection.select(con, "SELECT * FROM useraccount");
            try{
                if(rs!=null){
                    while (rs.next()){
                        listOfUsers.add(new UserAccount(
                                rs.getInt("userID"),
                                rs.getString("name"),
                                rs.getInt("totalpoints")
                        ));
                    }
                }
                return listOfUsers;

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
        //TODO: update all tasks liked to this user
        //TODO: update all projects linked to this user
        if(con!=null){
            return DBConnection.delete(con, "DELETE FROM  useraccount WHERE userID = '"+ userID + "'");
        }
        return false;
    }

    //TODO: update totalpoints

}
