import java.sql.*;

public class TaskDataProvider {
    //TODO: insert Task

    //TODO: select Task
    public static Task selectTask(Connection con, int id){
        if (con!=null){
            ResultSet rs = DBConnection.select(con, "SELECT * FROM tasks WHERE taskID = '"+ id + " '");

            try{
                if(rs!=null) {
                    while (rs.next()) {
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
