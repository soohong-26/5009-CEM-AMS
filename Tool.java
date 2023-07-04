/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ams;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class Tool {
    private Color color;
    
    //To display date 
    public void displayDate(javax.swing.JLabel label){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        label.setText(stringDate);
    }
    
    //To display time
    public void displayTime(javax.swing.JLabel label){
        new Timer (0,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("kk:mm:ss");
                String time = s.format(d);
                label.setText(time);
            }
        }).start();
    }
    
    //Get error message pane
    public void getErrorMessageOptionPane(String prompt){
        JOptionPane.showMessageDialog(null, prompt,
      "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    //Get information message pane
    public void getInformationMessageOptionPane (String prompt){
        JOptionPane.showMessageDialog(null, prompt);
    }
    
    //Setting colors whenever mouse enter or exit different panels
    public Color mouseEnterColorHome() {
        return color = new Color (0,74,112);
    }
    
    public Color mouseExitColorHome() {
        return color = new Color (0,48,73);
    }
    
    public Color mouseEnterColor() {
        return color = new Color(255,221,157);
    }
    
    public Color mouseExitColor() {
        return color = new Color(252,191,73);
    }
    
    public Color mouseEnterLogOut() {
        return color = new Color (255,163,101);
    }
    
    public Color mouseExitLogOut(){
        return color = new Color(255,102,0);
    }
    
    public Color mouseEnterExit() {
        return color = new Color(223,92,92);
    }
    
    public Color mouseExitExit(){
        return color = new Color(214,40,40);
    }
    
    //Setting text field gained focus color, whenever the text field gained focus
    public Color textGainedFocusColor() {
        return color = new Color (0,0,0);
    }
    
    //Setting text field lost focus color, whenever the text field lost focus
    public Color textLostFocusColor() {
        return color = new Color (153, 153, 153);
    }
    
    //Getting split number, as or text field for phone number is have
    //country code and phone number
    public String[] getSplitNumber (String phoneNumber){
         String[] phoneNumberArr = new String[2];
         int tempIndex = 0;
         
         for (int i=0; i<phoneNumber.length(); i++){
             if (phoneNumber.substring(i, i+1).equals("-")){
                 tempIndex = i;
             }
         }
         phoneNumberArr[0] = phoneNumber.substring(0, tempIndex);
         phoneNumberArr[1] = phoneNumber.substring(tempIndex+1);
         return phoneNumberArr;
    }
}
