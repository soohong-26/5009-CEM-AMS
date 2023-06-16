/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ams;

import java.awt.Color;

/**
 *
 * @author User
 */
public class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    
    private Tool tool = new Tool();
    private Account account;
    
    public HomePage() {
        initComponents();
    }
      
    public HomePage(Account account) {
        this.account = account;
        initComponents();
        this.account = account;
        tool.displayDate(date);
        tool.displayTime(time);
        showPanel();
    }
    
    private void showPanel (){
        if (account.getRole().equals("GUARD")) {
            residentPanel.setVisible(false);
            guardPanel.setVisible(true);
            return;
        }
        
        if (account.getRole().equals("RESIDENT")){
            residentPanel.setVisible(true);
            guardPanel.setVisible(false);
            return;
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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        guardPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        checkInOutPanel = new javax.swing.JPanel();
        checkInOutLabel1 = new javax.swing.JLabel();
        homePanelGuard = new javax.swing.JPanel();
        homeLabel1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        residentPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        homePanelResident = new javax.swing.JPanel();
        homeLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AMS | Home Page");
        setPreferredSize(new java.awt.Dimension(1310, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 48, 73));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Super Solution AMS | Home page");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, -1));

        time.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));
        time.setText("0");
        jPanel2.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 50, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("0");
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Welcome, ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 350, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 80));

        guardPanel.setBackground(new java.awt.Color(247, 127, 0));
        guardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(252, 191, 73));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkInOutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkInOutLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkInOutLabel1.setText("Check in/out");
        checkInOutPanel.add(checkInOutLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 99, -1));

        jPanel4.add(checkInOutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 160, 50));

        homePanelGuard.setBackground(new java.awt.Color(0, 48, 73));
        homePanelGuard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homePanelGuardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homePanelGuardMouseExited(evt);
            }
        });
        homePanelGuard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        homeLabel1.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel1.setText("Home");
        homePanelGuard.add(homeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 15, 43, -1));

        jPanel4.add(homePanelGuard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 160, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Features");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 100, -1, -1));

        guardPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 640));

        jPanel13.setBackground(new java.awt.Color(247, 127, 0));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        guardPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 1120, 640));

        getContentPane().add(guardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1280, 640));

        residentPanel.setBackground(new java.awt.Color(247, 127, 0));
        residentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(252, 191, 73));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        homeLabel.setText("Home");

        javax.swing.GroupLayout homePanelResidentLayout = new javax.swing.GroupLayout(homePanelResident);
        homePanelResident.setLayout(homePanelResidentLayout);
        homePanelResidentLayout.setHorizontalGroup(
            homePanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelResidentLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(homeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        homePanelResidentLayout.setVerticalGroup(
            homePanelResidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelResidentLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(homeLabel)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.add(homePanelResident, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        residentPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 640));

        jPanel12.setBackground(new java.awt.Color(247, 127, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        residentPanel.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 1120, 640));

        getContentPane().add(residentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1280, 640));

        setSize(new java.awt.Dimension(1294, 727));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homePanelGuardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelGuardMouseEntered
        homePanelGuard.setBackground(tool.mouseEnterColorHome());
    }//GEN-LAST:event_homePanelGuardMouseEntered

    private void homePanelGuardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePanelGuardMouseExited
        homePanelGuard.setBackground(tool.mouseExitColorHome());
    }//GEN-LAST:event_homePanelGuardMouseExited

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
    private javax.swing.JLabel checkInOutLabel1;
    private javax.swing.JPanel checkInOutPanel;
    private javax.swing.JLabel date;
    private javax.swing.JPanel guardPanel;
    private javax.swing.JLabel homeLabel;
    private javax.swing.JLabel homeLabel1;
    private javax.swing.JPanel homePanelGuard;
    private javax.swing.JPanel homePanelResident;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel residentPanel;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}
