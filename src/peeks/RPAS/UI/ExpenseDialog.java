/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;


import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import peeks.RPAS.UI.fields.*;
import peeks.RPAS.dataobjects.DatabaseData;
import peeks.RPAS.dataobjects.Expense;
import peeks.RPAS.dataobjects.Property;
import peeks.database.DatabaseConnection;


/**
 *
 * @author Peeks
 */
public class ExpenseDialog extends javax.swing.JDialog {

    
    // consts
    public final static int ADD      = 1;
    public final static int DELETE   = 2;
    public final static int UPDATE   = 3;
    
    
    // member var to hold db data objects
    DatabaseData        dbData       = null;
    DatabaseConnection  dbConnection = null;
    int                 iAction      = -1;
    Expense             expense      = null;
    boolean             bReloadDb    = false;       
    
    // expense var to hold new expense object
    Expense newExpense = new Expense();
    
    /**
     * Creates new form AddExpenseDialog
     */
    public ExpenseDialog(java.awt.Frame parent, boolean modal, Expense exp, int action  ) {
        super(parent, modal);
        initComponents();
        
        // set member var
        dbData = ((RPASUI)parent).dbData;
        dbConnection = ((RPASUI)parent).dbConnection;
        iAction = action;
        expense = exp;       
        
        // populate property combo
        Property prop = null;
        String addr = null;
        for ( int i = 0; i< dbData.getProperties().size(); i++ )
        {
            prop = (Property)dbData.getProperties().get( i );
            if ( !prop.sAddressLine1.equals( RPASUI.ALL ) )
                propertyCombo.addItem( prop.sAddressLine1 );
        }
        
        // determin mode and setup UI. Are we adding, deleting or updating
        switch (  action )
        {
            case UPDATE :
                setUpdateMode( exp );
                break;
            case DELETE :
                setDeleteMode( exp );
                break;
             case ADD :
                // do nothing as default mode is add
                break;
        }
            
        // CENTRALISE WINDOW
        setLocationRelativeTo(null);
        
        // grey out the recipt link label and box until checkbox selected
        receiptLinkLabel.setEnabled(false);
        receiptLinkBox.setEnabled( false );
        
    }
        
    public boolean setUpdateMode( Expense exp )
    {
        // ok if action passed in is UPDATE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"  
        
        boolean bReturn = true;
        
        actionButton.setText( "Update" );
        bReturn = loadUIFields( exp );
        
        return bReturn;
    }
    
    private boolean setDeleteMode( Expense exp )
    {
        // ok if action passed in is UPDATE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"  
        
        boolean bReturn = true;
        
        actionButton.setText( "Delete" );
        bReturn = loadUIFields( exp );
        
        supplierField.setEditable( false );
        commentBox.setEditable( false );
        descBox.setEditable( false );
        amountField.setEditable( false );
        dateField.setEditable( false );
        receiptLinkBox.setEditable( false );
        receiptCheckBox.setEnabled( false );
        propertyCombo.setEnabled( false );
        
        return bReturn;
    }
    
    // popuate UI filds with expense object
    private boolean loadUIFields( Expense exp )
    {
        
        boolean bReturn = true;
    
        supplierField.setText( exp.sSupplier );
        commentBox.setText( exp.sComment );
        descBox.setText( exp.sDesc );        
        amountField.setText( String.format("%.2f",exp.dValue) );
        dateField.setText( exp.expDate.toString() );
        receiptLinkBox.setText( exp.sReceiptLink );
        if ( exp.sReciept.equals( "Y"))
            receiptCheckBox.setSelected( true );
        else
            receiptCheckBox.setSelected( false );
        
        // need to set the property selector box to the value for the passed in expense
        String AddrLine1 = dbData.getPropertyAddressLine1( exp.iPropID );
        String sTemp = null;
        int iCount = propertyCombo.getItemCount();
        for ( int i = 0; i < iCount; i++ )
        {
            sTemp = propertyCombo.getItemAt( i );
            if ( sTemp.equals( AddrLine1) )
            {
                propertyCombo.setSelectedIndex( i );
                break;
            }    
        }
        
        try
        {
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yy");
            dateField.setText( dFormat.format( exp.expDate ) );
        }
        catch( Exception ex )
        {
            RPASUI.logger.debug( "Exception thrown formatting data object to populate UI date filed on Expnse Dlg");
            RPASUI.logger.debug( ex.toString() );
            bReturn = false;
        }
        
        return bReturn;
    }
    
