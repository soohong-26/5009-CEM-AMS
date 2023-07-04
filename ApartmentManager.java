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
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class ApartmentManager extends User{
   
    //Constructors
    public ApartmentManager(String name, String userID, String userIC, String contactNo, String gender, String email) {
        super(name, userID, userIC, contactNo, gender, email);
    }

    public ApartmentManager() {
        super();
    }
    
    //Setting details from database using accountID.
    public void setDetailsFromDB(String accountID) {
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        
        try {
            con = db.connectDB();           
            String sql = "select * from manager where accountDBIDM = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, accountID);
            rs = pst.executeQuery();
            
            if (rs.next()){
                this.name = rs.getString("name");
                this.contactNo = rs.getString("contactNo");
                this.gender = rs.getString("gender");
                this.email = rs.getString("email");
                this.userIC = rs.getString("managerIC");
            }
            
        } catch (Exception e){
            e.printStackTrace();
            
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    //Showing all tables
    public void showOverallVisitorTable(JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [8];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        try {
            con = db.connectDB();
            String sql = "SELECT name, contactNo, visitUnit, overnightDays, timeIn, timeOut, " +
                         "dateIn, dateOut from visitationentries " +
                         "WHERE DATE_FORMAT(dateIn, '%Y-%m') BETWEEN ? AND ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("visitUnit");
                row [3] = rs.getString("overnightDays");
                row [4] = rs.getString("timeIn");
                row [6] = rs.getString("dateIn");
                if (rs.getString("timeOut").equals("24:00:00") &&
                    rs.getString("dateOut").equals("0001-01-01")){
                    row [5] = "-";
                    row [7] = "-";
                } else {
                    row [5] = rs.getString("timeOut");
                    row [7] = rs.getString("dateOut");
                }
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showMostVisitedTable(JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [3];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;

        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, COUNT(*) as mostVisited " +
                         "from visitationentries " +
                         "where DATE_FORMAT(dateIn, '%Y-%m') BETWEEN ? AND ? " +
                         "group by name order by mostVisited desc ";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("mostVisited");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showMostOvernights(JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [3];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;

        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, overnightDays " +
                         "from visitationentries " +
                         "where DATE_FORMAT(dateIn, '%Y-%m') BETWEEN ? AND ? " +
                         "order by overnightDays desc ";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("overnightDays");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showMostVisitedUnit (JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [4];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select name, contactNo, visitUnit, count(*) as mostVisitedUnit " +
                         "from visitationentries " +
                         "where DATE_FORMAT(dateIn, '%Y-%m') BETWEEN ? AND ? " +
                         "group by visitUnit " +
                         "order by mostVisitedUnit desc ";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("contactNo");
                row [2] = rs.getString("visitUnit");
                row [3] = rs.getString("mostVisitedUnit");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showOverallBills (JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select bills.billType, bills.status, bills.issueDate, bills.payDate, bills.expiredDate, " +
                         "resident.residentName, resident.stayingUnit from bills " +
                         "inner join resident on bills.accountIDBills = resident.accountRDBID " +
                         "where DATE_FORMAT(bills.issueDate, '%Y-%m') BETWEEN ? AND ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("stayingUnit");
                row [2] = rs.getString("billType");
                row [3] = rs.getString("status");
                row [4] = rs.getString("issueDate");
                row [5] = rs.getString("payDate");
                row [6] = rs.getString("expiredDate");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showPaidBills (JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select bills.billType, bills.status, bills.issueDate, bills.payDate, bills.expiredDate, " +
                         "resident.residentName, resident.stayingUnit from bills " +
                         "inner join resident on bills.accountIDBills = resident.accountRDBID " +
                         "where DATE_FORMAT(bills.issueDate, '%Y-%m') BETWEEN ? AND ? and bills.status = \"PAID\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("stayingUnit");
                row [2] = rs.getString("billType");
                row [3] = rs.getString("status");
                row [4] = rs.getString("issueDate");
                row [5] = rs.getString("payDate");
                row [6] = rs.getString("expiredDate");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showUnpaidBills (JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [6];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select bills.billType, bills.status, bills.issueDate, bills.payDate, bills.expiredDate, " +
                         "resident.residentName, resident.stayingUnit from bills " +
                         "inner join resident on bills.accountIDBills = resident.accountRDBID " +
                         "where DATE_FORMAT(bills.issueDate, '%Y-%m') BETWEEN ? AND ? and bills.status = \"UNPAID\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("stayingUnit");
                row [2] = rs.getString("billType");
                row [3] = rs.getString("status");
                row [4] = rs.getString("issueDate");
                row [5] = rs.getString("expiredDate");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showGuardAttendance (JTable table, String yearFrom, String yearTo, String monthFrom, String monthTo){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [6];
        String yearMonthFrom = yearFrom + "-" + monthFrom;
        String yearMonthTo = yearTo + "-" + monthTo;
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select accountentries.timeIn, accountentries.timeOut, accountentries.dateIn, accountentries.dateOut, " +
                         "guard.name " +
                         "from accountentries " +
                         "inner join guard on accountentries.accountDBIDE = guard.accountIDG " +
                         "where DATE_FORMAT(accountentries.timeIn, '%Y-%m') BETWEEN ? AND ? and accountentries.role = \"GUARD\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, yearMonthFrom);
            pst.setString(2, yearMonthTo);
            
            rs = pst.executeQuery();
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("timeIn");
                row [2] = rs.getString("timeOut");
                row [3] = rs.getString("dateIn");
                row [4] = rs.getString("dateOut");
                
                model.addRow(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    public void showAccountWithInformations (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select resident.residentID, resident.residentName, resident.phoneNumber, " +
                         "resident.email, resident.gender, resident.relationshipToOwner, resident.stayingUnit " +
                         "from resident " +
                         "inner join account on account.accountID = resident.accountRDBID " +
                         "where account.status = \"ACTIVE\"";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("residentID");
                row [2] = rs.getString("phoneNumber");
                row [3] = rs.getString("email");
                row [4] = rs.getString("gender");
                row [5] = rs.getString("relationshipToOwner");
                row [6] = rs.getString("stayingUnit");
                
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
    
    public void showAccountPendingInformations (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "SELECT a.* " +
                         "FROM account a " +
                         "LEFT JOIN resident r ON a.accountID = r.accountRDBID " +
                         "WHERE a.role = \"RESIDENT\" AND r.accountRDBID IS NULL ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("username");
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
    
    //Getting resident username by using user's IC
    public String getResidentUsername(String userIC){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select a.username " +
                         "from resident r inner join account a on r.accountRDBID = a.accountID " +
                         "where r.residentID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userIC);
            
            rs = pst.executeQuery();
            if (rs.next()){
                return rs.getString("username");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
        return "";
    }
    
    //Getting guard's username by using guard's IC number
    public String getGuardUsername(String userIC){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select a.username " +
                         "from guard g inner join account a on g.accountIDG = a.accountID " +
                         "where g.guardIC = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userIC);
            
            rs = pst.executeQuery();
            if (rs.next()){
                return rs.getString("username");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
        return "";
    }
    
    //Search resident's informations
    public void searchResidentInformations (JTable table, String type){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select resident.residentID, resident.residentName, resident.phoneNumber, " +
                         "resident.email, resident.gender, resident.relationshipToOwner, resident.stayingUnit " +
                         "from resident " +
                         "inner join account on account.accountID = resident.accountRDBID " +
                         "where account.status = \"ACTIVE\" and " + type;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("residentID");
                row [2] = rs.getString("phoneNumber");
                row [3] = rs.getString("email");
                row [4] = rs.getString("gender");
                row [5] = rs.getString("relationshipToOwner");
                row [6] = rs.getString("stayingUnit");
                
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
    
    //Search guard informations
    public void searchGuardInformations (JTable table, String type){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [6];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select guard.guardID, guard.guardIC, guard.name, " +
                         "guard.email, guard.gender, guard.contactNo " +
                         "from guard " +
                         "inner join account on account.accountID = guard.accountIDG " +
                         "where account.status = \"ACTIVE\" and " + type;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("guardID");
                row [2] = rs.getString("guardIC");
                row [3] = rs.getString("contactNo");
                row [4] = rs.getString("email");
                row [5] = rs.getString("gender");
                
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
    
    //Insert resident's "SELF" information
    public boolean insertResidentSelfInformation(Resident resident, ResidentAccount rAccount) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int update = 0, update2 = 0;
        
        try {
            con = db.connectDB();
            
            for (int i=0; i<2; i++){
                if (i == 0){
                    String sql = "insert into resident (residentID, "
                       + "residentName, accountRDBID, phoneNumber, email, "
                       + "gender, relationshipToOwner, stayingUnit) "
                       + "VALUES (?,?,?,?,?,?,?,?)";
                    pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, resident.getUserIC());
                    pst.setString(2, resident.getName());
                    pst.setString(3, rAccount.getAccountIDFromDB());
                    pst.setString(4, resident.getContactNo());
                    pst.setString(5, resident.getEmail());
                    pst.setString(6, resident.getGender());
                    pst.setString(7, resident.getRelationshipToOwner());
                    pst.setString(8, resident.getStayingUnit());

                    update = pst.executeUpdate();
                    rs = pst.getGeneratedKeys();

                    if (rs.next()){
                        resident.setOwnerDBID(rs.getInt(1));
                    }
                } else {
                    String sql = "UPDATE resident SET ownerDBID = ? WHERE (residentDBID = ?)";
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, resident.getOwnerDBID());
                    pst.setInt(2, resident.getOwnerDBID());
                    update2 = pst.executeUpdate();
                    break;
                }
            }
            return update > 0 && update2 > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Getting owner's ID using username
    public int getOwnerID(String username){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int ownerID = 0;
        
        try {
            con = db.connectDB();
            String sql = "select resident.residentDBID " +
                         "from resident inner join account on account.accountID = resident.accountRDBID " +
                         "where account.username = ? and resident.relationshipToOwner = \"SELF\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            
            if (rs.next()){
                ownerID = rs.getInt("residentDBID");
            }

            return ownerID;
            
        } catch (Exception e){
            e.printStackTrace();
            return ownerID;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Inserting "FAMILY_MEMBERS" or "FRIENDS" relationship details to database
    public boolean insertResidentInformation(Resident resident, ResidentAccount rAccount, int ownerID) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        int update = 0;
        
        try {
            con = db.connectDB();
            String sql = "insert into resident (residentID, "
               + "residentName, accountRDBID, phoneNumber, email, "
               + "gender, relationshipToOwner, stayingUnit, ownerDBID) "
               + "VALUES (?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, resident.getUserIC());
            pst.setString(2, resident.getName());
            pst.setString(3, rAccount.getAccountIDFromDB());
            pst.setString(4, resident.getContactNo());
            pst.setString(5, resident.getEmail());
            pst.setString(6, resident.getGender());
            pst.setString(7, resident.getRelationshipToOwner());
            pst.setString(8, resident.getStayingUnit());
            pst.setInt(9, ownerID);

            update = pst.executeUpdate();

            return update > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //To validate resident's IC whether is it duplicating
    public boolean validateResidentIC(String IC){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select * from resident where residentID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, IC);
            rs = pst.executeQuery();
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        } 
    }
    
    //To validate whether the "SELF" is same their "FAMILY_MEMBER" or "FRIENDS"
    public boolean validateUnit(String unit, String username){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select resident.* " +
                         "from resident " +
                         "inner join account on account.accountID = resident.accountRDBID " +
                         "where account.username = ? and resident.relationshipToOwner = \"SELF\" " +
                         "and resident.stayingUnit = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, unit);
            rs = pst.executeQuery();
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        } 
    }
    
    //To check whether their account ID has owner or not 
    public boolean validateResidentHasOwner(String accountID){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select * from resident where accountRDBID = ? and relationshipToOwner = \"SELF\"";
            pst = con.prepareStatement(sql);
            pst.setString(1, accountID);
            
            rs = pst.executeQuery();
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        } 
    }
    
    //Showing data from database
    public void showAccountWithInformationsGuard (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [6];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select * FROM guard";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("guardID");
                row [2] = rs.getString("guardIC");
                row [3] = rs.getString("contactNo");
                row [4] = rs.getString("email");
                row [5] = rs.getString("gender");
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
    
    public void showAccountPendingInformationsGuard (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [7];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "SELECT a.* " +
                         "FROM account a " +
                         "LEFT JOIN guard g ON a.accountID = g.accountIDG " +
                         "WHERE a.role = \"GUARD\" AND g.accountIDG IS NULL ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("username");
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
    
    //Inserting guard's information into database
    public boolean insertGuardInformation(Guard guard, Account account) {
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        int update = 0;
        
        try {
            con = db.connectDB();
            String sql = "insert into guard (guardID, "
               + "guardIC, accountIDG, name, gender, "
               + "contactNo, email) "
               + "VALUES (?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, guard.getUserID());
            pst.setString(2, guard.getUserIC());
            pst.setString(3, account.getAccountIDFromDB());
            pst.setString(4, guard.getName());
            pst.setString(5, guard.getGender());
            pst.setString(6, guard.getContactNo());
            pst.setString(7, guard.getEmail());

            update = pst.executeUpdate();

            return update > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Showing all data from database
    public void showAllAccounts (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [4];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "SELECT account.username, account.role, account.status, CONCAT(IFNULL(resident.residentName, ''), IFNULL(guard.name, '')) AS names " +
                         "FROM account " +
                         "LEFT JOIN resident ON account.accountID = resident.accountRDBID " +
                         "LEFT JOIN guard ON account.accountID = guard.accountIDG " +
                         "WHERE (account.role = 'RESIDENT' OR account.role = 'GUARD') " +
                         "AND (resident.residentName IS NOT NULL " +
                         "or guard.name IS NOT NULL)";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("names");
                row [1] = rs.getString("username");
                row [2] = rs.getString("role");
                row [3] = rs.getString("status");
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
    
   
    public void showAllResidentAccounts (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [4];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select account.username, account.status, resident.residentName, resident.stayingUnit " +
                         "from account " +
                         "inner join resident on account.accountID = resident.accountRDBID";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("residentName");
                row [1] = rs.getString("username");
                row [2] = rs.getString("stayingUnit");
                row [3] = rs.getString("status");
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
    
    public void showAllGuardAccounts (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [3];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select account.username, account.status, guard.name " +
                         "from account " +
                         "inner join guard on account.accountID = guard.accountIDG";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("username");
                row [2] = rs.getString("status");
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
    
    //Validating whether the username already occured in database
    public boolean validateUsername(String username){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.connectDB();
            String sql = "select username from account where username = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            
            return rs.next();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Creating account for different residents or guards
    public boolean createAccount(Account account){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        int update = 0;
        
        try {
            con = db.connectDB();
            String sql = "insert into account (username, password, role, status) " +
                         "VALUES (?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, account.getUsername());
            pst.setString(2, account.getPassword());
            pst.setString(3, account.getRole());
            pst.setString(4, account.getStatus());
            
            update = pst.executeUpdate();

            return update > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Showing information
    public void showAccountPendingToAddInformations (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [3];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "SELECT account.username, account.role, account.status " +
                         "FROM account " +
                         "LEFT JOIN resident ON account.accountID = resident.accountRDBID " +
                         "LEFT JOIN guard ON account.accountID = guard.accountIDG " +
                         "WHERE (account.role = 'RESIDENT' OR account.role = 'GUARD') " +
                         "and resident.residentName IS NULL " +
                         "and guard.name IS NULL";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("username");
                row [1] = rs.getString("role");
                row [2] = rs.getString("status");
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
    
    public void showReminderTable (JTable table){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object [] row = new Object [5];
        
        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        try {
            con = db.connectDB();
            String sql = "select notification.*, manager.name " +
                         "from notification " +
                         "inner join manager on notification.accountIDAM = manager.accountDBIDM ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                row [0] = rs.getString("name");
                row [1] = rs.getString("type");
                row [2] = rs.getString("notificationContent");
                row [3] = rs.getString("sentTime");
                row [4] = rs.getString("sentDate");
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
    
    //Insert reminder to database
    public boolean createReminder (Notification notification, int accountID){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        int update = 0;
        Date timeDate = new Date();
        SimpleDateFormat timeF = new SimpleDateFormat("kk:mm:ss");
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            con = db.connectDB();
            String sql = "insert into notification (type, notificationContent, " +
                         "accountIDAM, sentDate, sentTime) " +
                         "VALUES (?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, notification.getType());
            pst.setString(2, notification.getNotificationText());
            pst.setInt(3, accountID);
            pst.setString(4, dateF.format(timeDate));
            pst.setString(5, timeF.format(timeDate));
            
            update = pst.executeUpdate();

            return update > 0;
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
}
