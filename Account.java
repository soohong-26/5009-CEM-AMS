/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Account {
    private int accountID;
    private String username;
    private String password;
    private String role;
    private String status;
    private ArrayList <Profile> profiles;
    
    //Constctor
    public Account(){
        this.accountID = 0;
        this.username = "";
        this.password = "";
        this.role = "";
        this.status = "";
        this.profiles = new ArrayList();
    }

    public Account(int accountID, String username, String password, String role, String status, ArrayList<Profile> profiles) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.profiles = profiles;
    }
    
    //Getters
    public int getAccountID () {
        return this.accountID;
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public ArrayList <Profile> getProfiles() {
        return this.profiles;
    }
    
    //Setters
    public void setAccountID (int accountID) {
        this.accountID = accountID;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setProfile (ArrayList <Profile> profiles) {
        this.profiles = profiles;
    }
    
    public void pushProfile (Profile profile) {
        this.profiles.add(profile);
    }
    
    //Other function(s)
    public void setRoleDB() {
        Dbclass db = new Dbclass();
        
        try {
            Connection con = db.connectDB();
            String sql = "select role from account where username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, this.username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                this.role = rs.getString("role");
            }
            
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
    
    public void setStatusDB() {
        Dbclass db = new Dbclass();
        
        try {
            Connection con = db.connectDB();
            String sql = "select status from account where username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, this.username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                this.status = rs.getString("status");
            }
            
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
    
    public boolean isActive(){
        return this.status.equals("ACTIVE");
    }
    
    public void setAccountIDDB() {
        Dbclass db = new Dbclass();
        
        try {
            Connection con = db.connectDB();
            String sql = "select accountID from account where username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, this.username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                this.accountID = rs.getInt("accountID");
            }
            
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
}
