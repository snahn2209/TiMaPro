import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQuery {
    //TODO: separate this Class into 3 dataProvider-Classes for User,Project,Task

    /**
     * adds new user to database
     * @param name
     * @return UserAccount that got added
     */
    public static UserAccount insertUser(String name) {
        int totalPoints = 0; //new users don't have points yet

        Connection con = DBConnection.getConnection();
        if(con!=null){
            ResultSet rs = DBConnection.insert(con, "INSERT INTO useraccount(name,totalpoints) VALUES('"+ name +"',"+ totalPoints +")");

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
            DBConnection.disconnect(con);
        }

        return null;
    }

    /**
     * selects user with specific name
     * @param name
     * @return UserAccount
     */
    public static UserAccount selectUser(String name) {
        Connection con = DBConnection.getConnection();
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
            DBConnection.disconnect(con);
        }

        return null;
    }
}
