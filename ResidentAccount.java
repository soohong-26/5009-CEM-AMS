/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ResidentAccount extends Account{
    private Profile mainProfile;
    private Profile usingProfile;
    private ArrayList <Profile> subProfiles;
    private ArrayList <Bill> bills;
    
    //Constructor
    public ResidentAccount(){
        super();
        this.mainProfile = new Profile();
        this.usingProfile = new Profile();
        this.subProfiles = new ArrayList();
    }
    
    //Getter
    public Profile getMainProfile() {
        return mainProfile;
    }

    public Profile getUsingProfile() {
        return usingProfile;
    }

    public ArrayList<Profile> getSubProfiles() {
        return subProfiles;
    }
    
    public ArrayList<Bill> getBills() {
        return this.bills;
    }
    
    //Setter
    public void setMainProfile(Profile mainProfile) {
        this.mainProfile = mainProfile;
    }

    public void setUsingProfile(Profile usingProfile) {
        this.usingProfile = usingProfile;
    }

    public void setSubProfiles(ArrayList<Profile> subProfiles) {
        this.subProfiles = subProfiles;
    }
    
    public void pushSubProfiles(Profile profile){
        this.subProfiles.add(profile);
    }
    
    public void setBills (ArrayList <Bill> bills){
        this.bills = bills; 
    }
    
    public void pushBills (Bill bill){
        this.bills.add(bill);
    }
}
