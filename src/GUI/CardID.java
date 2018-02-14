/**
 *
 * @author Danish Bangash
 */
 
package GUI;

import Controller.DatabaseController;
import Controller.Zone;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import Controller.ZoneList;
import Model.ResultSetTableModel;
import java.util.Random;
import java.util.Calendar;

public class CardID extends javax.swing.JFrame {
private DatabaseController controller;
  private ResultSetTableModel resultSetTable;
  private ListOfTravels lot;
  public int id;
  Zone startZone;
  public  String sZone;
  public  String sTime;
  Zone endZone;
  public  String eZone;
  public  String eTime;
  int index;
  int in;
  public int price;
  public ZoneList zone;
  Date date;
   Calendar cal;
   DateFormat dateFormat;
    /**
     * Creates new form CardID
     */
    public CardID() {
        initComponents();
        resultSetTable = new ResultSetTableModel();
        controller = new DatabaseController(resultSetTable, lot);
        lot=new ListOfTravels(resultSetTable, controller);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   //get current date time with Date()
	   date = new Date();
            cal = Calendar.getInstance();
           
           zone=new ZoneList();
           

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkInButton = new javax.swing.JButton();
        checkOutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        checkInButton.setText("Check In");
        checkInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInButtonActionPerformed(evt);
            }
        });

        checkOutButton.setText("Check Out");
        checkOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("User ID");

        idTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkInButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkOutButton)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkInButton)
                    .addComponent(checkOutButton))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutButtonActionPerformed
        // TODO add your handling code here:
           price=0;
         this.id=Integer.parseInt(idTextField.getText());
          controller.getId(id);
         Random r=new Random();
         this.index=r.nextInt(9);
        this.eZone=zone.zoneList[index];
        this.endZone=(Zone) zone.list.get(index);
        this.eTime=dateFormat.format(cal.getTime()).toString();
       /* if(in>index){
        price-=index;
        }
        else {
            price=Math.abs(index-price);
        }*/
        try {
            controller.checkOut(id, eZone, eTime, lot);
        } catch (SQLException ex) {
            Logger.getLogger(CardID.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //this.price=zone.price(zone,zone.getZone(in),zone.getZone(index));
        
           // this.setVisible(false);
            lot.setVisible(true);
                
    }//GEN-LAST:event_checkOutButtonActionPerformed

    private void idTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTextFieldActionPerformed

    private void checkInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInButtonActionPerformed
        // TODO add your handling code here:
     this.id=Integer.parseInt(idTextField.getText());
     
         Random r=new Random();
         this.in=r.nextInt(9);
        this.sZone=zone.zoneList[in];
        this.startZone=(Zone) zone.list.get(in);
        price+=in;
        this.sTime=dateFormat.format(cal.getTime()).toString();
        try {
            controller.checkIn(id, sZone, sTime,lot);
        } catch (SQLException ex) {
            Logger.getLogger(CardID.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        //this.setVisible(false);
        lot.setVisible(true);
    }//GEN-LAST:event_checkInButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CardID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CardID cid = new CardID();
                cid.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkInButton;
    private javax.swing.JButton checkOutButton;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}