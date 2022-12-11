import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQuery {

    /**
     * adds new user to database
     * @param name
     * @return UserAccount that got added
     */
    public static void insertUser(String name){
        int totalPoints = 0; //new users don't have points yet

        DBConnection.connect();
        DBConnection.insert("INSERT INTO useraccount(name,totalpoints) VALUES('"+ name +"',"+ totalPoints +")");
        DBConnection.disconnect();
    }

    /**
     * selects user with specific name
     * @param name
     * @return UserAccount
     */
    public static UserAccount selectUser(String name) {
        DBConnection.connect();
        ResultSet rs = DBConnection.select("SELECT * FROM useraccount WHERE name='"+ name +"'");

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
        DBConnection.disconnect();
        return null;
    }
}
