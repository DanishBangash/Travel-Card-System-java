
package Command;

import com.sun.rowset.CachedRowSetImpl;
import Controller.DatabaseController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Model.ConnectionFactory;

/**
 *
 * @author Danish Bangash
 */
public class SelectCommand implements Command {
    Connection con;
    public SelectCommand(Connection con)
    {
        this.con = con;
    }
    @Override
    public void execute(String request, DatabaseController databaseController) throws java.sql.SQLException {
        //Connection con = null;
       
        //con = ConnectionFactory.createConnection();
       
        PreparedStatement stmt = con.prepareStatement(request, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        /*
        if(stmt == null)
            System.out.print("STMT NULL!");
        else
            System.out.print("DEGIL");
        */
        ResultSet resultSet = stmt.executeQuery();
       
         if(resultSet == null)
            System.out.print("RSET NULL!");
        else
            System.out.print("DEGIL");
        
        CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
        cachedRowSet.populate(resultSet);
        
        
        if (con != null) {
            con.close();
            
        }
        databaseController.setRowSet(cachedRowSet);
    }
}
