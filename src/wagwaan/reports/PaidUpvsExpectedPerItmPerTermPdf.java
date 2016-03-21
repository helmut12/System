/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wagwaan.reports;

/**
 *
 * @author Helmut
 */

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import wagwaan.config.AMSUtility;
import wagwaan.config.DBObject;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCell;
import java.sql.*;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wagwaan.config.ReportUtil;
import wagwaan.config.SQLHelper;


public class PaidUpvsExpectedPerItmPerTermPdf implements java.lang.Runnable{
  
     java.awt.Desktop deskTop = Desktop.getDesktop();
    
    DBObject dbObject;
    
    int numberSeq = 0;
    
    public static java.sql.Connection connectDB = null;
    
    public java.lang.String dbUserName = null;
    
    org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
    
    boolean threadCheck = true;
    
    java.lang.Thread threadSample;
    
    com.lowagie.text.Font pFontHeader = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    com.lowagie.text.Font pFontHeader1 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
    
   
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    
    java.lang.Process prThread;
    AMSUtility label = new AMSUtility();
    private String term_id;
//            private String payables;
            private String current_class;
    
    public void PaidUpvsExpectedPerItmPerTermPdf(java.sql.Connection connDb, String cur_class, String term) {
        term_id=term;
        
        current_class=cur_class;
        connectDB = connDb;
        
        dbObject = new DBObject();
        
        threadSample = new java.lang.Thread(this, "SampleThread");
        
        System.out.println("threadSample created");
        
        threadSample.start();
        
        System.out.println("threadSample fired");
        
    }
    
    
    public void run() {
        
        System.out.println("System has entered running mode");
        
        while (threadCheck) {
            
            System.out.println("O.K. see how we execute target program");
            
            this.generatePdf();
            
            try {
                
                System.out.println("Right, let's wait for task to complete of fail");
                
                java.lang.Thread.currentThread().sleep(200);
                
                System.out.println("It's time for us threads to get back to work after the nap");
                
            } catch(java.lang.InterruptedException IntExec) {
                
                System.out.println(IntExec.getMessage());
                
            }
            
            threadCheck = false;
            
            
            System.out.println("We shall be lucky to get back to start in one piece");
            
        }
        
        if (!threadCheck) {
            
            
            
            Thread.currentThread().stop();
            
        }
        
    }
    
    public void generatePdf() {
       
        try {
            
            java.io.File tempFile = java.io.File.createTempFile("REP"+label.getDateLable()+"_", ".pdf");
            
            tempFile.deleteOnExit();
            
            java.lang.Runtime rt = java.lang.Runtime.getRuntime();
            
            java.lang.String debitTotal = null;
            
            java.lang.String creditTotal = null;
            com.lowagie.text.Document docPdf = new com.lowagie.text.Document(PageSize.A4.rotate());
            
            
                    
                    com.lowagie.text.pdf.PdfWriter.getInstance(docPdf, new java.io.FileOutputStream(tempFile));
                    
                    
                    Double amount = 0.00;
                    String date = null;
                    try {
                      java.sql.Statement st3 = connectDB.createStatement();
                        java.sql.Statement st4 = connectDB.createStatement();
                        
//     
                        java.sql.ResultSet rset4 = st4.executeQuery("SELECT date(now()) as Date");
        
                        while(rset4.next()){
                            date = rset4.getObject(1).toString();
                        }
                        
                    } catch(java.sql.SQLException SqlExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                        
                    }
                    
                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("Expected Income vs Paid Up Per Class Per Term- Page: "), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));
                    
                    docPdf.setFooter(footer);
                    
                    
                    docPdf.open();
                    
                    
     
                    ReportUtil.addCenteredTitlePage(docPdf, connectDB);    
                        
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(4);
                        
                        int headerwidths[] = {10, 30, 30, 30};
                        
                        table.setWidths(headerwidths);
                        
                        table.setWidthPercentage((100));
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        
                        
                        
                        
                        Phrase phrase; 
                        
