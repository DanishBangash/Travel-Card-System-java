
package Command;

import Controller.DatabaseController;

/**
 *
 * @author Danish Bangash
 */
public interface Command {

    public void execute(String request, DatabaseController databaseController) throws java.sql.SQLException;
}
