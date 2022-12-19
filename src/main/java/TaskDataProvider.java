import java.sql.*;

public class TaskDataProvider {

    /**
     * inserts new task to database
     * @param con Connection do DB
     * @param name name of new task as String (must be UNIQUE)
     * @param deadline deadline of task
     * @param timeEstimation estimated time needed for new task
     * @param priority priority of the new task
     * @param userId userID of responsible Person (in group-projects)
     * @param projectId projectId of project to which the task belongs to
     * @return inserted Task
     * @throws SQLIntegrityConstraintViolationException when task has a name that already exists in DB
     */
    public static Task insertTask(Connection con, String name, Date deadline, double timeEstimation, int priority, int userId, int projectId) throws SQLIntegrityConstraintViolationException {
        boolean done = false;
        int maxPoints = Task.calculateMaxPoints(timeEstimation);

        if(con!=null){
            ResultSet keys = DBConnection.insert(con, "INSERT INTO tasks (name, deadline, timeestimation, prio, done, maxpoints, responsibleuserid, projectid) VALUES ('" + name + "', '" + deadline + "', " + timeEstimation + ", " + priority + ", " + done + ", " + maxPoints+ ","+userId+","+projectId+ ")");
            try {
                if(keys.next()){
                    int taskID = keys.getInt(1);
                    return TaskDataProvider.selectTask(con, taskID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * selects task with name
     * @param con Connection to database
     * @param taskID unique id of task
     * @return selected task
     */
    public static Task selectTask(Connection con, int taskID){
        if (con!=null){
            ResultSet rs = DBConnection.select(con, "SELECT * FROM tasks WHERE taskID = '"+ taskID + "'");

            try{
                if(rs!=null) {
                    if(rs.next()) {
                        return new Task(
                                rs.getInt("taskID"),
                                rs.getString("name"),
                                rs.getDate("deadline"),
                                rs.getDouble("timeestimation"),
                                rs.getInt("prio"),
                                rs.getBoolean("done"),
                                rs.getDate("gotdonedate"),
                                rs.getInt("maxpoints")
                                //TODO rs.getInt("responsibleuserid")
                                //TODO get project
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
