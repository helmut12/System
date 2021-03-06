/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wagwaan.setup;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import wagwaan.config.ConnectionDB;
import wagwaan.config.SQLHelper;
/**
 *
 * @author Helmut
 */
public class ExamLevelsIntfr extends javax.swing.JInternalFrame {
    Connection con;
    /**
     * Creates new form ExamLevelsIntfr
     */
    public ExamLevelsIntfr() {
        initComponents();
        con=ConnectionDB.getInstance().getCon();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("EXAM LEVELS");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Level ID");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jTextField1.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTextField1, gridBagConstraints);

        jLabel2.setText("Level Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTextField2, gridBagConstraints);

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jButton1, gridBagConstraints);

        jButton3.setText("RESET");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jButton3, gridBagConstraints);

        jButton4.setText("CLOSE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jButton4, gridBagConstraints);

        jLabel3.setText("Maximum Score");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel3, gridBagConstraints);

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTextField3, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jTextField2.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "please input the level name", "missing variable", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(jTextField3.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please input the maximum score", "missing variable", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        PreparedStatement pr=null;
        String sql="insert into exam_levels(level_id, level, MAXIMUM_SCORE)values(?, ?, ?::INT)";
        String id=null;
        try {
            con.setAutoCommit(false);
//            select 'INV NO.' || nextval('invoice_no_seq')
            ResultSet rs=SQLHelper.getResultset(con, "select 'LVL NO.' || nextval('exam_level_seq')");
            while(rs.next()){
                id=rs.getString(1);
            }
            pr=con.prepareStatement(sql);
            pr.setString(1, id);
            pr.setString(2, jTextField2.getText());
            pr.setObject(3, jTextField3.getText());
            pr.executeUpdate();
            
            con.commit();
            con.setAutoCommit(true);
            
            if(pr!=null){
                JOptionPane.showMessageDialog(this, "Insert is Successful");
                resetDetails();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ExamLevelsIntfr.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ExamLevelsIntfr.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        resetDetails();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        Character ch=evt.getKeyChar();
        if(!Character.isDigit(ch)){
            JOptionPane.showMessageDialog(this, "Maximum score cannot be an alphabet. It must a non-decimal number", "Data type mismatch", JOptionPane.ERROR_MESSAGE);
            jTextField3.setText("");
        }
        int val=Integer.parseInt(jTextField3.getText());
            if(val<0 || val>100){
                JOptionPane.showMessageDialog(this, "maximum score cannot be less than zero or greater than 100", "Invalid entries", JOptionPane.INFORMATION_MESSAGE);
                jTextField3.setText("");
            }
    }//GEN-LAST:event_jTextField3KeyReleased
        private void resetDetails(){
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
