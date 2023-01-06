import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskBuilderTest {
    @Test
    void createTaskTest(){
        Task.TaskBuilder builder = new Task.TaskBuilder();
        Task newTask = builder.setId(7).
                setName("Daedalus").
                setDeadline(Date.valueOf("2023-07-07")).
                setTimeEstimation(6).
                setPriority(5).
                setDone(false).
                setMaxPoints(4).
                setResponsiblePersonId(3).
                setProject(2).
                createTask();

        assertAll(
                () -> assertEquals(7,newTask.getId()),
                () -> assertEquals("Daedalus", newTask.getName()),
                () -> assertEquals(Date.valueOf("2023-07-07"),newTask.getDeadline()),
                () -> assertEquals(6,newTask.getTimeEstimation()),
                () -> assertEquals(5,newTask.getPriority()),
                () -> assertEquals(false,newTask.isDone()),
                () -> assertEquals(4,newTask.getMaxPoints()),
                () -> assertEquals(3,newTask.getResponsiblePersonId()),
                () -> assertEquals(2,newTask.getProject())

        );
    }
}
