/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class AccountEntries {
    private int entriesID;
    private String timeIn;
    private String timeOut;
    private String dateIn;
    private String dateOut;
    private SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeF = new SimpleDateFormat("kk:mm:ss");
    private Date now = new Date();
    
    //Constructor
    public AccountEntries(int entriesID, String timeIn, String timeOut, String dateIn, String dateOut) {
        this.entriesID = entriesID;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }
    
    public AccountEntries(){
        this.entriesID = 0;
        this.timeIn = "";
        this.timeOut = "";
        this.dateIn = "";
        this.dateOut = "";
    }
    
    //Getters
    public int getEntriesID() {
        return entriesID;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }
    
    //Setters 
    public void setEntriesID (int entriesID){
        this.entriesID = entriesID;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }
    
    //Other function(s)
    //Record when the user log into the system
    public boolean timeIn(int accountID, String role) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = db.connectDB();
            String sql = "INSERT INTO accountentries (accountDBIDE, " +
                         "role, timeIn, timeOut, dateIn, " +
                         "dateOut) " +
                         "VALUES (?,?,?,?,?,?)";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, accountID);
            pst.setString(2, role);
            pst.setString(3, timeF.format(this.now));
            pst.setString(4, "24:00:00");
            pst.setString(5, dateF.format(this.now));
            pst.setString(6, "0000-01-01");
            
            int update = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            
            if (rs.next()){
                this.entriesID = rs.getInt(1);
            }
            return update > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Record when the user logout or exit the system.
    public boolean timeOut(){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        Date time = new Date();
        Date date = new Date();
        
        try {
            con = db.connectDB();
            String sql = "UPDATE accountentries SET timeOut = ?, dateOut = ? "
                       + "WHERE entriesID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, timeF.format(time));
            pst.setString(2, dateF.format(date));
            pst.setInt(3, this.entriesID);
            
            int update = pst.executeUpdate();
            return update > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
}
