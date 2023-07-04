/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

/**
 *
 * @author User
 */
public class User {
    protected String name;
    protected String userID;
    protected String userIC;
    protected String contactNo;
    protected String gender;
    protected String email;
    
    //Constructor
    public User(String name, String userID, String userIC, String contactNo, String gender, String email) {
        this.name = name;
        this.userID = userID;
        this.userIC = userIC;
        this.contactNo = contactNo;
        this.gender = gender;
        this.email = email;
    }
    
    public User(){
        this.name = "";
        this.userID = "";
        this.userIC = "";
        this.contactNo = "";
        this.gender = "";
        this.email = "";
    }
    
    //Getters
    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserIC() {
        return userIC;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getGender() {
        return gender;
    }
    
    public String getEmail(){
        return email;
    }
    
    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserIC(String userIC) {
        this.userIC = userIC;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
}
