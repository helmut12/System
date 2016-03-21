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

import wagwaan.config.AMSUtility;
import wagwaan.config.DBObject;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import java.util.Date;
import com.lowagie.text.pdf.PdfCell;
import java.sql.*;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wagwaan.config.ReportUtil;


public class PurchasesvsFeesPdf implements java.lang.Runnable{
  
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
            private Date beginDate, endDate;
    
    public void PurchasesvsFeesPdf(java.sql.Connection connDb, Date begin, Date end) {
        beginDate=begin;
        endDate=end;
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
            
            try {
                
                try {
                    
                    com.lowagie.text.pdf.PdfWriter.getInstance(docPdf, new java.io.FileOutputStream(tempFile));
                    
                    
                    Double amount = 0.00;
                    String date = null;
                    try {
                      
                        java.sql.Statement st4 = connectDB.createStatement();
                        
                        
                        java.sql.ResultSet rset4 = st4.executeQuery("SELECT date(now()) as Date");
                        
                        while(rset4.next())
                            date = rset4.getObject(1).toString();
                        
                    } catch(java.sql.SQLException SqlExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                        
                    }
                    
                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("Purchases vs Fees Paid - Page: "), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));
                    
                    docPdf.setFooter(footer);
                    
                    
                    docPdf.open();
                    
                    
                    try {
                    ReportUtil.addCenteredTitlePage(docPdf, connectDB);    
                        
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
                        
                        int headerwidths[] = {20, 20, 20, 20, 20};
                        
                        table.setWidths(headerwidths);
                        
                        table.setWidthPercentage((100));
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        
                        table.getDefaultCell().setColspan(3);
                        
                        
                        Phrase phrase; 
                        
                        
                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        phrase=new Phrase("Purchases versus Fees Paid");
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setColspan(2);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        phrase = new Phrase("Printed On  :" +date , pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setColspan(5);
                        String receiving_no=null;
                        table.getDefaultCell().setColspan(1);
                        table.getDefaultCell().setBorder(PdfCell.TOP | PdfCell.BOTTOM | PdfCell.LEFT | PdfCell.RIGHT);
                        
                        java.sql.Statement st3 = connectDB.createStatement();
                        
                        java.sql.ResultSet rset2 = st3.executeQuery("select distinct(receiving_no) from st_receiving_issuing where receiving_date between '"+beginDate+"' and '"+endDate+"'");
                        
                        while(rset2.next()){
                            receiving_no=rset2.getString(1);
                    
                        table.getDefaultCell().setColspan(5);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase=new Phrase("Receiving No. "+receiving_no);
                        table.addCell(phrase);
                        
                        
                        table.getDefaultCell().setColspan(1);
                        table.getDefaultCell().setBorder(PdfCell.TOP | PdfCell.BOTTOM | PdfCell.LEFT | PdfCell.RIGHT);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER); 
                        phrase = new Phrase("Order ID",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Item Name",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Quantity Received",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Discounted Price",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase = new Phrase("Total",pFontHeader);
                        table.addCell(phrase);
                        
                        
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        
                         table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                       
                        
                            
                            
                            java.sql.Statement st = connectDB.createStatement();
                            
                            java.sql.ResultSet rset = st.executeQuery("select order_id, itm.item_name, quantity_received, (buying_price-(buying_price*discount)) as discounted_price, \n" +
                            "(quantity_received*(buying_price-(buying_price*discount)))::numeric(10, 2) as total from st_receiving_issuing rec, stock_items itm "
                                    + "where itm.item_code=rec.item_code and receiving_no='"+receiving_no+"' and receiving_date between '"+beginDate+"' and '"+endDate+"'");
                            
                            while (rset.next()) {
                                
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                                table.getDefaultCell().setColspan(1);
                                numberSeq += 1;
                                
                            
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset.getObject(1), "-"),pFontHeader1);
                                table.addCell(phrase);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset.getObject(2), "-"),pFontHeader1);    
                                table.addCell(phrase);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset.getObject(3), "-"),pFontHeader1);
                               table.addCell(phrase);
                               
                               table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset.getObject(4), "-"),pFontHeader1);
                               table.addCell(phrase);
                               
                               table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset.getObject(5), "-"),pFontHeader1);
                               table.addCell(phrase);
                            
                            }
                            table.getDefaultCell().setColspan(5);
                            phrase=new Phrase(" ");
                            table.addCell(phrase);
                            
                            Statement st2=connectDB.createStatement();
                            ResultSet r=st2.executeQuery("select sum((quantity_received*(buying_price-(buying_price*discount)))::numeric(10, 2)) from stock_items i, "
                                    + "st_receiving_issuing r where r.item_code=i.item_code  and  receiving_no='"+receiving_no+"'  and receiving_date between '"+beginDate+"' and '"+endDate+"'");
                            while(r.next()){
                                table.getDefaultCell().setColspan(3);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Total for Receiving No. "+receiving_no+": ",pFontHeader);
                               table.addCell(phrase);
                                
                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(r.getObject(1), "-"),pFontHeader1);
                               table.addCell(phrase);
                               
                            }
                            
                            phrase=new Phrase(" ");
                            table.addCell(phrase);
                        }//end of receiving no loop
                        table.getDefaultCell().setColspan(5);
                        phrase=new Phrase(" ");
                            table.addCell(phrase);
                        Statement q=connectDB.createStatement();
                        ResultSet rq=q.executeQuery("select sum((quantity_received*(buying_price-(buying_price*discount)))::numeric(10, 2)) from stock_items i, st_receiving_issuing r "
                                + "where r.item_code=i.item_code  and receiving_date between '"+beginDate+"' and '"+endDate+"'");
                        
                        while(rq.next()){
                        table.getDefaultCell().setColspan(3);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("TOTAL EXPENSES FOR ALL RECEIVABLES FOR THE SPECIFIED PERIOD: ",pFontHeader);
                               table.addCell(phrase);
                                
                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rq.getObject(1), "-"),pFontHeader1);
                               table.addCell(phrase);
                 
                            
                        }
                        
                             docPdf.add(table);
                             
                             boolean newpage=docPdf.newPage();
                            com.lowagie.text.pdf.PdfPTable table2 = new com.lowagie.text.pdf.PdfPTable(7);
                        
                        int headerwidths2[] = {5, 15, 16, 16, 16, 16, 16};
                        
                        table2.setWidths(headerwidths2);
                        
                        table2.setWidthPercentage((100));
                        
