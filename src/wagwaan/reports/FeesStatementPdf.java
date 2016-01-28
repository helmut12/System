 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wagwaan.reports;

//import com.ams.ohs0.*;
//import com.afrisoftech.utils.AMSUtility;
//import com.ams.common.reports.ReportUtil;
import wagwaan.config.AMSUtility;
import wagwaan.config.DBObject;
import wagwaan.config.ReportUtil;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCell;
import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
/**
 *
 * @author Maurice
 */
 
public class FeesStatementPdf implements java.lang.Runnable {
    java.util.Date beginDate = null;
     java.awt.Desktop deskTop = Desktop.getDesktop();
    java.util.Date endDate = null;
    
     DBObject dbObject;
    
    int numberSeq = 0;
    
    public static java.sql.Connection connectDB = null;
    
    public java.lang.String dbUserName = null;
    
//    org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
    
    boolean threadCheck = true;
    
    java.lang.Thread threadSample;
    
    com.lowagie.text.Font pFontHeader = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
    com.lowagie.text.Font pFontHeader1 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
    
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    
    java.lang.Process prThread;
    private String term_id;
    private String current_class;
    private String student_id;
    
    public void FeesStatementPdf(java.sql.Connection connDb, String term, String daro, String std_id) {
        term_id=term;
        current_class=daro;
        student_id=std_id;
        connectDB = connDb;
       
//        beginDate = begindate;
       
//        endDate = enddate;
        
        dbObject = new DBObject();
        
        threadSample = new java.lang.Thread(this,"SampleThread");
        
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
            
            java.io.File tempFile = java.io.File.createTempFile("REP"+ AMSUtility.getDateLable()+"_", ".pdf");
            
            tempFile.deleteOnExit();
            
            com.lowagie.text.Document docPdf = new com.lowagie.text.Document(PageSize.A4.rotate());
         
            try {
                
                try {
                    
                    com.lowagie.text.pdf.PdfWriter.getInstance(docPdf, new java.io.FileOutputStream(tempFile));
                    
                    
                    String compName = null;
                    String date = null;
                    try {
                        java.sql.Statement st4 = connectDB.createStatement();
                        
                        java.sql.ResultSet rset4 = st4.executeQuery("SELECT date('now') as Date");
                        while(rset4.next())
                            date = rset4.getObject(1).toString();
                        
                    } catch(java.sql.SQLException SqlExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                        
                    }
                    
                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("Fees Statement Per Student - Page :-"), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));
                    
                    docPdf.setFooter(footer);
                    
                    
                    docPdf.open();
                    ReportUtil.addTitlePage(docPdf, connectDB);
                    
                    try {
                        
                        
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(2);
                        
                        int headerwidths[] = {50, 50};
                        
                        table.setWidths(headerwidths);
                        
                        table.setWidthPercentage((100));
                        
//                         table.setHeaderRows(2);
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        
                        table.getDefaultCell().setColspan(2);
                        
                        
                        Phrase phrase;
                        
                        table.getDefaultCell().setBorder(PdfCell.TOP | PdfCell.BOTTOM | PdfCell.LEFT | PdfCell.RIGHT);
                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
                        table.getDefaultCell().setColspan(1);
                        phrase = new Phrase("Fees Statement Per Student: ",  pFontHeader);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.addCell(phrase);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        phrase = new Phrase("Printed On  :" +date , pFontHeader);
                        table.addCell(phrase);
                        //table.addCell(phrase);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        
                        table.getDefaultCell().setColspan(1);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                        try {
                            String slip_no=null;
                            Statement st2=connectDB.createStatement();
                            ResultSet rset2=st2.executeQuery("SELECT slip_no FROM fees_ledger f, student_registration stu_reg where f.student_id=stu_reg.student_id "
                                    + "and current_term ='"+term_id+"' and current_class='"+current_class+"' and F.student_id='"+student_id+"'");
                            while(rset2.next()){
                            slip_no=rset2.getString(1);
                            }
                            Statement st = connectDB.createStatement();
                            ResultSet rset = st.executeQuery("SELECT slip_no, f.student_id, upper(stu_reg.first_name|| ' '||middle_name||' '||last_name) as names, "
                                    + "current_class, current_term FROM fees_ledger f, student_registration stu_reg where f.student_id=stu_reg.student_id "
                                    + "and current_term ='"+term_id+"' and current_class='"+current_class+"' and F.student_id='"+student_id+"' ");
                        
                            
                            while (rset.next()) {

                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
//                                table.getDefaultCell().setColspan(1);
                                numberSeq = numberSeq + 1;

                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Slip No: "+dbObject.getDBObject(rset.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Student ID: "+dbObject.getDBObject(rset.getObject(2), "-"), pFontHeader1);
                                table.addCell(phrase);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Student Names: "+dbObject.getDBObject(rset.getObject(3), "-"), pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Class: "+dbObject.getDBObject(rset.getObject(4), "-"), pFontHeader1);
                                table.addCell(phrase);
                                 table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Term: "+dbObject.getDBObject(rset.getObject(5), "-"), pFontHeader1);
                                table.addCell(phrase);    
                            }
                            int countreceipt=0;
                            Statement st1=connectDB.createStatement();
                            ResultSet rs1=st1.executeQuery("select count(receipt_no) from fees_mgt where student_id='"+student_id+"' "
                                    + "and slip_no='"+slip_no+"' and current_class='"+current_class+"' AND current_term_id='"+term_id+"'");
                            while(rs1.next()){
                            countreceipt=rs1.getInt(1);
                            }
                            String receipt=null;
                            
                            Statement st_receipt=connectDB.createStatement();
                            ResultSet rs_receipt=st_receipt.executeQuery("select receipt_no from fees_mgt where student_id='"+student_id+"' "
                                        + "and slip_no='"+slip_no+"' and current_class='"+current_class+"' AND current_term_id='"+term_id+"' order by payment_date asc");
                            
                            while(rs_receipt.next()){
                                receipt=rs_receipt.getString(1);
                                System.out.println(receipt);
                                Statement st3=connectDB.createStatement();
                                ResultSet rset3=st3.executeQuery("select receipt_no, payment_mode, payment_date, payables from fees_mgt where student_id='"+student_id+"' "
                                        + "and slip_no='"+slip_no+"' and current_class='"+current_class+"' AND current_term_id='"+term_id+"' and receipt_no='"+receipt+"' order by payment_date asc");
                            table.getDefaultCell().setColspan(2);
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
//                            for(int i=0;i<countreceipt;i++){
                                
                                    while(rset3.next()){
                                table.getDefaultCell().setColspan(1);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Receipt No: "+dbObject.getDBObject(rset3.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Payment Mode: "+dbObject.getDBObject(rset3.getObject(2), "-"), pFontHeader1);
                                table.addCell(phrase);
                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Payment Date: "+dbObject.getDBObject(rset3.getObject(3), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                                    
                            String payables=null;
                            Statement st4=connectDB.createStatement();
                            ResultSet rset4=st4.executeQuery("select payables from fees_mgt where student_id='"+student_id+"' and slip_no='"+slip_no+"' and "
                                    + "current_class='"+current_class+"' AND current_term_id='"+term_id+"' and receipt_no='"+receipt+"' order by payment_date asc");
                            while(rset4.next()){
                            payables=rset4.getString(1);
                            }
                            phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    phrase=new Phrase("PAYABLES");
                                    table.addCell(phrase);
                            table.getDefaultCell().setColspan(1);
                            //TRANSPORT
                            if(payables.contains("Transport")){
                            Statement st5=connectDB.createStatement();
                            ResultSet rset5=st5.executeQuery("select location, fees from location, fees_ledger where location=location_name and student_id='"+student_id+"' "
                                    + "and current_term='"+term_id+"' and current_class='"+current_class+"' ");
                                
                                table.getDefaultCell().setColspan(1);
                                while(rset5.next()){   
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Transport: "+dbObject.getDBObject(rset5.getObject(2), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//END OF TRANSPORT
                            
                            //BEGIN TUTION
                            if(payables.contains("Tution")){
                            Statement st6=connectDB.createStatement();
                            ResultSet rset6=st6.executeQuery("select tution_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset6.next()){
                                   table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Tution: "+dbObject.getDBObject(rset6.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//end tution
                            if(payables.contains("Lunch")){//lunch
                            Statement st7=connectDB.createStatement();
                            ResultSet rset7=st7.executeQuery("select lunch_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset7.next()){
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Lunch: "+dbObject.getDBObject(rset7.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//end lunch
                            if(payables.contains("Extra-Tution")){//extra-tution
                            Statement st8=connectDB.createStatement();
                            ResultSet rset8=st8.executeQuery("select extra_tution from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset8.next()){
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Extra- Tution: "+dbObject.getDBObject(rset8.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//endextra-tution
                            if(payables.contains("Tea/Milk")){//tea
                            Statement st9=connectDB.createStatement();
                            ResultSet rset9=st9.executeQuery("select tea_milk_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset9.next()){
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Tea/ Milk: "+dbObject.getDBObject(rset9.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//end tea
                            
                            if(payables.contains("Swimming")){//swimming
                            Statement st10=connectDB.createStatement();
                            ResultSet rset10=st10.executeQuery("select swimming_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset10.next()){
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Swimming: "+dbObject.getDBObject(rset10.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//end swimming
                            
                            if(payables.contains("Uniform")){//uniform
                            Statement st11=connectDB.createStatement();
                            ResultSet rset11=st11.executeQuery("select uniform_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset11.next()){
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Uniform: "+dbObject.getDBObject(rset11.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }//end uniform
                            
                            if(payables.contains("Diary")){//Diary
                            Statement st12=connectDB.createStatement();
                            ResultSet rset12=st12.executeQuery("select diary_fees from term_fees_setup where  current_term_id='"+term_id+"' and current_class='"+current_class+"'");
                            while(rset12.next()){
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Diary: "+dbObject.getDBObject(rset12.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            }
                            table.getDefaultCell().setColspan(2);
                            Statement st13=connectDB.createStatement();
                            ResultSet rset13=st13.executeQuery("SELECT paid, paid+balance, balance from fees_mgt where receipt_no='"+receipt+"'and current_class='"+current_class+"' and current_term_id='"+term_id+"' and receipt_no='"+receipt+"'");
                            while(rset13.next()){
                            phrase = new Phrase("Amount Paid for(receipt_no "+receipt+"): "+dbObject.getDBObject(rset13.getObject(1), "-"), pFontHeader1);
                                table.addCell(phrase);
                                phrase = new Phrase("Amount Payable: "+dbObject.getDBObject(rset13.getObject(2), "-"), pFontHeader1);
                                table.addCell(phrase);
                                phrase = new Phrase("Balance: "+dbObject.getDBObject(rset13.getObject(3), "-"), pFontHeader1);
                                table.addCell(phrase);
                            }
                            
                            
                            countreceipt--;
                            System.out.println(countreceipt);
                            }
                            table.getDefaultCell().setColspan(2);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase=new Phrase("Signature:...........................................................................................................");
                            table.addCell(phrase);
                            
                            docPdf.add(table);
                            
                        } catch(java.sql.SQLException SqlExec) {
                            
                            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
//                            System.err.println(SqlExec);
                            SqlExec.printStackTrace();
                        }
                    } catch(com.lowagie.text.BadElementException BadElExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), BadElExec.getMessage());
                        
                    }
                    
                } catch(java.io.FileNotFoundException fnfExec) {
                    
                    javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), fnfExec.getMessage());
                    
                }
            } catch(com.lowagie.text.DocumentException lwDocexec) {
                
                javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), lwDocexec.getMessage());
                
            }
            
            try {
                docPdf.close();
                 deskTop.open(tempFile);
            
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
                
            }

        } catch(java.io.IOException IOexec) {
            
            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), IOexec.getMessage());
            
        }
        
    }
    
}