    // member var that dictates whether we reload DB
    // ie, have we added, updated ir deleted db expenses
    public boolean getReloadDB()
    {
        return bReloadDb;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        actionButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        addExpensePanel = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        descLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descBox = new javax.swing.JTextArea();
        supplierLabel = new javax.swing.JLabel();
        supplierField = new javax.swing.JTextField();
        commentLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        commentBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ReceiptPanel = new javax.swing.JPanel();
        receiptCheckBox = new javax.swing.JCheckBox();
        receiptLinkLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        receiptLinkBox = new javax.swing.JTextArea();
        dateField = new javax.swing.JTextField();
        propertyCombo = new javax.swing.JComboBox<>();
        propertyLabel = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        jTextField1.setText("jTextField1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Expenses");
        setLocation(new java.awt.Point(400, 300));
        setResizable(false);

        actionButton.setText("Add");
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        addExpensePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Expense Detail"));

        dateLabel.setText("Date");

        amountLabel.setText("Amount");

        amountField.setToolTipText("Expense value in pounds");

        descLabel.setText("Desc");

        descBox.setColumns(20);
        descBox.setRows(5);
        descBox.setToolTipText("Expense description");
        jScrollPane1.setViewportView(descBox);

        supplierLabel.setText("Supplier");

        supplierField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierFieldActionPerformed(evt);
            }
        });

        commentLabel.setText("Comment");

        commentBox.setColumns(20);
        commentBox.setRows(5);
        jScrollPane2.setViewportView(commentBox);

        ReceiptPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Receipt"));

        receiptCheckBox.setText("Receipt");
        receiptCheckBox.setToolTipText("Is a receipt available");
        receiptCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiptCheckBoxActionPerformed(evt);
            }
        });

        receiptLinkLabel.setText("Receipt Link");

        receiptLinkBox.setColumns(20);
        receiptLinkBox.setRows(5);
        jScrollPane3.setViewportView(receiptLinkBox);

        javax.swing.GroupLayout ReceiptPanelLayout = new javax.swing.GroupLayout(ReceiptPanel);
        ReceiptPanel.setLayout(ReceiptPanelLayout);
        ReceiptPanelLayout.setHorizontalGroup(
            ReceiptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(receiptCheckBox)
                .addGap(36, 36, 36)
                .addComponent(receiptLinkLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        ReceiptPanelLayout.setVerticalGroup(
            ReceiptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReceiptPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReceiptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(receiptCheckBox)
                    .addComponent(receiptLinkLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ReceiptPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        dateField.setToolTipText("dd/mm/yy");

        propertyLabel.setText("Property");

        javax.swing.GroupLayout addExpensePanelLayout = new javax.swing.GroupLayout(addExpensePanel);
        addExpensePanel.setLayout(addExpensePanelLayout);
        addExpensePanelLayout.setHorizontalGroup(
            addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addExpensePanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReceiptPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(commentLabel)
                            .addComponent(supplierLabel)
                            .addComponent(amountLabel)
                            .addComponent(dateLabel))
                        .addGap(18, 18, 18)
                        .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addExpensePanelLayout.createSequentialGroup()
                                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addComponent(descLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addComponent(supplierField)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(29, 29, 29))
            .addGroup(addExpensePanelLayout.createSequentialGroup()
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel1)
                        .addGap(189, 189, 189))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addExpensePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(propertyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addExpensePanelLayout.setVerticalGroup(
            addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addExpensePanelLayout.createSequentialGroup()
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addExpensePanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(descLabel))
                            .addGroup(addExpensePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addExpensePanelLayout.createSequentialGroup()
                                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amountLabel))))
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierLabel)
                    .addComponent(supplierField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addExpensePanelLayout.createSequentialGroup()
                        .addComponent(commentLabel)
                        .addGap(15, 15, 15)))
                .addComponent(ReceiptPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33))
                    .addGroup(addExpensePanelLayout.createSequentialGroup()
                        .addGroup(addExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propertyLabel))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addExpensePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addGap(176, 176, 176))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addExpensePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actionButton)
                    .addComponent(cancelButton))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Expense");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        // TODO add your handling code here:

        // clear expense object of new any old data from last attempt to add
        newExpense.clear();
        
        boolean bRet = true;
        switch ( iAction )
        {
            case ADD : 
                    bRet = createExpense();

                    break;
            case UPDATE :
                    bRet = updateExpense();
                    break;
            case DELETE :
                    bRet = deleteExpense();    
                    break;
        }
        
        // close dlg if operation worked
        if ( bRet )
        {
            // close dlg
            setVisible( false );

            // set flag to tell parent to reload db as we had added a new exp
            bReloadDb = true;
            dispose();
        }
    }//GEN-LAST:event_actionButtonActionPerformed

    // called to updateExpense in db
    public boolean updateExpense()
    {
        
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newExpemnse with the value of those fields
        if ( validateFields() )
        {
            try
            {
                RPASUI.logger.debug( "All Expense fields valid so attempting to update existing expense record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newExpense.iPropID = iPropID;
                newExpense.sTaxYear = dbData.getTaxYearDesc( newExpense.iTaxID );
                RPASUI.logger.debug( expense.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                // test SQL
                String sql = "UPDATE Expenses SET " +
                             "TAX_YEAR = " + "'" + newExpense.sTaxYear + "'," +              // 
                             "DATE = '" + dFormat.format( newExpense.expDate) + "'," + // 
                             "DESCRIPTION = '" + newExpense.sDesc + "'," +             // 
                             "SUPPLIER = '" + newExpense.sSupplier + "'," +            // 
                             "VALUE = " + newExpense.dValue + "," +                    //
                             "RECEIPT = '" + newExpense.sReciept + "'," +              //
                             "RECEIPT_LINK = '" + newExpense.sReceiptLink + "'," +     //
                             "COMMENT = '" + newExpense.sComment + "'," +              //
                             "PROPERTY_ID = " + newExpense.iPropID + "," +             //
                             "TAX_YEAR_ID = " + newExpense.iTaxID  +                   //
                             " WHERE ID = " + expense.iID + ";";

                RPASUI.logger.debug( "ADD EXPENSE SQL : " + sql );
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured updating expense in database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
                
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured updating expense in database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on Expense fields");
            bReturn = false;
        } 
        
        return bReturn;    
    }
            
    // called from addBUtonEvent Hander if dlg box is in DELETE mode
    private boolean deleteExpense()
    {
        
        boolean bReturn = true;
        
        try
        {    
            // test SQL
            String sql = "DELETE FROM Expenses WHERE ID = " + expense.iID + ";";
                 
            // RPASUI.logger.debug( "DELETE EXPENSE SQL : " + sql );
            int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );

            if ( iUpdateCount != 1 )
            {
                JOptionPane.showMessageDialog( this,                                //    
                                               "Error occured delete expense from database", //
                                               "Database Error",                           //
                                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        catch( Exception ex )
        {    
            RPASUI.logger.error( ex.toString() );
            JOptionPane.showMessageDialog( this,                                //    
                           "Error occured deleteing expense from database", //
                           "Database Error",                           //
                           JOptionPane.ERROR_MESSAGE);
            bReturn = false;
        }

        return bReturn;
    }
    
    
    // called from addBUtonEvent Hander if dlg box is in ADD mode
    private boolean createExpense()
    {
        
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newExpemnse with the value of those fields
        if ( validateFields() )
        {
            try
            {
                RPASUI.logger.debug( "All Expense fields valid so attempting to create new expense record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newExpense.iPropID = iPropID;
                newExpense.sTaxYear = dbData.getTaxYearDesc( newExpense.iTaxID );
                RPASUI.logger.debug( newExpense.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                // test SQL
                String sql = "INSERT INTO Expenses ( TAX_YEAR, DATE, DESCRIPTION, SUPPLIER, VALUE, " + //
                             "RECEIPT, RECEIPT_LINK, COMMENT, PROPERTY_ID, TAX_YEAR_ID )" +            //
                             "VALUES (" +                                                               //
                             "'" + newExpense.sTaxYear +"'," +                   //
                             "'" + dFormat.format( newExpense.expDate) + "'," +        //
                             "'" + newExpense.sDesc + "'," +                     //
                             "'" + newExpense.sSupplier + "'," +                 //
                             newExpense.dValue + "," +                    //
                             "'" + newExpense.sReciept + "'," +                  //
                             "'" + newExpense.sReceiptLink + "'," +              //
                             "'" + newExpense.sComment + "'," +                  //
                             newExpense.iPropID + "," +                   //
                             newExpense.iTaxID + ");";                     
                
                // RPASUI.logger.debug( "ADD EXPENSE SQL : " + sql );
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured adding new expense to database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured adding new expense to database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on Expense fields");
            bReturn = false;
        } 
        
        return bReturn;
        
    }
    
    // valid date entries in UI fields
    public boolean validateFields()
    {
        boolean bReturn             = true;
        boolean bDateValid          = true;
        boolean bAmountValid        = true;
        boolean bTextFieldsValid    = true;
        
        bDateValid = validateDateField();
        bAmountValid = validateAmountField(); 
        bTextFieldsValid = validateTextFields();
        
        // date field
        if ( !bDateValid    ||                                    //
             !bAmountValid  ||                                    //
             !bTextFieldsValid   
           )
        {    
                bReturn = false;
        }
        
        return bReturn;
    }
    
    public boolean validateAmountField()
    {
        // validate all fields
        
        boolean bReturn = true;
        try
        {
            String sAmount  = amountField.getText();
            AmountField amount = new AmountField( 12, true );
            System.out.println("Amount field = " + sAmount );
            
            if ( !amount.validate( sAmount ) )
            {
                amountField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                amountField.setBackground(Color.white);
                        
                // success - put value in member var expense object
                newExpense.dValue = amount.fAmount;
            }
        }
        catch( NullPointerException ex )
        {
            System.out.println("Exception thrown validating amount field");
            bReturn = false;
        }
                
        return bReturn;
    }
    
    // valid a string field.
    // sValue is field to validate
    // comp is the UI comp asscoaited with the field
    public boolean validateTextFields()
    {
        // validate all fields - each fleld has a max length as define by bd table
        boolean bReturn = true;
        
        try
        {
            // creat validator objects
            StringField descValidator = new StringField( RPASUI.TBL_EXP_DESC_LEN , true );
            StringField supplierValidator = new StringField( RPASUI.TBL_EXP_SUPPLIER , true );
            StringField commentValidator = new StringField( RPASUI.TBL_EXP_COMMENT_LEN , false );
            StringField reclnkValidator = new StringField( RPASUI.TBL_EXP_RECEIPT_LNK, false );
            
            // desc box
            if ( !descValidator.validate( descBox.getText() ) )
                descBox.setBackground(Color.yellow);
            else
            {
                descBox.setBackground(Color.white);              
                // success - put value in member var expense object
                newExpense.sDesc = descValidator.sValue;
            }
            // supplier box
            if ( !supplierValidator.validate( supplierField.getText() ) )
                supplierField.setBackground(Color.yellow);
            else
            {
                supplierField.setBackground(Color.white);
                // success - put value in member var expense object
                newExpense.sSupplier = supplierValidator.sValue;
            }
            // comment box
            if ( !commentValidator.validate( commentBox.getText() ) )
                commentBox.setBackground(Color.yellow);
            else
            {
                commentBox.setBackground(Color.white);
                // success - put value in member var expense object
                newExpense.sComment = commentValidator.sValue;
            }
            // comment box
            if ( receiptLinkBox.isEnabled() )
            {
                // add receipt to member exp var
                newExpense.sReciept = "Y";
                
                if ( !reclnkValidator.validate( receiptLinkBox.getText() ) )
                    receiptLinkBox.setBackground(Color.yellow);
                else
                {
                    receiptLinkBox.setBackground(Color.white);
                    // success - put value in member var expense object
                    newExpense.sReceiptLink = reclnkValidator.sValue;
                }
            }
            else
            {
                // no receipt
                reclnkValidator.bValid = true;
                newExpense.sReciept = "N";
                newExpense.sReceiptLink = "";
            }
            
            // check all validator are treu to set return vlaue
            if ( !descValidator.bValid      ||                  //
                 !supplierValidator.bValid  ||                  //
                 !commentValidator.bValid   ||                  //   
                 !reclnkValidator.bValid                        //   
               )
            {
                bReturn = false;
            }    
            
        }
        catch( NullPointerException ex )
        {
            System.out.println("Exception thrown validating amount field");
            bReturn = false;
        }
        
        return bReturn;
    }
 
    // check date entered is valid in terms of both date and within a tax year configured within the system,
    public boolean validateDateField()
    {
        // validate all fields
        
        boolean bReturn = true;
        
        try
        {
            String sDate  = dateField.getText();
            TaxYearDate date = new TaxYearDate( true );
            
            if ( !date.validate( sDate ) )
            {
                dateField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                int iTaxID = date.getConfiguredTaxYear( dbData.getTaxYears() );
                RPASUI.logger.debug( "Matching tax year ID for " + sDate + " is " + iTaxID);
                
                // if valid date but not wihin tax year then date is ivalid for RPAS
                if ( iTaxID == -1 )
                {
                    dateField.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Entered date is not within a configured Tax Year", //
                                                   "Date Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
                else
                {
                    // success so turn UI field white and add vals to member exp obj
                    dateField.setBackground(Color.white);
                    // success - put value in member var expense object
                    newExpense.expDate = date.date;
                    newExpense.iTaxID = date.iTaxID;
                }
            }
        }
        catch( NullPointerException ex )
        {
            System.out.println("No date field value entered");
            bReturn = false;
        }
        
        return bReturn;
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        setVisible( false );
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void supplierFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supplierFieldActionPerformed

    private void receiptCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiptCheckBoxActionPerformed
        // TODO add your handling code here:
        if ( receiptCheckBox.isSelected() )
        {
            receiptLinkLabel.setEnabled(true);
            receiptLinkBox.setEnabled( true );      
        }
        else
        {
            receiptLinkLabel.setEnabled(false);
            receiptLinkBox.setEnabled( false );
        }
        
    }//GEN-LAST:event_receiptCheckBoxActionPerformed

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
            java.util.logging.Logger.getLogger(ExpenseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpenseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpenseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpenseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ExpenseDialog dialog = new ExpenseDialog(new javax.swing.JFrame(), true, null, -1 );
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ReceiptPanel;
    private javax.swing.JButton actionButton;
    private javax.swing.JPanel addExpensePanel;
    private javax.swing.JTextField amountField;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea commentBox;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextArea descBox;
    private javax.swing.JLabel descLabel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> propertyCombo;
    private javax.swing.JLabel propertyLabel;
    private javax.swing.JCheckBox receiptCheckBox;
    private javax.swing.JTextArea receiptLinkBox;
    private javax.swing.JLabel receiptLinkLabel;
    private javax.swing.JTextField supplierField;
    private javax.swing.JLabel supplierLabel;
    // End of variables declaration//GEN-END:variables
}
