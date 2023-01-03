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
     * @param con Connection to DB
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
     * @param con Connection to DB
     * @param qry Query String
     * @return keys to inserted rows
     */
    public static ResultSet insert(Connection con, String qry) throws SQLIntegrityConstraintViolationException {
        try {
            PreparedStatement stmt = con.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS); //returns autoincrement indices
            stmt.execute();
            ResultSet keys = stmt.getGeneratedKeys();
            System.out.println("[INSERT executed]");

            return keys;

        } catch(SQLIntegrityConstraintViolationException e){
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    /**
     * @param con Connection to DB
     * @param qry query String
     * @return boolean: weather or not the execution was successfully
     */
    public static boolean delete(Connection con, String qry){
        try {
            PreparedStatement stmt = con.prepareStatement(qry); //returns autoincrement indices
            stmt.execute();
            System.out.println("[DELETE executed]");
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO: update()
}
