/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;

import java.awt.Point;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import peeks.RPAS.RPASProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import peeks.database.DatabaseConnectionParams;
import peeks.database.DatabaseConnection;
import peeks.RPAS.dataobjects.DatabaseData;
import peeks.RPAS.dataobjects.Expense;
import peeks.RPAS.dataobjects.MortgagePayment;
import peeks.RPAS.dataobjects.TaxYear;
import peeks.RPAS.dataobjects.Property;
import peeks.RPAS.dataobjects.RentalIncome;
import peeks.RPAS.dataobjects.TaxBand;
import peeks.RPAS.dataobjects.TaxYearSummary;
import peeks.RPAS.DatabaseConfig;
import peeks.RPAS.DatabaseConfigList;
import peeks.RPAS.DatabaseConfigManager;



/**
 *
 * @author Peeks
 */
public class RPASUI extends javax.swing.JFrame {

    // DEFINE CONSTANTS
    public static final int     SUMMARY_EXP_COL                 = 6;
    public static final int     SUMMARY_TAX_INCOME_COL          = 5;
    public static final int     NON_SUMMARY_TABLE_DATE_COL      = 0;
    public static final int     INCOME_TAB                      = 0;
    public static final int     MORTGAGE_TAB                    = 1;
    public static final int     EXPENSES_TAB                    = 2;
    public static final int     SUMMARY_TAB                     = 3;
    public static final int     SUMMARY_TABLE_DATE_COL          = 0;
    public static final String  ALL                             = "ALL";
    public static final String  DB_MENU_TITLE                   = "DataBase";
    
    
    // TABLE FIELD SIZES
    public static final int     TBL_EXP_DESC_LEN                = 256;
    public static final int     TBL_EXP_COMMENT_LEN             = 256;
    public static final int     TBL_EXP_RECEIPT_LNK             = 256;
    public static final int     TBL_EXP_SUPPLIER                = 100;
    public static final int     TBL_MORT_COMMENT_LEN            = 256;
    public static final int     TBL_INCOM_COMMENT_LEN           = 150;
    public static final int     TBL_INCOM_PAYMODE_LEN           = 100;
    public static final int     TBL_INCOM_PAY_SRC_LEN           = 100;
    public static final int     TBL_INCOM_TENANT_LEN            = 45;
    
    
    // static log4J 
    public static final Logger logger = LogManager.getLogger(RPASUI.class.getName());
    public DatabaseConnection dbConnection = null;
    public DatabaseData dbData = null;
    private boolean bUIFrameworkInitialised = false;
    
    //peeks support for Database menu item data - db sources loaded from JSON
    DatabaseConfigList dbList = null;
    
    /**
     * Creates new form RPASUI
     */
    public RPASUI() {
        initComponents();
    
        // base UI initlaised so set flag to true
        // stops table populate calls being doen on creating of the combo boxes
        bUIFrameworkInitialised = true;
                
        // load config and db stuff
        if ( !initialiseRPAS() )
        {
            JOptionPane.showMessageDialog( this, "Failiure Initiliasing Application", //
                                           "Startup Error",                           //
                                           JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // CENTRALISE WINDOW
        setLocationRelativeTo(null);
        
        
        /////////////////////////////
        // we have loaded jSON file with db target list - if it was found and valid
        // now need to dynaically add a menu item for entry in the JSON file
        /////////////////////////////
        // create menu bar
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar( menuBar );
        JMenu dbMenu = new JMenu( DB_MENU_TITLE );
        
        // iterate thru list of db tagret created from jOSN file and add a menu item or each
        JMenuItem menuItem = null;
        DatabaseConfig dbConfig = null;
        int iNumItems = dbList.getListCount();
        for (int i = 0; i < iNumItems; i++ ) 
        {
            dbConfig = dbList.getConfigItem( i );
            menuItem = new JMenuItem( dbConfig.getUsername() + "@" + dbConfig.getHost() + ":" + dbConfig.getDatabaseName() );
            dbMenu.add( menuItem );
            menuItem.addActionListener(new MenuListener( this, menuBar, i ));
            
        }
        
        // add menu bar to frame
        menuBar.add(dbMenu);
        
        /////////////////////////////
        // peeks end test crap
        /////////////////////////////
        
        
        //add windows listener so we cna detect frame event, close, minimise, maximise, etc
        addWindowListener(new RPASWindowListener());
        
        // populate UI stuff with DB data
        popultaeTaxCombo();
        propertyCombo();
        populateTaxRateCombo();
        populateIncomeTable(); 
        populateMortgageTable();
        populateExepnsesTable();
                          
        // lets get the current value of the tax year box
        String selTaxYear  = (String)taxYearCombo.getSelectedItem();
        String selProp  = (String)propertyCombo.getSelectedItem();
        int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
        int iPropID = dbData.getMatchingPropertyID( selProp );
        int iTaxRate = Integer.parseInt( (String)comboTaxBand.getSelectedItem());
        // int iTaxRate = 40;
                
        dbData.buildSummaryData( iTaxID, iPropID, iTaxRate );
        
        populateSummaryTable();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedFrame = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        mortgageTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        expensesTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        summaryTable = new javax.swing.JTable();
        propertyCombo = new javax.swing.JComboBox<>();
        propertyLabel = new javax.swing.JLabel();
        taxYearCombo = new javax.swing.JComboBox<>();
        taxYearLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        comboTaxBand = new javax.swing.JComboBox<>();
        taxBandLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("RootFrame"); // NOI18N
        setResizable(false);

        tabbedFrame.setBorder(javax.swing.BorderFactory.createTitledBorder("Rental Property Accounting System"));
        tabbedFrame.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedFrameStateChanged(evt);
            }
        });

        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Rent", "Bank", "Payment Type", "Source", "Tenant", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        incomeTable.setShowGrid(true);
        incomeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incomeTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(incomeTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1308, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
        );

