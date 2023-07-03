/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ams;

/**
 *
 * @author User
 */
public class AMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String test = "Hello";
        int tempIndex = 0;
        String text = new String();
        
        for (int i=0; i<test.length();i++){
            if (test.substring(i, i+1).equals("e")){
                tempIndex = i;
            }
        }
        
        tempIndex++;
        text = test.substring(tempIndex, test.length());
        System.out.println(text);
    }
    
}
