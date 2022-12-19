import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class UserDataProviderTest {

    //NOTE: test have to be run separately

    @Test
    void insertUserThatDoesntExist() {
        //NOTE: look for a name that isnt already in the db before running this test
        Connection con = DBConnection.getConnection();
        assertAll(
            () -> assertEquals("Tom", UserDataProvider.insertUser(con, "Tom").getName())
        );
        DBConnection.disconnect(con);
        //TODO: remove name after test
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
        assertAll(
                () -> assertEquals("Pia", UserDataProvider.selectUser(con, "Pia").getName())
        );
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


}