/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

/**
 *
 * @author User
 */
public class Notification {
    private ApartmentManager am;
    private String notificationText;
    private String type;
    private String sentDate;
    private String sentTime;

    //Constructor
    public Notification(ApartmentManager am, String type, String notificationText, String sentDate, String sentTime) {
        this.am = am;
        this.type = type;
        this.notificationText = notificationText;
        this.sentDate = sentDate;
        this.sentTime = sentTime;
    }
    
    public Notification(){
        this.am = new ApartmentManager();
        this.type = "";
        this.notificationText = "";
        this.sentDate = "";
        this.sentTime = "";
    }
    
    //Getters
    public ApartmentManager getAm() {
        return am;
    }
    
    public String getType(){
        return type;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getSentTime() {
        return sentTime;
    }
    
    //Setters
    public void setAm(ApartmentManager am) {
        this.am = am;
    }
    
    public void setType(String type){
        this.type = type;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
    
    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }
    
}
