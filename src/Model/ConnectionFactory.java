/**
 *
 * @author Danish Bangash
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author BEGUM
 */
public class ConnectionFactory {

    private static final String URL = "jdbc:derby://localhost:1527/database";
    private static final String USERNAME = "Danier007";
    private static final String PASSWORD = "bangash88";
    
   

   public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
   
    
    
}
