/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wagwaan.reports;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCell;
import java.awt.Desktop;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import wagwaan.config.AMSUtility;
import wagwaan.config.DBObject;
import wagwaan.config.ReportUtil;

/**
 *
 * @author Helmut
 */
public class IndividualReportCardPdf implements Runnable{
    java.awt.Desktop deskTop = Desktop.getDesktop();
    
    DBObject dbObject;
    
    int numberSeq = 0;
    
    public static java.sql.Connection connectDB = null;
    
    public java.lang.String dbUserName = null;
    
    org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
    
    boolean threadCheck = true;
    
    java.lang.Thread threadSample;
    
    com.lowagie.text.Font pFontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
    com.lowagie.text.Font pFontHeaderLARGE = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
    com.lowagie.text.Font pFontHeaderN = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
    
   
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    
    java.lang.Process prThread;
    AMSUtility label = new AMSUtility();
    private String student_id, term_id, curr_class;
    public void IndividualReportCardPdf(java.sql.Connection connDb, String std_id, String term, String daro) {
        this.student_id=std_id;
        this.term_id=term;
        this.curr_class=daro;
        connectDB = connDb;
        
        dbObject = new DBObject();
        
        threadSample = new java.lang.Thread( this, "SampleThread");
        
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
            
            
            com.lowagie.text.Document docPdf = new com.lowagie.text.Document(PageSize.A4.rotate());
            
            try {
                
                try {
                    
                    com.lowagie.text.pdf.PdfWriter.getInstance(docPdf, new java.io.FileOutputStream(tempFile));
                    
                    
                    String date = null;
                    try {
                      java.sql.Statement st3 = connectDB.createStatement();
                        java.sql.Statement st4 = connectDB.createStatement();
                        
                        java.sql.ResultSet rset2 = st3.executeQuery("SELECT header_name from pb_header");
                        java.sql.ResultSet rset4 = st4.executeQuery("SELECT date(now()) as Date");
                        while(rset2.next()){
                        }
                        while(rset4.next()){
                            date = rset4.getObject(1).toString();
                        }
                        
                        com.lowagie.text.HeaderFooter headerFoter = new com.lowagie.text.HeaderFooter(new Phrase(" "),false);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 14, Font.BOLDITALIC,java.awt.Color.blue)));
                        headerFoter.setAlignment(com.lowagie.text.HeaderFooter.ALIGN_CENTER);
                        
                        headerFoter.setRight(5);
                        docPdf.setHeader(headerFoter);
                        
                    } catch(java.sql.SQLException SqlExec) {
                        System.err.println(SqlExec);
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                        
                    }
                    
                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("Student Report Card - Page: "), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));
                    
                    docPdf.setFooter(footer);
                    
                    
                    docPdf.open();
                    
                    ReportUtil.addCenteredTitlePage(docPdf, connectDB);
                    try {
                        
                        
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
                        
                        int headerwidths[] = {20, 20, 20, 20, 20};
                        
                        table.setWidths(headerwidths);
                        
                        table.setWidthPercentage((100));
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        
                        table.getDefaultCell().setColspan(5);
                        
                        
                        Phrase phrase; 
                        
                        
                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        phrase=new Phrase("REPORT CARD", pFontHeaderLARGE);
                        table.addCell(phrase);
                        
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        phrase = new Phrase("PRINTED ON  :" +date , pFontHeader);
                        table.addCell(phrase);
                        
//                        String order_nuos=null;
                        
                        Statement sta=connectDB.createStatement();
                        ResultSet rst=sta.executeQuery("select distinct(re.student_id), UPPER(first_name||' '||middle_name||' '||last_name) as stu_names, current_class, term "
                                + "from student_Registration s, student_results re where re.student_id=s.student_id and term='"+term_id+"' and current_class='"+curr_class+"' "
                                + "and re.student_id='"+student_id+"'");
                        while(rst.next()){
                            table.getDefaultCell().setColspan(3);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase=new Phrase("STUDENT ID: "+dbObject.getDBObject(rst.getObject(1), "-"), pFontHeader);
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setColspan(2);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase=new Phrase("NAMES: "+dbObject.getDBObject(rst.getObject(2), "-"), pFontHeader);
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setColspan(3);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase=new Phrase("CLASS: "+dbObject.getDBObject(rst.getObject(3), "-"), pFontHeader);
                            table.addCell(phrase);
                            
                            table.getDefaultCell().setColspan(2);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase=new Phrase("TERM ID: "+dbObject.getDBObject(rst.getObject(4), "-"), pFontHeader);
                            table.addCell(phrase);
                        }
                        
                        table.getDefaultCell().setColspan(5);
                        phrase=new Phrase(" ");
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setColspan(1);
                        table.getDefaultCell().setBorder(PdfCell.TOP | PdfCell.LEFT | PdfCell.BOTTOM | PdfCell.RIGHT);
                        
                         phrase = new Phrase("SUBJECT",pFontHeader);
                        table.addCell(phrase);
                        
                        phrase = new Phrase("CAT 1 (OUT OF 50)",pFontHeader);
                        table.addCell(phrase);
                        
                        phrase = new Phrase("CAT II (OUT OF 50)",pFontHeader);
                        table.addCell(phrase);
                        
                        phrase = new Phrase("MAIN EXAM (OUT OF 100)",pFontHeader);
                        table.addCell(phrase);
                        
                        phrase = new Phrase("AVERAGE",pFontHeader);
                        table.addCell(phrase);
                        
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        
                         table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                          Statement s=connectDB.createStatement();
                            java.sql.Statement st = connectDB.createStatement();
                            
                         
                        try {
                         
                            System.out.println("its okay upto here");
                            java.sql.ResultSet length = s.executeQuery("select count(DISTINCT(subject_names)) from subjects s, student_results re where s.subject_id=re.subject_id "
                                    + "and term='"+term_id+"' and current_class='"+curr_class+"' and student_id='"+student_id+"'");
                            int subjectlength=0;
                            while(length.next()){
                                subjectlength=length.getInt(1);
                            }
                            int overall_marks=subjectlength*100;
                            System.out.println("Overall marks:  "+overall_marks);
                            System.err.println("sub length:  "+subjectlength);
                            Float[] data=new Float[subjectlength];
                            
//                            ArrayList<Integer> data =new ArrayList();
                            System.out.println("Array size is "+data.length);
                            int score1=0, score2=0, score3=0;
                            float average = 0;
                            String subject_name=null;
                            java.sql.ResultSet rset = st.executeQuery("select distinct(subject_names) from subjects s, student_results re where s.subject_id=re.subject_id "
                                    + "and term='"+term_id+"' and current_class='"+curr_class+"' and student_id='"+student_id+"'");
                            while (rset.next()) {
//                                for(int tester=0;tester<data.length;tester++){
                                    
                                subject_name=rset.getString(1);
                                System.err.println("Subject_name is "+subject_name);
                                String sub=subject_name.toUpperCase();
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase=new Phrase(sub, pFontHeaderN);
                                table.addCell(phrase);
                                
                                Statement st1=connectDB.createStatement();
                                ResultSet rset1=st1.executeQuery( "SELECT SCORE FROM STUDENT_RESULTS re, subjects s where re.subject_id=s.subject_id "
                                        + "and subject_names='"+subject_name+"' and exam_level_id='LVL NO.1' AND CURRENT_CLASS='"+curr_class+"' and term='"+term_id+"' "
                                        + "and student_id='"+student_id+"'");
                                
                                while(rset1.next()){
                                    score1=rset1.getInt(1);
                                }
                                System.err.println("Score 1 is "+score1);
                                Statement st2=connectDB.createStatement();
                                ResultSet rset2=st2.executeQuery("SELECT SCORE FROM STUDENT_RESULTS re, subjects s where re.subject_id=s.subject_id "
                                        + "and subject_names='"+subject_name+"' and exam_level_id='LVL NO.2' AND CURRENT_CLASS='"+curr_class+"' and term='"+term_id+"' "
                                        + "and student_id='"+student_id+"'");
                                
                                while(rset2.next()){
                                    score2=rset2.getInt(1);
                                }
                                System.err.println("Score 2 is "+score2);
                                
                                Statement st33=connectDB.createStatement();
                                ResultSet rset33=st33.executeQuery("SELECT SCORE FROM STUDENT_RESULTS re, subjects s where re.subject_id=s.subject_id "
                                        + "and subject_names='"+subject_name+"' and exam_level_id='LVL NO.3' AND CURRENT_CLASS='"+curr_class+"' and term='"+term_id+"' "
                                        + "and student_id='"+student_id+"'");
                                
                                while(rset33.next()){
                                    score3=rset33.getInt(1);
                                }
                                
                                System.err.println("Score 3 is "+score3);
                                
                                System.err.println("Score1 is "+score1+", Score2 is "+score2+" and score3 is "+score3);
                                average=(score1+score2+score3)/2;    
                                
                                System.err.println("Average is "+average);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase=new Phrase(""+score1+"", pFontHeaderN);
                                table.addCell(phrase);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase=new Phrase(""+score2+"", pFontHeaderN);
                                table.addCell(phrase);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase=new Phrase(""+score3+"", pFontHeaderN);
                                table.addCell(phrase);
                                
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase=new Phrase(""+average+"", pFontHeaderN);
                                table.addCell(phrase);
                            
                                int i=rset.getRow()-1;
                                System.err.println("i is "+i);
                                
                                System.err.println("I for get row()-1 should be 0 "+i);
                                    data [i]=average;
                                    System.err.println("Data["+i+"] is "+data[i]);
                            }
                            
//                                }
                                System.err.println("final part is here");
                                float x=0;
                                for(int i=0;i<data.length;i++){
                                    x+=data[i];
                                    System.out.println("X is "+x);
                                }
                                
                                table.getDefaultCell().setColspan(5);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase=new Phrase("TOTAL MARKS: "+(int)x+"/"+overall_marks, pFontHeaderLARGE);
                                table.addCell(phrase);
                                Statement totstu=connectDB.createStatement();
                                int total_students=0;
                                ResultSet totstudents=totstu.executeQuery("select count(distinct(student_id)) as total_Students   from student_results "
                                        + "where term='"+term_id+"' and current_class='"+curr_class+"'");
                                while(totstudents.next()){
                                    total_students=totstudents.getInt(1);
                                }
//                                String position=null;
                                Statement poss=connectDB.createStatement();
                                ResultSet pos=poss.executeQuery("select row_number() over() as rownum, student_id, sum(score)/2 as total_score from student_results "
                                        + "where exam_level_id in (select level_id from exam_levels) and current_class='"+curr_class+"' and term='"+term_id+"' "
                                        + " group by student_id order by 3 desc");
                                while(pos.next()){
                                if(student_id.equals(pos.getString(2))){
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                                    phrase=new Phrase("POSITION "+pos.getString(1)+" OUT OF "+total_students, pFontHeaderLARGE);
                                    table.addCell(phrase);
                                    System.err.println("POSITION "+pos.getString(1)+" OUT OF "+total_students);
                                }
                                }
                                 table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase=new Phrase("CLASS TEACHER'S COMMENTS: ", pFontHeader);
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase=new Phrase("CLASS TEACHER'S SIGNATURE: ", pFontHeader);
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase=new Phrase("HEADTEACHER'S/PRINCIPAL'S COMMENTS: ", pFontHeader);
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase=new Phrase("HEADTEACHER'S/PRINCIPAL'S SIGNATURE: ", pFontHeader);
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    phrase=new Phrase(" ");
                                    table.addCell(phrase);
                                    
                                    docPdf.add(table);
                            
                        } catch(java.sql.SQLException SqlExec) {
                            
                            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                            System.err.println(SqlExec);
                        }
                        
                      
                    } catch(com.lowagie.text.BadElementException BadElExec) {
                        
                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), BadElExec.getMessage());
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(OrdersPdf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch(java.io.FileNotFoundException fnfExec) {
                    
                    javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), fnfExec.getMessage());
                    
                } catch (SQLException ex) {
                    Logger.getLogger(IndividualReportCardPdf.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadElementException ex) {
                    Logger.getLogger(IndividualReportCardPdf.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch(com.lowagie.text.DocumentException lwDocexec) {
                
                javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), lwDocexec.getMessage());
                
            }
            
            docPdf.close();
            
            deskTop.open(tempFile);
            
            
            
        } catch(java.io.IOException IOexec) {
            
            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), IOexec.getMessage());
            
        }
        
        
        
    }
    
    
    public java.lang.Object[] getListofStaffNos() {
        
        java.lang.Object[] listofStaffNos = null;
        
        java.util.Vector listStaffNoVector = new java.util.Vector(1,1);
        
        
        
        listofStaffNos = listStaffNoVector.toArray();
        System.out.println("Done list of Staff Nos ...");
        return listofStaffNos;
    }
}
