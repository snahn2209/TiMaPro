import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryTest {

    @Test
    void insertUserThatDoesntExist() {
        //NOTE: look for a name that isnt already in the db before running this test
        Connection con = DBConnection.getConnection();
        assertAll(
            () -> assertEquals("Ralf",SQLQuery.insertUser(con, "Ralf").getName())
        );
        DBConnection.disconnect(con);
        //TODO: remove name after test
    }

    @Test
    void insertUserThatDoesAlreadyExist(){
        //TODO: should return a SQLIntegrityConstraintViolationException
        Connection con = DBConnection.getConnection();
        assertThrows(SQLIntegrityConstraintViolationException.class,() -> SQLQuery.insertUser(con, "Otto"));
        DBConnection.disconnect(con);
    }

    @Test
    void selectUserThatExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                () -> assertEquals("Pia", SQLQuery.selectUser(con, "Pia").getName())
        );
        DBConnection.disconnect(con);
    }

    @Test
    void selectUserThatDoesntExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                () -> assertNull(SQLQuery.selectUser(con, "123"))
        );
        DBConnection.disconnect(con);
    }
}