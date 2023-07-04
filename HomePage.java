/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ams;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author User
 */
public class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    
    //Global Variables
    private Tool tool = new Tool();
    private Account account = new Account();
    private int checkBoxGuardClicked = 0, checkBoxResidentClicked = 0, residentGuardState = 0; //1 is resident, 0 is guard
    private Guard guard = new Guard();
    private Resident resident = new Resident();
    private Visitor visitor = new Visitor();
    private ApartmentManager manager = new ApartmentManager();
    private ResidentAccount rAccount = new ResidentAccount();
    private SimpleDateFormat year = new SimpleDateFormat("yyyy");
    private SimpleDateFormat month = new SimpleDateFormat("MM");
    private Date now = new Date();
    
    //Constructors
    public HomePage() {
        initComponents();
    }
    
    public HomePage(Account account) {
        this.account = account;
        initComponents();
        tool.displayDate(date);
        tool.displayTime(time);
        showPanel();
    }
    
    public HomePage (ResidentAccount rAccount){
        this.rAccount = rAccount;
        initComponents();
        tool.displayDate(date);
        tool.displayTime(time);
        showPanel();
    }
    
    //Other function(s)
    //To decide what to show
    private void showPanel (){
        //All character
        sideNavBar.setVisible(true);
        
        //Guard
        guardPanel.setVisible(false);
        guardNavBar.setVisible(false);
        homeScreenGuard.setVisible(false);
        checkInOutScreen.setVisible(false);
        parkingLotAsterisk.setVisible(false);
        carPlateAsterisk.setVisible(false);
        
        //Resident
        residentPanel.setVisible(false);
        residentNavBar.setVisible(false);
        payBillsScreen.setVisible(false);
        homeScreenResident.setVisible(false);
        forumScreen.setVisible(false);
        manageProfileScreenResident.setVisible(false);
        
        //Apartment Manager
        apartmentManagerPanel.setVisible(false);
        apartmentManagerNavBar.setVisible(false);
        reportScreenApartmentManager.setVisible(false);
        informationsScreenApartmentManager.setVisible(false);
        manageAccountsScreenApartmentManager.setVisible(false);
        reminderScreenApartmentManager.setVisible(false);
        
        //Guard
        if (account.getRole().equals("GUARD")) {
            this.guard = new Guard();
            welcomeTextLabel.setText("Welcome, " + 
                                    this.guard.getNameFromDB(this.account.getAccountIDFromDB()) +
                                    "!");
            guardPanel.setVisible(true);
            guardNavBar.setVisible(true);
            homeScreenGuard.setVisible(true);
            guard.showHomeHistoryTable(historyDataTable);
            return;
        }
        
        //Apartment Manager
        if (account.getRole().equals("APARTMENT_MANAGER")) {
            this.manager = new ApartmentManager();
            this.manager.setDetailsFromDB(account.getAccountIDFromDB());
            welcomeTextLabel.setText("Welcome, " +
                                     this.manager.getName() + "!");
            apartmentManagerNavBar.setVisible(true);
            apartmentManagerPanel.setVisible(true);
            return;
        }
        
        //Resident
        this.resident = new Resident();
        this.resident.setDetailsFromDB(rAccount.getAccountID());
        residentPanel.setVisible(true);
        welcomeTextLabel.setText("Welcome, " +
                                this.rAccount.getUsingProfile().getAppearingName() +
                                "!");
        heyLabel.setText("Hey, " + this.rAccount.getUsingProfile().getAppearingName() + "!");
        residentNavBar.setVisible(true);
        homeScreenResident.setVisible(true);
    }
    
    //Set all screens to not visible
    private void resetAllGuardScreenScreen() {
        homeScreenGuard.setVisible(false);
        checkInOutScreen.setVisible(false);
    }
    
    private void resetAllResidentScreen() {
        homeScreenResident.setVisible(false);
        payBillsScreen.setVisible(false);
        forumScreen.setVisible(false);
        manageProfileScreenResident.setVisible(false);
    }
    
    private void resetAllManagerScreen(){
        reportScreenApartmentManager.setVisible(false);
        informationsScreenApartmentManager.setVisible(false);
        manageAccountsScreenApartmentManager.setVisible(false);
        reminderScreenApartmentManager.setVisible(false);
    }
    
    private void resetAllManagerSubScreen(){
        visitorReport.setVisible(false);
        residentReport.setVisible(false);
        guardReport.setVisible(false);
    }
    
    //Clear all tables for different modules
    private void clearAllGuardTable(){
        DefaultTableModel model1 = (DefaultTableModel) historyTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) visitorsCarsDetailsTable.getModel();
        DefaultTableModel model3 = (DefaultTableModel) timeOutTable.getModel();
        DefaultTableModel model4 = (DefaultTableModel) historyDataTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
        model3.setRowCount(0);
        model4.setRowCount(0);
    }
    
    private void clearAllResidentTable(){
        DefaultTableModel model1 = (DefaultTableModel) unpaidBillsTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) paidBillsTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
    }
    
    private void clearAllManagerReportTable() {
        DefaultTableModel model1 = (DefaultTableModel) overallReportTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) mostVisitedTable.getModel();
        DefaultTableModel model3 = (DefaultTableModel) mostOvernightsTable.getModel();
        DefaultTableModel model4 = (DefaultTableModel) mostVisitedUnitsTable.getModel();
        DefaultTableModel model5 = (DefaultTableModel) overallBillRecordTable.getModel();
        DefaultTableModel model6 = (DefaultTableModel) paidRecordTable.getModel();
        DefaultTableModel model7 = (DefaultTableModel) unpaidRecordTable.getModel();
        DefaultTableModel model8 = (DefaultTableModel) attendanceTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
        model3.setRowCount(0);
        model4.setRowCount(0);
        model5.setRowCount(0);
        model6.setRowCount(0);
        model7.setRowCount(0);
        model8.setRowCount(0);
    }
    
    private void clearAllInformationTable() {
        DefaultTableModel model1 = (DefaultTableModel) accountWithInformationsTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) accountPendingToAddInformationsTable.getModel();
        DefaultTableModel model3 = (DefaultTableModel) accountWithInformationsGuardTable.getModel();
        DefaultTableModel model4 = (DefaultTableModel) accountPendingToAddInformationsGuardTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
        model3.setRowCount(0);
        model4.setRowCount(0);
    }
    
    private void clearAllManageAccountsTable(){
        DefaultTableModel model1 = (DefaultTableModel) overallAccountsTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) residentAccountsTable.getModel();
        DefaultTableModel model3 = (DefaultTableModel) guardAccountsTable.getModel();
        DefaultTableModel model4 = (DefaultTableModel) accountPendingToAddInformationTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
        model3.setRowCount(0);
        model4.setRowCount(0);
    }
    
    private void clearAllReminderTable(){
        DefaultTableModel model1 = (DefaultTableModel) reminderHistoryTable.getModel();
        model1.setRowCount(0);
    }
    
    //Showing data from database
    private void showYourDetailsResident(){
        setAllResidentTextFieldToGainedFocusState();
        ArrayList <String> details = rAccount.getUsingProfile().getDetailsFromDB();
        //0. Name, 1. phoneNumber, 2. Email, 3. Gender

        String [] phoneNumber = tool.getSplitNumber(details.get(1));
        
        residentRealNameLabel.setText(details.get(0));
        residentRealNameLabel2.setText(details.get(0));
        appearingProfileNameTextField.setText(rAccount.getUsingProfile().getAppearingName());
        jLabel31.setText(rAccount.getUsingProfile().getAppearingName());
        countryCode1.setText(phoneNumber[0]);
        phoneNumberTextField1.setText(phoneNumber[1]);
        jLabel33.setText(details.get(1));
        emailTextField.setText(details.get(2));
        jLabel35.setText(details.get(2));
        jLabel37.setText(details.get(3));
        genderComboBox.setSelectedItem(details.get(3));
    }
    
    private void showAllDefaultVisitorManagerTable() {
        this.manager.showOverallVisitorTable(overallReportTable, 
                year.format(now), year.format(now),
                month.format(now), month.format(now));
        this.manager.showMostVisitedTable(mostVisitedTable ,
                                   year.format(now), year.format(now),
                            month.format(now), month.format(now));
        this.manager.showMostOvernights(mostOvernightsTable, 
                                year.format(now), year.format(now),
                                month.format(now), month.format(now));
        this.manager.showMostVisitedUnit(mostVisitedUnitsTable, 
                            year.format(now), year.format(now),
                            month.format(now), month.format(now));
    }
    
    private void showAllDefaultResidentManagerTable(){
        this.manager.showOverallBills(overallBillRecordTable, 
                year.format(now), year.format(now),
                month.format(now), month.format(now));
        this.manager.showPaidBills(paidRecordTable ,
                                   year.format(now), year.format(now),
                            month.format(now), month.format(now));
        this.manager.showUnpaidBills(unpaidRecordTable, 
                                year.format(now), year.format(now),
                                month.format(now), month.format(now));
    }
    
    private void showAllDefaultGuardManagerTable() {
        this.manager.showGuardAttendance(attendanceTable, year.format(now), year.format(now),
                                month.format(now), month.format(now));
    }
    
    private Visitor getVisitorInfo() {
        Visitor visitor = new Visitor();
        visitor.setName(nameTextField.getText().trim().toUpperCase());
        visitor.setContactNo(countryCode.getText().trim() + " - " + phoneNumberTextField.getText().trim());
        visitor.setVisitUnit(visitUnitTextField.getText().trim().toUpperCase());
        visitor.setOvernightDays((Integer) overnightDaysSpinner.getValue());
        if (checkbox1.isEnabled()){
            visitor.setCarPlate(carPlateTextField.getText().trim().toUpperCase());
            visitor.setParkingLot(parkingLotTextField.getText().trim().toUpperCase());
        } else {
            visitor.setCarPlate("-");
            visitor.setParkingLot("-");
        }
        return visitor;
    }
    
    //Validate all field is null or not
    private boolean allFieldIsNullGuard() {
        if (!checkbox1.isEnabled()){
            return nameTextField.getText().trim().equals("Enter name...") ||
                    countryCode.getText().trim().equals("+6010") ||
                    phoneNumberTextField.getText().trim().equals("1234567") ||
                    visitUnitTextField.getText().trim().equals("A-2-13...");
        } else {
            return nameTextField.getText().trim().equals("Enter name...") ||
                    countryCode.getText().trim().equals("+6010") ||
                    phoneNumberTextField.getText().trim().equals("1234567") ||
                    visitUnitTextField.getText().trim().equals("A-2-13...") ||
                    carPlateTextField.getText().trim().equals("WWW 9999...") ||
                    parkingLotTextField.getText().trim().equals("A1...");
        }
    }
    
    private boolean allFieldIsNullResident(){
        if (appearingProfileNameTextField.getText().trim().equals("") ||
            appearingProfileNameTextField.getText().equals("Enter Appearing/Profile Name...") ||
            oldPinField.getText().trim().equals("") || oldPinField.getText().equals("Enter Pin...") || 
            countryCode1.getText().trim().equals("") || countryCode1.getText().equals("+6010") ||
            phoneNumberTextField1.getText().trim().equals("") || phoneNumberTextField1.getText().equals("1234567") ||
            emailTextField.getText().trim().equals("") || emailTextField.getText().equals("Enter Email...")) {
            tool.getErrorMessageOptionPane("All fields are mandatory!");
            return true;
        }
        return false;
    }
    
    private boolean allFieldResidentIsNullApartmentManagerInformation(){
        if (nameTextField1.getText().trim().equals("") || nameTextField1.getText().trim().equals("Enter name...") ||
            ICTextField.getText().trim().equals("") || ICTextField.getText().trim().equals("Enter IC number...") ||
            countryCode3.getText().trim().equals("") || countryCode3.getText().trim().equals("+6010...") ||
            phoneNumberTextField3.getText().trim().equals("") || phoneNumberTextField3.getText().trim().equals("1234567...") ||
            stayingUnitTextField.getText().trim().equals("") || stayingUnitTextField.getText().trim().equals("A-2-13...") ||
            emailTextFieldResident.getText().trim().equals("") || emailTextFieldResident.getText().trim().equals("Enter Email...")) {
            tool.getErrorMessageOptionPane("All fields are mandatory!");
            return true;
        }
        return false;
    }
    
    private boolean allFieldGuardIsNullApartmentManagerInformation(){
        if (nameTextField1.getText().trim().equals("") || nameTextField1.getText().trim().equals("Enter name...") ||
            ICTextField.getText().trim().equals("") || ICTextField.getText().trim().equals("Enter IC number...") ||
            countryCode3.getText().trim().equals("") || countryCode3.getText().trim().equals("+6010...") ||
            phoneNumberTextField3.getText().trim().equals("") || phoneNumberTextField3.getText().trim().equals("1234567...") ||
            guardIDTextField.getText().trim().equals("") || guardIDTextField.getText().trim().equals("Enter Guard ID...") ||
            emailTextFieldResident.getText().trim().equals("") || emailTextFieldResident.getText().trim().equals("Enter Email...")) {
            tool.getErrorMessageOptionPane("All fields are mandatory!");
            return true;
        }
        return false;
    }
    
    private boolean allFieldManageAccountIsNullApartmentManager(){
        if (usernameAccountTextField.getText().trim().equals("") || usernameAccountTextField.getText().trim().equals("Enter Username...") ||
            passwordField1.getText().trim().equals("") || passwordField1.getText().trim().equals("Enter Password...") ||
            passwordField.getText().trim().equals("") || passwordField.getText().trim().equals("Enter Confirm Password...")) {
            tool.getErrorMessageOptionPane("All fields are mandatory!");
            return true;
        }
        return false;
    }
    
    //Set all text fields to lost focus
    private void setAllGuardToLostFocusState(){
        nameTextField.setForeground(tool.textLostFocusColor());
        countryCode.setForeground(tool.textLostFocusColor());
        phoneNumberTextField.setForeground(tool.textLostFocusColor());
        carPlateTextField.setForeground(tool.textLostFocusColor());
        parkingLotTextField.setForeground(tool.textLostFocusColor());
        visitUnitTextField.setForeground(tool.textLostFocusColor());
        checkbox1.setState(false);
        checkBoxGuardClicked = 0;
        overnightDaysSpinner.setValue(0);
        nameTextField.setText("Enter name...");
        countryCode.setText("+6010");
        phoneNumberTextField.setText("1234567");
        visitUnitTextField.setText("A-2-13...");
        carPlateTextField.setText("-");
        parkingLotTextField.setText("-");
    }
    
    private void setAllManageAccountsToLostFocusState(){
        usernameAccountTextField.setForeground(tool.textLostFocusColor());
        usernameAccountTextField.setText("Enter Username...");
        passwordField1.setForeground(tool.textLostFocusColor());
        passwordField1.setEchoChar('\u0000');
        passwordField1.setText("Enter Password...");
        passwordField.setForeground(tool.textLostFocusColor());
        passwordField.setEchoChar('\u0000');
        passwordField.setText("Enter Confirm Password...");
    }
    
    private void setAllResidentTextFieldToLostFocusState(){
        appearingProfileNameTextField.setForeground(tool.textLostFocusColor());
        countryCode1.setForeground(tool.textLostFocusColor());
        phoneNumberTextField1.setForeground(tool.textLostFocusColor());
        emailTextField.setForeground(tool.textLostFocusColor());
        oldPinField.setForeground(tool.textLostFocusColor());
        oldPinField.setEchoChar('\u0000');
        newPinField.setForeground(tool.textLostFocusColor());
        newPinField.setEchoChar('\u0000');
        
        appearingProfileNameTextField.setText("Enter Appearing/Profile Name...");
        oldPinField.setText("Enter Pin...");
        newPinField.setText("Enter Pin...");
        countryCode1.setText("+6010");
        phoneNumberTextField1.setText("1234567");
        emailTextField.setText("Enter Email...");
        genderComboBox.setSelectedItem(0);
    }
    
    private void setAllManagerResidentInformationToLostFocusState(){
        nameTextField1.setForeground(tool.textLostFocusColor());
        nameTextField1.setText("Enter name...");
        ICTextField.setForeground(tool.textLostFocusColor());
        ICTextField.setText("Enter IC number...");
        countryCode3.setForeground(tool.textLostFocusColor());
        countryCode3.setText("+6010...");
        phoneNumberTextField3.setForeground(tool.textLostFocusColor());
        phoneNumberTextField3.setText("1234567...");
        stayingUnitTextField.setForeground(tool.textLostFocusColor());
        stayingUnitTextField.setText("A-2-13...");
        emailTextFieldResident.setForeground(tool.textLostFocusColor());
        emailTextFieldResident.setText("Enter Email...");
        genderComboBox2.setSelectedItem("MALE");
        relationshipToOwnerComboBox.setSelectedItem("SELF");
    }
    
    private void setAllManagerGuardInformationToLostFocusState() {
        nameTextField1.setForeground(tool.textLostFocusColor());
        nameTextField1.setText("Enter name...");
        ICTextField.setForeground(tool.textLostFocusColor());
        ICTextField.setText("Enter IC number...");
        countryCode3.setForeground(tool.textLostFocusColor());
        countryCode3.setText("+6010...");
        phoneNumberTextField3.setForeground(tool.textLostFocusColor());
        phoneNumberTextField3.setText("1234567...");
        guardIDTextField.setForeground(tool.textLostFocusColor());
        guardIDTextField.setText("Enter Guard ID...");
        emailTextFieldResident.setForeground(tool.textLostFocusColor());
        emailTextFieldResident.setText("Enter Email...");
        genderComboBox2.setSelectedItem("MALE");
    }
    
    //Set all text fields to gain focus state
    private void setAllGuardTextFieldToGainedFocusState(){
        nameTextField.setForeground(tool.textGainedFocusColor());
        countryCode.setForeground(tool.textGainedFocusColor());
        phoneNumberTextField.setForeground(tool.textGainedFocusColor());
        visitUnitTextField.setForeground(tool.textGainedFocusColor());
    }
    
    private void setAllResidentTextFieldToGainedFocusState(){
        appearingProfileNameTextField.setForeground(tool.textGainedFocusColor());
        countryCode1.setForeground(tool.textGainedFocusColor());
        phoneNumberTextField1.setForeground(tool.textGainedFocusColor());
        emailTextField.setForeground(tool.textGainedFocusColor());
    }
    
    private void setAllManagerResidentInformationToGainedFocusState(){
        nameTextField1.setForeground(tool.textGainedFocusColor());
        ICTextField.setForeground(tool.textGainedFocusColor());
        countryCode3.setForeground(tool.textGainedFocusColor());
        phoneNumberTextField3.setForeground(tool.textGainedFocusColor());
        stayingUnitTextField.setForeground(tool.textGainedFocusColor());
        emailTextFieldResident.setForeground(tool.textGainedFocusColor());
    }
    
    private void setAllManagerGuardInformationToGainedFocusState(){
        nameTextField1.setForeground(tool.textGainedFocusColor());
        ICTextField.setForeground(tool.textGainedFocusColor());
        countryCode3.setForeground(tool.textGainedFocusColor());
        phoneNumberTextField3.setForeground(tool.textGainedFocusColor());
        emailTextFieldResident.setForeground(tool.textGainedFocusColor());
        guardIDTextField.setForeground(tool.textGainedFocusColor());
    }
    
    //Validate confirm password field and password field is same or not
    private boolean validateConfirmPasswordManageAccounts(){
        if (passwordField1.getText().equals(passwordField.getText())){
            return true;
        }
        return false;
    }
    
    //Search functions
    private void searchResidentInformation(){
        DefaultTableModel model1 = (DefaultTableModel) accountWithInformationsTable.getModel();
        model1.setRowCount(0);
        
        if (searchByComboBox.getSelectedItem().toString().equals("Search by IC number")){
            this.manager.searchResidentInformations(accountWithInformationsTable, 
                    ("resident.residentID like \"%" + searchBarTextField.getText().trim() + 
                     "\" or resident.residentID like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or resident.residentID like \"" + searchBarTextField.getText().trim() + "%\""));
            
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by name")) {
            this.manager.searchResidentInformations(accountWithInformationsTable, 
                    ("resident.residentName like \"%" + searchBarTextField.getText().trim() + 
                     "\" or resident.residentName like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or resident.residentName like \"" + searchBarTextField.getText().trim() + "%\""));
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by contact number")) {
            this.manager.searchResidentInformations(accountWithInformationsTable, 
                    ("resident.phoneNumber like \"%" + searchBarTextField.getText().trim() + 
                     "\" or resident.phoneNumber like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or resident.phoneNumber like \"" + searchBarTextField.getText().trim() + "%\""));
        }
    }
    
    private void searchGuardInformation(){
        DefaultTableModel model1 = (DefaultTableModel) accountWithInformationsTable.getModel();
        model1.setRowCount(0);
        
        if (searchByComboBox.getSelectedItem().toString().equals("Search by IC number")){
            this.manager.searchGuardInformations(accountWithInformationsGuardTable, 
                    ("guard.guardIC like \"%" + searchBarTextField.getText().trim() + 
                     "\" or guard.guardIC like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or guard.guardIC like \"" + searchBarTextField.getText().trim() + "%\""));
            
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by name")) {
            this.manager.searchResidentInformations(accountWithInformationsGuardTable, 
                    ("guard.name like \"%" + searchBarTextField.getText().trim() + 
                     "\" or guard.name like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or guard.name like \"" + searchBarTextField.getText().trim() + "%\""));
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by contact number")) {
            this.manager.searchResidentInformations(accountWithInformationsGuardTable, 
                    ("guard.contactNo like \"%" + searchBarTextField.getText().trim() + 
                     "\" or guard.contactNo like \"%" + searchBarTextField.getText().trim() + 
                     "%\" or guard.contactNo like \"" + searchBarTextField.getText().trim() + "%\""));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sharedPanel = new javax.swing.JPanel();
        pathLabel = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        welcomeTextLabel = new javax.swing.JLabel();
        mainHomePage = new javax.swing.JLayeredPane();
        sideNavBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        homePanelGuard = new javax.swing.JPanel();
        homeLabel1 = new javax.swing.JLabel();
        apartmentManagerNavBar = new javax.swing.JPanel();
        manageAccountsPanel = new javax.swing.JPanel();
        manageAccountsLabel = new javax.swing.JLabel();
        informationPanel = new javax.swing.JPanel();
        informationLabel = new javax.swing.JLabel();
        reportPanel = new javax.swing.JPanel();
        reportLabel = new javax.swing.JLabel();
        reminderPanel = new javax.swing.JPanel();
        reminderLabel = new javax.swing.JLabel();
        residentNavBar = new javax.swing.JPanel();
        payBillsPanel = new javax.swing.JPanel();
        payBillsLabel = new javax.swing.JLabel();
        forumPanel = new javax.swing.JPanel();
        forumLabel = new javax.swing.JLabel();
        manageProfilePanel = new javax.swing.JPanel();
        forumLabel1 = new javax.swing.JLabel();
        guardNavBar = new javax.swing.JPanel();
        checkInOutPanel = new javax.swing.JPanel();
        checkInOutLabel = new javax.swing.JLabel();
        logOutPanel = new javax.swing.JPanel();
        logOutLabel = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        exitLabel = new javax.swing.JLabel();
        apartmentManagerPanel = new javax.swing.JPanel();
        screenApartmentManager = new javax.swing.JLayeredPane();
        manageAccountsScreenApartmentManager = new javax.swing.JPanel();
        accountDetailsPanel = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        usernameAccountTextField = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        roleComboBox = new javax.swing.JComboBox<>();
        passwordField = new javax.swing.JPasswordField();
        passwordField1 = new javax.swing.JPasswordField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        residentAccountsTable = new javax.swing.JTable();
        jScrollPane17 = new javax.swing.JScrollPane();
        guardAccountsTable = new javax.swing.JTable();
        jScrollPane18 = new javax.swing.JScrollPane();
        accountPendingToAddInformationTable = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        overallAccountsTable = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        reminderScreenApartmentManager = new javax.swing.JPanel();
        accountDetailsPanel1 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        reminderTypeComboBox = new javax.swing.JComboBox<>();
        jScrollPane19 = new javax.swing.JScrollPane();
        contentTextArea = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        reminderHistoryTable = new javax.swing.JTable();
        jLabel66 = new javax.swing.JLabel();
        informationsScreenApartmentManager = new javax.swing.JPanel();
        informationsDetailsPanel = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        nameTextField1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        ICTextField = new javax.swing.JTextField();
        countryCode3 = new javax.swing.JTextField();
        phoneNumberTextField3 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        stayingUnitTextField = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        emailTextFieldResident = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        relationshipToOwnerComboBox = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        genderComboBox2 = new javax.swing.JComboBox<>();
        guardIDTextField = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        accountWithInformationsTable = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        accountPendingToAddInformationsTable = new javax.swing.JTable();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        accountWithInformationsGuardTable = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        accountPendingToAddInformationsGuardTable = new javax.swing.JTable();
        selectedAccountUsername = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        searchBarTextField = new javax.swing.JTextField();
        searchButton1 = new javax.swing.JButton();
        searchByComboBox = new javax.swing.JComboBox<>();
        selectedAccountUsername1 = new javax.swing.JLabel();
        reportScreenApartmentManager = new javax.swing.JPanel();
        visitorReport = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        visitorReportTab = new javax.swing.JScrollPane();
        overallReportTable = new javax.swing.JTable();
        paymentReportTab = new javax.swing.JScrollPane();
        mostVisitedTable = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        mostOvernightsTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        mostVisitedUnitsTable = new javax.swing.JTable();
        guardReport = new javax.swing.JPanel();
        JTabbedPane = new javax.swing.JTabbedPane();
        visitorReportTab2 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        reportsPanel = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        yearToComboBox = new javax.swing.JComboBox<>();
        monthToComboBox = new javax.swing.JComboBox<>();
        yearFromComboBox = new javax.swing.JComboBox<>();
        monthFromComboBox = new javax.swing.JComboBox<>();
        reportTableLabel = new javax.swing.JLabel();
        residentReport = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        visitorReportTab1 = new javax.swing.JScrollPane();
        overallBillRecordTable = new javax.swing.JTable();
        paymentReportTab1 = new javax.swing.JScrollPane();
        paidRecordTable = new javax.swing.JTable();
        paymentReportTab2 = new javax.swing.JScrollPane();
        unpaidRecordTable = new javax.swing.JTable();
        residentPanel = new javax.swing.JPanel();
        screenReisdent = new javax.swing.JLayeredPane();
        homeScreenResident = new javax.swing.JPanel();
        heyLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        forumScreen = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        forumChatScreen = new javax.swing.JTextArea();
        payBillsScreen = new javax.swing.JPanel();
        billDetailsPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        issuePaidDateLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        billMonth = new javax.swing.JLabel();
        billIDLabel = new javax.swing.JLabel();
        issueDateLabel = new javax.swing.JLabel();
        billAmountLabel = new javax.swing.JLabel();
        billTypeLabel = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        expiredDateLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        unpaidPanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        unpaidBillsTable = new javax.swing.JTable();
        paidPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        paidBillsTable = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        manageProfileScreenResident = new javax.swing.JPanel();
        billDetailsPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        issuePaidDateLabel1 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        residentRealNameLabel2 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        oldPinField = new javax.swing.JPasswordField();
        emailTextField = new javax.swing.JTextField();
        genderComboBox = new javax.swing.JComboBox<>();
        countryCode1 = new javax.swing.JTextField();
        phoneNumberTextField1 = new javax.swing.JTextField();
        appearingProfileNameTextField = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        newPinField = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        residentRealNameLabel = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        guardPanel = new javax.swing.JPanel();
        screenGuard = new javax.swing.JLayeredPane();
        checkInOutScreen = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        timeOutPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        timeOutTable = new javax.swing.JTable();
        visitorsCarsDetails = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        visitorsCarsDetailsTable = new javax.swing.JTable();
        historyPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        detailsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        visitUnitTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        carPlateTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        parkingLotTextField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        overnightDaysSpinner = new javax.swing.JSpinner();
        countryCode = new javax.swing.JTextField();
        phoneNumberTextField = new javax.swing.JTextField();
        parkingLotAsterisk = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        carPlateAsterisk = new javax.swing.JLabel();
        checkbox1 = new java.awt.Checkbox();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        timeOutLabel = new javax.swing.JLabel();
        timeInLabel = new javax.swing.JLabel();
        dateInLabel = new javax.swing.JLabel();
        dateOutLabel = new javax.swing.JLabel();
        homeScreenGuard = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        historyDataTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("AMS | Home Page");
        setMinimumSize(new java.awt.Dimension(1294, 727));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sharedPanel.setBackground(new java.awt.Color(0, 48, 73));
        sharedPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pathLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pathLabel.setForeground(new java.awt.Color(255, 255, 255));
        pathLabel.setText("Super Solution AMS | Home page");
        sharedPanel.add(pathLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 380, -1));

        time.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));
        time.setText("0");
        sharedPanel.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 50, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("0");
        sharedPanel.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 20, -1, -1));

        welcomeTextLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        welcomeTextLabel.setForeground(new java.awt.Color(255, 255, 255));
        welcomeTextLabel.setText("Welcome, ");
        sharedPanel.add(welcomeTextLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 550, -1));

        getContentPane().add(sharedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 80));

        mainHomePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideNavBar.setBackground(new java.awt.Color(252, 191, 73));
        sideNavBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Features");
        sideNavBar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 100, -1, -1));

        homePanelGuard.setBackground(new java.awt.Color(0, 48, 73));
        homePanelGuard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePanelGuardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homePanelGuardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homePanelGuardMouseExited(evt);
            }
        });
        homePanelGuard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        homeLabel1.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel1.setText("Home");
        homeLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeLabel1MouseExited(evt);
            }
        });
        homePanelGuard.add(homeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 15, 50, -1));

        sideNavBar.add(homePanelGuard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 160, 50));

        apartmentManagerNavBar.setBackground(new java.awt.Color(252, 191, 73));
        apartmentManagerNavBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        manageAccountsPanel.setBackground(new java.awt.Color(252, 191, 73));
        manageAccountsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageAccountsPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageAccountsPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageAccountsPanelMouseExited(evt);
            }
        });
        manageAccountsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        manageAccountsLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        manageAccountsLabel.setForeground(new java.awt.Color(255, 255, 255));
        manageAccountsLabel.setText("Manage Accounts");
        manageAccountsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageAccountsLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageAccountsLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageAccountsLabelMouseExited(evt);
            }
        });
        manageAccountsPanel.add(manageAccountsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 14, 140, -1));

        apartmentManagerNavBar.add(manageAccountsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 160, 50));

        informationPanel.setBackground(new java.awt.Color(252, 191, 73));
        informationPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                informationPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                informationPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                informationPanelMouseExited(evt);
            }
        });
        informationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        informationLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        informationLabel.setForeground(new java.awt.Color(255, 255, 255));
        informationLabel.setText("Informations");
        informationLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                informationLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                informationLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                informationLabelMouseExited(evt);
            }
        });
        informationPanel.add(informationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 14, 100, -1));

        apartmentManagerNavBar.add(informationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 50));

        reportPanel.setBackground(new java.awt.Color(252, 191, 73));
        reportPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportPanelMouseExited(evt);
            }
        });
        reportPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        reportLabel.setForeground(new java.awt.Color(255, 255, 255));
        reportLabel.setText("Reports");
        reportLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportLabelMouseExited(evt);
            }
        });
        reportPanel.add(reportLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 14, 100, -1));

        apartmentManagerNavBar.add(reportPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 50));

        reminderPanel.setBackground(new java.awt.Color(252, 191, 73));
        reminderPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reminderPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reminderPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reminderPanelMouseExited(evt);
            }
        });
        reminderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reminderLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        reminderLabel.setForeground(new java.awt.Color(255, 255, 255));
        reminderLabel.setText("Reminder");
        reminderLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reminderLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reminderLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reminderLabelMouseExited(evt);
            }
        });
        reminderPanel.add(reminderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 14, 100, -1));

        apartmentManagerNavBar.add(reminderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 160, 50));

        sideNavBar.add(apartmentManagerNavBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 470));

        residentNavBar.setBackground(new java.awt.Color(252, 191, 73));
        residentNavBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        payBillsPanel.setBackground(new java.awt.Color(252, 191, 73));
        payBillsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payBillsPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payBillsPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                payBillsPanelMouseExited(evt);
            }
        });
        payBillsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        payBillsLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        payBillsLabel.setForeground(new java.awt.Color(255, 255, 255));
        payBillsLabel.setText("Bills");
        payBillsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payBillsLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payBillsLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                payBillsLabelMouseExited(evt);
            }
        });
        payBillsPanel.add(payBillsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 100, -1));

        residentNavBar.add(payBillsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 50));

        forumPanel.setBackground(new java.awt.Color(252, 191, 73));
        forumPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forumPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forumPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                forumPanelMouseExited(evt);
            }
        });
        forumPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        forumLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        forumLabel.setForeground(new java.awt.Color(255, 255, 255));
        forumLabel.setText("Forum");
        forumLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forumLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forumLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                forumLabelMouseExited(evt);
            }
        });
        forumPanel.add(forumLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 100, -1));

        residentNavBar.add(forumPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 50));

        manageProfilePanel.setBackground(new java.awt.Color(252, 191, 73));
        manageProfilePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageProfilePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageProfilePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageProfilePanelMouseExited(evt);
            }
        });
        manageProfilePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        forumLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        forumLabel1.setForeground(new java.awt.Color(255, 255, 255));
        forumLabel1.setText("Manage profile");
        forumLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forumLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forumLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                forumLabel1MouseExited(evt);
            }
        });
        manageProfilePanel.add(forumLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 130, -1));

        residentNavBar.add(manageProfilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 252, 160, 50));

        sideNavBar.add(residentNavBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 470));

        guardNavBar.setBackground(new java.awt.Color(252, 191, 73));
        guardNavBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkInOutPanel.setBackground(new java.awt.Color(252, 191, 73));
        checkInOutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkInOutPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                checkInOutPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                checkInOutPanelMouseExited(evt);
            }
        });
        checkInOutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkInOutLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        checkInOutLabel.setForeground(new java.awt.Color(255, 255, 255));
        checkInOutLabel.setText("Check in/out");
        checkInOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkInOutLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                checkInOutLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                checkInOutLabelMouseExited(evt);
            }
        });
        checkInOutPanel.add(checkInOutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 100, -1));

        guardNavBar.add(checkInOutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 50));

        sideNavBar.add(guardNavBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 470));

        logOutPanel.setBackground(new java.awt.Color(255, 102, 0));
        logOutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOutPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOutPanelMouseExited(evt);
            }
        });
        logOutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logOutLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
        logOutLabel.setText("Log out");
        logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOutLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOutLabelMouseExited(evt);
            }
        });
        logOutPanel.add(logOutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 14, 60, -1));

        sideNavBar.add(logOutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 160, 50));

        exitPanel.setBackground(new java.awt.Color(214, 40, 40));
        exitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitPanelMouseExited(evt);
            }
        });
        exitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exitLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        exitLabel.setForeground(new java.awt.Color(255, 255, 255));
        exitLabel.setText("Exit");
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitLabelMouseExited(evt);
            }
        });
        exitPanel.add(exitLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 14, 30, -1));

        sideNavBar.add(exitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 160, 50));

        mainHomePage.add(sideNavBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 640));

        apartmentManagerPanel.setBackground(new java.awt.Color(247, 127, 0));
        apartmentManagerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        screenApartmentManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        manageAccountsScreenApartmentManager.setBackground(new java.awt.Color(247, 127, 0));
        manageAccountsScreenApartmentManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountDetailsPanel.setBackground(new java.awt.Color(234, 226, 183));
        accountDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel52.setText("Account Details");
        accountDetailsPanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 20, 180, 30));

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        accountDetailsPanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("Username");
        accountDetailsPanel.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, -1));

        usernameAccountTextField.setForeground(new java.awt.Color(153, 153, 153));
        usernameAccountTextField.setText("Enter Username...");
        usernameAccountTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameAccountTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameAccountTextFieldFocusLost(evt);
            }
        });
        accountDetailsPanel.add(usernameAccountTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 210, 30));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Password");
        accountDetailsPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 90, -1));

        jButton13.setBackground(new java.awt.Color(0, 51, 204));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Create Account");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        accountDetailsPanel.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 200, 40));

        jButton14.setBackground(new java.awt.Color(255, 204, 0));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Clear All");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        accountDetailsPanel.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 200, 40));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Confirm Password");
        accountDetailsPanel.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 140, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("Role");
        accountDetailsPanel.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 90, -1));

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE", "INACTIVE" }));
        accountDetailsPanel.add(statusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Status");
        accountDetailsPanel.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 90, -1));

        roleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RESIDENT", "GUARD" }));
        accountDetailsPanel.add(roleComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 140, 30));

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwordField.setForeground(new java.awt.Color(153, 153, 153));
        passwordField.setText("Enter Confirm Password...");
        passwordField.setEchoChar('\u0000');
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFieldFocusLost(evt);
            }
        });
        accountDetailsPanel.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 210, 30));

        passwordField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwordField1.setForeground(new java.awt.Color(153, 153, 153));
        passwordField1.setText("Enter Password...");
        passwordField1.setEchoChar('\u0000');
        passwordField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordField1FocusLost(evt);
            }
        });
        accountDetailsPanel.add(passwordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 210, 30));

        jCheckBox2.setText("Show Password");
        jCheckBox2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox2StateChanged(evt);
            }
        });
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        accountDetailsPanel.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, -1, -1));

        manageAccountsScreenApartmentManager.add(accountDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        residentAccountsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        residentAccountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Username", "Unit", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        residentAccountsTable.setRowHeight(30);
        jScrollPane16.setViewportView(residentAccountsTable);

        jTabbedPane8.addTab("Resident Accounts", jScrollPane16);

        guardAccountsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        guardAccountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Username", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        guardAccountsTable.setRowHeight(30);
        jScrollPane17.setViewportView(guardAccountsTable);

        jTabbedPane8.addTab("Guard Accounts", jScrollPane17);

        accountPendingToAddInformationTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountPendingToAddInformationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Role", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountPendingToAddInformationTable.setRowHeight(30);
        jScrollPane18.setViewportView(accountPendingToAddInformationTable);

        jTabbedPane8.addTab("Account Pending To Add Informations", jScrollPane18);

        overallAccountsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        overallAccountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Username", "Role", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        overallAccountsTable.setRowHeight(30);
        jScrollPane15.setViewportView(overallAccountsTable);

        jTabbedPane8.addTab("Overall Accounts", jScrollPane15);

        manageAccountsScreenApartmentManager.add(jTabbedPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 860, 560));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Accounts");
        manageAccountsScreenApartmentManager.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        screenApartmentManager.add(manageAccountsScreenApartmentManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        reminderScreenApartmentManager.setBackground(new java.awt.Color(247, 127, 0));
        reminderScreenApartmentManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountDetailsPanel1.setBackground(new java.awt.Color(234, 226, 183));
        accountDetailsPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel64.setText("Reminder Details");
        accountDetailsPanel1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 200, 30));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        accountDetailsPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Content");
        accountDetailsPanel1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jButton15.setBackground(new java.awt.Color(0, 51, 204));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Send Reminder");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        accountDetailsPanel1.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 200, 40));

        jButton16.setBackground(new java.awt.Color(255, 204, 0));
        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Clear All");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        accountDetailsPanel1.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 200, 40));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel68.setText("Types");
        accountDetailsPanel1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 90, -1));

        reminderTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ANNOUNCEMENT", "BILL", "ALERT", "OTHER" }));
        reminderTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reminderTypeComboBoxActionPerformed(evt);
            }
        });
        accountDetailsPanel1.add(reminderTypeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 140, 30));

        contentTextArea.setColumns(20);
        contentTextArea.setRows(5);
        contentTextArea.setText("     << ANNOUNCEMENT >>\n------------------------------------------");
        jScrollPane19.setViewportView(contentTextArea);

        accountDetailsPanel1.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 220, 250));

        reminderScreenApartmentManager.add(accountDetailsPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        reminderHistoryTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reminderHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Apartment Manager Name", "Type", "Content", "Send Time", "Send Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reminderHistoryTable.setRowHeight(30);
        reminderHistoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reminderHistoryTableMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(reminderHistoryTable);

        reminderScreenApartmentManager.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 860, 550));

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Reminder History");
        reminderScreenApartmentManager.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        screenApartmentManager.add(reminderScreenApartmentManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        informationsScreenApartmentManager.setBackground(new java.awt.Color(247, 127, 0));
        informationsScreenApartmentManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        informationsDetailsPanel.setBackground(new java.awt.Color(234, 226, 183));
        informationsDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel47.setText("Details");
        informationsDetailsPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 20, 80, 30));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        informationsDetailsPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setText("Name");
        informationsDetailsPanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, -1));

        nameTextField1.setForeground(new java.awt.Color(153, 153, 153));
        nameTextField1.setText("Enter name...");
        nameTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextField1FocusLost(evt);
            }
        });
        informationsDetailsPanel.add(nameTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 210, 30));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setText("IC number");
        informationsDetailsPanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 90, -1));

        jButton7.setBackground(new java.awt.Color(0, 51, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Update information");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        informationsDetailsPanel.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 200, 40));

        jButton12.setBackground(new java.awt.Color(255, 204, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Clear All");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        informationsDetailsPanel.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 200, 40));

        ICTextField.setForeground(new java.awt.Color(153, 153, 153));
        ICTextField.setText("Enter IC number...");
        ICTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ICTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ICTextFieldFocusLost(evt);
            }
        });
        informationsDetailsPanel.add(ICTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 210, 30));

        countryCode3.setForeground(new java.awt.Color(153, 153, 153));
        countryCode3.setText("+6010...");
        countryCode3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                countryCode3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                countryCode3FocusLost(evt);
            }
        });
        informationsDetailsPanel.add(countryCode3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 60, 30));

        phoneNumberTextField3.setForeground(new java.awt.Color(153, 153, 153));
        phoneNumberTextField3.setText("1234567...");
        phoneNumberTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneNumberTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneNumberTextField3FocusLost(evt);
            }
        });
        informationsDetailsPanel.add(phoneNumberTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 140, 30));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setText("Contact No.");
        informationsDetailsPanel.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 90, -1));

        stayingUnitTextField.setForeground(new java.awt.Color(153, 153, 153));
        stayingUnitTextField.setText("A-2-13...");
        stayingUnitTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stayingUnitTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stayingUnitTextFieldFocusLost(evt);
            }
        });
        informationsDetailsPanel.add(stayingUnitTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 210, 30));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("Relationship To Owner");
        informationsDetailsPanel.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 160, -1));

        emailTextFieldResident.setForeground(new java.awt.Color(153, 153, 153));
        emailTextFieldResident.setText("Enter Email...");
        emailTextFieldResident.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailTextFieldResidentFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailTextFieldResidentFocusLost(evt);
            }
        });
        informationsDetailsPanel.add(emailTextFieldResident, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 210, 30));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setText("Email");
        informationsDetailsPanel.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, 90, -1));

        relationshipToOwnerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELF", "FAMILY_MEMBER", "FRIEND" }));
        informationsDetailsPanel.add(relationshipToOwnerComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 140, 30));

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setText("Unit");
        informationsDetailsPanel.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 315, 90, -1));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setText("Gender");
        informationsDetailsPanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 90, -1));

        genderComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MALE\t", "FEMALE", "OTHER", "RATHER NOT SAY" }));
        informationsDetailsPanel.add(genderComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 140, 30));

        guardIDTextField.setForeground(new java.awt.Color(153, 153, 153));
        guardIDTextField.setText("Enter Guard ID...");
        guardIDTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                guardIDTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                guardIDTextFieldFocusLost(evt);
            }
        });
        guardIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardIDTextFieldActionPerformed(evt);
            }
        });
        informationsDetailsPanel.add(guardIDTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 210, 30));

        informationsScreenApartmentManager.add(informationsDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Informations");
        informationsScreenApartmentManager.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jTabbedPane6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTabbedPane6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane6StateChanged(evt);
            }
        });

        jTabbedPane5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane5StateChanged(evt);
            }
        });

        accountWithInformationsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountWithInformationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "IC Number", "Contact Number", "Email", "Gender", "Relationship To Owner", "Unit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountWithInformationsTable.setRowHeight(30);
        accountWithInformationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountWithInformationsTableMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(accountWithInformationsTable);

        jTabbedPane5.addTab("Account with informations", jScrollPane12);

        accountPendingToAddInformationsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountPendingToAddInformationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account Username"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountPendingToAddInformationsTable.setRowHeight(30);
        accountPendingToAddInformationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountPendingToAddInformationsTableMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(accountPendingToAddInformationsTable);

        jTabbedPane5.addTab("Account pending to add informations", jScrollPane14);

        jTabbedPane6.addTab("Resident's informations", jTabbedPane5);

        jTabbedPane7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane7StateChanged(evt);
            }
        });

        accountWithInformationsGuardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Guard ID", "Guard IC", "Contact Number", "Email", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountWithInformationsGuardTable.setRowHeight(30);
        accountWithInformationsGuardTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountWithInformationsGuardTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(accountWithInformationsGuardTable);

        jTabbedPane7.addTab("Account with informations", jScrollPane11);

        accountPendingToAddInformationsGuardTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountPendingToAddInformationsGuardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account Username"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountPendingToAddInformationsGuardTable.setRowHeight(30);
        accountPendingToAddInformationsGuardTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountPendingToAddInformationsGuardTableMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(accountPendingToAddInformationsGuardTable);

        jTabbedPane7.addTab("Account pending to add informations", jScrollPane13);

        jTabbedPane6.addTab("Guard's informations", jTabbedPane7);

        informationsScreenApartmentManager.add(jTabbedPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 860, 470));

        selectedAccountUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        selectedAccountUsername.setForeground(new java.awt.Color(255, 255, 255));
        selectedAccountUsername.setText("-");
        informationsScreenApartmentManager.add(selectedAccountUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 260, -1));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Search:");
        informationsScreenApartmentManager.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 555, -1, -1));

        searchBarTextField.setForeground(new java.awt.Color(153, 153, 153));
        searchBarTextField.setText("Enter IC Number...");
        searchBarTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBarTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchBarTextFieldFocusLost(evt);
            }
        });
        searchBarTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBarTextFieldKeyPressed(evt);
            }
        });
        informationsScreenApartmentManager.add(searchBarTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 560, 260, 30));

        searchButton1.setBackground(new java.awt.Color(51, 153, 0));
        searchButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchButton1.setForeground(new java.awt.Color(255, 255, 255));
        searchButton1.setText("Search");
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });
        informationsScreenApartmentManager.add(searchButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 560, 110, 30));

        searchByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Search by IC number", "Search by name", "Search by contact number" }));
        searchByComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchByComboBoxItemStateChanged(evt);
            }
        });
        informationsScreenApartmentManager.add(searchByComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 260, 30));

        selectedAccountUsername1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        selectedAccountUsername1.setForeground(new java.awt.Color(255, 255, 255));
        selectedAccountUsername1.setText("Selected Account Username: ");
        informationsScreenApartmentManager.add(selectedAccountUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 260, -1));

        screenApartmentManager.add(informationsScreenApartmentManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        reportScreenApartmentManager.setBackground(new java.awt.Color(247, 127, 0));
        reportScreenApartmentManager.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        visitorReport.setBackground(new java.awt.Color(247, 127, 0));
        visitorReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        overallReportTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        overallReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No", "Visit Unit", "Overnight Days", "Time In", "Time Out", "Date In", "Date Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        overallReportTable.setRowHeight(30);
        visitorReportTab.setViewportView(overallReportTable);

        jTabbedPane3.addTab("Overall report", visitorReportTab);

        mostVisitedTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mostVisitedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No.", "Number of visitation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mostVisitedTable.setRowHeight(30);
        paymentReportTab.setViewportView(mostVisitedTable);

        jTabbedPane3.addTab("Most visited", paymentReportTab);

        mostOvernightsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mostOvernightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No", "Overnight Days"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mostOvernightsTable.setRowHeight(30);
        jScrollPane9.setViewportView(mostOvernightsTable);

        jTabbedPane3.addTab("Most overnights", jScrollPane9);

        mostVisitedUnitsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mostVisitedUnitsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact", "Unit", "Number of times"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mostVisitedUnitsTable.setRowHeight(30);
        jScrollPane10.setViewportView(mostVisitedUnitsTable);

        jTabbedPane3.addTab("Most visited units", jScrollPane10);

        visitorReport.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 860, 540));

        reportScreenApartmentManager.add(visitorReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 880, 640));

        guardReport.setBackground(new java.awt.Color(247, 127, 0));
        guardReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attendanceTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Time In", "Time Out ", "Date In", "Date Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceTable.setRowHeight(30);
        visitorReportTab2.setViewportView(attendanceTable);

        JTabbedPane.addTab("Attandence Report", visitorReportTab2);

        guardReport.add(JTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 860, 540));

        reportScreenApartmentManager.add(guardReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 880, 640));

        reportsPanel.setBackground(new java.awt.Color(234, 226, 183));
        reportsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setText("Reports");
        reportsPanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 90, 30));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        reportsPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jButton8.setBackground(new java.awt.Color(0, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Residents");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        reportsPanel.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 220, 50));

        jButton9.setBackground(new java.awt.Color(255, 204, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Visitors");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        reportsPanel.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 220, 50));

        jButton10.setBackground(new java.awt.Color(0, 153, 153));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Apply");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        reportsPanel.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 220, 40));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 0));
        jLabel39.setText("To");
        reportsPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 0));
        jLabel41.setText("From");
        reportsPanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("Month");
        reportsPanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setText("Year");
        reportsPanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("Month");
        reportsPanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, -1, -1));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setText("Year");
        reportsPanel.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        jButton11.setBackground(new java.awt.Color(0, 0, 204));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Guard");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        reportsPanel.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 220, 50));

        yearToComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));
        reportsPanel.add(yearToComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 130, 30));

        monthToComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        reportsPanel.add(monthToComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 510, 130, 30));

        yearFromComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));
        reportsPanel.add(yearFromComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 130, 30));

        monthFromComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        reportsPanel.add(monthFromComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 130, 30));

        reportScreenApartmentManager.add(reportsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        reportTableLabel.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        reportTableLabel.setForeground(new java.awt.Color(255, 255, 255));
        reportTableLabel.setText("Vistior Report");
        reportScreenApartmentManager.add(reportTableLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 400, -1));

        residentReport.setBackground(new java.awt.Color(247, 127, 0));
        residentReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        overallBillRecordTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        overallBillRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner Name", "Unit", "Bill Type", "Status", "Issue Date", "Pay Date", "Expired Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        overallBillRecordTable.setRowHeight(30);
        visitorReportTab1.setViewportView(overallBillRecordTable);

        jTabbedPane4.addTab("Overall Record", visitorReportTab1);

        paidRecordTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        paidRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner Name", "Unit", "Bill Type", "Status", "Issue Date", "Pay Date", "Expired Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        paidRecordTable.setRowHeight(30);
        paymentReportTab1.setViewportView(paidRecordTable);

        jTabbedPane4.addTab("Paid Record", paymentReportTab1);

        unpaidRecordTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        unpaidRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner Name", "Unit", "Bill Type", "Status", "Issue Date", "Expired Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unpaidRecordTable.setRowHeight(30);
        paymentReportTab2.setViewportView(unpaidRecordTable);

        jTabbedPane4.addTab("Unpaid Record", paymentReportTab2);

        residentReport.add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 860, 540));

        reportScreenApartmentManager.add(residentReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 880, 640));

        screenApartmentManager.add(reportScreenApartmentManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        apartmentManagerPanel.add(screenApartmentManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        mainHomePage.add(apartmentManagerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 1120, 640));

        residentPanel.setBackground(new java.awt.Color(247, 127, 0));
        residentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        screenReisdent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeScreenResident.setBackground(new java.awt.Color(247, 127, 0));
        homeScreenResident.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        heyLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        heyLabel.setForeground(new java.awt.Color(255, 255, 255));
        heyLabel.setText("Hey, ");
        homeScreenResident.add(heyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 780, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("My Notification ");
        homeScreenResident.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 290, -1));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 71, 71));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setFocusable(false);
        jScrollPane8.setViewportView(jTextArea1);

        homeScreenResident.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 980, 320));

        screenReisdent.add(homeScreenResident, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        forumScreen.setBackground(new java.awt.Color(51, 51, 51));
        forumScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textArea.setBackground(new java.awt.Color(102, 102, 102));
        textArea.setColumns(20);
        textArea.setForeground(new java.awt.Color(255, 255, 255));
        textArea.setRows(5);
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textAreaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(textArea);

        forumScreen.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 1020, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Forum");
        forumScreen.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jButton1.setBackground(new java.awt.Color(51, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        forumScreen.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 570, 70, 40));

        forumChatScreen.setBackground(new java.awt.Color(51, 51, 51));
        forumChatScreen.setColumns(20);
        forumChatScreen.setForeground(new java.awt.Color(255, 255, 255));
        forumChatScreen.setRows(5);
        forumChatScreen.setFocusable(false);
        jScrollPane6.setViewportView(forumChatScreen);

        forumScreen.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1100, 490));

        screenReisdent.add(forumScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        payBillsScreen.setBackground(new java.awt.Color(247, 127, 0));
        payBillsScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        billDetailsPanel.setBackground(new java.awt.Color(234, 226, 183));
        billDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("Bill Details");
        billDetailsPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 20, 130, 30));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        billDetailsPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 255));
        jLabel11.setText("ID");
        billDetailsPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 50, -1));

        issuePaidDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        issuePaidDateLabel.setForeground(new java.awt.Color(0, 102, 255));
        issuePaidDateLabel.setText("Issue Date/Pay Date");
        billDetailsPanel.add(issuePaidDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 190, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 255));
        jLabel15.setText("Type");
        billDetailsPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 110, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 255));
        jLabel19.setText("For Month");
        billDetailsPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 120, -1));

        jButton4.setBackground(new java.awt.Color(0, 51, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Pay Bill");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        billDetailsPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 200, 40));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 255));
        jLabel20.setText("Amount");
        billDetailsPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 120, -1));

        billMonth.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billMonth.setText("-");
        billDetailsPanel.add(billMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 220, -1));

        billIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billIDLabel.setText("-");
        billDetailsPanel.add(billIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 190, -1));

        issueDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        issueDateLabel.setText("-");
        billDetailsPanel.add(issueDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 220, -1));

        billAmountLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billAmountLabel.setText("-");
        billDetailsPanel.add(billAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 220, -1));

        billTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billTypeLabel.setText("-");
        billDetailsPanel.add(billTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 220, -1));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 102, 255));
        jLabel46.setText("Expired Date");
        billDetailsPanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 120, -1));

        expiredDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        expiredDateLabel.setText("-");
        billDetailsPanel.add(expiredDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 220, -1));

        payBillsScreen.add(billDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        unpaidPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        unpaidBillsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill ID", "Issue Date", "Expired Date", "Amount", "Type", "For Month"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unpaidBillsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unpaidBillsTableMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(unpaidBillsTable);

        unpaidPanel.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        jTabbedPane1.addTab("Unpaid", unpaidPanel);

        paidPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paidBillsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill ID", "Pay Date", "Expired Date", "Amount", "Type", "For Month"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        paidBillsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paidBillsTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(paidBillsTable);

        paidPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        jTabbedPane1.addTab("Paid", paidPanel);

        payBillsScreen.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 840, 540));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Bills");
        payBillsScreen.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        screenReisdent.add(payBillsScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        manageProfileScreenResident.setBackground(new java.awt.Color(247, 127, 0));
        manageProfileScreenResident.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        billDetailsPanel1.setBackground(new java.awt.Color(234, 226, 183));
        billDetailsPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setText("Set Details");
        billDetailsPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 20, 130, 30));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        billDetailsPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 255));
        jLabel22.setText("Name");
        billDetailsPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 50, -1));

        issuePaidDateLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        issuePaidDateLabel1.setForeground(new java.awt.Color(0, 102, 255));
        issuePaidDateLabel1.setText("Appearing/Profile Name");
        billDetailsPanel1.add(issuePaidDateLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 220, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 255));
        jLabel23.setText("Contact No.");
        billDetailsPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 110, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 255));
        jLabel25.setText("Gender");
        billDetailsPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 120, -1));

        jButton5.setBackground(new java.awt.Color(255, 204, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Clear All");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        billDetailsPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 220, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 102, 255));
        jLabel26.setText("Old Profile Pin");
        billDetailsPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 170, -1));

        residentRealNameLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        residentRealNameLabel2.setText("-");
        billDetailsPanel1.add(residentRealNameLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 210, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 255));
        jLabel27.setText("Email");
        billDetailsPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 120, -1));

        oldPinField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        oldPinField.setForeground(new java.awt.Color(153, 153, 153));
        oldPinField.setText("Enter Pin...");
        oldPinField.setEchoChar('\u0000');
        oldPinField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                oldPinFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                oldPinFieldFocusLost(evt);
            }
        });
        oldPinField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldPinFieldKeyPressed(evt);
            }
        });
        billDetailsPanel1.add(oldPinField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 200, 30));

        emailTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        emailTextField.setForeground(new java.awt.Color(153, 153, 153));
        emailTextField.setText("Enter Email...");
        emailTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailTextFieldFocusLost(evt);
            }
        });
        billDetailsPanel1.add(emailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 200, 30));

        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MALE\t", "FEMALE", "OTHER", "RATHER NOT SAY" }));
        billDetailsPanel1.add(genderComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 110, 30));

        countryCode1.setForeground(new java.awt.Color(153, 153, 153));
        countryCode1.setText("+6010");
        countryCode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                countryCode1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                countryCode1FocusLost(evt);
            }
        });
        billDetailsPanel1.add(countryCode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 60, 30));

        phoneNumberTextField1.setForeground(new java.awt.Color(153, 153, 153));
        phoneNumberTextField1.setText("1234567");
        phoneNumberTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneNumberTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneNumberTextField1FocusLost(evt);
            }
        });
        billDetailsPanel1.add(phoneNumberTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 140, 30));

        appearingProfileNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        appearingProfileNameTextField.setForeground(new java.awt.Color(153, 153, 153));
        appearingProfileNameTextField.setText("Enter Appearing/Profile Name...");
        appearingProfileNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                appearingProfileNameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                appearingProfileNameTextFieldFocusLost(evt);
            }
        });
        billDetailsPanel1.add(appearingProfileNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 200, 30));

        jButton6.setBackground(new java.awt.Color(0, 51, 204));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Update Details");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        billDetailsPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 220, 40));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 255));
        jLabel28.setText("New Profile Pin");
        billDetailsPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 170, -1));

        newPinField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newPinField.setForeground(new java.awt.Color(153, 153, 153));
        newPinField.setText("Enter Pin...");
        newPinField.setEchoChar('\u0000');
        newPinField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                newPinFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                newPinFieldFocusLost(evt);
            }
        });
        billDetailsPanel1.add(newPinField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 200, 30));

        jCheckBox1.setText("show password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        billDetailsPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        manageProfileScreenResident.add(billDetailsPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        residentRealNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        residentRealNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        residentRealNameLabel.setText("Name");
        manageProfileScreenResident.add(residentRealNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 710, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Your Details");
        manageProfileScreenResident.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 220, -1));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 255));
        jLabel30.setText("Name");
        manageProfileScreenResident.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        jLabel31.setBackground(new java.awt.Color(0, 102, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Appearing/Profile name");
        manageProfileScreenResident.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, 570, -1));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 102, 255));
        jLabel32.setText("Appearing/Profile name");
        manageProfileScreenResident.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, -1, -1));

        jLabel33.setBackground(new java.awt.Color(0, 102, 255));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Contact No.");
        manageProfileScreenResident.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 570, -1));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 102, 255));
        jLabel34.setText("Contact No.");
        manageProfileScreenResident.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, -1, -1));

        jLabel35.setBackground(new java.awt.Color(0, 102, 255));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Email");
        manageProfileScreenResident.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 570, -1));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 102, 255));
        jLabel36.setText("Email");
        manageProfileScreenResident.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, -1, -1));

        jLabel37.setBackground(new java.awt.Color(0, 102, 255));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Gender");
        manageProfileScreenResident.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 530, 570, -1));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 102, 255));
        jLabel38.setText("Gender");
        manageProfileScreenResident.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, -1, -1));

        screenReisdent.add(manageProfileScreenResident, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        residentPanel.add(screenReisdent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        mainHomePage.add(residentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 1120, 640));

        guardPanel.setBackground(new java.awt.Color(247, 127, 0));
        guardPanel.setMinimumSize(new java.awt.Dimension(1280, 720));
        guardPanel.setPreferredSize(new java.awt.Dimension(1280, 720));
        guardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        screenGuard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkInOutScreen.setBackground(new java.awt.Color(247, 127, 0));
        checkInOutScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        timeOutTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timeOutTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No", "Visit Unit", "Time In", "Date In", "Overnight Days", "Has Car"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        timeOutTable.setRowHeight(30);
        timeOutTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timeOutTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(timeOutTable);

        javax.swing.GroupLayout timeOutPanelLayout = new javax.swing.GroupLayout(timeOutPanel);
        timeOutPanel.setLayout(timeOutPanelLayout);
        timeOutPanelLayout.setHorizontalGroup(
            timeOutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        timeOutPanelLayout.setVerticalGroup(
            timeOutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timeOutPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Time out", timeOutPanel);

        visitorsCarsDetailsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        visitorsCarsDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No.", "Car Plate", "Parking Lot"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        visitorsCarsDetailsTable.setRowHeight(30);
        visitorsCarsDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visitorsCarsDetailsTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(visitorsCarsDetailsTable);

        javax.swing.GroupLayout visitorsCarsDetailsLayout = new javax.swing.GroupLayout(visitorsCarsDetails);
        visitorsCarsDetails.setLayout(visitorsCarsDetailsLayout);
        visitorsCarsDetailsLayout.setHorizontalGroup(
            visitorsCarsDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        visitorsCarsDetailsLayout.setVerticalGroup(
            visitorsCarsDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visitorsCarsDetailsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Visitors Cars Details", visitorsCarsDetails);

        historyPanel.setMinimumSize(new java.awt.Dimension(860, 389));

        historyTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No.", "Visit Unit", "Overnight Days", "Time In", "Time Out", "Date In", "Date Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historyTable.setRowHeight(30);
        historyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(historyTable);

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("History", historyPanel);

        checkInOutScreen.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 860, 420));

        detailsPanel.setBackground(new java.awt.Color(234, 226, 183));
        detailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Details");
        detailsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 20, 80, 30));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        detailsPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 240, 3));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Name");
        detailsPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, -1));

        nameTextField.setForeground(new java.awt.Color(153, 153, 153));
        nameTextField.setText("Enter name...");
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });
        detailsPanel.add(nameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 210, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Contact No.");
        detailsPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 90, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Visit Unit");
        detailsPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 90, -1));

        visitUnitTextField.setForeground(new java.awt.Color(153, 153, 153));
        visitUnitTextField.setText("A-2-13...");
        visitUnitTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                visitUnitTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                visitUnitTextFieldFocusLost(evt);
            }
        });
        detailsPanel.add(visitUnitTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 210, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Car plate");
        detailsPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 90, -1));

        carPlateTextField.setForeground(new java.awt.Color(153, 153, 153));
        carPlateTextField.setText("-");
        carPlateTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                carPlateTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                carPlateTextFieldFocusLost(evt);
            }
        });
        carPlateTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                carPlateTextFieldKeyTyped(evt);
            }
        });
        detailsPanel.add(carPlateTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 210, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Parking lot");
        detailsPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 90, -1));

        parkingLotTextField.setForeground(new java.awt.Color(153, 153, 153));
        parkingLotTextField.setText("-");
        parkingLotTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                parkingLotTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                parkingLotTextFieldFocusLost(evt);
            }
        });
        parkingLotTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                parkingLotTextFieldKeyTyped(evt);
            }
        });
        detailsPanel.add(parkingLotTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 210, 30));

        jButton2.setBackground(new java.awt.Color(0, 51, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Check In/Out");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        detailsPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 200, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Overnight days");
        detailsPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 120, -1));
        detailsPanel.add(overnightDaysSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 285, 70, 30));

        countryCode.setForeground(new java.awt.Color(153, 153, 153));
        countryCode.setText("+6010");
        countryCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                countryCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                countryCodeFocusLost(evt);
            }
        });
        detailsPanel.add(countryCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, 30));

        phoneNumberTextField.setForeground(new java.awt.Color(153, 153, 153));
        phoneNumberTextField.setText("1234567");
        phoneNumberTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneNumberTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneNumberTextFieldFocusLost(evt);
            }
        });
        detailsPanel.add(phoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 140, 30));

        parkingLotAsterisk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        parkingLotAsterisk.setForeground(new java.awt.Color(255, 51, 0));
        parkingLotAsterisk.setText("*");
        detailsPanel.add(parkingLotAsterisk, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 420, 40, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 0));
        jLabel16.setText("*");
        detailsPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 70, 30, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 0));
        jLabel17.setText("*");
        detailsPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 30, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 0));
        jLabel18.setText("*");
        detailsPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 190, 50, 30));

        carPlateAsterisk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        carPlateAsterisk.setForeground(new java.awt.Color(255, 51, 0));
        carPlateAsterisk.setText("*");
        detailsPanel.add(carPlateAsterisk, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 355, 30, 30));

        checkbox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        checkbox1.setLabel("  Parking space needed");
        checkbox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkbox1MouseClicked(evt);
            }
        });
        detailsPanel.add(checkbox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 150, -1));

        jButton3.setBackground(new java.awt.Color(255, 204, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        detailsPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 200, 40));

        checkInOutScreen.add(detailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 640));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time In/Out");
        checkInOutScreen.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        timeOutLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        timeOutLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeOutLabel.setText("Time out:");
        checkInOutScreen.add(timeOutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 260, 20));

        timeInLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        timeInLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeInLabel.setText("Time in:");
        checkInOutScreen.add(timeInLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 500, 240, -1));

        dateInLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dateInLabel.setForeground(new java.awt.Color(255, 255, 255));
        dateInLabel.setText("Date in:");
        checkInOutScreen.add(dateInLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 560, 250, -1));

        dateOutLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dateOutLabel.setForeground(new java.awt.Color(255, 255, 255));
        dateOutLabel.setText("Date out:");
        checkInOutScreen.add(dateOutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 590, 270, -1));

        screenGuard.add(checkInOutScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        homeScreenGuard.setBackground(new java.awt.Color(247, 127, 0));
        homeScreenGuard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        historyDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact No.", "Visit Unit", "Overnight Days", "Time In", "Time Out", "Date In", "Date Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(historyDataTable);

        homeScreenGuard.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 1060, 520));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("History data");
        homeScreenGuard.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

        screenGuard.add(homeScreenGuard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 640));

        guardPanel.add(screenGuard, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 1120, 640));

        mainHomePage.add(guardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 640));

        getContentPane().add(mainHomePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1280, 640));

        setSize(new java.awt.Dimension(1296, 757));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained
        if(nameTextField.getText().trim().equals("Enter name...")){
            nameTextField.setText("");
            nameTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        if (nameTextField.getText().trim().equals("")){
            nameTextField.setText("Enter name...");
            nameTextField.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void visitUnitTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_visitUnitTextFieldFocusGained
        if(visitUnitTextField.getText().trim().equals("A-2-13...")){
            visitUnitTextField.setText("");
            visitUnitTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_visitUnitTextFieldFocusGained

    private void visitUnitTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_visitUnitTextFieldFocusLost
        if(visitUnitTextField.getText().trim().equals("")){
            visitUnitTextField.setText("A-2-13...");
            visitUnitTextField.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_visitUnitTextFieldFocusLost

    private void carPlateTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carPlateTextFieldFocusGained
        if (!checkbox1.isEnabled()){
            tool.getErrorMessageOptionPane("No parking space needed for this entry!");
            return;
        }
        
        if(carPlateTextField.getText().trim().equals("WWW 9999...")){
            carPlateTextField.setText("");
            carPlateTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_carPlateTextFieldFocusGained

    private void carPlateTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carPlateTextFieldFocusLost
        if(carPlateTextField.getText().trim().equals("")){
            carPlateTextField.setText("WWW 9999...");
            carPlateTextField.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_carPlateTextFieldFocusLost

    private void parkingLotTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parkingLotTextFieldFocusGained
        if (!checkbox1.isEnabled()){
            tool.getErrorMessageOptionPane("No parking space needed for this entry!");
            return;
        }
        
        if(parkingLotTextField.getText().trim().equals("A1...")){
            parkingLotTextField.setText("");
            parkingLotTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_parkingLotTextFieldFocusGained

    private void parkingLotTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parkingLotTextFieldFocusLost
        if(parkingLotTextField.getText().trim().equals("")){
            parkingLotTextField.setForeground(tool.textLostFocusColor());
            parkingLotTextField.setText("A1...");
        }
    }//GEN-LAST:event_parkingLotTextFieldFocusLost

    private void homeLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel1MouseClicked
        if (this.account.getRole().equals("GUARD")){
            pathLabel.setText("Super Solution AMS | Home page");
            resetAllGuardScreenScreen();
            homeScreenGuard.setVisible(true);
            clearAllGuardTable();
            guard.showHomeHistoryTable(historyDataTable);
            return;
        } 
        
        if (this.account.getRole().equals("APARTMENT_MANAGER")){
            pathLabel.setText("Super Solution AMS | Home page");
            resetAllManagerScreen();
            return;
        }
        
        pathLabel.setText("Super Solution AMS | Home page");
        resetAllResidentScreen();
        homeScreenResident.setVisible(true);
        jTextArea1.setText("");
        this.resident.showNotification(jTextArea1);
        heyLabel.setText("Hey, " + rAccount.getUsingProfile().getAppearingName()  + "!");
    }//GEN-LAST:event_homeLabel1MouseClicked

    private void homeLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel1MouseEntered
        homePanelGuard.setBackground(tool.mouseEnterColorHome());
    }//GEN-LAST:event_homeLabel1MouseEntered

    private void homeLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel1MouseExited
        homePanelGuard.setBackground(tool.mouseExitColorHome());
    }//GEN-LAST:event_homeLabel1MouseExited

    private void homePanelGuardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelGuardMouseClicked
        if (this.account.getRole().equals("GUARD")){
            pathLabel.setText("Super Solution AMS | Home page");
            resetAllGuardScreenScreen();
            homeScreenGuard.setVisible(true);
            clearAllGuardTable();
            guard.showHomeHistoryTable(historyDataTable);
            return;
        } 
        
        if (this.account.getRole().equals("APARTMENT_MANAGER")){
            pathLabel.setText("Super Solution AMS | Home page");
            resetAllManagerScreen();
            return;
        }
        
        pathLabel.setText("Super Solution AMS | Home page");
        resetAllResidentScreen();
        homeScreenResident.setVisible(true);
        jTextArea1.setText("");
        this.resident.showNotification(jTextArea1);
        heyLabel.setText("Hey, " + rAccount.getUsingProfile().getAppearingName()  + "!");
    }//GEN-LAST:event_homePanelGuardMouseClicked

    private void homePanelGuardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelGuardMouseEntered
        homePanelGuard.setBackground(tool.mouseEnterColorHome());
    }//GEN-LAST:event_homePanelGuardMouseEntered

    private void homePanelGuardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelGuardMouseExited
        homePanelGuard.setBackground(tool.mouseExitColorHome());
    }//GEN-LAST:event_homePanelGuardMouseExited

    private void checkInOutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutLabelMouseClicked
        resetAllGuardScreenScreen();
        pathLabel.setText("Super Solution AMS | Check in/out");
        checkInOutScreen.setVisible(true);
        clearAllGuardTable();
        guard.showHistoryTable(historyTable);
        guard.showVisitorsCarsDetailsTable(visitorsCarsDetailsTable);
        guard.showTimeOutTable(timeOutTable);
    }//GEN-LAST:event_checkInOutLabelMouseClicked

    private void checkInOutLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutLabelMouseEntered
        checkInOutPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_checkInOutLabelMouseEntered

    private void checkInOutLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutLabelMouseExited
        checkInOutPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_checkInOutLabelMouseExited

    private void checkInOutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutPanelMouseClicked
        resetAllGuardScreenScreen();
        pathLabel.setText("Super Solution AMS | Check in/out");
        checkInOutScreen.setVisible(true);
        clearAllGuardTable();
        guard.showHistoryTable(historyTable);
        guard.showVisitorsCarsDetailsTable(visitorsCarsDetailsTable);
        guard.showTimeOutTable(timeOutTable);
    }//GEN-LAST:event_checkInOutPanelMouseClicked

    private void checkInOutPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutPanelMouseEntered
        checkInOutPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_checkInOutPanelMouseEntered

    private void checkInOutPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInOutPanelMouseExited
        checkInOutPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_checkInOutPanelMouseExited

    private void checkbox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkbox1MouseClicked
        parkingLotTextField.setForeground(tool.textLostFocusColor());
        carPlateTextField.setForeground(tool.textLostFocusColor()); 
        if (checkBoxGuardClicked == 0){
            parkingLotTextField.setText("A1...");
            carPlateTextField.setText("WWW 9999...");
            parkingLotAsterisk.setVisible(true);
            carPlateAsterisk.setVisible(true);
        } else {
            parkingLotAsterisk.setVisible(false);
            carPlateAsterisk.setVisible(false);
            parkingLotTextField.setText("-");
            carPlateTextField.setText("-");
        }
        checkBoxGuardClicked++;
        if (checkBoxGuardClicked >= 2){
            checkBoxGuardClicked = 0;
        }
    }//GEN-LAST:event_checkbox1MouseClicked

    private void carPlateTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carPlateTextFieldKeyTyped
        if(checkBoxGuardClicked == 0){
            tool.getErrorMessageOptionPane("This entry doesn't need a parking lot.");
            evt.consume();
        }
    }//GEN-LAST:event_carPlateTextFieldKeyTyped

    private void parkingLotTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parkingLotTextFieldKeyTyped
        if(checkBoxGuardClicked == 0){
            tool.getErrorMessageOptionPane("This entry doesn't need a parking lot.");
            evt.consume();
        }
    }//GEN-LAST:event_parkingLotTextFieldKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (allFieldIsNullGuard()){
            tool.getErrorMessageOptionPane("Mandatory fields are requierd!");
            return;
        }
        
        this.visitor = getVisitorInfo();
        if (!this.visitor.hasCheckedIn()){
            guard.insertVisitorTimeIn(getVisitorInfo());
            setAllGuardToLostFocusState();
            clearAllGuardTable();
            guard.showHistoryTable(historyTable);
            guard.showVisitorsCarsDetailsTable(visitorsCarsDetailsTable);
            guard.showTimeOutTable(timeOutTable);
        } else {
            guard.insertVisitorTimeOut(getVisitorInfo());
            setAllGuardToLostFocusState();
            clearAllGuardTable();
            guard.showHistoryTable(historyTable);
            guard.showVisitorsCarsDetailsTable(visitorsCarsDetailsTable);
            guard.showTimeOutTable(timeOutTable);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void countryCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCodeFocusGained
        countryCode.setForeground(tool.textGainedFocusColor());
        if (countryCode.getText().trim().equals("+6010")){
            countryCode.setText("");
        }
    }//GEN-LAST:event_countryCodeFocusGained

    private void countryCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCodeFocusLost
        if (countryCode.getText().trim().equals("")){
            countryCode.setForeground(tool.textLostFocusColor());
            countryCode.setText("+6010");
        }
    }//GEN-LAST:event_countryCodeFocusLost

    private void phoneNumberTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextFieldFocusGained
        phoneNumberTextField.setForeground(tool.textGainedFocusColor());
        if (phoneNumberTextField.getText().trim().equals("1234567")){
            phoneNumberTextField.setText("");
        }
    }//GEN-LAST:event_phoneNumberTextFieldFocusGained

    private void phoneNumberTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextFieldFocusLost
        if (phoneNumberTextField.getText().trim().equals("")){
            phoneNumberTextField.setForeground(tool.textLostFocusColor());
            phoneNumberTextField.setText("1234567");
        }
    }//GEN-LAST:event_phoneNumberTextFieldFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setAllGuardToLostFocusState();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void historyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyTableMouseClicked
        int rowNo = historyTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        String phoneNumber [] = tool.getSplitNumber(model.getValueAt(rowNo, 1).toString());
        String overnightDaysString = model.getValueAt(rowNo, 3).toString();

        this.visitor.setName(model.getValueAt(rowNo, 0).toString());
        this.visitor.setContactNo(model.getValueAt(rowNo, 1).toString());
        setAllGuardTextFieldToGainedFocusState();
        
        if (this.visitor.hasCarHistory()){
            carPlateTextField.setForeground(tool.textGainedFocusColor());
            parkingLotTextField.setForeground(tool.textGainedFocusColor());
            checkbox1.setState(true);
            checkBoxGuardClicked++;
            carPlateTextField.setText(this.visitor.getCarPlateFromDB("not null"));
            parkingLotTextField.setText(this.visitor.getParkingLotFromDB("not null"));
        } else {
            carPlateTextField.setForeground(tool.textLostFocusColor());
            parkingLotTextField.setForeground(tool.textLostFocusColor());
            checkbox1.setState(false);
            checkBoxGuardClicked = 0;
            carPlateTextField.setText("-");
            parkingLotTextField.setText("-");
        }
        
        nameTextField.setText(model.getValueAt(rowNo, 0).toString());
        countryCode.setText(phoneNumber[0]);
        phoneNumberTextField.setText(phoneNumber[1]);
        visitUnitTextField.setText(model.getValueAt(rowNo, 2).toString());
        overnightDaysSpinner.setValue(Integer.valueOf(overnightDaysString));
        timeInLabel.setText("Time in: " + model.getValueAt(rowNo, 4).toString());
        dateOutLabel.setText("Date out: " + model.getValueAt(rowNo, 7).toString());
        timeOutLabel.setText("Time out: " + model.getValueAt(rowNo, 5).toString());
        dateInLabel.setText("Date in: " + model.getValueAt(rowNo, 6).toString());
    }//GEN-LAST:event_historyTableMouseClicked

    private void visitorsCarsDetailsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visitorsCarsDetailsTableMouseClicked
        Dbclass db = new Dbclass();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = null;
        DefaultTableModel model = (DefaultTableModel) visitorsCarsDetailsTable.getModel();
        int rowNo = visitorsCarsDetailsTable.getSelectedRow();
        String splitNumber [];
        
        try {
            con = db.connectDB();           
            String sql = "select visitUnit, overnightDays, timeIn, dateIn "
                       + "from visitationentries where name = ? and timeOut = \"24:00:00\" and carPlate is not null";
            pst = con.prepareStatement(sql);
            pst.setString(1, model.getValueAt(rowNo, 0).toString());
            rs = pst.executeQuery();
            
            if (rs.next()){
                this.visitor.setName(model.getValueAt(rowNo, 0).toString());
                this.visitor.setContactNo(model.getValueAt(rowNo, 1).toString());
                this.visitor.getCarPlateFromDB("null");
                this.visitor.getParkingLotFromDB("null");
                setAllGuardTextFieldToGainedFocusState();
                carPlateTextField.setForeground(tool.textGainedFocusColor());
                parkingLotTextField.setForeground(tool.textGainedFocusColor());
                checkbox1.setState(true);
                checkBoxGuardClicked++;
                splitNumber = tool.getSplitNumber(model.getValueAt(rowNo, 1).toString());
                
                countryCode.setText(splitNumber[0]);
                phoneNumberTextField.setText(splitNumber[1]);
                nameTextField.setText(model.getValueAt(rowNo, 0).toString());
                overnightDaysSpinner.setValue((Integer) rs.getInt("overnightDays"));
                carPlateTextField.setText(model.getValueAt(rowNo, 2).toString());
                parkingLotTextField.setText(model.getValueAt(rowNo, 3).toString());
                timeInLabel.setText("Time in: " + rs.getString("timeIn"));
                timeOutLabel.setText("Time out: -");
                dateInLabel.setText("Date in: " + rs.getString("dateIn"));
                dateOutLabel.setText("Date out: -");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }//GEN-LAST:event_visitorsCarsDetailsTableMouseClicked

    private void timeOutTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeOutTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) timeOutTable.getModel();
        int rowNo = timeOutTable.getSelectedRow();
        String splitNumber [] = tool.getSplitNumber(model.getValueAt(rowNo, 1).toString());
        this.visitor.setName(model.getValueAt(rowNo, 0).toString());
        this.visitor.setContactNo(model.getValueAt(rowNo, 1).toString());
        setAllGuardTextFieldToGainedFocusState();
        
        nameTextField.setText(model.getValueAt(rowNo, 0).toString());
        countryCode.setText(splitNumber[0]);
        phoneNumberTextField.setText(splitNumber[1]);
        visitUnitTextField.setText(model.getValueAt(rowNo, 2).toString());
        overnightDaysSpinner.setValue(Integer.valueOf(model.getValueAt(rowNo, 5).toString())); 
        timeInLabel.setText("Time in: " + model.getValueAt(rowNo, 3).toString());
        timeOutLabel.setText("Time out: -");
        dateInLabel.setText("Date in: " + model.getValueAt(rowNo, 4).toString());
        dateOutLabel.setText("Date out: -");
        
        if (model.getValueAt(rowNo, 6).toString().equals("Y")){
            checkbox1.setState(true);
            checkBoxGuardClicked++;
            carPlateTextField.setText(this.visitor.getCarPlateFromDB("null"));
            parkingLotTextField.setText(this.visitor.getParkingLotFromDB("null"));
        } else {
            checkbox1.setState(false);
            checkBoxGuardClicked = 0;
            carPlateTextField.setText("-");
            parkingLotTextField.setText("-");
        }
    }//GEN-LAST:event_timeOutTableMouseClicked

    private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutLabelMouseClicked
        if (this.account.getRole().equals("APARTMENT_MANAGER") || this.account.getRole().equals("GUARD")) {
            this.account.getAccountEntries().timeOut();
        } else {
            this.rAccount.getAccountEntries().timeOut();
        }
        LoginPage lp = new LoginPage();
        dispose();
        lp.setVisible(true);
    }//GEN-LAST:event_logOutLabelMouseClicked

    private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutLabelMouseEntered
        logOutPanel.setBackground(tool.mouseEnterLogOut());
    }//GEN-LAST:event_logOutLabelMouseEntered

    private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutLabelMouseExited
        logOutPanel.setBackground(tool.mouseExitLogOut());
    }//GEN-LAST:event_logOutLabelMouseExited

    private void logOutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutPanelMouseClicked
        if (this.account.getRole().equals("APARTMENT_MANAGER") || this.account.getRole().equals("GUARD")) {
            this.account.getAccountEntries().timeOut();
        } else {
            this.rAccount.getAccountEntries().timeOut();
        }
        LoginPage lp = new LoginPage();
        dispose();
        lp.setVisible(true);
    }//GEN-LAST:event_logOutPanelMouseClicked

    private void logOutPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutPanelMouseEntered
        logOutPanel.setBackground(tool.mouseEnterLogOut());
    }//GEN-LAST:event_logOutPanelMouseEntered

    private void logOutPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutPanelMouseExited
        logOutPanel.setBackground(tool.mouseExitLogOut());
    }//GEN-LAST:event_logOutPanelMouseExited

    private void payBillsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Bills");
        resetAllResidentScreen();
        payBillsScreen.setVisible(true);
        clearAllResidentTable();
        this.resident.showUnpaidBillsTable(unpaidBillsTable);
        this.resident.showPaidBillsTable(paidBillsTable);
    }//GEN-LAST:event_payBillsLabelMouseClicked

    private void payBillsLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsLabelMouseEntered
        payBillsPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_payBillsLabelMouseEntered

    private void payBillsLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsLabelMouseExited
        payBillsPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_payBillsLabelMouseExited

    private void payBillsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Bills");
        resetAllResidentScreen();
        payBillsScreen.setVisible(true);
        clearAllResidentTable();
        this.resident.showUnpaidBillsTable(unpaidBillsTable);
        this.resident.showPaidBillsTable(paidBillsTable);
    }//GEN-LAST:event_payBillsPanelMouseClicked

    private void payBillsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsPanelMouseEntered
        payBillsPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_payBillsPanelMouseEntered

    private void payBillsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsPanelMouseExited
        payBillsPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_payBillsPanelMouseExited

    private void forumLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Forum");
        resetAllResidentScreen();
        Forum chat = new Forum();
        forumScreen.setVisible(true);
        forumChatScreen.setText("");
        chat.showHistoryText(forumChatScreen);
    }//GEN-LAST:event_forumLabelMouseClicked

    private void forumLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabelMouseEntered
        forumPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_forumLabelMouseEntered

    private void forumLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabelMouseExited
        forumPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_forumLabelMouseExited

    private void forumPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Forum");
        resetAllResidentScreen();
        Forum chat = new Forum();
        forumScreen.setVisible(true);
        forumChatScreen.setText("");
        chat.showHistoryText(forumChatScreen);
    }//GEN-LAST:event_forumPanelMouseClicked

    private void forumPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumPanelMouseEntered
        forumPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_forumPanelMouseEntered

    private void forumPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumPanelMouseExited
        forumPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_forumPanelMouseExited

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (billIDLabel.getText().equals("-") || billIDLabel.getText().equals("")){
            tool.getErrorMessageOptionPane("Please select bills from the table.");
            return;
        }
        
        Bill bill = new Bill();
        bill.setBillID(billIDLabel.getText());
        PayBillPanel pb = new PayBillPanel(rAccount, billIDLabel.getText());
        pb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void unpaidBillsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unpaidBillsTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) unpaidBillsTable.getModel();
        int rowNo = unpaidBillsTable.getSelectedRow();
        
        billIDLabel.setText(model.getValueAt(rowNo, 0).toString());
        issueDateLabel.setText(model.getValueAt(rowNo, 1).toString());
        expiredDateLabel.setText(model.getValueAt(rowNo, 2).toString());
        billAmountLabel.setText("RM " + model.getValueAt(rowNo, 3).toString());
        billTypeLabel.setText(model.getValueAt(rowNo, 4).toString());
        billMonth.setText(model.getValueAt(rowNo, 5).toString());
    }//GEN-LAST:event_unpaidBillsTableMouseClicked

    private void paidBillsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paidBillsTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) paidBillsTable.getModel();
        int rowNo = paidBillsTable.getSelectedRow();
        
        billIDLabel.setText(model.getValueAt(rowNo, 0).toString());
        issueDateLabel.setText(model.getValueAt(rowNo, 1).toString());
        expiredDateLabel.setText(model.getValueAt(rowNo, 2).toString());
        billAmountLabel.setText("RM " + model.getValueAt(rowNo, 3).toString());
        billTypeLabel.setText(model.getValueAt(rowNo, 4).toString());
        billMonth.setText(model.getValueAt(rowNo, 5).toString());
    }//GEN-LAST:event_paidBillsTableMouseClicked

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowLostFocus

    private void textAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            Forum chat = new Forum(this.rAccount.getUsingProfile());
            chat.setText(textArea.getText());
            chat.insertToTextArea(forumChatScreen);
            textArea.setText("");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    textArea.setCaretPosition(0); 
                }
            });
        }
    }//GEN-LAST:event_textAreaKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Forum chat = new Forum(this.rAccount.getUsingProfile());
            chat.setText(textArea.getText());
            chat.insertToTextArea(forumChatScreen);
            textArea.setText("");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    textArea.setCaretPosition(0); 
                }
            });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void forumLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabel1MouseClicked
        pathLabel.setText("Super Solution AMS | Manage Profile");
        resetAllResidentScreen();
        showYourDetailsResident();
        manageProfileScreenResident.setVisible(true);
    }//GEN-LAST:event_forumLabel1MouseClicked

    private void forumLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabel1MouseEntered
        manageProfilePanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_forumLabel1MouseEntered

    private void forumLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forumLabel1MouseExited
        manageProfilePanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_forumLabel1MouseExited

    private void manageProfilePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageProfilePanelMouseClicked
        pathLabel.setText("Super Solution AMS | Manage Profile");
        resetAllResidentScreen();
        showYourDetailsResident();
        manageProfileScreenResident.setVisible(true);
    }//GEN-LAST:event_manageProfilePanelMouseClicked

    private void manageProfilePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageProfilePanelMouseEntered
        manageProfilePanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_manageProfilePanelMouseEntered

    private void manageProfilePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageProfilePanelMouseExited
        manageProfilePanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_manageProfilePanelMouseExited

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        setAllResidentTextFieldToLostFocusState();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void oldPinFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oldPinFieldFocusGained
        oldPinField.setEchoChar('\u25cf');
        oldPinField.setForeground(tool.textGainedFocusColor());
        if (oldPinField.getText().trim().equals("Enter Pin...")){
            oldPinField.setText("");
        }
    }//GEN-LAST:event_oldPinFieldFocusGained

    private void oldPinFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oldPinFieldFocusLost
        if (oldPinField.getText().trim().equals("")){
            oldPinField.setForeground(tool.textLostFocusColor());
            oldPinField.setEchoChar('\u0000');
            oldPinField.setText("Enter Pin...");
        }
    }//GEN-LAST:event_oldPinFieldFocusLost

    private void emailTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldFocusGained
        emailTextField.setForeground(tool.textGainedFocusColor());
        if(emailTextField.getText().trim().equals("Enter Email...")){
            emailTextField.setText("");
        }
    }//GEN-LAST:event_emailTextFieldFocusGained

    private void emailTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldFocusLost
        if(emailTextField.getText().trim().equals("")){
            emailTextField.setForeground(tool.textLostFocusColor());
            emailTextField.setText("Enter Email...");
        }
    }//GEN-LAST:event_emailTextFieldFocusLost

    private void countryCode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCode1FocusGained
        countryCode1.setForeground(tool.textGainedFocusColor());
        if(countryCode1.getText().trim().equals("+6010")){
            countryCode1.setText("");
        }
    }//GEN-LAST:event_countryCode1FocusGained

    private void countryCode1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCode1FocusLost
        if(countryCode1.getText().trim().equals("")){
            countryCode1.setForeground(tool.textLostFocusColor());
            countryCode1.setText("+6010");
        }
    }//GEN-LAST:event_countryCode1FocusLost

    private void phoneNumberTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextField1FocusGained
        phoneNumberTextField1.setForeground(tool.textGainedFocusColor());
        if(phoneNumberTextField1.getText().trim().equals("1234567")){
            phoneNumberTextField1.setText("");
        }
    }//GEN-LAST:event_phoneNumberTextField1FocusGained

    private void phoneNumberTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextField1FocusLost
        if(phoneNumberTextField1.getText().trim().equals("")){
            phoneNumberTextField1.setText("1234567");
            phoneNumberTextField1.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_phoneNumberTextField1FocusLost

    private void appearingProfileNameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_appearingProfileNameTextFieldFocusGained
        appearingProfileNameTextField.setForeground(tool.textGainedFocusColor());
        if(appearingProfileNameTextField.getText().trim().equals("Enter Appearing/Profile Name...")){
            appearingProfileNameTextField.setText("");
        }
    }//GEN-LAST:event_appearingProfileNameTextFieldFocusGained

    private void appearingProfileNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_appearingProfileNameTextFieldFocusLost
       if(appearingProfileNameTextField.getText().trim().equals("")){
            appearingProfileNameTextField.setForeground(tool.textLostFocusColor());
            appearingProfileNameTextField.setText("Enter Appearing/Profile Name...");
        }
    }//GEN-LAST:event_appearingProfileNameTextFieldFocusLost

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (!allFieldIsNullResident())
            return;
        
        if (!rAccount.getUsingProfile().validatePin(oldPinField.getText())){
            tool.getErrorMessageOptionPane("Incorrect old pin, try again");
            return;
        }
        
        Resident newResidentDetails = this.resident;
        Profile newProfileDetails = new Profile();
        newProfileDetails.setAppearingName(appearingProfileNameTextField.getText().trim());
        newProfileDetails.setPin(newPinField.getText());
        newResidentDetails.setContactNo(countryCode1.getText().trim() + " - " + phoneNumberTextField1.getText().trim());
        newResidentDetails.setEmail(emailTextField.getText().trim());
        newResidentDetails.setGender(genderComboBox.getSelectedItem().toString());
        
        if (!rAccount.getUsingProfile().updateResidentDetails(newResidentDetails) ||
            !rAccount.getUsingProfile().updateProfileDetails(newProfileDetails, rAccount.getUsingProfile().getAppearingName())) {
            tool.getErrorMessageOptionPane("Failed to update details!");
            return;
        }
        tool.getInformationMessageOptionPane("Details updated successfully!");
        rAccount.setUsingProfile(newProfileDetails);
        this.resident = newResidentDetails;
        welcomeTextLabel.setText("Welcome, " + rAccount.getUsingProfile().getAppearingName() + "!");
        showYourDetailsResident();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void newPinFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newPinFieldFocusGained
        newPinField.setEchoChar('\u25cf');
        newPinField.setForeground(tool.textGainedFocusColor());
        if (newPinField.getText().trim().equals("Enter Pin...")){
            newPinField.setText("");
        }
    }//GEN-LAST:event_newPinFieldFocusGained

    private void newPinFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newPinFieldFocusLost
        if (newPinField.getText().trim().equals("")){
            newPinField.setForeground(tool.textLostFocusColor());
            newPinField.setEchoChar('\u0000');
            newPinField.setText("Enter Pin...");
        }
    }//GEN-LAST:event_newPinFieldFocusLost

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (checkBoxResidentClicked == 0) {
            newPinField.setEchoChar('\u0000');
            oldPinField.setEchoChar('\u0000');
            checkBoxResidentClicked = 1;
        } else {
            newPinField.setEchoChar('\u25cf');
            oldPinField.setEchoChar('\u25cf');
            checkBoxResidentClicked = 0;
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void oldPinFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldPinFieldKeyPressed
        
    }//GEN-LAST:event_oldPinFieldKeyPressed

    private void reportLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Report");
        clearAllManagerReportTable();
        resetAllManagerScreen();
        resetAllManagerSubScreen();
        reportScreenApartmentManager.setVisible(true);
        visitorReport.setVisible(true);
        showAllDefaultVisitorManagerTable();
    }//GEN-LAST:event_reportLabelMouseClicked

    private void reportLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportLabelMouseEntered
        reportPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_reportLabelMouseEntered

    private void reportLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportLabelMouseExited
        reportPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_reportLabelMouseExited

    private void reportPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Report");
        clearAllManagerReportTable();
        resetAllManagerScreen();
        resetAllManagerSubScreen();
        reportScreenApartmentManager.setVisible(true);
        visitorReport.setVisible(true);
        showAllDefaultVisitorManagerTable();
    }//GEN-LAST:event_reportPanelMouseClicked

    private void reportPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportPanelMouseEntered
        reportPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_reportPanelMouseEntered

    private void reportPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportPanelMouseExited
        reportPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_reportPanelMouseExited

    private void informationLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Informations");
        resetAllManagerScreen();  
        informationsScreenApartmentManager.setVisible(true);
        clearAllInformationTable();
        this.manager.showAccountWithInformations(accountWithInformationsTable);
        this.manager.showAccountPendingInformations(accountPendingToAddInformationsTable);
        this.manager.showAccountWithInformationsGuard(accountWithInformationsGuardTable);
        this.manager.showAccountPendingInformationsGuard(accountPendingToAddInformationsGuardTable);
        informationsScreenApartmentManager.setVisible(true);
    }//GEN-LAST:event_informationLabelMouseClicked

    private void informationLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationLabelMouseEntered
        informationPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_informationLabelMouseEntered

    private void informationLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationLabelMouseExited
        informationPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_informationLabelMouseExited

    private void informationPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Informations");
        resetAllManagerScreen();
        informationsScreenApartmentManager.setVisible(true);
        clearAllInformationTable();
        this.manager.showAccountWithInformations(accountWithInformationsTable);
        this.manager.showAccountPendingInformations(accountPendingToAddInformationsTable);
        this.manager.showAccountWithInformationsGuard(accountWithInformationsGuardTable);
        this.manager.showAccountPendingInformationsGuard(accountPendingToAddInformationsGuardTable);
        informationsScreenApartmentManager.setVisible(true);
    }//GEN-LAST:event_informationPanelMouseClicked

    private void informationPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationPanelMouseEntered
        informationPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_informationPanelMouseEntered

    private void informationPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informationPanelMouseExited
        informationPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_informationPanelMouseExited

    private void manageAccountsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Manage Accounts");
        resetAllManagerScreen();
        manageAccountsScreenApartmentManager.setVisible(true);
        clearAllManageAccountsTable();
        this.manager.showAllAccounts(overallAccountsTable);
        this.manager.showAllResidentAccounts(residentAccountsTable);
        this.manager.showAllGuardAccounts(guardAccountsTable);
        this.manager.showAccountPendingToAddInformations(accountPendingToAddInformationTable);
    }//GEN-LAST:event_manageAccountsLabelMouseClicked

    private void manageAccountsLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsLabelMouseEntered
        manageAccountsPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_manageAccountsLabelMouseEntered

    private void manageAccountsLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsLabelMouseExited
        manageAccountsPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_manageAccountsLabelMouseExited

    private void manageAccountsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Manage Accounts");
        resetAllManagerScreen();
        manageAccountsScreenApartmentManager.setVisible(true);
        clearAllManageAccountsTable();
        this.manager.showAllAccounts(overallAccountsTable);
        this.manager.showAllResidentAccounts(residentAccountsTable);
        this.manager.showAllGuardAccounts(guardAccountsTable);
        this.manager.showAccountPendingToAddInformations(accountPendingToAddInformationTable);
    }//GEN-LAST:event_manageAccountsPanelMouseClicked

    private void manageAccountsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsPanelMouseEntered
        manageAccountsPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_manageAccountsPanelMouseEntered

    private void manageAccountsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccountsPanelMouseExited
        manageAccountsPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_manageAccountsPanelMouseExited

    private void reminderLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderLabelMouseClicked
        pathLabel.setText("Super Solution AMS | Reminder");
        resetAllManagerScreen();
        reminderScreenApartmentManager.setVisible(true);
        clearAllReminderTable();
        this.manager.showReminderTable(reminderHistoryTable);
    }//GEN-LAST:event_reminderLabelMouseClicked

    private void reminderLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderLabelMouseEntered
        reminderPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_reminderLabelMouseEntered

    private void reminderLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderLabelMouseExited
        reminderPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_reminderLabelMouseExited

    private void reminderPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderPanelMouseClicked
        pathLabel.setText("Super Solution AMS | Reminder");
        resetAllManagerScreen();
        reminderScreenApartmentManager.setVisible(true);
        clearAllReminderTable();
        this.manager.showReminderTable(reminderHistoryTable);
    }//GEN-LAST:event_reminderPanelMouseClicked

    private void reminderPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderPanelMouseEntered
        reminderPanel.setBackground(tool.mouseEnterColor());
    }//GEN-LAST:event_reminderPanelMouseEntered

    private void reminderPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderPanelMouseExited
        reminderPanel.setBackground(tool.mouseExitColor());
    }//GEN-LAST:event_reminderPanelMouseExited

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        clearAllManagerReportTable();
        reportTableLabel.setText("Resident Report");
        resetAllManagerSubScreen();
        residentReport.setVisible(true);
        showAllDefaultResidentManagerTable();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        clearAllManagerReportTable();
        reportTableLabel.setText("Visitor Report");
        resetAllManagerSubScreen();
        visitorReport.setVisible(true);
        showAllDefaultVisitorManagerTable();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (yearFromComboBox.getSelectedIndex() > yearToComboBox.getSelectedIndex()){
            tool.getErrorMessageOptionPane("Invalid year range");
            return;
        }
        
        clearAllManagerReportTable();
        String yearFrom = yearFromComboBox.getSelectedItem().toString();
        String yearTo = yearToComboBox.getSelectedItem().toString();
        String monthFrom = monthFromComboBox.getSelectedItem().toString();
        String monthTo = monthToComboBox.getSelectedItem().toString();
        
        this.manager.showOverallVisitorTable(overallReportTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showMostVisitedTable(mostVisitedTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showMostOvernights(mostOvernightsTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showMostVisitedUnit(mostVisitedUnitsTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showOverallBills(overallBillRecordTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showPaidBills(paidBillsTable, yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showUnpaidBills(unpaidBillsTable,yearFrom, yearTo, monthFrom, monthTo);
        this.manager.showGuardAttendance(attendanceTable, yearFrom, yearTo, monthFrom, monthTo);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        clearAllManagerReportTable();
        reportTableLabel.setText("Guard Report");
        resetAllManagerSubScreen();
        guardReport.setVisible(true);
        showAllDefaultGuardManagerTable();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void exitLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLabelMouseClicked
        if (this.account.getRole().equals("APARTMENT_MANAGER") || this.account.getRole().equals("GUARD")) {
            this.account.getAccountEntries().timeOut();
            System.exit(0);
            return;
        }
        this.rAccount.getAccountEntries().timeOut();
        System.exit(0);
    }//GEN-LAST:event_exitLabelMouseClicked

    private void exitLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLabelMouseEntered
        exitPanel.setBackground(this.tool.mouseEnterExit());
    }//GEN-LAST:event_exitLabelMouseEntered

    private void exitLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLabelMouseExited
        exitPanel.setBackground(this.tool.mouseExitExit());
    }//GEN-LAST:event_exitLabelMouseExited

    private void exitPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitPanelMouseClicked
        if (this.account.getRole().equals("APARTMENT_MANAGER") || this.account.getRole().equals("GUARD")) {
            this.account.getAccountEntries().timeOut();
            System.exit(0);
            return;
        }
        this.rAccount.getAccountEntries().timeOut();
        System.exit(0);
    }//GEN-LAST:event_exitPanelMouseClicked

    private void exitPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitPanelMouseEntered
        exitPanel.setBackground(this.tool.mouseEnterExit());
    }//GEN-LAST:event_exitPanelMouseEntered

    private void exitPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitPanelMouseExited
        exitPanel.setBackground(this.tool.mouseExitExit());
    }//GEN-LAST:event_exitPanelMouseExited

    private void nameTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextField1FocusGained
        if(nameTextField1.getText().trim().equals("Enter name...")){
            nameTextField1.setText("");
            nameTextField1.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_nameTextField1FocusGained

    private void nameTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextField1FocusLost
        if (nameTextField1.getText().trim().equals("")){
            nameTextField1.setText("Enter name...");
            nameTextField1.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_nameTextField1FocusLost

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (selectedAccountUsername.getText().equals("-")) {
            tool.getErrorMessageOptionPane("Please select account from tables");
            return;
        } else if(manager.validateResidentIC(ICTextField.getText().trim())){
            tool.getErrorMessageOptionPane("Details already existed!");
            return;
        } else if (!manager.validateUnit(stayingUnitTextField.getText().trim(), selectedAccountUsername.getText().trim())){
            tool.getErrorMessageOptionPane("Unit not same as owner!");
            return;
        }
        
        if (residentGuardState == 1){
            //Adding resident information
            if (allFieldResidentIsNullApartmentManagerInformation()){return;}
            Resident resident = new Resident();
            ResidentAccount rAccount = new ResidentAccount();

            resident.setName(nameTextField1.getText().trim().toUpperCase());
            resident.setUserIC(ICTextField.getText().trim());
            resident.setContactNo(countryCode3.getText().trim() + " - " + phoneNumberTextField3.getText().trim());
            resident.setStayingUnit(stayingUnitTextField.getText().trim().toUpperCase());
            resident.setEmail(emailTextFieldResident.getText().trim());
            resident.setGender(genderComboBox2.getSelectedItem().toString());
            resident.setRelationshipToOwner(relationshipToOwnerComboBox.getSelectedItem().toString());
            rAccount.setUsername(selectedAccountUsername.getText());
            rAccount.getAccountIDFromDB();

            if (this.manager.validateResidentHasOwner(rAccount.getAccountIDFromDB()) && 
                    relationshipToOwnerComboBox.getSelectedItem().toString().equals("SELF")){
                tool.getErrorMessageOptionPane("This account already has an owner, details failed to update!");
                return;
            } else if (!this.manager.validateResidentHasOwner(rAccount.getAccountIDFromDB()) && 
                    !relationshipToOwnerComboBox.getSelectedItem().toString().equals("SELF")){
                tool.getErrorMessageOptionPane("This account has not had an owner details yet!\n Every details must have main or \"SELF\" details first!");
                return;
            } 

            if (relationshipToOwnerComboBox.getSelectedItem().toString().equals("SELF")){
                this.manager.insertResidentSelfInformation(resident, rAccount);
                clearAllInformationTable();
                this.manager.showAccountWithInformations(accountWithInformationsTable);
                this.manager.showAccountPendingInformations(accountPendingToAddInformationsTable);
                setAllManagerResidentInformationToLostFocusState();
                selectedAccountUsername.setText("-");
                tool.getInformationMessageOptionPane("Details updated successfully!");
            } else if (!relationshipToOwnerComboBox.getSelectedItem().toString().equals("SELF")) {
                this.manager.insertResidentInformation(resident, rAccount, 
                        this.manager.getOwnerID(selectedAccountUsername.getText()));
                clearAllInformationTable();
                this.manager.showAccountWithInformations(accountWithInformationsTable);
                this.manager.showAccountPendingInformations(accountPendingToAddInformationsTable);
                this.manager.showAccountWithInformationsGuard(accountWithInformationsGuardTable);
                this.manager.showAccountPendingInformationsGuard(accountPendingToAddInformationsGuardTable);
                setAllManagerResidentInformationToLostFocusState();
                selectedAccountUsername.setText("-");
                tool.getInformationMessageOptionPane("Details updated successfully!");
            } else {
                tool.getErrorMessageOptionPane("Something went wrong!");
            }
        } else {
            //Add guard's informations
            if (allFieldGuardIsNullApartmentManagerInformation()) {return;}
            Guard guard = new Guard();
            Account account = new Account();
            
            guard.setName(nameTextField1.getText().trim().toUpperCase());
            guard.setUserIC(ICTextField.getText().trim());
            guard.setContactNo(countryCode3.getText().trim() + " - " + phoneNumberTextField3.getText().trim());
            guard.setUserID(guardIDTextField.getText().trim().toUpperCase());
            guard.setEmail(emailTextFieldResident.getText().trim());
            guard.setGender(genderComboBox2.getSelectedItem().toString());
            account.setUsername(selectedAccountUsername.getText());
            account.getAccountIDFromDB();
            this.manager.insertGuardInformation(guard, account);
            clearAllInformationTable();
            setAllManagerGuardInformationToLostFocusState();
            selectedAccountUsername.setText("-");
            this.manager.showAccountWithInformationsGuard(accountWithInformationsGuardTable);
            this.manager.showAccountPendingInformationsGuard(accountPendingToAddInformationsGuardTable);
            this.manager.showAccountWithInformations(accountWithInformationsTable);
            this.manager.showAccountPendingInformations(accountPendingToAddInformationsTable);
            tool.getInformationMessageOptionPane("Details updated successfully!");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if (residentGuardState == 0) {
            setAllManagerGuardInformationToLostFocusState();
        } else {
            setAllManagerResidentInformationToLostFocusState();
        }
        selectedAccountUsername.setText("-");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void phoneNumberTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextField3FocusGained
        if(phoneNumberTextField3.getText().trim().equals("1234567...")){
            phoneNumberTextField3.setText("");
            phoneNumberTextField3.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_phoneNumberTextField3FocusGained

    private void phoneNumberTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneNumberTextField3FocusLost
        if (phoneNumberTextField3.getText().trim().equals("")){
            phoneNumberTextField3.setText("1234567...");
            phoneNumberTextField3.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_phoneNumberTextField3FocusLost

    private void countryCode3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCode3FocusGained
        if(countryCode3.getText().trim().equals("+6010...")){
            countryCode3.setText("");
            countryCode3.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_countryCode3FocusGained

    private void countryCode3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_countryCode3FocusLost
        if (countryCode3.getText().trim().equals("")){
            countryCode3.setText("+6010...");
            countryCode3.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_countryCode3FocusLost

    private void stayingUnitTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stayingUnitTextFieldFocusGained
        if(stayingUnitTextField.getText().trim().equals("A-2-13...")){
            stayingUnitTextField.setText("");
            stayingUnitTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_stayingUnitTextFieldFocusGained

    private void stayingUnitTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stayingUnitTextFieldFocusLost
        if (stayingUnitTextField.getText().trim().equals("")){
            stayingUnitTextField.setText("A-2-13...");
            stayingUnitTextField.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_stayingUnitTextFieldFocusLost

    private void ICTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICTextFieldFocusGained
        if(ICTextField.getText().trim().equals("Enter IC number...")){
            ICTextField.setText("");
            ICTextField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_ICTextFieldFocusGained

    private void ICTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICTextFieldFocusLost
        if (ICTextField.getText().trim().equals("")){
            ICTextField.setText("Enter IC number...");
            ICTextField.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_ICTextFieldFocusLost

    private void emailTextFieldResidentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldResidentFocusGained
        if(emailTextFieldResident.getText().trim().equals("Enter Email...")){
            emailTextFieldResident.setText("");
            emailTextFieldResident.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_emailTextFieldResidentFocusGained

    private void emailTextFieldResidentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldResidentFocusLost
        if (emailTextFieldResident.getText().trim().equals("")){
            emailTextFieldResident.setText("Enter Email...");
            emailTextFieldResident.setForeground(tool.textLostFocusColor());
        }
    }//GEN-LAST:event_emailTextFieldResidentFocusLost

    private void accountWithInformationsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountWithInformationsTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) accountWithInformationsTable.getModel();
        int rowNo = accountWithInformationsTable.getSelectedRow();
        String splitNumber [] = tool.getSplitNumber(model.getValueAt(rowNo, 2).toString());
        setAllManagerResidentInformationToGainedFocusState();
        
        nameTextField1.setText(model.getValueAt(rowNo, 0).toString());
        ICTextField.setText(model.getValueAt(rowNo, 1).toString());
        countryCode3.setText(splitNumber[0]);
        phoneNumberTextField3.setText(splitNumber[1]);
        emailTextFieldResident.setText(model.getValueAt(rowNo, 3).toString());
        genderComboBox2.setSelectedItem(model.getValueAt(rowNo, 4).toString());
        relationshipToOwnerComboBox.setSelectedItem(model.getValueAt(rowNo, 5).toString());
        stayingUnitTextField.setText(model.getValueAt(rowNo, 6).toString());
        selectedAccountUsername.setText(this.manager.getResidentUsername(model.getValueAt(rowNo, 1).toString()));
    }//GEN-LAST:event_accountWithInformationsTableMouseClicked

    private void accountPendingToAddInformationsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountPendingToAddInformationsTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) accountPendingToAddInformationsTable.getModel();
        int rowNo = accountPendingToAddInformationsTable.getSelectedRow();
        setAllManagerResidentInformationToLostFocusState();
        selectedAccountUsername.setText(model.getValueAt(rowNo, 0).toString());
    }//GEN-LAST:event_accountPendingToAddInformationsTableMouseClicked

    private void searchByComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchByComboBoxItemStateChanged
        if (searchByComboBox.getSelectedItem().toString().equals("Search by IC number")){
            searchBarTextField.setText("Enter IC Number...");
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by name")){
            searchBarTextField.setText("Enter Name...");
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by contact number")){
            searchBarTextField.setText("Enter Contact Number...");
        }
    }//GEN-LAST:event_searchByComboBoxItemStateChanged

    private void searchBarTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBarTextFieldKeyPressed
        if (evt.getKeyCode() != KeyEvent.VK_ENTER){
            return;
        }
        if (residentGuardState == 1){
            searchResidentInformation();
        } else {
            searchGuardInformation();
        }
    }//GEN-LAST:event_searchBarTextFieldKeyPressed

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
        searchResidentInformation();
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void searchBarTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBarTextFieldFocusGained
        if(searchBarTextField.getText().trim().equals("Enter IC Number...") ||
            searchBarTextField.getText().trim().equals("Enter Name...") ||
            searchBarTextField.getText().trim().equals("Enter Contact Number...")){
            searchBarTextField.setForeground(tool.textGainedFocusColor());
            searchBarTextField.setText("");
        }
    }//GEN-LAST:event_searchBarTextFieldFocusGained

    private void searchBarTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBarTextFieldFocusLost
        if(!searchBarTextField.getText().trim().equals("")){
            return;
        }
        
        searchBarTextField.setForeground(tool.textLostFocusColor());
        
        if (searchByComboBox.getSelectedItem().toString().equals("Search by IC number")) {
            searchBarTextField.setText("Enter IC Number...");
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by name")){
            searchBarTextField.setText("Enter Name...");
        } else if (searchByComboBox.getSelectedItem().toString().equals("Search by contact number")) {
            searchBarTextField.setText("Enter Contact Number...");
        }
    }//GEN-LAST:event_searchBarTextFieldFocusLost

    private void jTabbedPane5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane5StateChanged
        int selectedIndex = jTabbedPane5.getSelectedIndex();
        if (selectedIndex == 0){
            jLabel54.setVisible(true);
            searchBarTextField.setVisible(true);
            searchByComboBox.setVisible(true);
            searchButton1.setVisible(true);
            return;
        }
        jLabel54.setVisible(false);
        searchBarTextField.setVisible(false);
        searchByComboBox.setVisible(false);
        searchButton1.setVisible(false);
    }//GEN-LAST:event_jTabbedPane5StateChanged

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        if (selectedIndex == 0){
            jButton4.setVisible(true);
            return;
        }
        jButton4.setVisible(false);
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jTabbedPane6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane6StateChanged
        int selectedIndex = jTabbedPane6.getSelectedIndex();
        if (selectedIndex == 0){
            jLabel59.setText("Unit");
            stayingUnitTextField.setVisible(true);
            guardIDTextField.setVisible(false);
            jLabel58.setVisible(true);
            relationshipToOwnerComboBox.setVisible(true);
            setAllManagerGuardInformationToLostFocusState();
            setAllManagerResidentInformationToLostFocusState();
            residentGuardState = 1; //Is resident
            return;
        }
        jLabel54.setVisible(true);
        searchBarTextField.setVisible(true);
        searchByComboBox.setVisible(true);
        searchButton1.setVisible(true);
        jLabel59.setText("Guard ID");
        stayingUnitTextField.setVisible(false);
        guardIDTextField.setVisible(true);
        jLabel58.setVisible(false);
        relationshipToOwnerComboBox.setVisible(false);
        setAllManagerGuardInformationToLostFocusState();
        setAllManagerResidentInformationToLostFocusState();
        residentGuardState = 0; //Is guard
    }//GEN-LAST:event_jTabbedPane6StateChanged

    private void guardIDTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_guardIDTextFieldFocusGained
        if (guardIDTextField.getText().trim().equals("Enter Guard ID...")){
            guardIDTextField.setForeground(tool.textGainedFocusColor());
            guardIDTextField.setText("");
        }
    }//GEN-LAST:event_guardIDTextFieldFocusGained

    private void guardIDTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_guardIDTextFieldFocusLost
        if (guardIDTextField.getText().trim().equals("")){
            guardIDTextField.setForeground(tool.textLostFocusColor());
            guardIDTextField.setText("Enter Guard ID...");
        }
    }//GEN-LAST:event_guardIDTextFieldFocusLost

    private void accountWithInformationsGuardTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountWithInformationsGuardTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) accountWithInformationsGuardTable.getModel();
        int rowNo = accountWithInformationsGuardTable.getSelectedRow();
        String splitNumber [] = tool.getSplitNumber(model.getValueAt(rowNo, 3).toString());
        setAllManagerGuardInformationToGainedFocusState();
        
        nameTextField1.setText(model.getValueAt(rowNo, 0).toString());
        ICTextField.setText(model.getValueAt(rowNo, 2).toString());
        countryCode3.setText(splitNumber[0]);
        phoneNumberTextField3.setText(splitNumber[1]);
        emailTextFieldResident.setText(model.getValueAt(rowNo, 4).toString());
        genderComboBox2.setSelectedItem(model.getValueAt(rowNo, 5).toString());
        guardIDTextField.setText(model.getValueAt(rowNo, 1).toString());
        selectedAccountUsername.setText(this.manager.getGuardUsername(model.getValueAt(rowNo, 2).toString()));
    }//GEN-LAST:event_accountWithInformationsGuardTableMouseClicked

    private void accountPendingToAddInformationsGuardTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountPendingToAddInformationsGuardTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) accountPendingToAddInformationsGuardTable.getModel();
        int rowNo = accountPendingToAddInformationsGuardTable.getSelectedRow();
        setAllManagerGuardInformationToLostFocusState();
        selectedAccountUsername.setText(model.getValueAt(rowNo, 0).toString());
    }//GEN-LAST:event_accountPendingToAddInformationsGuardTableMouseClicked

    private void guardIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardIDTextFieldActionPerformed

    private void jTabbedPane7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane7StateChanged
        int selectedIndex = jTabbedPane7.getSelectedIndex();
        if (selectedIndex == 0){
            jLabel54.setVisible(true);
            searchBarTextField.setVisible(true);
            searchByComboBox.setVisible(true);
            searchButton1.setVisible(true);
            return;
        }
        jLabel54.setVisible(false);
        searchBarTextField.setVisible(false);
        searchByComboBox.setVisible(false);
        searchButton1.setVisible(false);
    }//GEN-LAST:event_jTabbedPane7StateChanged

    private void usernameAccountTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameAccountTextFieldFocusGained
        if (usernameAccountTextField.getText().trim().equals("Enter Username...")){
            usernameAccountTextField.setForeground(tool.textGainedFocusColor());
            usernameAccountTextField.setText("");
        }
    }//GEN-LAST:event_usernameAccountTextFieldFocusGained

    private void usernameAccountTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameAccountTextFieldFocusLost
        if (usernameAccountTextField.getText().trim().equals("")){
            usernameAccountTextField.setForeground(tool.textLostFocusColor());
            usernameAccountTextField.setText("Enter Username...");
        }
    }//GEN-LAST:event_usernameAccountTextFieldFocusLost

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (allFieldManageAccountIsNullApartmentManager()){return;}
        if (!validateConfirmPasswordManageAccounts()) {
            tool.getErrorMessageOptionPane("\"Confirm Password\" field is not same as \"Password\" field!");
            return;
        } if (this.manager.validateUsername(usernameAccountTextField.getText().trim())){
            tool.getErrorMessageOptionPane("Username already existed in the database!");
            return;
        }
        
        Account account = new Account();
        account.setUsername(usernameAccountTextField.getText().trim());
        account.setPassword(passwordField.getText());
        account.setRole(roleComboBox.getSelectedItem().toString());
        account.setStatus(statusComboBox.getSelectedItem().toString());
        
        if (this.manager.createAccount(account)) {
            setAllManageAccountsToLostFocusState();
            clearAllManageAccountsTable();
            this.manager.showAllAccounts(overallAccountsTable);
            this.manager.showAllResidentAccounts(residentAccountsTable);
            this.manager.showAllGuardAccounts(guardAccountsTable);
            tool.getInformationMessageOptionPane("Account successfully created!");
        } else {
            tool.getErrorMessageOptionPane("Something went wrong, account has not created!");
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        setAllManageAccountsToLostFocusState();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void passwordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldFocusGained
        if (passwordField.getText().trim().equals("Enter Confirm Password...")){
            passwordField.setText("");
            passwordField.setEchoChar('\u25cf');
            passwordField.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_passwordFieldFocusGained

    private void passwordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldFocusLost
        if (passwordField.getText().trim().equals("")){
            passwordField.setForeground(tool.textLostFocusColor());
            passwordField.setEchoChar('\u0000');
            passwordField.setText("Enter Confirm Password...");
        }
    }//GEN-LAST:event_passwordFieldFocusLost

    private void passwordField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordField1FocusGained
        if (passwordField1.getText().trim().equals("Enter Password...")){
            passwordField1.setText("");
            passwordField1.setEchoChar('\u25cf');
            passwordField1.setForeground(tool.textGainedFocusColor());
        }
    }//GEN-LAST:event_passwordField1FocusGained

    private void passwordField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordField1FocusLost
        if (passwordField1.getText().trim().equals("")){
            passwordField1.setForeground(tool.textLostFocusColor());
            passwordField1.setEchoChar('\u0000');
            passwordField1.setText("Enter Password...");
        }
    }//GEN-LAST:event_passwordField1FocusLost

    private void jCheckBox2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox2StateChanged
        
    }//GEN-LAST:event_jCheckBox2StateChanged

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (checkBoxResidentClicked == 0) {
            passwordField1.setEchoChar('\u0000');
            passwordField.setEchoChar('\u0000');
            checkBoxResidentClicked = 1;
        } else {
            passwordField1.setEchoChar('\u25cf');
            passwordField.setEchoChar('\u25cf');
            checkBoxResidentClicked = 0;
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Notification notification = new Notification();
        notification.setAm(this.manager);
        notification.setType(reminderTypeComboBox.getSelectedItem().toString());
        notification.setNotificationText(contentTextArea.getText());
        this.manager.createReminder(notification, this.account.getAccountID());
        tool.getInformationMessageOptionPane("Reminder have been created!");
        clearAllReminderTable();
        this.manager.showReminderTable(reminderHistoryTable);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        contentTextArea.setText("");
        reminderTypeComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void reminderTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reminderTypeComboBoxActionPerformed
        String selectedType = reminderTypeComboBox.getSelectedItem().toString();
        String headerText;

        if (selectedType.equals("ANNOUNCEMENT")) {
            headerText = "     << ANNOUNCEMENT >>\n";
        } else if (selectedType.equals("BILL")) {
            headerText = "     <<      BILL    >>\n";
        } else if (selectedType.equals("ALERT")) {
            headerText = "     <<      ALERT   >>\n";
        } else {
            headerText = "     <<      OTHER   >> \n";
        }

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(headerText);
        contentBuilder.append("------------------------------------------\n");

        String fullText = contentTextArea.getText();
        String[] lines = fullText.split("\n");
        int startLine = Math.min(2, lines.length);

        for (int i = startLine; i < lines.length; i++) {
            contentBuilder.append(lines[i]);
            contentBuilder.append("\n");
        }

        contentTextArea.setText(contentBuilder.toString());
    }//GEN-LAST:event_reminderTypeComboBoxActionPerformed

    private void reminderHistoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reminderHistoryTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) reminderHistoryTable.getModel();
        int rowNo = reminderHistoryTable.getSelectedRow();
        contentTextArea.setText("");
        contentTextArea.setText(model.getValueAt(rowNo, 2).toString());
        reminderTypeComboBox.setSelectedItem(model.getValueAt(rowNo, 1).toString());
    }//GEN-LAST:event_reminderHistoryTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ICTextField;
    private javax.swing.JTabbedPane JTabbedPane;
    private javax.swing.JPanel accountDetailsPanel;
    private javax.swing.JPanel accountDetailsPanel1;
    private javax.swing.JTable accountPendingToAddInformationTable;
    private javax.swing.JTable accountPendingToAddInformationsGuardTable;
    private javax.swing.JTable accountPendingToAddInformationsTable;
    private javax.swing.JTable accountWithInformationsGuardTable;
    private javax.swing.JTable accountWithInformationsTable;
    private javax.swing.JPanel apartmentManagerNavBar;
    private javax.swing.JPanel apartmentManagerPanel;
    private javax.swing.JTextField appearingProfileNameTextField;
    private javax.swing.JTable attendanceTable;
    private javax.swing.JLabel billAmountLabel;
    private javax.swing.JPanel billDetailsPanel;
    private javax.swing.JPanel billDetailsPanel1;
    private javax.swing.JLabel billIDLabel;
    private javax.swing.JLabel billMonth;
    private javax.swing.JLabel billTypeLabel;
    private javax.swing.JLabel carPlateAsterisk;
    private javax.swing.JTextField carPlateTextField;
    private javax.swing.JLabel checkInOutLabel;
    private javax.swing.JPanel checkInOutPanel;
    private javax.swing.JPanel checkInOutScreen;
    private java.awt.Checkbox checkbox1;
    private javax.swing.JTextArea contentTextArea;
    private javax.swing.JTextField countryCode;
    private javax.swing.JTextField countryCode1;
    private javax.swing.JTextField countryCode3;
    private javax.swing.JLabel date;
    private javax.swing.JLabel dateInLabel;
    private javax.swing.JLabel dateOutLabel;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JTextField emailTextFieldResident;
    private javax.swing.JLabel exitLabel;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JLabel expiredDateLabel;
    private javax.swing.JTextArea forumChatScreen;
    private javax.swing.JLabel forumLabel;
    private javax.swing.JLabel forumLabel1;
    private javax.swing.JPanel forumPanel;
    private javax.swing.JPanel forumScreen;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JComboBox<String> genderComboBox2;
    private javax.swing.JTable guardAccountsTable;
    private javax.swing.JTextField guardIDTextField;
    private javax.swing.JPanel guardNavBar;
    private javax.swing.JPanel guardPanel;
    private javax.swing.JPanel guardReport;
    private javax.swing.JLabel heyLabel;
    private javax.swing.JTable historyDataTable;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel homeLabel1;
    private javax.swing.JPanel homePanelGuard;
    private javax.swing.JPanel homeScreenGuard;
    private javax.swing.JPanel homeScreenResident;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JPanel informationsDetailsPanel;
    private javax.swing.JPanel informationsScreenApartmentManager;
    private javax.swing.JLabel issueDateLabel;
    private javax.swing.JLabel issuePaidDateLabel;
    private javax.swing.JLabel issuePaidDateLabel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel logOutLabel;
    private javax.swing.JPanel logOutPanel;
    private javax.swing.JLayeredPane mainHomePage;
    private javax.swing.JLabel manageAccountsLabel;
    private javax.swing.JPanel manageAccountsPanel;
    private javax.swing.JPanel manageAccountsScreenApartmentManager;
    private javax.swing.JPanel manageProfilePanel;
    private javax.swing.JPanel manageProfileScreenResident;
    private javax.swing.JComboBox<String> monthFromComboBox;
    private javax.swing.JComboBox<String> monthToComboBox;
    private javax.swing.JTable mostOvernightsTable;
    private javax.swing.JTable mostVisitedTable;
    private javax.swing.JTable mostVisitedUnitsTable;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField nameTextField1;
    private javax.swing.JPasswordField newPinField;
    private javax.swing.JPasswordField oldPinField;
    private javax.swing.JTable overallAccountsTable;
    private javax.swing.JTable overallBillRecordTable;
    private javax.swing.JTable overallReportTable;
    private javax.swing.JSpinner overnightDaysSpinner;
    private javax.swing.JTable paidBillsTable;
    private javax.swing.JPanel paidPanel;
    private javax.swing.JTable paidRecordTable;
    private javax.swing.JLabel parkingLotAsterisk;
    private javax.swing.JTextField parkingLotTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JLabel payBillsLabel;
    private javax.swing.JPanel payBillsPanel;
    private javax.swing.JPanel payBillsScreen;
    private javax.swing.JScrollPane paymentReportTab;
    private javax.swing.JScrollPane paymentReportTab1;
    private javax.swing.JScrollPane paymentReportTab2;
    private javax.swing.JTextField phoneNumberTextField;
    private javax.swing.JTextField phoneNumberTextField1;
    private javax.swing.JTextField phoneNumberTextField3;
    private javax.swing.JComboBox<String> relationshipToOwnerComboBox;
    private javax.swing.JTable reminderHistoryTable;
    private javax.swing.JLabel reminderLabel;
    private javax.swing.JPanel reminderPanel;
    private javax.swing.JPanel reminderScreenApartmentManager;
    private javax.swing.JComboBox<String> reminderTypeComboBox;
    private javax.swing.JLabel reportLabel;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JPanel reportScreenApartmentManager;
    private javax.swing.JLabel reportTableLabel;
    private javax.swing.JPanel reportsPanel;
    private javax.swing.JTable residentAccountsTable;
    private javax.swing.JPanel residentNavBar;
    private javax.swing.JPanel residentPanel;
    private javax.swing.JLabel residentRealNameLabel;
    private javax.swing.JLabel residentRealNameLabel2;
    private javax.swing.JPanel residentReport;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JLayeredPane screenApartmentManager;
    private javax.swing.JLayeredPane screenGuard;
    private javax.swing.JLayeredPane screenReisdent;
    private javax.swing.JTextField searchBarTextField;
    private javax.swing.JButton searchButton1;
    private javax.swing.JComboBox<String> searchByComboBox;
    private javax.swing.JLabel selectedAccountUsername;
    private javax.swing.JLabel selectedAccountUsername1;
    private javax.swing.JPanel sharedPanel;
    private javax.swing.JPanel sideNavBar;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTextField stayingUnitTextField;
    private javax.swing.JTextArea textArea;
    private javax.swing.JLabel time;
    private javax.swing.JLabel timeInLabel;
    private javax.swing.JLabel timeOutLabel;
    private javax.swing.JPanel timeOutPanel;
    private javax.swing.JTable timeOutTable;
    private javax.swing.JTable unpaidBillsTable;
    private javax.swing.JPanel unpaidPanel;
    private javax.swing.JTable unpaidRecordTable;
    private javax.swing.JTextField usernameAccountTextField;
    private javax.swing.JTextField visitUnitTextField;
    private javax.swing.JPanel visitorReport;
    private javax.swing.JScrollPane visitorReportTab;
    private javax.swing.JScrollPane visitorReportTab1;
    private javax.swing.JScrollPane visitorReportTab2;
    private javax.swing.JPanel visitorsCarsDetails;
    private javax.swing.JTable visitorsCarsDetailsTable;
    private javax.swing.JLabel welcomeTextLabel;
    private javax.swing.JComboBox<String> yearFromComboBox;
    private javax.swing.JComboBox<String> yearToComboBox;
    // End of variables declaration//GEN-END:variables
}
