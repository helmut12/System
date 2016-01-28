/*
 * DatePanel.java
 *
 * Created on May 1, 2003, 7:40 PM
 */

package wagwaan.accounts;

//import com.afrisoftech.hrmlib.*;

import wagwaan.reports.PaidUpvsExpectedIncomePdf;
import wagwaan.config.AMSUtility;
import wagwaan.config.ConnectionDB;
import wagwaan.config.TableModel;


/**
 *
 * @author  Fowami
 */
public class ExpectedIncomeDates extends javax.swing.JDialog {
    
    int reportName;
    
    java.text.SimpleDateFormat simpleDateFormat;
    
    java.sql.Connection connectDB = null;
    
    java.util.Vector dateStartEnd = null;
    
    javax.swing.JSpinner beginDateSpinner = null;
    
    javax.swing.JSpinner endDateSpinner = null;
     org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
   
    /** Creates new form DatePanel  Frame,modal,report no,*/
    public ExpectedIncomeDates(java.awt.Frame parent,  boolean modal) {
        
        super(parent, modal);
        
        
        //      dateStartEnd = new java.util.Vector(1,1);
        
        simpleDateFormat = new java.text.SimpleDateFormat("d/M/yy");
        connectDB=ConnectionDB.getInstance().getCon();
        initComponents();
        
        //       return dateStartEnd;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSearchDialog21 = new javax.swing.JDialog();
        jSearchPanel21 = new javax.swing.JPanel();
        jTextField1131 = new javax.swing.JTextField();
        jSearchScrollPane21 = new javax.swing.JScrollPane();
        jSearchTable21 = new javax.swing.JTable();
        jButton521 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        searchButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jSearchDialog21.setModal(true);
        jSearchDialog21.setUndecorated(true);
        jSearchDialog21.getContentPane().setLayout(new java.awt.GridBagLayout());

        jSearchPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSearchPanel21.setLayout(new java.awt.GridBagLayout());

        jTextField1131.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1131CaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jSearchPanel21.add(jTextField1131, gridBagConstraints);

        jSearchTable21.setToolTipText("");
        jSearchTable21.setShowHorizontalLines(false);
        /*javax.swing.table.TableColumn column = null;

        for (int i = 0; i < 4; i++) {

            column = jSearchTable2.getColumnModel().getColumn(i);

            if (i == 1) {

                column.setPreferredWidth(400);
                //sport column is bigger
            } else {

                column.setPreferredWidth(200);

            }
        }
        */
        jSearchTable21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSearchTable21MouseClicked(evt);
            }
        });
        jSearchScrollPane21.setViewportView(jSearchTable21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jSearchPanel21.add(jSearchScrollPane21, gridBagConstraints);

        jButton521.setText("Close");
        jButton521.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton521ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jSearchPanel21.add(jButton521, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jSearchDialog21.getContentPane().add(jSearchPanel21, gridBagConstraints);

        setTitle("Begin & End Date Panel");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(230, 230, 230));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel41.setLayout(new java.awt.GridBagLayout());

        txtid.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel41.add(txtid, gridBagConstraints);

        searchButton1.setToolTipText("Search");
        searchButton1.setMaximumSize(new java.awt.Dimension(74, 53));
        searchButton1.setMinimumSize(new java.awt.Dimension(20, 20));
        searchButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        searchButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel41.add(searchButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jPanel41, gridBagConstraints);

        jLabel3.setText("Term ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel3, gridBagConstraints);

        setSize(new java.awt.Dimension(380, 164));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     this.dispose();   // Add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   getReport();
     this.dispose();
        // Add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
        AMSUtility.showSearchDialog(txtid, jSearchDialog21);
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void jTextField1131CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField1131CaretUpdate
        if(jTextField1131.getCaretPosition()>2){            
                jSearchTable21.setModel(TableModel.createTableVectors(connectDB, "select distinct(current_term_id) from term_fees_setup where current_term_id ilike '%"+jTextField1131.getText()+"%' "));
                jSearchScrollPane21.setViewportView(jSearchTable21);
            
        }
    }//GEN-LAST:event_jTextField1131CaretUpdate

    private void jSearchTable21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSearchTable21MouseClicked
        txtid.setText(jSearchTable21.getValueAt(jSearchTable21.getSelectedRow(), 0).toString());
        jSearchDialog21.dispose();      // Add your handling code here:
    }//GEN-LAST:event_jSearchTable21MouseClicked

    private void jButton521ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton521ActionPerformed
        jSearchDialog21.dispose();      // Add your handling code here:
    }//GEN-LAST:event_jButton521ActionPerformed
    
    /**
     * @param args the command line arguments
     */
   
    public java.util.Vector getBeginEndDates() {
        
        dateStartEnd = new java.util.Vector(1,1);
        
        dateStartEnd.addElement(beginDateSpinner.getValue().toString());
        
        dateStartEnd.addElement(endDateSpinner.getValue().toString());
        
        return dateStartEnd;
        
    }
    
    public void getReport() {
        
        
               PaidUpvsExpectedIncomePdf policy = new PaidUpvsExpectedIncomePdf();

                policy.PaidUpvsExpectedIncomePdf(connectDB, String.valueOf(txtid.getText()));

               
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton521;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JDialog jSearchDialog21;
    private javax.swing.JPanel jSearchPanel21;
    private javax.swing.JScrollPane jSearchScrollPane21;
    private javax.swing.JTable jSearchTable21;
    private javax.swing.JTextField jTextField1131;
    private javax.swing.JButton searchButton1;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
    
}
