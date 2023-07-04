/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Resident
 */
public class Resident extends User{
    private String relationshipToOwner;
    private String stayingUnit;
    private int ownerDBID;
    
    //Constructor
    public Resident(String relationshipToOwner, String name, String userID, String userIC, 
                    String contactNo, String gender, String email, String stayingUnit,
                    int ownerDBID) {
        super(name, userID, userIC, contactNo, gender, email);
        this.relationshipToOwner = relationshipToOwner;
        this.stayingUnit = stayingUnit;
        this.ownerDBID = ownerDBID;
    }
    
    public Resident() {
        super();
        this.relationshipToOwner = "";
        this.stayingUnit = "";
        this.ownerDBID = 0;
    }
    
    //Getters
    public String getRelationshipToOwner() {
        return relationshipToOwner;
    }
    
    public String getStayingUnit(){
        return stayingUnit;
    }
    
    public int getOwnerDBID() {
        return ownerDBID;
    }
    
    //Setters
    public void setRelationshipToOwner (String relationshipToOwner){
        this.relationshipToOwner = relationshipToOwner;
    }
    
    public void setStayingUnit(String stayingUnit){
        this.stayingUnit = stayingUnit;
    }
    
    public void setOwnerDBID(int ownerDBID){
        this.ownerDBID = ownerDBID;
    }
    
    //Other function(s)
    //Setting details from database using account ID.
    public void setDetailsFromDB(int accountID) {
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select * from resident where accountRDBID = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, accountID);
            rs = pst.executeQuery();
            
            if (rs.next()){
                this.name = rs.getString("residentName");
                this.userID = rs.getString("residentDBID");
                this.contactNo = rs.getString("phoneNumber");
                this.email = rs.getString("email");
                this.gender = rs.getString("gender");
                this.relationshipToOwner = rs.getString("relationshipToOwner");
            }
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    //Displaying unpaid bills table from database
    public void showUnpaidBillsTable(JTable unpaidBillsTable) {
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)unpaidBillsTable.getModel();
        Object [] row = new Object [6];
        
        try {
            con = db.connectDB();
            String sql = "select billID, issueDate, expiredDate, billAmount, billType, billMonth " +
                            "from bills where status = \"UNPAID\" ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("billID");
                row [1] = rs.getString("issueDate");
                row [2] = rs.getString("expiredDate");
                row [3] = rs.getString("billAmount");
                row [4] = rs.getString("billType");
                row [5] = rs.getString("billMonth");
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Displaying paid bills from database
    public void showPaidBillsTable(JTable paidBillsTable) {
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)paidBillsTable.getModel();
        Object [] row = new Object [6];
        
        try {
            con = db.connectDB();
            String sql = "select billID, issueDate, expiredDate, billAmount, billType, billMonth, payDate " +
                            "from bills where status = \"PAID\" or status = \"PAID_LATE\" ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("billID");
                row [1] = rs.getString("payDate");
                row [2] = rs.getString("expiredDate");
                row [3] = rs.getString("billAmount");
                row [4] = rs.getString("billType");
                row [5] = rs.getString("billMonth");
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //To check whether the account ID is existed or not
    public boolean accountIDIsExist(int accountID){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select * from resident where accountRDBID = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, accountID);
            rs = pst.executeQuery();
            
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public void showNotification(JTextArea textArea) {
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String tempDate = "";
        
        try {
            con = db.connectDB();
            String sql = "select * from notification";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                if (!rs.getString("sentDate").equals(tempDate)){
                    textArea.append("Sent Date: " + rs.getString("sentDate") + "\n");
                    textArea.append("---------------------------------------------------------------- \n");
                }
                textArea.append("Sent Time: " + rs.getString("sentTime") + "\n");
                textArea.append(rs.getString("notificationContent"));
                textArea.append("\n");
                textArea.append("---------------------------------------------------------------- \n");
                tempDate = rs.getString("sentDate");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
}
