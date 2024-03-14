/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;

import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import static peeks.RPAS.UI.ExpenseDialog.ADD;
import static peeks.RPAS.UI.ExpenseDialog.DELETE;
import static peeks.RPAS.UI.ExpenseDialog.UPDATE;
import peeks.RPAS.UI.fields.AmountField;
import peeks.RPAS.UI.fields.StringField;
import peeks.RPAS.UI.fields.TaxYearDate;
import peeks.RPAS.dataobjects.DatabaseData;
import peeks.RPAS.dataobjects.Expense;
import peeks.RPAS.dataobjects.Property;
import peeks.RPAS.dataobjects.RentalIncome;
import peeks.database.DatabaseConnection;

/**
 *
 * @author Peeks
 */
public class IncomeDialog extends javax.swing.JDialog {

    
        // consts
    public final static int ADD      = 1;
    public final static int DELETE   = 2;
    public final static int UPDATE   = 3;
    
    
    // member var to hold db data objects
    DatabaseData        dbData       = null;
    DatabaseConnection  dbConnection = null;
    int                 iAction      = -1;
    RentalIncome        rent         = null;
    boolean             bReloadDb    = false;       
    
    // expense var to hold new expense object
    RentalIncome newRent = new RentalIncome();
    
    /**
     * Creates new form IncomeDialog
     */
    public IncomeDialog(java.awt.Frame parent, boolean modal, RentalIncome rentIncome, int action) {
        super(parent, modal);
        initComponents();
        
         // set member var
        dbData = ((RPASUI)parent).dbData;
        dbConnection = ((RPASUI)parent).dbConnection;
        iAction = action;
        rent = rentIncome;       
        
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
                setUpdateMode( rent );
                break;
            case DELETE :
                setDeleteMode( rent );
                break;
             case ADD :
                // do nothing as default mode is add
                break;
        }
            
