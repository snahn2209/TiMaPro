import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectBuilderTest {
    @Test
    void createProjectTest(){
        Project.ProjectBuilder builder = new Project.ProjectBuilder();
        Project newProject = builder.setID(5).setName("TestProjekt").setDeadline(Date.valueOf("2023-03-01")).createProject();

        assertAll(
                () -> assertEquals("TestProjekt", newProject.getName()),
                () -> assertEquals(5, newProject.getID()),
                () -> assertEquals(Date.valueOf("2023-03-01"), newProject.getDeadline())

        );

    }
}
