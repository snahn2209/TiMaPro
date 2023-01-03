import java.sql.*;

public class DBConnection {
    private static final  String host = "localhost";
    private static final  String port = "3306";
    private static final  String database = "TMproject";
    private static final  String username = "root";
    private static final  String password = "TMProject";

    public static boolean isConnected(Connection con){
        return con != null;
    }

    /**
     * establishes connection to MySQL database
     * @return Connection
     */
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,password);
            System.out.println("[DB connected]");
            return con;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * breaks connectin to database
     * @param con
     */
    public static void disconnect(Connection con){
        if(isConnected(con)){
            try {
                con.close();
                System.out.println("[DB disconnected]");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sends INSERT query to database
     * @param con Connection
     * @param qry Query String
     */
    public static void insert(Connection con, String qry) throws SQLIntegrityConstraintViolationException {
        try {
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.execute();
            System.out.println("[INSERT executed]");

        } catch(SQLIntegrityConstraintViolationException e){
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends SELECT query to database and returns result
     * @param qry query String
     * @param con Connection
     * @return Selected Rows as a ResultSet
     */
    public static ResultSet select(Connection con,String qry){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
            System.out.println("[SELECT executed]");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: update()
    //TODO: delete()
}
