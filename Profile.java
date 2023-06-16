/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

/**
 *
 * @author Resident
 */
public class Profile {
    private Resident resident;
    private String appearingName;
    private String pin;
    
    //Constructors
    public Profile (Resident resident, String appearingName, String pin){
        this.resident = resident;
        this.appearingName = appearingName;
        this.pin = pin;
    }
    
    public Profile(){
        this.resident = new Resident();
        this.appearingName = "";
        this.pin = "";
    }
    
    //Getters
    public Resident getResident() {
        return resident;
    }

    public String getAppearingName() {
        return appearingName;
    }

    public String getPin() {
        return pin;
    }
    
    //Setters
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public void setAppearingName(String appearingName) {
        this.appearingName = appearingName;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
}
