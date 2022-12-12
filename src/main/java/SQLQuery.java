import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQuery {
    //TODO: separate this Class into 3 dataProvider-Classes for User,Project,Task

    /**
     * adds new user to database
     * @param con Connection to database
     * @param name name of inserted user as String
     * @return inserted UserAccount
     */
    public static UserAccount insertUser(Connection con, String name) {
        int totalPoints = 0; //new users don't have points yet

        if(con!=null){
            DBConnection.insert(con, "INSERT INTO useraccount(name,totalpoints) VALUES('"+ name +"',"+ totalPoints +")");
            return SQLQuery.selectUser(con, name);
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
}
