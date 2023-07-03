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
    
    public void displayDate(javax.swing.JLabel label){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        label.setText(stringDate);
    }
    
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
    
    public void getErrorMessageOptionPane(String prompt){
        JOptionPane.showMessageDialog(null, prompt,
      "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void getInformationMessageOptionPane (String prompt){
        JOptionPane.showMessageDialog(null, prompt);
    }
    
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
        return color = new Color (225,96,96);
    }
    
    public Color mouseExitLogOut(){
        return color = new Color(214,40,40);
    }
    
    public Color textGainedFocusColor() {
        return color = new Color (0,0,0);
    }
    
    public Color textLostFocusColor() {
        return color = new Color (153, 153, 153);
    }
}
