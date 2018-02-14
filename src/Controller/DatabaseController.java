/**
 *
 * @author Danish Bangash
 */

package Controller;

import Model.ConnectionFactory;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.ResultSetTableModel;
import java.util.HashMap;
import Command.Command;
import Command.UpdateCommand;
import Command.SelectCommand;
import GUI.ListOfTravels;

public class DatabaseController {

    private Map commands;
    private ListOfTravels lot;
    private ResultSetTableModel resultSetTableModel;
    static int id;
    private String req = "select *from \"Trips\" where \"CardID\"=?";

    public DatabaseController(ResultSetTableModel resultSetTableModel, ListOfTravels lot) {
        this.resultSetTableModel = resultSetTableModel;
        this.lot = lot;
        commands = new HashMap<String, Command>();

        commands.put("update", new UpdateCommand());
        commands.put("insert", new UpdateCommand());
        //commands.put("select",new SelectCommand());

    }

    public void selectProcess(String request, Connection con) throws SQLException {

        //sc.execute(request, this);
        //SelectCommand sc;
        SelectCommand sc = new SelectCommand(con);
        sc.execute(request, this);
    }

    public void processRequest(String request) throws SQLException {
        Command cmd = resolveCommand(request);

        cmd.execute(request, this);
    }

    private Command resolveCommand(String request) {
        String[] requestTokens = request.split(" ");
        Command cmd = (Command) commands.get(requestTokens[0].toLowerCase()); //obtain first word
        if (cmd == null) {
            cmd = (Command) commands.get("unknownCommand");
        }
        return cmd;
    }

    public void setRowSet(CachedRowSetImpl thatRowSet) {
        resultSetTableModel.setRowSet(thatRowSet);
    }

    public int addUser(String firstName, String LastName, int cardID) throws SQLException {

        String request = " insert into \"Users \" (\"firstName\",\"lastName\",\"CardID\") values (?,?,?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            con = ConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(request);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setInt(3, cardID);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cardID;

    }

    public int addCard(int cardID, int balance) throws SQLException {

        String request = " insert into \"TravelCard \" (\"CardID\",\"Balance\") values (?,?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            con = ConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(request);
            preparedStatement.setInt(1, cardID);
            preparedStatement.setInt(2, balance);

            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cardID;

    }

    public int deposit(int cardID, int balance) throws SQLException {

        String request = "update \"TravelCard\" set \"Balance\"=? where \"CardID\"=?";
        String selectReq = "select \"Balance\" from \"TravelCard\" where \"CardID\"=?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps2 = null;

        int result = 0;
        ResultSet results = null;
        try {
            con = ConnectionFactory.createConnection();
            ps2 = con.prepareStatement(selectReq);
            ps2.setInt(1, cardID);



            results = ps2.executeQuery();

            //System.out.println(results.getInt(1));
            while (results.next()) {
                int balance2 = results.getInt(1);
                // System.out.print(balance2);

                balance += balance2;
            }
            preparedStatement = con.prepareStatement(request);
            preparedStatement.setInt(1, balance);
            preparedStatement.setInt(2, cardID);

            result = preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cardID;

    }

    public void checkIn(int id, String StartZone, String StartTime, ListOfTravels lot) throws SQLException {

        String request = "insert into \"Trips \" (\"CardID\",\"StartZone\",\"StartTime\") values (?,?,?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        ResultSet results;
        try {
            this.lot = lot;
            con = ConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(request);


            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, StartZone);
            preparedStatement.setString(3, StartTime);

            result = preparedStatement.executeUpdate();
            //selectProcess(req, con);
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, id);
            results = preparedStatement.executeQuery();

            //boolean f = results.next();
            //System.out.print(f);


            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(results);
            //setRowSet(cachedRowSet);
            //if(cachedRowSet != null)
            //  System.out.print("NULL");

            resultSetTableModel.setRowSet(cachedRowSet);
            this.lot.ListOfTravel.setModel(resultSetTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void checkOut(int id, String EndZone, String EndTime, ListOfTravels lot) throws SQLException {

        String request = "update \"Trips\" set \"EndZone\"=?,\"EndTime\"=?,\"Price\"=? where \"CardID\"=?";
        String selectReq = "select \"StartZone\" from \"Trips\" where \"CardID\"=?";
        String selectRq = "select \"EndZone\" from \"Trips\" where \"CardID\"=?";
        String selectBal = "select \"Balance\" from \"TravelCard\" where \"CardID\"=?";
        String setBal = "update \"TravelCard\" set \"Balance\"=? where \"CardID\"=?";
        int balance = 0;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        int result = 0;
        ResultSet results = null;
        try {
            this.lot = lot;

            con = ConnectionFactory.createConnection();
            ps = con.prepareStatement(selectReq);
            ps.setInt(1, id);
            results = ps.executeQuery();


            int val = 0;
            while (results.next()) {

                String sZone;
                sZone = results.getString(1);
                char s = sZone.charAt(4);
                val = Character.getNumericValue(s);

            }

            int valu = 0;

            char e = EndZone.charAt(4);
            valu = Character.getNumericValue(e);



            int price = Math.abs(val - valu);

            con = ConnectionFactory.createConnection();
            ps2 = con.prepareStatement(selectBal);
            ps2.setInt(1, id);
            results = ps2.executeQuery();
            while (results.next()) {
                balance = results.getInt(1);
                // System.out.print(balance2);
                balance = balance - price;

            }
//setting balance on travelCard table
            ps3 = con.prepareStatement(setBal);
            ps3.setInt(1, balance);
            ps3.setInt(2, id);

            result = ps3.executeUpdate();
// setting trips table
            
            con = ConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(request);
            
//            result = preparedStatement.executeUpdate();
            
            preparedStatement.setString(1, EndZone);
            preparedStatement.setString(2, EndTime);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, id);
            
            result = preparedStatement.executeUpdate();
            //selectProcess(req, con);
            


            //boolean f = results.next();
            //System.out.print(f);

            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, id);
            results = preparedStatement.executeQuery();

            //boolean f = results.next();
            //System.out.print(f);


            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(results);
            //setRowSet(cachedRowSet);
            //if(cachedRowSet != null)
            //  System.out.print("NULL");

            resultSetTableModel.setRowSet(cachedRowSet);
            this.lot.ListOfTravel.setModel(resultSetTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        lot.balanceTextField.setText(Integer.toString(balance));
    }

    public void getId(int cardid) {

        id = cardid;

    }
}
