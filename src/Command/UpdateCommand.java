
package Command;

import com.sun.rowset.CachedRowSetImpl;
import Controller.DatabaseController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.ConnectionFactory;

/**
 *
 * @author Danish Bangash
 */
public class UpdateCommand implements Command {

    @Override
    public void execute(String request, DatabaseController databaseController) throws SQLException {
        Connection con = null;
        con = ConnectionFactory.createConnection();
        PreparedStatement stmt = con.prepareStatement(request, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int rowCount = stmt.executeUpdate();
        CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
        if (con != null) {
            con.close();
        }
        databaseController.setRowSet(cachedRowSet);
    }
}
