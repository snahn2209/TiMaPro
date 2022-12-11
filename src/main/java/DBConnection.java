import java.sql.*;

public class DBConnection {
    private static final  String host = "localhost";
    private static final  String port = "3306";
    private static final  String database = "TMproject";
    private static final  String username = "root";
    private static final  String password = "TMProject";

    private static Connection con;

    public static boolean isConnected(){
        return con != null;
    }

    /**
     * establishes connection to MySQL database
     */
    public static void connect(){
        if(!isConnected()){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,password);
                System.out.println("[DB connected]");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * releases  connection to Database
     */
    public static void disconnect(){
        if(isConnected()){
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
     * @param qry query String
     */
    public static void insert(String qry){
        try {
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.execute();
            System.out.println("[INSERT executed]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends SELECT query to database and returns result
     * @param qry query String
     * @return Selected Rows as a ResultSet
     */
    public static ResultSet select(String qry){
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
}
