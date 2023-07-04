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
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Forum {
    private String text;
    private Profile profile;
    private int chatID;
    private String sendTime;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    //Constructor
    public Forum(String text, Profile profile, int chatID, String sendTime) {
        this.text = text;
        this.profile = profile;
        this.chatID = chatID;
        this.sendTime = sendTime;
    }
    
    public Forum() {
        this.text = "";
        this.profile = new Profile();
        this.chatID = 0;
        this.sendTime = "";
    }
    
    public Forum(Profile profile){
        this.profile = profile;
        this.text = "";
        this.chatID = 0;
        this.sendTime = "";
    }
    
    //Getters
    public String getText() {
        return text;
    }

    public Profile getProfile() {
        return profile;
    }

    public int getChatID() {
        return chatID;
    }
    
    public String getSendTime() {
        return this.sendTime;
    }
    
    //Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }
    
    public void setSendTime(String sendTime){
        this.sendTime = sendTime;
    }
    
    //Other function(s)
    //Showing history text of the forum
    public void showHistoryText(JTextArea pane){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String currName = "";
        String previousName = "";
        String currDate = "";
        String previousDate = "";
        int counter = 0;
        
        try {
            con = db.connectDB();
            String sql = "select forum.chatText, forum.sendTime, forum.sendDate, profile.appearingName " +
                         "from forum " +
                         "inner join profile ON forum.profileIDchat = profile.profileID " +
                         "order by forum.sendDate, forum.sendTime asc";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                currDate = rs.getString("sendDate");
                currName = rs.getString("appearingName"); 
                
                if (currName.equals(previousName) && currDate.equals(previousDate)){
                    pane.append("(" + rs.getString("sendTime") + ")  " + rs.getString("chatText") + "\n");
                    previousName = currName;
                    previousDate = currDate;
                    continue;
                } 
                
                if (counter != 0)
                    pane.append("\n");
                
                if (!currDate.equals(previousDate)){
                    pane.append("\n");
                    pane.append("--------------------------------------------------------------" + "\n");
                    pane.append("                            | " + rs.getString("sendDate")  + " |" + "\n");
                    pane.append("--------------------------------------------------------------" + "\n");
                }
               
                pane.append("----------------------------" + "\n");
                pane.append(rs.getString("appearingName") + "\n");
                pane.append("----------------------------" + "\n");
                pane.append("(" + rs.getString("sendTime") + ")  " + rs.getString("chatText") + "\n");
                previousName = currName;
                previousDate = currDate;
                counter = 1;
            }// End while
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Inserting to text area of resident's forum
    public void insertToTextArea(JTextArea pane){
        Tool tool = new Tool();
        Date time = new Date();
        Date date = new Date();
        
        if (getLatestName().equals(this.profile.getAppearingName()) && getLatestDate().equals(dateFormat.format(date))){
            pane.append("(" + timeFormat.format(time) + ")  " + this.text.trim() + "\n");
            if (!insertToDB(pane)){
                tool.getErrorMessageOptionPane("Something went wrong, this text cannot be update to database!");
                return;
            }
            return;
        }
        
        if (!getLatestDate().equals(dateFormat.format(date))){
            pane.append("\n");
            pane.append("--------------------------------------------------------------" + "\n");
            pane.append("                            | " + dateFormat.format(date)  + " |" + "\n");
            pane.append("--------------------------------------------------------------" + "\n");
        } else {
            pane.append("\n");
        }
        
        pane.append("----------------------------" + "\n");
        pane.append(this.profile.getAppearingName() + "\n");
        pane.append("----------------------------" + "\n");
        pane.append("(" + timeFormat.format(time) + ")  " + this.text.trim() + "\n");
        
        
        if (!insertToDB(pane)){
            tool.getErrorMessageOptionPane("Something went wrong, this text cannot be update to database!");
        }
    }
    
    //Inserting the text into database
    public boolean insertToDB(JTextArea pane){
        Dbclass db = new Dbclass();
        Connection con = null;
        PreparedStatement pst = null;
        Tool tool = new Tool();
        Date time = new Date();
        Date date = new Date();
        
        try {
            con = db.connectDB();
            String sql = "INSERT INTO forum (profileIDChat, "
                       + "chatText, sendTime, sendDate) VALUES (?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, this.profile.getProfileIDFromDB());
            pst.setString(2, this.text.trim());
            pst.setString(3, timeFormat.format(time));
            pst.setString(4, dateFormat.format(date));
            
            int update = pst.executeUpdate();
            return update > 0;
        } catch (Exception e){
            e.printStackTrace();
            tool.getInformationMessageOptionPane("Something went wrong!");
            return false;
        } finally {
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { pst.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }
    
    //Getting latest name of the forum
    public String getLatestName(){
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String latestName = "";
        
        try {
            con = db.connectDB();
            String sql = " Select profile.appearingName\n" +
                         " From forum " +
                         "Inner join profile on forum.profileIDChat = profile.profileID " +
                         "Order By forum.sendDate desc, forum.sendTime desc";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                latestName = rs.getString("appearingName");
                return latestName;
            }// End if
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
        return latestName;
    }
    
    //Get latest date from database for later use in the forum
    public String getLatestDate() {
        Dbclass db = new Dbclass();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String latestDate = "";
        
        try {
            con = db.connectDB();
            String sql = "Select sendDate from forum " +
                         "order by sendDate Desc";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                latestDate = rs.getString("sendDate");
                return latestDate;
            }// End if
            
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { con.close(); } catch (Exception e) { e.printStackTrace();}
            try { rs.close(); } catch (Exception e) { e.printStackTrace();}
        }
        return latestDate;
    }
}
