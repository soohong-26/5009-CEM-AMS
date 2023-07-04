/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author User
 */
public class Account {
    protected int accountID;
    protected String username;
    protected String password;
    protected String role;
    protected String status;
    protected AccountEntries accountEntries;
    
    //Constctor
    public Account() {
        this.accountID = 0;
        this.username = "";
        this.password = "";
        this.role = "";
        this.status = "";
        accountEntries = new AccountEntries();
    }

    public Account(int accountID, String username, String password, String role, String status, AccountEntries accountEntries) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.accountEntries = accountEntries;
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
    
    public AccountEntries getAccountEntries() {
        return this.accountEntries;
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
    
    public void setAccountEntries (AccountEntries accountEntries){
        this.accountEntries = accountEntries;
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
    
    //To check whether this is valid
    public boolean isActive(){
        return this.status.equals("ACTIVE");
    }
    
    //To set account details based on username, since he can log in, and username is unique key
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
    
    //Getting account ID from database
    public String getAccountIDFromDB(){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String accountIDDB = "";
        
        try {
            con = db.connectDB();
            String sql = "select accountID from account where username = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.username);
            rs = pst.executeQuery();
            
            if (rs.next()){
                accountIDDB = rs.getString("accountID");
                return accountIDDB;
            } else {
                return accountIDDB;
            }
            
        } catch (Exception e){
            e.printStackTrace();
            return accountIDDB;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
}
