import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
        /*
        UserDataProvider.insertUser("Pia");

        UserAccount user = UserDataProvider.selectUser("Pia");
        System.out.println(user.toString());*/

        /*Connection con = DBConnection.getConnection();

        UserAccount user = UserDataProvider.insertUser(con, "Mayaaa");
        System.out.println(user.toString());

        DBConnection.disconnect(con);*/

        Connection con = DBConnection.getConnection();

        Task task= TaskDataProvider.selectTask(con, 1);
        System.out.println(task.toString());

        DBConnection.disconnect(con);
    }
}
