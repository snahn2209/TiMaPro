import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
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
