import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDataProvider {

    /**
     * inserts new task to database
     * 
     * @param con            Connection do DB
     * @param name           name of new task as String (must be UNIQUE)
     * @param deadline       deadline of task
     * @param timeEstimation estimated time needed for new task
     * @param priority       priority of the new task
     * @param userId         userID of responsible Person (in group-projects)
     * @param projectId      projectId of project to which the task belongs to
     * @return inserted Task
     * @throws SQLIntegrityConstraintViolationException when task has a name that
     *                                                  already exists in DB
     */
    public static Task insertTask(Connection con, String name, Date deadline, double timeEstimation, int priority,
            Integer userId, int projectId) throws SQLIntegrityConstraintViolationException {
        boolean done = false;
        int maxPoints = Task.calculateMaxPoints(timeEstimation);

        if (con != null) {
            ResultSet key = DBConnection.insert(con,
                    "INSERT INTO tasks (name, deadline, timeestimation, prio, done, maxpoints, responsibleuserid, projectid) VALUES ('"
                            + name + "', '" + deadline + "', " + timeEstimation + ", " + priority + ", " + done + ", "
                            + maxPoints + "," + userId + "," + projectId + ")");
            try {
                if (key.next()) {
                    int taskID = key.getInt(1);
                    return TaskDataProvider.selectTask(con, taskID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * selects a task specific ID
     * 
     * @param con    Connection to database
     * @param taskID unique id of task
     * @return selected task
     */
    public static Task selectTask(Connection con, int taskID) {
        if (con != null) {
            ResultSet rs = DBConnection.select(con, "SELECT * FROM tasks WHERE taskID = '" + taskID + "'");

            try {
                if (rs != null) {
                    if (rs.next()) {
                        Task.TaskBuilder builder= new Task.TaskBuilder();
                        Task newTask = builder.setId(rs.getInt("taskID")).
                                setName(rs.getString("name")).
                                setDeadline(rs.getDate("deadline")).
                                setTimeEstimation(rs.getInt("timeestimation")).
                                setPriority(rs.getInt("prio")).
                                setDone(rs.getBoolean("done")).
                                setMaxPoints(rs.getInt("maxPoints")).
                                setResponsiblePersonId(rs.getInt("responsibleuserid")).
                                setProject(rs.getInt("projectid")).
                                createTask();
                        return newTask;
                        // TODO get responsible person by id
                        // TODO get project by id

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * deletes a task with specific ID
     * 
     * @param con    Connection to DB
     * @param taskID unique ID of task
     * @return booelan -> weather or not deletion was successfully
     */
    public static boolean deleteTask(Connection con, int taskID) {
        if (con != null) {
            return DBConnection.delete(con, "DELETE FROM tasks WHERE taskID = '" + taskID + "'");
        }
        return false;
    }


    /**
     * selects all tasks which are part of specific project
     * @param con Connection to DB
     * @param projectID unique ID of project
     * @return List of Tasks
     */
    public static List<Task> selectAllTasksOfProject(Connection con, int projectID) {
        if(con!=null){
            List<Task> listOfTasks = new ArrayList<>();
            ResultSet rs = DBConnection.select(con, "SELECT * FROM TMproject.tasks WHERE projectid = "+ projectID +";");

            try{
                if(rs != null){
                    while (rs.next()){
                        Task.TaskBuilder builder = new Task.TaskBuilder();
                        Task newTask = builder.
                                setId(rs.getInt("taskID")).
                                setName(rs.getString("name")).
                                setDeadline(rs.getDate("deadline")).
                                setTimeEstimation(rs.getDouble("timeestimation")).
                                setPriority(rs.getInt("prio")).
                                setDone(rs.getBoolean("done")).
                                setMaxPoints(rs.getInt("maxpoints")).
                                setResponsiblePersonId(rs.getInt("responsibleuserid")).
                                setProject(rs.getInt("projectid")).
                                createTask();

                        listOfTasks.add(newTask);
                    }
                    return listOfTasks;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * changes done value of a task
     * @param con Connection to DB
     * @param taskID unique id of task
     * @return updated Task
     */
    public static Task checkOffTask(Connection con, int taskID) {
        if(con!=null){
            Boolean success = DBConnection.update(con, "UPDATE TMproject.tasks SET done = NOT done WHERE taskID="+taskID+";");
            if (success){
                return TaskDataProvider.selectTask(con, taskID);
            }
        }
        return null;
    }
}
