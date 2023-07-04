/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Guard extends User{
    private SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeF = new SimpleDateFormat("kk:mm:ss");
    
    //Constructor
    public Guard(String name, String userID, String userIC, String contactNo, String gender, String email) {
        super(name, userID, userIC, contactNo, gender, email);
    }

    public Guard() {
        super();
    }
    
    //Inserting visitor time in
    public boolean insertVisitorTimeIn(Visitor visitor){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        Tool tool = new Tool();
        
        try {
            con = db.connectDB();
            String sql = "INSERT INTO ams.visitationentries (name, "
                       + "contactNo, visitUnit, overnightDays, carPlate, "
                       + "parkingLot, timeIn, timeOut, dateIn, dateOut) "
                       + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, visitor.getName());
            pst.setString(2, visitor.getContactNo());
            pst.setString(3, visitor.getVisitUnit());
            pst.setInt(4, visitor.getOvernightDays());
            pst.setString(5, visitor.getCarPlate());
            pst.setString(6, visitor.getParkingLot());
            pst.setString(7, visitor.getTimeIn());
            pst.setString(8, visitor.getTimeOut());
            pst.setString(9, visitor.getDateIn());
            pst.setString(10, visitor.getDateOut());
            int update = pst.executeUpdate();
            if (update > 0){
                tool.getInformationMessageOptionPane("Time in successfully!");
                return true;
            } else {
                tool.getInformationMessageOptionPane("Time out failed!");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            tool.getInformationMessageOptionPane("Something went wrong!");
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Updating whenever visitor time out
    public boolean insertVisitorTimeOut(Visitor visitor) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        Tool tool = new Tool();
        Date time = new Date();
        Date date = new Date();
        
        try {
            con = db.connectDB();
            String sql = "UPDATE visitationentries SET timeOut = ?, dateOut = ? "
                       + "WHERE name = ? and contactNo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, timeF.format(time));
            pst.setString(2, dateF.format(date));
            pst.setString(3, visitor.getName());
            pst.setString(4, visitor.getContactNo());
            int update = pst.executeUpdate();
            if (update > 0){
                tool.getInformationMessageOptionPane("Time out successfully!");
                return true;
            } else {
                tool.getInformationMessageOptionPane("Time out failed!");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            tool.getInformationMessageOptionPane("Something went wrong!");
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Get visitor's name from database
    public String getNameFromDB(String accountID) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String name = "";
        
        try {
            con = db.connectDB();
            String sql = "select name from guard where accountIDG = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, accountID);
            rs = pst.executeQuery();
            
            if (rs.next()){
                name = rs.getString("name");
                return name;
            } else {
                return name;
            }
            
        } catch (Exception e){
            e.printStackTrace();
            return name;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Showing history table from database
    public void showHistoryTable(JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [8];
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, visitUnit, overnightDays, timeIn, timeOut"
                       + ", dateIn, dateOut from visitationentries where timeOut != \"24:00:00\" ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("visitUnit");
                row [3] = rs.getString("overnightDays");
                row [4] = rs.getString("timeIn");
                row [5] = rs.getString("timeOut");
                row [6] = rs.getString("dateIn");
                row [7] = rs.getString("dateOut");
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
    
    //Showing home history table at the home page of guard
    public void showHomeHistoryTable(JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [8];
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, visitUnit, overnightDays, timeIn, timeOut"
                       + ", dateIn, dateOut from visitationentries where timeOut != \"24:00:00\" ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("visitUnit");
                row [3] = rs.getString("overnightDays");
                row [4] = rs.getString("timeIn");
                row [5] = rs.getString("timeOut");
                row [6] = rs.getString("dateIn");
                row [7] = rs.getString("dateOut");
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
    
    //showing visitors' car details 
    public void showVisitorsCarsDetailsTable(JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [4];
        
        try {
            con = db.connectDB();
            String sql = "select name, carPlate, parkingLot, contactNo from "
                        + "visitationentries where timeOut = \"24:00:00\" and dateOut = \"0001-01-01\" and carPlate != \"-\"";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("carPlate");
                row [3] = rs.getString("parkingLot");
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
    
    //Showing who should time out table
    public void showTimeOutTable(JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, timeIn, dateIn, overnightDays, visitUnit, " +
                            "case when " +
                            " carPlate = \"-\" then \"N\" "+
                            " when carPlate is not null then \"Y\" " +
                            " end as hasCar " +
                            "from visitationentries where dateOut = \"0001-01-01\"";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("visitUnit");
                row [3] = rs.getString("timeIn");
                row [4] = rs.getString("dateIn");
                row [5] = rs.getString("overnightDays");
                row [6] = rs.getString("hasCar");
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
}
