import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
        /*
        UserDataProvider.insertUser("Pia");

        UserAccount user = UserDataProvider.selectUser("Pia");
        System.out.println(user.toString());*/
        Connection con = DBConnection.getConnection();

        List<UserAccount> users = UserDataProvider.selectAllUsers(con);
        System.out.println(users.toString());

        DBConnection.disconnect(con);
    }
}