        tabbedFrame.addTab("Income", jPanel1);

        mortgageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Date", "Amount", "Interest", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mortgageTable.setShowGrid(true);
        mortgageTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mortgageTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mortgageTable);

        tabbedFrame.addTab("Mortgage", jScrollPane2);

        expensesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Description ", "Supplier", "Amount", "Receipt", "ReceiptLink", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expensesTable.setShowGrid(true);
        expensesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expensesTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(expensesTable);

        tabbedFrame.addTab("Expenses", jScrollPane3);

        summaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TaxYear", "Income", "Mortgage", "Mortage Interest", "Expenses", "Taxabale Income", "Exposure", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        summaryTable.setShowGrid(true);
        jScrollPane4.setViewportView(summaryTable);

        tabbedFrame.addTab("Summary", jScrollPane4);

        propertyCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertyComboActionPerformed(evt);
            }
        });

        propertyLabel.setText("Proprerty");

        taxYearCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxYearComboActionPerformed(evt);
            }
        });

        taxYearLabel.setText("Selected Tax Year");

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        printButton.setText("Print");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        comboTaxBand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTaxBandActionPerformed(evt);
            }
        });

        taxBandLabel.setText("Tax Band");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(propertyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270)
                .addComponent(taxBandLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboTaxBand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(taxYearLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taxYearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(tabbedFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 1341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(tabbedFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propertyLabel)
                    .addComponent(taxYearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxYearLabel)
                    .addComponent(comboTaxBand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxBandLabel))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(deleteButton)
                    .addComponent(printButton)
                    .addComponent(exitButton)
                    .addComponent(updateButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        tabbedFrame.getAccessibleContext().setAccessibleName("Mortgage");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        
        int iTab = tabbedFrame.getSelectedIndex();
        switch ( iTab ) 
        {
            case INCOME_TAB:  
                    logger.debug( "ADD button prressed - creating new Add Income Dlg" );
                    IncomeDialog incomeDlg = new IncomeDialog( this, true, null, IncomeDialog.ADD );
                    incomeDlg.setVisible( true );
                    if (incomeDlg.bReloadDb )
                    {
                        logger.debug( "New income added - reload database object and repopulate grid controls" );
                        dbConnection.connect();
                        dbConnection.getRentalIncome(dbData );
                        populateIncomeTable();
                        reBuildSummaryData();
                    }
                    break;
            case MORTGAGE_TAB:  
                    logger.debug( "ADD button prressed - creating new Add Mortgage Dlg" );
                    MortgageDialog morgDlg = new MortgageDialog( this, true, null, MortgageDialog.ADD );
                    morgDlg.setVisible( true );
                    if (morgDlg.bReloadDb )
                    {
                        logger.debug( "New mortgage added - reload database object and repopulate grid controls" );
                        dbConnection.connect();
                        dbConnection.getMortgagePayments(dbData );
                        populateMortgageTable();
                        
                        reBuildSummaryData();
                    }
                    break;
            case EXPENSES_TAB:  
                    logger.debug( "ADD button prressed - creating new Add Expense Dlg" );
                    ExpenseDialog expDlg = new ExpenseDialog( this, true, null, ExpenseDialog.ADD );
                    expDlg.setVisible( true );
                    if (expDlg.bReloadDb )
                    {
                        logger.debug( "New expense added - reload database object and repopulate grid controls" );
                        dbConnection.connect();
                        dbConnection.getDBExpenses( dbData );
                        populateExepnsesTable();  
                        
                        reBuildSummaryData();
                    }
                    break;
        }
    }//GEN-LAST:event_addButtonActionPerformed

    
    // if we have made ancy changes to expense, mortage or income data we need to rebuild the summary data
    private void reBuildSummaryData()
    {
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProp  = (String)propertyCombo.getSelectedItem();
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProp );
            int iTaxRate = Integer.parseInt( (String)comboTaxBand.getSelectedItem());

            dbData.buildSummaryData( iTaxID, iPropID, iTaxRate );
            populateSummaryTable();    
    }
    
    
    // update button handler
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        
        
        String sVal = null;
        int iRow = -1;
        int iTab = tabbedFrame.getSelectedIndex();
        
        switch ( iTab ) 
        {
            case INCOME_TAB:  
                    iRow = incomeTable.getSelectedRow();
                    
                    // if IRow == -1 then noething has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "UPDATE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)incomeTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR UPDATE ON INCOME TAB - IGNORING" );
                    else
                    {    
                        RentalIncome income = dbData.getRentalIncomeObject(iRow );

                        // invoke Income dlg in UPDATE mode
                        IncomeDialog incomeDlg = new IncomeDialog( this, true, income, IncomeDialog.UPDATE );
                        incomeDlg.setVisible( true );  
                        
                        if (incomeDlg.bReloadDb )
                        {
                            logger.debug( "Income update - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            // PEEKS_TODO
                            dbConnection.getRentalIncome(dbData );
                            populateIncomeTable();                          
                            reBuildSummaryData();
                        }
                    }
                    break;
            case MORTGAGE_TAB:  
                    iRow = mortgageTable.getSelectedRow();
                    // if IRow == -1 then noething has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "UPDATE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)mortgageTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR UPDATE ON MORTGAGE TAB - IGNORING" );
                    else
                    {
                        MortgagePayment morg = dbData.getMortgageObject(iRow );

                        // invoke Expense dlg in DELETE mode
                        MortgageDialog morgDlg = new MortgageDialog( this, true, morg, MortgageDialog.UPDATE );
                        morgDlg.setVisible( true );  
                        
                        if (morgDlg.bReloadDb )
                        {
                            logger.debug( "Mortgage update - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            dbConnection.getMortgagePayments(dbData );
                            populateMortgageTable();  
                            
                            reBuildSummaryData();
                        }
                        break;
                    }
            case EXPENSES_TAB:  
                    iRow = expensesTable.getSelectedRow();
                    // if IRow == -1 then nothing has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "UPDATE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)expensesTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR UPDATE ON EXPENSE TAB - IGNORING" );
                    else
                    {    
                        Expense exp = dbData.getExpenseObject( iRow );
                        //logger.debug( "tab " + exp.toString() ); 
                        
                        // invoke Expense dlg in DELETE mode
                        ExpenseDialog expDlg = new ExpenseDialog( this, true, exp, ExpenseDialog.UPDATE );
                        expDlg.setVisible( true ); 
                        
                                                if (expDlg.bReloadDb )
                        {
                            logger.debug( "Expense updated - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            dbConnection.getDBExpenses( dbData );
                            populateExepnsesTable();  
                            
                            reBuildSummaryData();
                        }
                        break;
                    }
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    // fired when delete button clicked
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
             // your valueChanged overridden method 
        String sVal = null;
        int iRow = -1;
        ExpenseDialog expDlg = null;
        int iTab = tabbedFrame.getSelectedIndex();
        
        switch ( iTab ) 
        {
            case INCOME_TAB:  
                    iRow = incomeTable.getSelectedRow();
                    
                    // if IRow == -1 then noething has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "UPDATE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)incomeTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR UPDATE ON INCOME TAB - IGNORING" );
                    else
                    {    
                        RentalIncome income = dbData.getRentalIncomeObject(iRow );

                        // invoke Income dlg in UPDATE mode
                        IncomeDialog incomeDlg = new IncomeDialog( this, true, income, IncomeDialog.DELETE );
                        incomeDlg.setVisible( true );  
                        
                        if (incomeDlg.bReloadDb )
                        {
                            logger.debug( "Income deleted - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            dbConnection.getRentalIncome(dbData );
                            populateIncomeTable();  
                            
                            reBuildSummaryData(); 
                        }
                        break;
                    }
            case MORTGAGE_TAB:  
                    iRow = mortgageTable.getSelectedRow();
                    // if IRow == -1 then nothing has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "UPDATE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)mortgageTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR DELETE ON MORTGAGE TAB - IGNORING" );
                    else
                    {
                        MortgagePayment morg = dbData.getMortgageObject(iRow );

                        // invoke mortgage dlg in DELETE mode
                        MortgageDialog morgDlg = new MortgageDialog( this, true, morg, MortgageDialog.DELETE );
                        morgDlg.setVisible( true );  
                        
                        if (morgDlg.bReloadDb )
                        {
                            logger.debug( "Mortgage deleted - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            dbConnection.getMortgagePayments(dbData );
                            populateMortgageTable();  
                            
                            reBuildSummaryData();
                        }
                        break;
                    }
            case EXPENSES_TAB:  
                    iRow = expensesTable.getSelectedRow();
                    // if IRow == -1 then nothing has been selected so nothing to do
                    if ( iRow == -1 )
                    {
                          logger.debug( "DELETE PUSHED BUT NO ROW SLECTED - IGNORING" );
                          break;
                    }
                    sVal = (String)expensesTable.getModel().getValueAt(iRow, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
                    if( sVal.equals("TOTALS") ) 
                        logger.debug( "TOTALS ROW SELCTED FOR DELETE ON EXPENSE TAB - IGNORING" );
                    else
                    {
                        Expense exp = dbData.getExpenseObject( iRow );

                        // invoke Expense dlg in DELETE mode
                        expDlg = new ExpenseDialog( this, true, exp, ExpenseDialog.DELETE );
                        expDlg.setVisible( true );  
                        
                        if (expDlg.bReloadDb )
                        {
                            logger.debug( "Expense deleted - reload database object and repopulate grid controls" );
                            dbConnection.connect();
                            dbConnection.getDBExpenses( dbData );
                            populateExepnsesTable();  
                            
                            reBuildSummaryData();
                        }
                        break;
                    }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    
    // claaed when orunt button ckicked
    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        // TODO add your handling code here:
    
        try
        {
            int iTab = tabbedFrame.getSelectedIndex();
            switch ( iTab ) 
            {
                case INCOME_TAB:  incomeTable.print();
                         break;
                case MORTGAGE_TAB:  mortgageTable.print();
                         break;
                case EXPENSES_TAB:  expensesTable.print();
                         break;
                case SUMMARY_TAB:  summaryTable.print();
                         break;
            }
        }
        catch( PrinterException ex)
        {
            logger.error("Exception thown trying to print : " + ex.toString() );
        }
    }//GEN-LAST:event_printButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

   
    private void taxYearComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxYearComboActionPerformed
        // TODO add your handling code here:
        
        
       // don't call table load on until base UI initiated. Framework calls this
       // when it creates combo box
        if ( bUIFrameworkInitialised )
        {
            populateIncomeTable(); 
            populateMortgageTable();
            populateExepnsesTable();
            
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty  = (String)taxYearCombo.getSelectedItem();
           
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProperty );
            String sTemp = (String)comboTaxBand.getSelectedItem();
            
            // due to frameowrk UI stuff sTemp wil be NULL the fitrst time this func is 
            // called - presumbaly due to the fact that the taxRate combo is not initlaised
            // so if sTemp - null then box taxrate combo not yet initliased so 
            // build summary data against first vlaue in data object aTaxBands
            int iTaxRate = 0;
            if ( sTemp == null )
            {
                TaxBand taxBand = (TaxBand)dbData.getTaxBands().get(0);
                iTaxRate = taxBand.iTaxRate;
            }
            else
            {
                System.out.println("COMBO CHANGE VALUE = " +sTemp);
                // gte value from combo as must have been intialised as non null value returned in sTemp
                iTaxRate = Integer.parseInt( (String)comboTaxBand.getSelectedItem());
            }    
            
            
            dbData.buildSummaryData( iTaxID, iPropID, iTaxRate );
            populateSummaryTable();
        }
        
    }//GEN-LAST:event_taxYearComboActionPerformed

    private void propertyComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertyComboActionPerformed
        // TODO add your handling code here:
        
        // selection change made in tax year combo box
        System.out.println("Change made in property combo box");
        
        // don't call table load on until base UI initiated. Framework calls this
        // when it creates combo box
        if ( bUIFrameworkInitialised )
        {
            populateIncomeTable();
            populateMortgageTable();
            populateExepnsesTable();
            populateExepnsesTable();

        }
    }//GEN-LAST:event_propertyComboActionPerformed

    private void comboTaxBandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTaxBandActionPerformed
        // TODO add your handling code here:
        
        // value in combox for tax rate changed
         // don't call table load on until base UI initiated. Framework calls this
       // when it creates combo box
        if ( bUIFrameworkInitialised )
        {
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty  = (String)taxYearCombo.getSelectedItem();
           
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProperty );
            String sTemp = (String)comboTaxBand.getSelectedItem();
            
            // due to frameowrk UI stuff sTemp wil be NULL the fitrst time this func is 
            // called - presumbaly due to the fact that the taxRate combo is not initlaised
            // so if sTemp - null then box taxrate combo not yet initliased so 
            // build summary data against first vlaue in data object aTaxBands
            int iTaxRate = 0;
            if ( sTemp == null )
            {
                  System.out.println("COMBO TAX RATE CHANGE - VALUE IS NULL" );
                TaxBand taxBand = (TaxBand)dbData.getTaxBands().get(0);
                iTaxRate = taxBand.iTaxRate;
            }
            else
            {
                System.out.println("COMBO TAX RATE CHANGE VALUE = " +sTemp);
                // gte value from combo as must have been intialised as non null value returned in sTemp
                iTaxRate = Integer.parseInt( (String)comboTaxBand.getSelectedItem());
            }    
               
            dbData.buildSummaryData( iTaxID, iPropID, iTaxRate );
            populateSummaryTable();
       
        }
       
    }//GEN-LAST:event_comboTaxBandActionPerformed

    // called when a new tab is slected
    private void tabbedFrameStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedFrameStateChanged
        // TODO add your handling code here:
        JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
        int index = sourceTabbedPane.getSelectedIndex();
        // System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
        
        // if summary tab diable UPDATE and DELETE button
        if ( index == SUMMARY_TAB )
        {            
            updateButton.setEnabled( false );
            deleteButton.setEnabled( false );
            addButton.setEnabled( false );
        }
        else
        {
            updateButton.setEnabled( true );
            deleteButton.setEnabled( true );
            addButton.setEnabled( true );
        }
    }//GEN-LAST:event_tabbedFrameStateChanged

    // fired when user double clicks on a row in the expenses table
    private void expensesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expensesTableMouseClicked
        // TODO add your handling code here:

        Point point = evt.getPoint();
        int row = expensesTable.rowAtPoint(point);
        
        if (evt.getClickCount() == 2 && expensesTable.getSelectedRow() != -1) 
        {
            // your valueChanged overridden method 
            RPASUI.logger.debug("Expense table selcted row = " + row ); 
            
            // check of this is the last row in the model. If so its the totals model so ignore
            DefaultTableModel tabModel = (DefaultTableModel)expensesTable.getModel();
            if ( tabModel.getRowCount() == ( row + 1) )
            {
                RPASUI.logger.debug("Expense table - TOTALS row clicked so ignore");
            }
            else
            {
                // get dat from model and invoke expenseDialogBox
                Expense exp = dbData.getExpenseObject( row );

                ExpenseDialog expDlg = new ExpenseDialog( this, true, exp, ExpenseDialog.UPDATE );
                expDlg.setVisible( true );
                
                if (expDlg.bReloadDb )
                {
                    logger.debug( "Expense updated - reload database object and repopulate grid controls" );
                    dbConnection.connect();
                    dbConnection.getDBExpenses( dbData );
                    populateExepnsesTable();    
                    
                    // rebuild summary view
                    reBuildSummaryData();
                }
            }
         }
    }//GEN-LAST:event_expensesTableMouseClicked

    // called when user double clciks on a row
    private void mortgageTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mortgageTableMouseClicked
        // TODO add your handling code here:
        
        Point point = evt.getPoint();
        int row = mortgageTable.rowAtPoint(point);
        
        if (evt.getClickCount() == 2 && mortgageTable.getSelectedRow() != -1) 
        {
            // your valueChanged overridden method 
            RPASUI.logger.debug("Mortgage table selected row = " + row ); 
            
            // check of this is the last row in the model. If so its the totals model so ignore
            DefaultTableModel tabModel = (DefaultTableModel)mortgageTable.getModel();
            if ( tabModel.getRowCount() == ( row + 1) )
            {
                RPASUI.logger.debug("Mortgage table - TOTALS row clicked so ignore");
            }
            else
            {
                // get dat from model and invoke expenseDialogBox
                MortgagePayment morg = dbData.getMortgageObject( row );

                 // invoke Expense dlg in DELETE mode
                MortgageDialog morgDlg = new MortgageDialog( this, true, morg, MortgageDialog.UPDATE );
                morgDlg.setVisible( true );  

                if (morgDlg.bReloadDb )
                {
                    logger.debug( "Mortgage entry updated - reload database object and repopulate grid controls" );
                    dbConnection.connect();
                    dbConnection.getMortgagePayments(dbData );
                    populateMortgageTable();  

                    reBuildSummaryData();
                }
            }
         }
    }//GEN-LAST:event_mortgageTableMouseClicked

    // called when a user double ckicks on a row in th eincome table
    private void incomeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseClicked
        // TODO add your handling code here:
        
        Point point = evt.getPoint();
        int row = incomeTable.rowAtPoint(point);
        
        if (evt.getClickCount() == 2 && incomeTable.getSelectedRow() != -1) 
        {
            // your valueChanged overridden method 
            RPASUI.logger.debug("Income table selected row = " + row ); 
            
            // check of this is the last row in the model. If so its the totals model so ignore
            DefaultTableModel tabModel = (DefaultTableModel)incomeTable.getModel();
            if ( tabModel.getRowCount() == ( row + 1) )
            {
                RPASUI.logger.debug("Income table - TOTALS row clicked so ignore");
            }
            else
            {
                // get dat from model and invoke expenseDialogBox
                RentalIncome income = dbData.getRentalIncomeObject( row );

                 // invoke Expense dlg in DELETE mode
                IncomeDialog incomeDlg = new IncomeDialog( this, true, income, IncomeDialog.UPDATE );
                incomeDlg.setVisible( true );  

                if (incomeDlg.bReloadDb )
                {
                    logger.debug( "Income entry updated - reload database object and repopulate grid controls" );
                    dbConnection.connect();
                    // PEEKS_TODO
                    dbConnection.getRentalIncome(dbData );
                    populateIncomeTable();  

                    reBuildSummaryData();
                }
            }
         }
        
    }//GEN-LAST:event_incomeTableMouseClicked

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
            java.util.logging.Logger.getLogger(RPASUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RPASUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RPASUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RPASUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        logger.error("Creating mainframe");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RPASUI().setVisible(true);
            }
        });
        
        System.out.println("**** EXITING APP");
    }
    
    
    private boolean appClosing()
    {
        
        boolean bReturn = true;
        
        if ( dbConnection != null )
        {    
            dbConnection.disConnect();
        }
        
        return bReturn;
    }
    
    /*
    * PEEKS stuff
    */
    private boolean initialiseRPAS()
    {
        
        boolean bReturn = true;
        
        
        // load properties file
        RPASProperties configProperties = new RPASProperties( "config.properties");
        
        try
        {
            configProperties.getPropValues();
            
            // read DB JSON file to get list of possible db endpoints
            // Read the list back from the JSON file
            // returns an empty list if problme loading or parsing file
            List<DatabaseConfig> loadedDbConfigList = DatabaseConfigManager.readFromJson( configProperties.dbList);
            //System.out.println("Loaded Configs from JSON:");
            if ( !loadedDbConfigList.isEmpty() )
            {
                System.out.println( "Successfully loaded db list JSON file : " + configProperties.dbList);
                
                // create object to store list - member var
                dbList = new DatabaseConfigList( loadedDbConfigList );
                
                // iteertae thru list a set active object based on first item that has the word true in active field
                int iCount = 0;
                for (DatabaseConfig config : loadedDbConfigList) 
                {
                    
                    System.out.println(config);
                    if ( config.getActive().equals( DatabaseConfig.ACTIVE ) )
                    {
                            // found first active param in jSON so exit loop
                            dbList.setActive( iCount );
                            System.out.println( "setting active db config object from JSON : " + iCount );       
                    }
                            
                    iCount++;
                            
                    System.out.println(config);
                }
            }
            else
                System.out.println( "No JSON config found. File empty or missing" );
            
            DatabaseConnectionParams dbParams = new DatabaseConnectionParams( configProperties.dbJDBCdriver,    //
                                                                              configProperties.dbConnectionStr, //
                                                                              configProperties.dbUser,          //
                                                                              configProperties.dbPwd);
            
            // creat to db and connect
            dbConnection = new DatabaseConnection( dbParams );
            if ( !dbConnection.connect() )
            {    
                logger.error("Failed to connect to database");
                JOptionPane.showMessageDialog( this, "Failed to connect to Database", //
                               "Startup Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
            else
            {
                // load the data
                dbData = new DatabaseData();
                dbConnection.loadDatabaseData( dbData );
                dbConnection.disConnect();
            }         
        }
        catch ( Exception ex )
        {
            System.out.println( ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
    
    // put dab vals for tax years in combo drop down box
    private boolean popultaeTaxCombo()
    {
        boolean bReturn = true;
        
        try
        {
            
            // remove any exuisting vals - wil lhave when channge DBs while live
            taxYearCombo.removeAllItems();
                    
            ArrayList aTaxYears = dbData.getTaxYears();
            int iTaxCount = aTaxYears.size();
            TaxYear taxYear = null;
            
            for ( int iCount = 0; iCount< iTaxCount; iCount++ )
            {
                taxYear = (TaxYear)aTaxYears.get( iCount );
                taxYearCombo.addItem( taxYear.sDesc );
            }
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating TaxYearCombo box" + ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
        // put dab vals for tax years in combo drop down box
    private boolean populateTaxRateCombo()
    {
        boolean bReturn = true;
        
        try
        {
            
            // remove any exuisting vals - wil lhave when channge DBs while live
            comboTaxBand.removeAllItems();
            
            ArrayList aTaxBands = dbData.getTaxBands();
            int iTaxCount = aTaxBands.size();
            TaxBand taxRate = null;
            
            for ( int iCount = 0; iCount< iTaxCount; iCount++ )
            {
                taxRate = (TaxBand)aTaxBands.get( iCount );
                comboTaxBand.addItem( "" + taxRate.iTaxRate );
            }
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating TaxBandsCombo box" + ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
    // put dab vals for tax years in combo drop down box
    private boolean propertyCombo()
    {
        boolean bReturn = true;
        
        try
        {
            
            // remove any exuisting vals - wil lhave when channge DBs while live
            propertyCombo.removeAllItems();
            
            ArrayList aProperty = dbData.getProperties();
            int iPropCount = aProperty.size();
            Property prop = null;
            
            for ( int iCount = 0; iCount< iPropCount; iCount++ )
            {
                prop = (Property)aProperty.get( iCount );
                propertyCombo.addItem( prop.sAddressLine1 );
            }
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating PropertyCombo box" + ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
    // popukate income table with database data
    private boolean populateIncomeTable()
    {
        
        boolean bReturn = true;
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        try
        {
            DefaultTableModel tabModel = (DefaultTableModel)incomeTable.getModel();
            ArrayList aIncome = dbData.getRentalIncome();
            int iIncomeCount = aIncome.size();
            RentalIncome incom = null;
            int iAddedRowCount = 0;
            float fBankIncome = 0;
            float fRentIncome = 0;
            
            // set custom renderer to turn TOTAL row text blue
            incomeTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
            
            // before we populate clear current table data otherwise appended
            // each time this methos is called
           while (incomeTable.getRowCount() > 0) 
           {
                tabModel.removeRow(0);
           }
           
            // claer row id for each MortagegPayemnt Object. RowID is just 
            // tabvle row id stored in each dataobject so we can tie visual row to actual
            // data object. If we have called this func before row id can change for each
            // actual data id based on property and tax year combos so reset each time
            dbData.resetRentalIncomeRowIDs();
            
            
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty = (String)propertyCombo.getSelectedItem();
            
            // get IDS (foreigh Leys for the current selected taxyear and 
            // property combo boxes
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProperty );
            
            // iterate thorugh all data objects
            for ( int iCount = 0; iCount< iIncomeCount; iCount++ )
            {
                incom = (RentalIncome)aIncome.get( iCount );
                
                // only add to table if matches combo boxes for tax year and property
                // if combo box ID = 99 it maens add all for all properties
                // and tax years
                if ( ( iTaxID == incom.iTaxID || iTaxID == 99 ) &&            //
                     ( iPropID == incom.iPropID || iPropID == 99 ) )   
                {
                    String[] rowData = {    
                                        simpleDateFormat.format(incom.incomeDate),  //  
                                        String.format("%.2f",incom.dRentalIncome),                   //
                                        String.format("%.2f", incom.dBankIncome),                     //
                                        incom.sPayMode,                             //
                                        incom.sPaySource,                           //
                                        incom.sTenant,                              //
                                        incom.sComment 
                                       };
                    // because we are filtering we need a seperate counter (row index)
                    // iCount could be larger that row count as it will inc for
                    // every income object but we will have not added those that
                    // do not match our proId and taxID
                    tabModel.insertRow( iAddedRowCount, rowData);   
                    iAddedRowCount++;
                    
                    // we need to set the IRowCount member var for incom so that we can pull it out
                    // when we want tu update or delete
                    incom.iRowID = (iAddedRowCount - 1);
                    
                    // inc totals count
                    fBankIncome += incom.dBankIncome;
                    fRentIncome += incom.dRentalIncome;
                }
            }
            
            // add an extra row for the total
            String[] rowData = {    
                                "TOTALS",  //  
                                String.format("%.2f",fRentIncome),             //
                                String.format("%.2f",fBankIncome),             //
                                "",                           //
                                "",                           //
                                "",                           //
                                "" 
                               };
            tabModel.insertRow( iAddedRowCount, rowData);
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating Income table" + ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
    
    // populate mortgage table with database data
    private boolean populateMortgageTable()
    {
        
        boolean bReturn = true;
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        try
        {
            DefaultTableModel tabModel = (DefaultTableModel)mortgageTable.getModel();
            ArrayList aMortgage = dbData.getMortgagePayments();
            int iMorgCount = aMortgage.size();
            MortgagePayment morg = null;
            int iAddedRowCount = 0;
            float fMorgPayTotal = 0;
            float fMorgIntTotal = 0;
            
            // set custom rendere to turn totals col text blue
            mortgageTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
            
            // before we populate clear current table data otherwise appended
            // each time this methos is called
            while (mortgageTable.getRowCount() > 0) 
            {
                 tabModel.removeRow(0);
            }
            
            // claer row id for each MortagegPayemnt Object. RowID is just 
            // tabvle row id stored in each dataobject so we can tie visual row to actual
            // data object. If we have called this func before row id can change for each
            // actual data id based on property and tax year combos so reset each time
            dbData.resetMortgageRowIDs();
            
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty = (String)propertyCombo.getSelectedItem();
            
            // get IDS (foreigh Leys for the current selected taxyear and 
            // property combo boxes
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProperty );
            
            // iterate thorugh all data objects
            for ( int iCount = 0; iCount< iMorgCount; iCount++ )
            {
                morg = (MortgagePayment)aMortgage.get( iCount );
                
                 // only add to table if matches combo boxes for tax year and property
                // if combo box ID = 99 it maens add all for all properties
                // and tax years
                if ( ( iTaxID == morg.iTaxID || iTaxID == 99 ) &&            //
                     ( iPropID == morg.iPropID || iPropID == 99 ) )   
                {
                
                    String[] rowData = {    
                                        simpleDateFormat.format(morg.payDate),  //  
                                        "" + morg.dPayment,                     //
                                        "" + morg.dInterest,                    //
                                        morg.sComment 
                                       };
                    // because we are filtering we need a seperate counter (row index)
                    // iCount could be larger that row count as it will inc for
                    // every income object but we will have not added those that
                    // do not match our proId and taxID
                    tabModel.insertRow( iAddedRowCount, rowData);   
                    iAddedRowCount++;       
                    
                    // we need to set the IRowCount member var for incom so that we can pull it out
                    // when we want tu update or delete
                    morg.iRowID = (iAddedRowCount - 1);
                    
                    // keep running total for TOTAL row
                    fMorgPayTotal += morg.dPayment;
                    fMorgIntTotal += morg.dInterest;
                }
            }
            
            // add an extra row for the total
            String[] rowData = {    
                                "TOTALS",  //  
                                String.format("%.2f", fMorgPayTotal),                     //
                                String.format("%.2f", fMorgIntTotal),                    //
                                "" 
                               };
            tabModel.insertRow( iAddedRowCount, rowData);
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating mortgage table" + ex.toString() );
            bReturn = false;
        }
        
        
        return bReturn;
    }
    
    
    // populate mortgage table with database data
    private boolean populateSummaryTable()
    {
        
        boolean bReturn = true;

        
        try
        {
            DefaultTableModel tabModel = (DefaultTableModel)summaryTable.getModel();
            ArrayList aSummary = dbData.getSummary();
            int iSumCount = aSummary.size();
            TaxYearSummary sum = null;
            int iAddedRowCount = 0;
            
            // totals across years for all columns
            float fIncTotal = 0;
            float fMorgTotal = 0;
            float fMorgIntTotal = 0;
            float fExpTotal = 0;
            float fTaxableTotal = 0;
            float fTaxExposure = 0;

            // set cell renderer so -ve value rows go red
            summaryTable.setDefaultRenderer(Object.class, new SummaryTableCellRenderer());
            
            // before we populate clear current table data otherwise appended
            // each time this methos is called
            while (summaryTable.getRowCount() > 0) 
            {
                 tabModel.removeRow(0);
            }
            
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty = (String)propertyCombo.getSelectedItem();
            
            // get IDS (foreigh Leys for the current selected taxyear and 
            // property combo boxes
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            // int iPropID = dbData.getMatchingPropertyID( selProperty );
            
            // iterate thorugh all data objects
            for ( int iCount = 0; iCount< iSumCount; iCount++ )
            {
                sum = (TaxYearSummary)aSummary.get( iCount );
                
                 // only add to table if matches combo boxes for tax year and property
                // if combo box ID = 99 it maens add all for all properties
                // and tax years
                if ( ( iTaxID == sum.iTaxYearID || iTaxID == 99 ) )  
                {
                
                    String[] rowData = {    
                                        sum.sTaxYear,                     //  
                                        String.format("%.2f", sum.fIncome),                     //
                                        String.format("%.2f",sum.fMorgPay),                    //
                                        String.format("%.2f",sum.fMorgInt),                    //
                                        String.format("%.2f",sum.fExpenses),                    //
                                        String.format("%.2f",sum.fTaxableIncome),                    //
                                        String.format("%.2f",sum.fTaxEposure),                    //                                      
                                        sum.sComment 
                                       };
                    
                    // because we are filtering we need a seperate counter (row index)
                    // iCount could be larger that row count as it will inc for
                    // every income object but we will have not added those that
                    // do not match our proId and taxID
                    if ( !sum.sTaxYear.equals( "ALL" ) )
                    {
                        tabModel.insertRow( iAddedRowCount, rowData);   
                        iAddedRowCount++;       
                        
                        // add up totals
                        fIncTotal += sum.fIncome;
                        fMorgTotal += sum.fMorgPay;
                        fMorgIntTotal += sum.fMorgInt;
                        fExpTotal += sum.fExpenses;
                        fTaxableTotal += sum.fTaxableIncome;
                        fTaxExposure += sum.fTaxEposure;
                    }
                }
            }
            
            // add totals row
            String[] rowData = {    
                                "TOTALS",                     //  
                                String.format("%.2f",fIncTotal),                     //
                                String.format("%.2f",fMorgTotal),                    //
                                String.format("%.2f",fMorgIntTotal),                    //
                                String.format("%.2f",fExpTotal),                    //
                                String.format("%.2f",fTaxableTotal),                    //
                                String.format("%.2f",fTaxExposure),                    //                                      
                                "" 
                               };
            tabModel.insertRow( iAddedRowCount, rowData); 
                    
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating summary table" + ex.toString() );
            bReturn = false;
        }
        
        
        return bReturn;
    }
    
    
    
    // populate summary table
    private boolean populateExepnsesTable()
    {
        
        boolean bReturn = true;
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        try
        {
            DefaultTableModel tabModel = (DefaultTableModel)expensesTable.getModel();
            ArrayList aExpenses = dbData.getExpenses();
            int iExpCount = aExpenses.size();
            Expense expen = null;
            int iAddedRowCount = 0;
            float fExpenseTotal = 0;
            
            // set custom rendere to turn totals col text blue
            expensesTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
            
            // before we populate clear current table data otherwise appended
            // each time this methos is called
            while (expensesTable.getRowCount() > 0) 
            {
                 tabModel.removeRow(0);
            }
            
                        // claer row id for each MortagegPayemnt Object. RowID is just 
            // tabvle row id stored in each dataobject so we can tie visual row to actual
            // data object. If we have called this func before row id can change for each
            // actual data id based on property and tax year combos so reset each time
            dbData.resetExpenseRowIDs();
            
            // lets get the current value of the tax year box
            String selTaxYear  = (String)taxYearCombo.getSelectedItem();
            String selProperty = (String)propertyCombo.getSelectedItem();
            
            // get IDS (foreigh Leys for the current selected taxyear and 
            // property combo boxes
            int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
            int iPropID = dbData.getMatchingPropertyID( selProperty );
   
            // iterate thorugh all data objects
            for ( int iCount = 0; iCount< iExpCount; iCount++ )
            {
                expen = (Expense)aExpenses.get( iCount );
                
                 // only add to table if matches combo boxes for tax year and property
                // if combo box ID = 99 it maens add all for all properties
                // and tax years
                if ( ( iTaxID == expen.iTaxID || iTaxID == 99 ) &&            //
                     ( iPropID == expen.iPropID || iPropID == 99 ) )   
                {
                    String[] rowData = {    
                                        simpleDateFormat.format(expen.expDate),  //  
                                        expen.sDesc,                             //
                                        expen.sSupplier,                         //
                                        String.format("%.2f",expen.dValue),                       //
                                        expen.sReciept,                          //
                                        expen.sReceiptLink,                      //
                                        expen.sComment                           //
                                       };
                    
                    // because we are filtering we need a seperate counter (row index)
                    // iCount could be larger that row count as it will inc for
                    // every income object but we will have not added those that
                    // do not match our proId and taxID
                    tabModel.insertRow( iAddedRowCount, rowData);   
                    iAddedRowCount++;      
                    
                    // we need to set the IRowCount member var for incom so that we can pull it out
                    // when we want tu update or delete
                    expen.iRowID = (iAddedRowCount - 1);
                    
                    // keep running total of expenses for TOTALS row
                    fExpenseTotal += expen.dValue;
                }
            }
            
            // add an extra row for the total
            String[] rowData = {    
                                "TOTALS",     //  
                                "",     //
                                "",     //
                                String.format("%.2f",fExpenseTotal),       //
                                "",     //
                                "",     //
                                ""
                               };
            tabModel.insertRow( iAddedRowCount, rowData); 
        }
        catch( Exception ex )
        {
            logger.error("Exception thrown populating expenses table" + ex.toString() );
            bReturn = false;
        }
        
        
        return bReturn;
    }
    
    // reload all for DB and refresh all views
    public boolean changeDbandUpdateView( int iDbMenuItem )
    {

      boolean bReturn = true;  

      // get db JSON loaded object that relates to index
      DatabaseConfig dbConfig = dbList.getConfigItem(iDbMenuItem);
      
      // check got config form JSON file
      if ( dbConfig == null )
      {    
          logger.error("Could not fing valid DB config object to connect to Db" );
          return false;
      }

      DatabaseConnectionParams dbParams = new DatabaseConnectionParams( dbConfig.getJdbcDriver(),    //
                                                                        dbConfig.getConnectionString(), //
                                                                        dbConfig.getUsername(),          //
                                                                        dbConfig.getPassword());

      System.out.println( "Attempting to connect to : ");dbParams.toString();
      // creat to db and connect
      dbConnection = new DatabaseConnection( dbParams );
      if ( !dbConnection.connect() )
      {    
          logger.error("Failed to connect to database :" + dbParams.toString() );
          JOptionPane.showMessageDialog( this, "Failed to connect to Database", //
                         "Startup Error",                           //
                         JOptionPane.ERROR_MESSAGE);
          bReturn = false;
      }
      else
      {
          // load the data
          dbData = new DatabaseData();
          dbConnection.loadDatabaseData( dbData );
          dbConnection.disConnect();
          logger.info("Successfully to connected to database :" + dbParams.toString()+ " and loaded data");
      }           
      
      // ok build data views
      // populate UI stuff with DB data
      popultaeTaxCombo();
      propertyCombo();
      populateTaxRateCombo();
      populateIncomeTable(); 
      populateMortgageTable();
      populateExepnsesTable();

      // lets get the current value of the tax year box
      String selTaxYear  = (String)taxYearCombo.getSelectedItem();
      String selProp  = (String)propertyCombo.getSelectedItem();
      int iTaxID = dbData.getMatchingTaxYearID( selTaxYear );
      int iPropID = dbData.getMatchingPropertyID( selProp );
      int iTaxRate = Integer.parseInt( (String)comboTaxBand.getSelectedItem());
      // int iTaxRate = 40;

      dbData.buildSummaryData( iTaxID, iPropID, iTaxRate );

      populateSummaryTable();

      return bReturn;
  }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JComboBox<String> comboTaxBand;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JTable expensesTable;
    private javax.swing.JTable incomeTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable mortgageTable;
    private javax.swing.JButton printButton;
    private javax.swing.JComboBox<String> propertyCombo;
    private javax.swing.JLabel propertyLabel;
    private javax.swing.JTable summaryTable;
    private javax.swing.JTabbedPane tabbedFrame;
    private javax.swing.JLabel taxBandLabel;
    private javax.swing.JComboBox<String> taxYearCombo;
    private javax.swing.JLabel taxYearLabel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
