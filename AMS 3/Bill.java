/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author User
 */
public class Bill {
    private String billID;
    private String billType;
    private String billTo;
    private String billAddress;
    private double billAmount;
    private Date billExpiredDate;
    private String billMonth;
    private Date issueDate;
    private String billStatus;
    private String payDate;
    private SimpleDateFormat date = new SimpleDateFormat ("yyyy-MM-dd");
    
    //Constructor
    public Bill(){
        billID = "";
        billType = "";
        billTo = "";
        billAddress = "";
        billAmount = 0.0;
        billExpiredDate = new Date();
        billMonth = "";
        issueDate = new Date();
        billStatus = "";
        payDate = "";
    }
    
    //Getters
    public String getBillID() {
        return this.billID;
    }
    
    public String getBillType() {
        return this.billType;
    }
    
    public String getBillTo(){
        return this.billTo;
    }
    
    public String getBillAddresss(){
        return this.billAddress;
    }
    
    public double getBillAmount() {
        return this.billAmount;
    }
    
    public String getBillExpiredDate() {
        return date.format(this.billExpiredDate);
    }
    
    public String getBillMonth() {
        return this.billMonth;
    }
    
    public String getIssueDate() {
        return date.format(this.issueDate);
    }
    
    public String getBillStatus() {
        return this.billStatus;
    }
    
    public String getPayDate() {
        return this.payDate;
    }
    
    //Setters
    public void setBillID(String billID) {
        this.billID = billID;
    }
    
    public void setBillType(String billType){
        this.billType = billType;
    }
    
    public void setBillTo(String billTo){
        this.billTo = billTo;
    }
    
    public void setBillAddress (String billAddress){
        this.billAddress = billAddress;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public void setBillExpiredDate(Date billExpiredDate) {
        this.billExpiredDate = billExpiredDate;
    }
    
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
    public void setBillStatus(String billStatus){
        this.billStatus = billStatus;
    }
    
    public void setPayDate(String payDate){
        this.payDate = payDate;
    }
    
    //Other function(s)
    public boolean isUnpaid(){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        Tool tool = new Tool();
        
        try {
            con = db.connectDB();           
            String sql = "select * from bills where status = \"UNPAID\" and " +
                         "billID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.billID);
            
            rs = pst.executeQuery();
            
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            tool.getErrorMessageOptionPane("Failed to perform action!\n" + e);
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }
}
