import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        /*
        SQLQuery.insertUser("Pia");

        UserAccount user = SQLQuery.selectUser("Pia");
        System.out.println(user.toString());*/
        Connection con = DBConnection.getConnection();

        UserAccount user = SQLQuery.insertUser(con, "Mayaaa");
        System.out.println(user.toString());

        DBConnection.disconnect(con);
    }
}
