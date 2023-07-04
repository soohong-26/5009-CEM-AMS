/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Resident
 */
public class Profile {
    private String appearingName;
    private String pin;
    
    //Constructors
    public Profile (String appearingName, String pin){
        this.appearingName = appearingName;
        this.pin = pin;
    }
    
    public Profile(){
        this.appearingName = "";
        this.pin = "";
    }
    
    //Getters
    public String getAppearingName() {
        return appearingName;
    }

    public String getPin() {
        return pin;
    }
    
    //Setters
    public void setAppearingName(String appearingName) {
        this.appearingName = appearingName;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    //Other function(s)
    public int getProfileIDFromDB (){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select profileID from profile where appearingName = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.appearingName);
            rs = pst.executeQuery();
            
            if (rs.next()){
                return rs.getInt("profileID");
            }
            
        } catch (Exception e){
            e.printStackTrace();
            
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return 0;
    }
    
    //Getting details from database
    public ArrayList <String> getDetailsFromDB() {
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList <String> details = new ArrayList();
        
        try {
            con = db.connectDB();           
            String sql = "select resident.residentName, resident.phoneNumber, resident.email, resident.gender " +
                         "from profile " +
                         "INNER JOIN resident on resident.residentDBID = profile.residentDBID " +
                         "where profile.profileID = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, getProfileIDFromDB());
            rs = pst.executeQuery();
            
            if (rs.next()){
                details.add(rs.getString("residentName"));
                details.add(rs.getString("phoneNumber"));
                details.add(rs.getString("email"));
                details.add(rs.getString("gender"));
            }
            
        } catch (Exception e){
            e.printStackTrace();
            
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return details;
    }
    
    //Resident can update their own profile details, this is the function they use to update their details
    public boolean updateProfileDetails(Profile newProfileDetails, String oldAppearingName){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        Profile tempProfile = new Profile();
        tempProfile.setAppearingName(oldAppearingName);
        
        try {
            con = db.connectDB();
            String sql = "update profile set appearingName = ? , profilePin = ? " +
                         "where profileID = ?";
            
            pst = con.prepareStatement(sql);
            pst.setString(1, newProfileDetails.getAppearingName());
            pst.setString(2, newProfileDetails.getPin());
            pst.setInt(3, tempProfile.getProfileIDFromDB());
            
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
    
    //Update resident details, as they can update their profile, 
    //and some of the details is from another table, so this is it
    public boolean updateResidentDetails(Resident residentNewDetails){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = db.connectDB();
            String sql = "update resident join profile on " +
                         "profile.residentDBID = resident.residentDBID " +
                         "set resident.phoneNumber = ?, resident.email = ?, resident.gender = ? " +
                         "where profile.profileID = ? ";
            
            pst = con.prepareStatement(sql);
            pst.setString(1, residentNewDetails.getContactNo());
            pst.setString(2, residentNewDetails.getEmail());
            pst.setString(3, residentNewDetails.getGender());
            pst.setInt(4, getProfileIDFromDB());
            
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
    
    //Validating pin matches the appearing name or not
    //Appearing name is unique key
    public boolean validatePin(String oldProfilePin) {
        Dbclass db = new Dbclass();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
        try {
            con = db.connectDB();
            String sql = "select * from profile where appearingName = ? and profilePin = ?";
            
            pst = con.prepareStatement(sql);
            pst.setString(1, this.appearingName);
            pst.setString(2, oldProfilePin);
            rs = pst.executeQuery();
            
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
}
