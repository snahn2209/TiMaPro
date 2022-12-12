import java.sql.*;

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

    //TODO: select for multiple users
    //TODO: delete user
    //TODO: update totalpoints

}
