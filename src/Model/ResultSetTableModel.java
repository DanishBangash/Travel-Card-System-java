/**
 *
 * @author Danish Bangash
 */
package Model;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ibr
 */
public class ResultSetTableModel extends AbstractTableModel {

    private RowSet cachedRowSet;
    private ResultSetMetaData resultSetMetaData;

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            String className = resultSetMetaData.getColumnClassName(columnIndex + 1);
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Object.class; 
    }


    public int getRowCount() {
        try {
            if (cachedRowSet != null) {
                cachedRowSet.last();
                return cachedRowSet.getRow();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getColumnCount() {
        try {
            if (resultSetMetaData != null) {
                return resultSetMetaData.getColumnCount();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


    @Override
    public String getColumnName(int column) {
        try {
            if (resultSetMetaData != null) {
                return resultSetMetaData.getColumnName(column + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            if (cachedRowSet != null) {
                cachedRowSet.absolute(rowIndex + 1);
                return cachedRowSet.getObject(columnIndex + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void setRowSet(CachedRowSetImpl thatRowSet) {
        this.cachedRowSet = thatRowSet;
        try {
            if (cachedRowSet != null) {
                this.resultSetMetaData = cachedRowSet.getMetaData();
                this.fireTableStructureChanged();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