        // CENTRALISE WINDOW
        setLocationRelativeTo(null);
    }
    
    // set dilog box up for update of data income object
    public boolean setUpdateMode( RentalIncome incom )
    {
        // ok if action passed in is UPDATE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"  
        
        boolean bReturn = true;
        
        actionButton.setText( "Update" );
        bReturn = loadUIFields( incom );
        
        return bReturn;
    }
    
    private boolean setDeleteMode( RentalIncome incom )
    {
        // ok if action passed in is DELETE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"        
        boolean bReturn = true;
        
        actionButton.setText( "Delete" );
        bReturn = loadUIFields( incom );
        
        dateField.setEditable( false );
        commentField.setEditable( false );
        rentField.setEditable( false );
        bankField.setEditable( false );
        payTypeField.setEditable( false );
        propertyCombo.setEnabled( false );
        srcField.setEditable( false );
        tenantField.setEditable( false );
        
        return bReturn;
    }

    // popuate UI filds with expense object
    private boolean loadUIFields( RentalIncome incom )
    {
        
        boolean bReturn = true;
    
        rentField.setText( String.format("%.2f",incom.dRentalIncome) );
        bankField.setText( String.format("%.2f",incom.dBankIncome) );
        payTypeField.setText( incom.sPayMode );        
        srcField.setText( incom.sPaySource );
        tenantField.setText( incom.sTenant );
        commentField.setText( incom.sComment );
        
        // need to set the property selector box to the value for the passed in expense
        String AddrLine1 = dbData.getPropertyAddressLine1( incom.iPropID );
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
            dateField.setText( dFormat.format( incom.incomeDate ) );
        }
        catch( Exception ex )
        {
            RPASUI.logger.debug( "Exception thrown formatting data object to populate UI date filed on Income Dlg");
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

        incomePanel = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        rentField = new javax.swing.JTextField();
        rentLabel = new javax.swing.JLabel();
        bankField = new javax.swing.JTextField();
        srcLabel = new javax.swing.JLabel();
        srcField = new javax.swing.JTextField();
        payTypeLabel = new javax.swing.JLabel();
        payTypeField = new javax.swing.JTextField();
        bankLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentField = new javax.swing.JTextArea();
        commentLabel = new javax.swing.JLabel();
        propertyLabel = new javax.swing.JLabel();
        propertyCombo = new javax.swing.JComboBox<>();
        tenantLabel = new javax.swing.JLabel();
        tenantField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        actionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Income");

        incomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Income Details"));

        dateLabel.setText("Date");

        dateField.setToolTipText("Income deceived date");

        rentField.setToolTipText("Rent amount");

        rentLabel.setText("Rent");

        bankField.setToolTipText("Amount paid into bank");

        srcLabel.setText("Source");

        srcField.setToolTipText("Payment source");

        payTypeLabel.setText("Pay Type");

        payTypeField.setToolTipText("Payment mode");

        bankLabel1.setText("Bank");

        commentField.setColumns(20);
        commentField.setRows(5);
        jScrollPane1.setViewportView(commentField);

        commentLabel.setText("Comment");

        propertyLabel.setText("Property");

        tenantLabel.setText("Tenant");

        tenantField.setToolTipText("Tenant name");

        javax.swing.GroupLayout incomePanelLayout = new javax.swing.GroupLayout(incomePanel);
        incomePanel.setLayout(incomePanelLayout);
        incomePanelLayout.setHorizontalGroup(
            incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, incomePanelLayout.createSequentialGroup()
                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(incomePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(incomePanelLayout.createSequentialGroup()
                                .addComponent(commentLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, incomePanelLayout.createSequentialGroup()
                                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, incomePanelLayout.createSequentialGroup()
                                        .addComponent(dateLabel)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, incomePanelLayout.createSequentialGroup()
                                        .addComponent(payTypeLabel)
                                        .addGap(21, 21, 21)))
                                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(incomePanelLayout.createSequentialGroup()
                                        .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rentLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(rentField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(56, 56, 56)
                                        .addComponent(bankLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(bankField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(incomePanelLayout.createSequentialGroup()
                                        .addComponent(payTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(srcLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(srcField))))))
                    .addGroup(incomePanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(tenantLabel)
                        .addGap(18, 18, 18)
                        .addComponent(tenantField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(propertyLabel)
                        .addGap(18, 18, 18)
                        .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        incomePanelLayout.setVerticalGroup(
            incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incomePanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentLabel)
                    .addComponent(bankField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankLabel1))
                .addGap(22, 22, 22)
                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(srcField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(srcLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propertyLabel)
                    .addComponent(tenantLabel)
                    .addComponent(tenantField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commentLabel))
                .addContainerGap())
        );

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        actionButton.setText("Add");
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(incomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(incomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(actionButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        
                // TODO add your handling code here:
        setVisible( false );
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // called when action buttom 
    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        // TODO add your handling code here:
        
        // clear rent object of new any old data from last attempt to add
        newRent.clear();
        
        boolean bRet = true;
        switch ( iAction )
        {
            case ADD : 
                    bRet = createIncome();
                    break;
            case UPDATE :
                    bRet = updateIncome();
                    break;
            case DELETE :
                    bRet = deleteIncome();    
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

    // update existing income record
    private boolean updateIncome()
    {
               
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newRent with the value of those fields
        if ( validateFields() )
        {
            try
            {
                RPASUI.logger.debug( "All Income fields valid so attempting to update existing Income record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newRent.iPropID = iPropID;
                newRent.sTaxYear = dbData.getTaxYearDesc( newRent.iTaxID );
                RPASUI.logger.debug( newRent.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                // test SQL
                String sql = "UPDATE RENTAL_INCOME SET " +
                             "TAX_YEAR = " + "'" + newRent.sTaxYear + "'," +              // 
                             "DATE = '" + dFormat.format( newRent.incomeDate) + "'," + // 
                             "BANK_INCOME = " + newRent.dBankIncome + "," +             // 
                             "RENT_VALUE = " + newRent.dRentalIncome + "," +            // 
                             "PAYMENT_MODE = '" + newRent.sPayMode + "'," +                    //
                             "RENT_SOURCE = '" + newRent.sPaySource + "'," +              //
                             "TENANT = '" + newRent.sTenant + "'," +     //
                             "COMMENT = '" + newRent.sComment + "'," +              //
                             "PROPERTY_ID = " + newRent.iPropID + "," +             //
                             "TAX_YEAR_ID = " + newRent.iTaxID  +                   //
                             " WHERE ID = " + rent.iID + ";";

                RPASUI.logger.debug( "ADD INCOME SQL : " + sql );
                
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured updating income record in database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
                
                
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured updating income record in database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on Income fields");
            bReturn = false;
        } 
        
        return bReturn;    
    }
    
    // called to delet an income record
    public boolean deleteIncome()
    {
                
        boolean bReturn = true;
        
        try
        {    
            // test SQL
            String sql = "DELETE FROM RENTAL_INCOME WHERE ID = " + rent.iID + ";";
                 
            RPASUI.logger.debug( "DELETE EXPENSE SQL : " + sql );
            int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );

            if ( iUpdateCount != 1 )
            {
                JOptionPane.showMessageDialog( this,                                //    
                                               "Error occured deletingn income record from database", //
                                               "Database Error",                           //
                                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        catch( Exception ex )
        {    
            RPASUI.logger.error( ex.toString() );
            JOptionPane.showMessageDialog( this,                                //    
                           "Error occured deleteing income record from database", //
                           "Database Error",                           //
                           JOptionPane.ERROR_MESSAGE);
            bReturn = false;
        }

        return bReturn;      
    }
    
    // create  anew record
    private boolean createIncome()
    {
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newRent with the value of those fields
        if ( validateFields() )
        {
            
            try
            {
                RPASUI.logger.debug( "All Icome fields valid so attempting to create new Incom record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newRent.iPropID = iPropID;
                newRent.sTaxYear = dbData.getTaxYearDesc( newRent.iTaxID );
                RPASUI.logger.debug( newRent.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                  
                // test SQL
                String sql = "INSERT INTO RENTAL_INCOME ( TAX_YEAR, DATE, BANK_INCOME, RENT_VALUE, PAYMENT_MODE, " + //
                             "RENT_SOURCE, TENANT, COMMENT, PROPERTY_ID, TAX_YEAR_ID )" +            //
                             "VALUES (" +                                                               //
                             "'" + newRent.sTaxYear +"'," +                   //
                             "'" + dFormat.format( newRent.incomeDate) + "'," +        //
                             newRent.dBankIncome + "," +                     //
                             newRent.dRentalIncome + "," +                 //
                             "'" + newRent.sPayMode + "'," +                    //
                             "'" + newRent.sPaySource + "'," +                  //
                             "'" + newRent.sTenant + "'," +              //
                             "'" + newRent.sComment + "'," +                  //
                             newRent.iPropID + "," +                   //
                             newRent.iTaxID + ");";                     
                
                //RPASUI.logger.debug( "ADD EXPENSE SQL : " + sql );
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured adding new Incom record to database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured adding new incom record to database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
            
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on Incom fields");
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
            // validate rent income
            String sRent  = rentField.getText();
            AmountField rentF = new AmountField( 12, true );
            System.out.println("Rent field = " + sRent );
            
            if ( !rentF.validate( sRent ) )
            {
                rentField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                rentField.setBackground(Color.white);
                        
                // success - put value in member var expense object
                newRent.dRentalIncome = rentF.fAmount;
            }
            
            // validate bank income
            String sBank  = bankField.getText();
            AmountField bankF = new AmountField( 12, true );
            System.out.println("Bank field = " + sBank );
            
            if ( !bankF.validate( sBank ) )
            {
                bankField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                bankField.setBackground(Color.white);
                        
                // success - put value in member var expense object
                newRent.dBankIncome = bankF.fAmount;
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
            StringField payModeValidator = new StringField( RPASUI.TBL_INCOM_PAYMODE_LEN , true );
            StringField paySrcValidator = new StringField( RPASUI.TBL_INCOM_PAY_SRC_LEN , true );
            StringField commentValidator = new StringField( RPASUI.TBL_INCOM_COMMENT_LEN , false );
            StringField tenantValidator = new StringField( RPASUI.TBL_INCOM_TENANT_LEN, false );
            
            // pay mode box
            if ( !payModeValidator.validate( payTypeField.getText() ) )
                payTypeField.setBackground(Color.yellow);
            else
            {
                payTypeField.setBackground(Color.white);              
                // success - put value in member var expense object
                newRent.sPayMode = payModeValidator.sValue;
            }
            // pay src box
            if ( !paySrcValidator.validate( srcField.getText() ) )
                srcField.setBackground(Color.yellow);
            else
            {
                srcField.setBackground(Color.white);
                // success - put value in member var expense object
                newRent.sPaySource = paySrcValidator.sValue;
            }
            // comment box
            if ( !commentValidator.validate( commentField.getText() ) )
                commentField.setBackground(Color.yellow);
            else
            {
                commentField.setBackground(Color.white);
                // success - put value in member var expense object
                newRent.sComment = commentValidator.sValue;
            }

            // tenant field    
            if ( !tenantValidator.validate( tenantField.getText() ) )
                tenantField.setBackground(Color.yellow);
            else
            {
                tenantField.setBackground(Color.white);
                // success - put value in member var expense object
                newRent.sTenant = tenantValidator.sValue;
            }
            
            // check all validator are treu to set return vlaue
            if ( !payModeValidator.bValid      ||                  //
                 !paySrcValidator.bValid  ||                  //
                 !commentValidator.bValid   ||                  //   
                 !tenantValidator.bValid                        //   
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
                    newRent.incomeDate = date.date;
                    newRent.iTaxID = date.iTaxID;
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
            java.util.logging.Logger.getLogger(IncomeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IncomeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IncomeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IncomeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IncomeDialog dialog = new IncomeDialog(new javax.swing.JFrame(), true, null, -1);
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
    private javax.swing.JButton actionButton;
    private javax.swing.JTextField bankField;
    private javax.swing.JLabel bankLabel1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea commentField;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField payTypeField;
    private javax.swing.JLabel payTypeLabel;
    private javax.swing.JComboBox<String> propertyCombo;
    private javax.swing.JLabel propertyLabel;
    private javax.swing.JTextField rentField;
    private javax.swing.JLabel rentLabel;
    private javax.swing.JTextField srcField;
    private javax.swing.JLabel srcLabel;
    private javax.swing.JTextField tenantField;
    private javax.swing.JLabel tenantLabel;
    // End of variables declaration//GEN-END:variables
}