//                         table.setHeaderRows(2);
                        table2.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        
                        
                        
                        table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        
                        table2.getDefaultCell().setColspan(1);
                        table2.getDefaultCell().setBorder(Rectangle.TOP | Rectangle.LEFT |Rectangle.BOTTOM| Rectangle.RIGHT);
                        //   table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                        
                        phrase = new Phrase("#",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("ID",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("PAID",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("PAYABLE",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("BALANCE",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("PAYMENT DATE",pFontHeader);
                        table2.addCell(phrase);
                        
                        phrase = new Phrase("PAYMENT MODE",pFontHeader);
                        table2.addCell(phrase);
                        
                        
                        
                        table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        table2.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                      //  try {
                            
                            java.sql.Statement st2 = connectDB.createStatement();
                            
                            
                            java.sql.ResultSet rset4 = st2.executeQuery("select student_id, debit as paid, credit as payable_amount, credit-debit as balance, payment_date, payment_mode "
                                    + "from fees_ledger  where payment_date between '"+beginDate+"' and '"+endDate+"'");
                        
                            int count=1;
                            while (rset4.next()) {

                                table2.getDefaultCell().setColspan(1);
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(""+count+"", pFontHeader1);
                                table2.addCell(phrase);
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(1), "-"), pFontHeader1);
                                table2.addCell(phrase);
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(2), "-"), pFontHeader1);
                                table2.addCell(phrase);
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(3), "-"), pFontHeader1);
                                table2.addCell(phrase);

                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(4), "-"), pFontHeader1);
                                table2.addCell(phrase);
                                
                                 table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(5), "-"), pFontHeader1);
                                table2.addCell(phrase);
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(dbObject.getDBObject(rset4.getObject(6), "-"), pFontHeader1);
                                table2.addCell(phrase);
                               
                                count++;
                            }
                            System.err.println("rrr");
                            table2.getDefaultCell().setColspan(7);
                            
                            Statement f=connectDB.createStatement();
                            ResultSet rf=f.executeQuery("select sum(debit) from fees_ledger where payment_date between '"+beginDate+"' and '"+endDate+"'");
                            
                            while(rf.next()){
                                
                                table2.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Total fees Paid: "+dbObject.getDBObject(rf.getObject(1), "-"), pFontHeader);
                                table2.addCell(phrase);
                                
                            }
                            
                            docPdf.add(table2);
                             
                             
                             docPdf.close();
            
            deskTop.open(tempFile);
                            
                        } catch(java.sql.SQLException SqlExec) {
                            
                            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                            
                        }
                        
                      
                    } catch(com.lowagie.text.BadElementException BadElExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), BadElExec.getMessage());
                        
                    }
                    
                } catch(java.io.FileNotFoundException fnfExec) {
                    
                    javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), fnfExec.getMessage());
                    
                }
            } catch(com.lowagie.text.DocumentException lwDocexec) {
                
                javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), lwDocexec.getMessage());
                
            } catch (IOException ex) {
             Logger.getLogger(PurchasesvsFeesPdf.class.getName()).log(Level.SEVERE, null, ex);
         }
            
            
            
            
            
        } 
        
        
        
    }
    