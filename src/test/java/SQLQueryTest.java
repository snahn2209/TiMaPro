import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryTest {

    @Test
    void insertUserThatDoesntExist() {
        //TODO: should insert user
        assertAll(
            () -> assertEquals("Otto",SQLQuery.insertUser(DBConnection.getConnection(), "Otto").getName())
        );
    }

    @Test
    void insertUserThatDoesAlreadyExist(){
        //TODO: should return a SQLIntegrityConstraintViolationException
    }

    @Test
    void selectUserThatExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                //user that exists
                () -> assertEquals("Pia", SQLQuery.selectUser(con, "Pia").getName())
        );
        DBConnection.disconnect(con);
    }

    @Test
    void selectUserThatDoesntExists() {
        Connection con = DBConnection.getConnection();
        assertAll(
                //user that doesn't exist
                () -> assertNull(SQLQuery.selectUser(con, "123"))
        );
        DBConnection.disconnect(con);
    }
}