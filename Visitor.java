/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author User
 */
public class Visitor {
    private String name;
    private String contactNo;
    private String visitUnit;
    private int overnightDays;
    private String carPlate;
    private String parkingLot;
    private Date timeIn;
    private Date timeOut;
    private Date dateIn;
    private Date dateOut;
    private SimpleDateFormat time = new SimpleDateFormat("kk:mm:ss");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    
    //Constructor
    public Visitor(){
        String nullDate = "0000-01-01";
        String nullTime = "00:00:00";
        this.name = "";
        this.contactNo = "";
        this.visitUnit = "";
        this.overnightDays = 0;
        this.carPlate = "";
        this.parkingLot = "";
        this.timeIn = new Date();
        this.dateIn = new Date();
        
        try {
            this.timeOut = time.parse(nullTime);
            this.dateOut = date.parse(nullDate);
        } catch (Exception e){}
    }
    
    //Getters
    public String getName() {
        return name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getVisitUnit() {
        return visitUnit;
    }

    public int getOvernightDays() {
        return overnightDays;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public String getTimeIn() {
        return time.format(this.timeIn);
    }

    public String getTimeOut() {
        return time.format(this.timeOut);
    }

    public String getDateIn() {
        return date.format(this.dateIn);
    }

    public String getDateOut() {
        return date.format(this.dateOut);
    }
    
    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setVisitUnit(String visitUnit) {
        this.visitUnit = visitUnit;
    }

    public void setOvernightDays(int overnightDays) {
        this.overnightDays = overnightDays;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }
    
    //Other function(s)
    //Validating whether this visitor has car checked in in the apartment or not
    public boolean hasCarCheckedIn(){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select carPlate from visitationentries where name = ? and contactNo = ? "
                       + "and dateOut = \"0001-01-01\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.name);
            pst.setString(2, this.contactNo);
            rs = pst.executeQuery();
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }
    
    //Validating this visitor has car history or not
    public boolean hasCarHistory(){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select carPlate from visitationentries where name = ? and contactNo = ? and "
                       + "carPlate != \"-\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.name);
            pst.setString(2, this.contactNo);
            rs = pst.executeQuery();
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }
    
    //Getting visitor's car plate
    public String getCarPlateFromDB (String isNull){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        String carPlate = "";
        
        if (isNull.equals("null")){
            try {
                con = db.connectDB();           
                String sql = "select carPlate from visitationentries where name = ? and contactNo = ? "
                           + "and timeOut = \"24:00:00\"";
                pst = con.prepareStatement(sql);
                pst.setString(1, this.name);
                pst.setString(2, this.contactNo);
                rs = pst.executeQuery();

                if (rs.next()){
                    carPlate = rs.getString("carPlate");
                }

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
                    try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
                    try { con.close(); } catch (Exception e) { e.printStackTrace(); }
                }
                return carPlate;
        } else {
            try {
                con = db.connectDB();           
                String sql = "select carPlate from visitationentries where name = ? and contactNo = ? "
                           + "and timeOut != \"24:00:00\"";
                pst = con.prepareStatement(sql);
                pst.setString(1, this.name);
                pst.setString(2, this.contactNo);
                rs = pst.executeQuery();

                if (rs.next()){
                    carPlate = rs.getString("carPlate");
                }

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
                    try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
                    try { con.close(); } catch (Exception e) { e.printStackTrace(); }
                }
                return carPlate;
        }
        
    }
    
    //Getting where they park their car in the apartment
    public String getParkingLotFromDB (String isNull){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        String parkingLot = "";
        
        if (isNull.equals("null")){
            try {
                con = db.connectDB();           
                String sql = "select parkingLot from visitationentries where name = ? and timeOut = \"24:00:00\"";
                pst = con.prepareStatement(sql);
                pst.setString(1, this.name);
                rs = pst.executeQuery();

                if (rs.next()){
                    parkingLot = rs.getString("parkingLot");
                }

            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
                try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            return parkingLot;
        } else {
            try {
                con = db.connectDB();           
                String sql = "select parkingLot from visitationentries where name = ? and timeOut != \"24:00:00\"";
                pst = con.prepareStatement(sql);
                pst.setString(1, this.name);
                rs = pst.executeQuery();

                if (rs.next()){
                    parkingLot = rs.getString("parkingLot");
                }

            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
                try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            return parkingLot;
        }
        
    }
    
    //Validating this information has checked in or not
    public boolean hasCheckedIn (){
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select timeIn from visitationentries where name = ? and contactNo = ? and dateOut = \"0001-01-01\" ";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.name);
            pst.setString(2, this.contactNo);
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
}
