import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class UserDataProviderTest {

    //NOTE: test have to be run separately

    @Test
    void insertUserThatDoesntExist() throws SQLIntegrityConstraintViolationException {
        //NOTE: look for a name that isnt already in the db before running this test
        Connection con = DBConnection.getConnection();
        UserAccount insertedUser = UserDataProvider.insertUser(con, "Maria");
        System.out.println(insertedUser.toString());
        assertEquals("Maria", insertedUser.getName());

        //delete test-user
        UserDataProvider.deleteUser(con, insertedUser.getID());

        DBConnection.disconnect(con);
    }

    @Test
    void insertUserThatDoesAlreadyExist(){
        Connection con = DBConnection.getConnection();
        assertThrows(SQLIntegrityConstraintViolationException.class,() -> UserDataProvider.insertUser(con, "Pia"));
        DBConnection.disconnect(con);
    }

    @Test
    void selectUserThatExists() {
        Connection con = DBConnection.getConnection();
        UserAccount selectedUser = UserDataProvider.selectUser(con, "Pia");
        System.out.println(selectedUser.toString());
        assertEquals("Pia", selectedUser.getName());
        DBConnection.disconnect(con);
    }

    @Test
    void selectUserThatDoesntExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                () -> assertNull(UserDataProvider.selectUser(con, "123"))
        );
        DBConnection.disconnect(con);
    }

    @Test
    void deleteUser() {
        //NOTE: look for a user ID that exists
        //TODO: improve (User ID should not be found in tasks after deletion)
        Connection con = DBConnection.getConnection();
        boolean executedSuccessfully = UserDataProvider.deleteUser(con, 3);
        assertTrue(executedSuccessfully);
        DBConnection.disconnect(con);
    }


}