                        table.getDefaultCell().setColspan(3);
                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        phrase=new Phrase("Income per Payable Per Term Per Class", pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setColspan(1);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        phrase = new Phrase("Printed On  :" +date , pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setColspan(4);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase=new Phrase("Current Class:  "+current_class+"       Current Term: "+term_id, pFontHeader);
                        table.addCell(phrase);
                        
                        
                        
                        
                        String payable=null;
                        ResultSet pays=SQLHelper.getResultset(connectDB, "select distinct payable_item from payables");
                        while(pays.next()){
                            payable=pays.getString(1);
                            System.out.println("Payable:    "+payable);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            phrase=new Phrase(payable, pFontHeader);
                            table.addCell(phrase);
                            
                            
                        table.getDefaultCell().setBorder(PdfCell.TOP | PdfCell.BOTTOM | PdfCell.LEFT | PdfCell.RIGHT);
                        
                        table.getDefaultCell().setColspan(1);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("No. of Students",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Amount Paid",pFontHeader);
                        table.addCell(phrase);
                        
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Expected Income",pFontHeader);
                        table.addCell(phrase);
                        
                        
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Balance",pFontHeader);
                        table.addCell(phrase);
                        
                        
                        
                         table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                       

                            
//                            table.getDefaultCell().setColspan(4);
                            java.sql.Statement st = connectDB.createStatement();
                            int student_count=0;
                            //no of students
                            java.sql.ResultSet rset = st.executeQuery("select COUNT(STUDENT_ID) FROM PAYABLES_MANAGER WHERE PAYABLE ilike '"+payable+"%' and class='"+current_class+"' AND TERM='"+term_id+"'");
                            
                            while (rset.next()) {
                                student_count=rset.getInt(1);
                            }
                            System.out.println("student count:  "+student_count);
                            double total_amount=0.0;
                            double ratio=0;
                            Statement st2=connectDB.createStatement();
                            //expected income per payable
                            ResultSet r=st2.executeQuery("select sum(amount) from payables_manager where class='"+current_class+"' and term='"+term_id+"'");
                            while(r.next()){
                                total_amount=r.getDouble(1);
                            }
                            System.out.println("Total Amount:   "+total_amount);
                            
                            
                            double amount_per_payable=0.0;
                            ResultSet ap=SQLHelper.getResultset(connectDB, "select amount from payables_manager where payable='"+payable+"' and class='"+current_class+"' and term='"+term_id+"'");
                            while(ap.next()){
                            amount_per_payable=ap.getDouble(1);
                            }
                            System.out.println("Amount for payable "+payable+" "+amount_per_payable);
                            
                            if(student_count>1){
                            ratio=amount_per_payable/(total_amount/student_count)*100;
                            }
                            else{
                            ratio=amount_per_payable/total_amount*100;
                            }
                            
                            double amount_paidup=0.0;
                            
                            ResultSet total=SQLHelper.getResultset(connectDB, "select sum(debit) from fees_ledger where current_term='"+term_id+"' and current_class='"+current_class+"'");
                            
                            while(total.next()){
                            amount_paidup=total.getDouble(1);
                            }
                            System.out.println("Amount paid up:     "+amount_paidup);
                            double paidup_per_payable=ratio*amount_paidup;
                            
                            System.out.println("total xp per payable:   "+paidup_per_payable);
                            //amount_per_payable
                            
                            double amount_payable=0.0;
                            ResultSet pay=SQLHelper.getResultset(connectDB, "select sum(credit) from fees_ledger where current_term='"+term_id+"' and current_class='"+current_class+"'");
                            while(pay.next()){
                            amount_payable=pay.getDouble(1);
                            }
                            
                            double amount_payable_per_payable=ratio*amount_payable;
                            System.out.println("amount payable per payable: "+amount_payable_per_payable);
                            
                            
                            double balance=amount_payable_per_payable-paidup_per_payable;
                            
                            
                            table.getDefaultCell().setColspan(1);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            phrase=new Phrase(student_count, "-");
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            phrase=new Phrase(""+paidup_per_payable+"");
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            phrase=new Phrase( ""+amount_payable_per_payable+"");
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            phrase=new Phrase(""+balance+"");
                            table.addCell(phrase);
                        }
                             docPdf.add(table);
                        docPdf.close();
                        deskTop.open(tempFile);
                    } catch (DocumentException | IOException | SQLException ex) {
             Logger.getLogger(PaidUpvsExpectedPerItmPerTermPdf.class.getName()).log(Level.SEVERE, null, ex);
         } 
                    
               
            
            
            
            
            
            
            
       
        
        
        
    
    
}
}








