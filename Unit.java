/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.util.ArrayList;

/**
 *
 * @author Resident
 */
public class Unit {
    private Resident ownerDetails;
    private String unitName;
    private String block;
    private ArrayList <Resident> members;

    public Unit(Resident ownerDetails, String unitName, String block) {
        this.ownerDetails = ownerDetails;
        this.unitName = unitName;
        this.block = block;
        this.members = new ArrayList();
    }
    
    //Getters
    public Resident getOwnerDetails() {
        return ownerDetails;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getBlock() {
        return block;
    }

    public ArrayList<Resident> getMembers() {
        return members;
    }
    
    //Setters
    public void setOwnerDetails(Resident ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setMembers(ArrayList<Resident> members) {
        this.members = members;
    }
}

