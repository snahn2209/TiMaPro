import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SQLQueryTest {

    @Test
    void insertUserThatDoesntExist() {
        //TODO: should insert user
        assertAll(
            () -> assertEquals("Lisa",SQLQuery.insertUser("Lisa").getName())
        );

    }

    @Test
    void insertUserThatDoesAlreadyExist(){
        //TODO: should return a SQLIntegrityConstraintViolationException
    }

    @Test
    void selectUser() {
        assertAll(
                //user that exists
                () -> assertEquals("Pia", SQLQuery.selectUser("Pia").getName()),
                //user that doesn't exist
                () -> assertNull(SQLQuery.selectUser("123"))
        );
    }
}