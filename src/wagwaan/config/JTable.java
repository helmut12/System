/*
 * JTable.java
 *
 * Created on April 1, 2006, 4:37 PM
 */

package wagwaan.config;

//import com.afrisoftech.sys.Configuration;

/**
 *
 * @author  root
 */
public class JTable extends org.jdesktop.swing.JXTable implements java.lang.Runnable {//javax.swing.JTable {
    
    javax.swing.JPopupMenu tablePopupMenu;
    
    boolean exportTableBoolean = true;
    
    int tableColorA;
    
    int tableColorB;
    
    int tableColorHeader;
    
    Configuration appProp;
    
    java.lang.Thread threadExportTable = null;
    
    wagwaan.config.XMLExport xmlExport;
    
    wagwaan.config.ExcelExport excelExport;
    
    wagwaan.config.HTMLExport htmlExport;
    
    wagwaan.config.PDFExport pdfExport;
    
    java.lang.String tableName = null;
    
    java.awt.Color colorA;
    
    java.awt.Color colorB;    
    java.awt.Color colorHeader;
    
    private boolean sortable;
    
    public JTable() {
        super();
        
        appProp = Configuration.getInstance();
        tableColorA = Integer.parseInt(System.getProperty("tableColorA"));
        tableColorB = Integer.parseInt(System.getProperty("tableColorB"));
        tableColorHeader = Integer.parseInt(System.getProperty("tableColorHeader"));


        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(true);
        this.setCellSelectionEnabled(true);
        createPopupMenu();
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
        });



        if (colorA == null){
            colorA = new java.awt.Color(tableColorA);
        }
        
        if (colorB == null){
            colorB = new java.awt.Color(tableColorB);
        }
        
        if (colorHeader == null){           
            colorHeader = new java.awt.Color(tableColorHeader);            
        }
        
        setHighLighter();
        
        
        this.setRowHeight(20);
        this.setSortable(false);
        
        this.getTableHeader().setBackground(colorHeader);
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setForeground(java.awt.Color.BLACK);
        
        tableName = "MyTable";
    }
    /**
     * Constructing the table and indicating if its sortable or not
     * @param sortable
     */
    public JTable(boolean sortable) {
        super();
        
        appProp = Configuration.getInstance();
        tableColorA = Integer.parseInt(System.getProperty("tableColorA"));
        tableColorB = Integer.parseInt(System.getProperty("tableColorB"));
        tableColorHeader = Integer.parseInt(System.getProperty("tableColorHeader"));


        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(true);
        this.setCellSelectionEnabled(true);
        createPopupMenu();
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
        });



        if (colorA == null){
            colorA = new java.awt.Color(tableColorA);
        }
        
        if (colorB == null){
            colorB = new java.awt.Color(tableColorB);
        }
        
        if (colorHeader == null){           
            colorHeader = new java.awt.Color(tableColorHeader);            
        }
        
        setHighLighter();
        
        
        this.setRowHeight(20);
        this.setSortable(sortable);
        
        this.getTableHeader().setBackground(colorHeader);
        
        this.getTableHeader().setForeground(java.awt.Color.BLACK);
        
        tableName = "MyTable";
    }
    
    @Override
    public Class getColumnClass(int c) {
        
        java.lang.Object cellValue = new java.lang.Object();
        
        if(getValueAt(0, c) != null){
            
            //    cellValue = getValueAt(0, c);
            return getValueAt(0, c).getClass();
            
        } else {
            
            return cellValue.getClass();
            
        }
    }
    
    
    @Override
    public void run() {
        
        if (exportTableBoolean == false) {
            
            exportTableBoolean = true;
            
        }
        
        while (exportTableBoolean) {
            
            System.out.println("Export process started for thread ["+Thread.currentThread().getName()+"]");
            
            if (Thread.currentThread().getName().matches("Export2Excel")) {
                
                System.out.println("Exporting Export2Excel");
                
                excelExport = new wagwaan.config.ExcelExport(this.getExportTable(), tableName);
                
            } else if (Thread.currentThread().getName().matches("Export2Xml")) {
                
                xmlExport = new wagwaan.config.XMLExport(this.getExportTable(), tableName);
                
            } else if (Thread.currentThread().getName().matches("Export2HTML")) {
                
                htmlExport = new wagwaan.config.HTMLExport(this.getExportTable(), tableName);
                
            } else if (Thread.currentThread().getName().matches("Export2PDF")) {
                
                pdfExport = new wagwaan.config.PDFExport(this.getExportTable(), tableName);
                
            }
            else if (Thread.currentThread().getName().matches("PrintTable")) {
                
                printTable();
            }
            
            try {
                Thread.sleep(100);              
            } catch(java.lang.InterruptedException IntExec){ System.out.println(IntExec.getMessage());}
            
            exportTableBoolean = false;
            
        }
        
        
    }
    public javax.swing.JTable getExportTable() {
        return this;       
    }
    
    private void createPopupMenu(){
        
        tablePopupMenu = new javax.swing.JPopupMenu("Export Table");
        
        javax.swing.JMenuItem xmlMenuItem = new javax.swing.JMenuItem("XML Export");
        xmlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export2XmlActionPerformed(evt);
            }
        });
        tablePopupMenu.add(xmlMenuItem);
        
        javax.swing.JMenuItem excelMenuItem = new javax.swing.JMenuItem("Excel Export");
        excelMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export2ExcelActionPerformed(evt);
            }
        });
        tablePopupMenu.add(excelMenuItem);
        
        javax.swing.JMenuItem htmlMenuItem = new javax.swing.JMenuItem("HTML Export");
        htmlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export2HtmlActionPerformed(evt);
            }
        });
        tablePopupMenu.add(htmlMenuItem);
        
        javax.swing.JMenuItem pdfMenuItem = new javax.swing.JMenuItem("PDF Export");
        pdfMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export2PdfActionPerformed(evt);
            }
        });
        tablePopupMenu.add(pdfMenuItem);
        
        javax.swing.JMenuItem printMenuItem = new javax.swing.JMenuItem("Print table ...");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        tablePopupMenu.add(new javax.swing.JSeparator());
        tablePopupMenu.add(printMenuItem);
        //}
        javax.swing.JMenu prefMenu = new javax.swing.JMenu("Set Preferences");
        
        javax.swing.JMenuItem prefMenuItemColorA = new javax.swing.JMenuItem("Table Highlighter Color A");
        prefMenuItemColorA.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prefColorAActionPerformed(evt);
            }
        });
        //tablePopupMenu.add(new javax.swing.JSeparator());
        prefMenu.add(prefMenuItemColorA);
        
        javax.swing.JMenuItem prefMenuItemColorB = new javax.swing.JMenuItem("Table Highlighter Color B");
        prefMenuItemColorB.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prefColorBActionPerformed(evt);
            }
        });
        //tablePopupMenu.add(new javax.swing.JSeparator());
        prefMenu.add(prefMenuItemColorB);
        
        javax.swing.JMenuItem prefMenuItemColorHeader = new javax.swing.JMenuItem("Table Header Background");
        prefMenuItemColorHeader.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prefColorHeaderActionPerformed(evt);
            }
        });

        prefMenu.add(prefMenuItemColorHeader);
        
        tablePopupMenu.add(prefMenu);
        
    }
    
    private void prefColorHeaderActionPerformed(java.awt.event.ActionEvent evt) {

        setPreferencesColorHeader();
    }
    
    private void prefColorAActionPerformed(java.awt.event.ActionEvent evt) {

        setPreferencesColorA();

    }
    private void prefColorBActionPerformed(java.awt.event.ActionEvent evt) {

        setPreferencesColorB();

    }
    
    
    private void printActionPerformed(java.awt.event.ActionEvent evt) {
        
        threadExportTable = new java.lang.Thread(this, "PrintTable");
        
        threadExportTable.start();

    }
    
    private void export2XmlActionPerformed(java.awt.event.ActionEvent evt) {
        
        threadExportTable = new java.lang.Thread(this, "Export2Xml");
        
        threadExportTable.start();

    }
    
    private void export2HtmlActionPerformed(java.awt.event.ActionEvent evt) {
        
        threadExportTable = new java.lang.Thread(this, "Export2HTML");
        
        threadExportTable.start();

    }
    
    private void export2ExcelActionPerformed(java.awt.event.ActionEvent evt) {
        
        threadExportTable = new java.lang.Thread(this, "Export2Excel");
        
        threadExportTable.start();

    }
    
    private void export2PdfActionPerformed(java.awt.event.ActionEvent evt) {
        
        threadExportTable = new java.lang.Thread(this, "Export2PDF");
        
        threadExportTable.start();
        
        // Add your handling code here:
    }
    
    private void tableMousePressed(java.awt.event.MouseEvent evt) {
        
        if (evt.getModifiers() == java.awt.event.MouseEvent.BUTTON3_MASK){
            
            tablePopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            
        }
    }
    
    private void printTable(){
        try{
            this.print(javax.swing.JTable.PrintMode.FIT_WIDTH);
        } catch(java.awt.print.PrinterException prEx){
            javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), prEx.getMessage(), "ERROR: Printing problem!", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void setPreferencesColorA(){
        java.awt.Color prefColor = javax.swing.JColorChooser.showDialog(this, "Select Prefered Colour", java.awt.Color.CYAN);

        colorA = prefColor;
        setHighLighter();

        appProp.setProperty("tableColorA", java.lang.String.valueOf(prefColor.getRGB()));
        System.setProperty("tableColorA", java.lang.String.valueOf(prefColor.getRGB()));

    }
    
    
    private void setPreferencesColorHeader(){
        java.awt.Color prefColor = javax.swing.JColorChooser.showDialog(this, "Select Prefered Colour", java.awt.Color.CYAN);
        colorHeader = prefColor;
        setHighLighter();

        appProp.setProperty("tableColorHeader", java.lang.String.valueOf(prefColor.getRGB()));

        storeProperties();

    }
    
    
    private void setPreferencesColorB(){

        java.awt.Color prefColor = javax.swing.JColorChooser.showDialog(this, "Select Prefered Colour", java.awt.Color.CYAN);
        colorB = prefColor;

        setHighLighter();

        appProp.setProperty("tableColorB", java.lang.String.valueOf(prefColor.getRGB()));

        storeProperties();

    }
    
    private void setHighLighter(){
        this.getTableHeader().setBackground(colorHeader);
        org.jdesktop.swing.decorator.Highlighter[]   highlighters = new org.jdesktop.swing.decorator.Highlighter[] {
            new org.jdesktop.swing.decorator.AlternateRowHighlighter(colorA, colorB, null),
            //            new org.jdesktop.swing.decorator.PatternHighlighter(java.awt.Color.pink, java.awt.Color.pink, "[*].*", 0, 2,0)
            /* new org.jdesktop.swing.decorator.PatternHighlighter(java.awt.Color.cyan, java.awt.Color.RED,".*",1,2, 0),*/
            new org.jdesktop.swing.decorator.HierarchicalColumnHighlighter()
            
        };
        
        org.jdesktop.swing.decorator.HighlighterPipeline highlighterPipeline = new org.jdesktop.swing.decorator.HighlighterPipeline(highlighters);
        
        this.setHighlighters(highlighterPipeline);
        
        this.repaint();
        
    }
    

    private void storeProperties(){
        tableColorA = java.lang.Integer.parseInt(appProp.getProperty("tableColorA"));
        tableColorB = java.lang.Integer.parseInt(appProp.getProperty("tableColorB"));

        appProp.save();
    }

    /**
     * @return the sortable
     */
    
}
