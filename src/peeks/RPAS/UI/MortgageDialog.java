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
import peeks.RPAS.dataobjects.MortgagePayment;
import peeks.RPAS.dataobjects.Property;
import peeks.database.DatabaseConnection;


/**
 *
 * @author Peeks
 */
public class MortgageDialog extends javax.swing.JDialog {
    
    
        // consts
    public final static int ADD      = 1;
    public final static int DELETE   = 2;
    public final static int UPDATE   = 3;
    
    
    // member var to hold db data objects
    DatabaseData        dbData       = null;
    DatabaseConnection  dbConnection = null;
    int                 iAction      = -1;
    MortgagePayment     morgPay      = null;
    boolean             bReloadDb    = false;       
    
    // expense var to hold new expense object
    MortgagePayment     newMorgPay   = new MortgagePayment();

    /**
     * Creates new form MortgageDialog
     */
    public MortgageDialog(java.awt.Frame parent, boolean modal, MortgagePayment morg, int action ) {
        super(parent, modal);
        initComponents();
        
          // set member var
        dbData = ((RPASUI)parent).dbData;
        dbConnection = ((RPASUI)parent).dbConnection;
        iAction = action;
        morgPay = morg;       
        
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
                setUpdateMode( morg );
                break;
            case DELETE :
                setDeleteMode( morg );
                break;
             case ADD :
                // do nothing as default mode is add
                break;
        }
            
        // CENTRALISE WINDOW
        setLocationRelativeTo(null);
    }

   // setUI up for UPDATE Dlg
   public boolean setUpdateMode( MortgagePayment morg )
    {
        // ok if action passed in is UPDATE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"  
        
        boolean bReturn = true;
        
        actionButton.setText( "Update" );
        bReturn = loadUIFields( morg );
        
        return bReturn;
    }
    
    // SETUI up for delete Dlg
    private boolean setDeleteMode( MortgagePayment morg )
    {
        // ok if action passed in is UPDATE then populate UI with exp object passed in and chnage text o BUtton to say 'Update' instead of 'Add"  
        
        boolean bReturn = true;
        
        actionButton.setText( "Delete" );
        bReturn = loadUIFields( morg );
        
        // diable UI controls as deleteing so no need to change
        commentBox.setEditable( false );
        amountField.setEditable( false );
        interestField.setEditable( false );
        dateField.setEditable( false );
        propertyCombo.setEnabled( false );
        
        return bReturn;
    }
        
    // popuate UI filds with MortgagePayment object
    private boolean loadUIFields( MortgagePayment morg )
    {
        
        boolean bReturn = true;

        commentBox.setText( morg.sComment );   
        amountField.setText( String.format("%.2f",morg.dPayment) );
        interestField.setText( String.format("%.2f",morg.dInterest) );
        dateField.setText( morg.payDate.toString() );

        // need to set the property selector box to the value for the passed in expense
        String AddrLine1 = dbData.getPropertyAddressLine1( morg.iPropID );
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
            dateField.setText( dFormat.format( morg.payDate ) );
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
    
    // check amunt fields are valid numbers
    public boolean validateAmountField()
    {
        // validate all fields
        // we have 2 amounts, MortaggeAmount and Interest amount
        boolean bReturn = true;
        try
        {
            String sAmount  = amountField.getText();
            AmountField amount = new AmountField( 12, true );
            System.out.println("Amount field = " + sAmount );
            
            // do Mortgage payment amount
            if ( !amount.validate( sAmount ) )
            {
                amountField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                amountField.setBackground(Color.white);
                        
                // success - put value in member var expense object
                newMorgPay.dPayment = amount.fAmount;
            }
            
            // do Mortgage interest amount
            String sInt  = interestField.getText();
            if ( !amount.validate( sInt ) )
            {
                interestField.setBackground(Color.yellow);
                bReturn = false;
            }
            else
            {
                interestField.setBackground(Color.white);
                        
                // success - put value in member var expense object
                newMorgPay.dInterest = amount.fAmount;
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
            // create validator objects
            StringField commentValidator = new StringField( RPASUI.TBL_MORT_COMMENT_LEN , false );
            
            // comment box
            if ( !commentValidator.validate( commentBox.getText() ) )
                commentBox.setBackground(Color.yellow);
            else
            {
                commentBox.setBackground(Color.white);
                // success - put value in member var expense object
                newMorgPay.sComment = commentValidator.sValue;
            }
            
            // check all validator are treu to set return vlaue
            if ( !commentValidator.bValid )
            {
                bReturn = false;
            }    
            
        }
        catch( NullPointerException ex )
        {
            System.out.println("Exception thrown validating comment field");
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
                    newMorgPay.payDate = date.date;
                    newMorgPay.iTaxID = date.iTaxID;
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        mortgagePanel = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        propertyCombo = new javax.swing.JComboBox<>();
        propertyLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        interestLabel = new javax.swing.JLabel();
        interestField = new javax.swing.JTextField();
        commentLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentBox = new javax.swing.JTextArea();
        actionButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mortgage");
        setResizable(false);

        mortgagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Mortgage Detail"));

        dateLabel.setText("Date");

        propertyLabel.setText("Property");

        amountLabel.setText("Amount");

        interestLabel.setText("Interest");

        commentLabel.setText("Comment");

        commentBox.setColumns(20);
        commentBox.setRows(5);
        jScrollPane1.setViewportView(commentBox);

        javax.swing.GroupLayout mortgagePanelLayout = new javax.swing.GroupLayout(mortgagePanel);
        mortgagePanel.setLayout(mortgagePanelLayout);
        mortgagePanelLayout.setHorizontalGroup(
            mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mortgagePanelLayout.createSequentialGroup()
                .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mortgagePanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(amountLabel)
                            .addComponent(dateLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mortgagePanelLayout.createSequentialGroup()
                                .addComponent(propertyLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mortgagePanelLayout.createSequentialGroup()
                                .addComponent(interestLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(interestField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mortgagePanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(commentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        mortgagePanelLayout.setVerticalGroup(
            mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mortgagePanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propertyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propertyLabel))
                .addGap(18, 18, 18)
                .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(interestLabel)
                    .addComponent(interestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(mortgagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(commentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mortgagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addGap(85, 85, 85))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mortgagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actionButton)
                    .addComponent(cancelButton))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        setVisible( false );
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        // TODO add your handling code here:
        
        // clear expense object of new any old data from last attempt to add
        newMorgPay.clear();
        
        boolean bRet = true;
        switch ( iAction )
        {
            case ADD : 
                    bRet = createMorgEntry();

                    break;
            case UPDATE :
                    bRet = updateMorgEntry();
                    break;
            case DELETE :
                    bRet = deleteMorgEntry();    
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

    
    // called to update Mortgage Entry in db
    public boolean updateMorgEntry()
    {
        
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newExpemnse with the value of those fields
        if ( validateFields() )
        {
            try
            {
                RPASUI.logger.debug( "All mortgage fields valid so attempting to update existing expense record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newMorgPay.iPropID = iPropID;
                newMorgPay.sTaxYear = dbData.getTaxYearDesc( newMorgPay.iTaxID );
                RPASUI.logger.debug( newMorgPay.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                // test SQL
                String sql = "UPDATE Mortgage SET " +
                             "TAX_YEAR = " + "'" + newMorgPay.sTaxYear + "'," +              // 
                             "DATE = '" + dFormat.format( newMorgPay.payDate) + "'," + // 
                             "VALUE = " + newMorgPay.dPayment + "," +                    //
                             "INTEREST = " + newMorgPay.dInterest + "," +                    //
                             "PROVIDER = '', "  +                                       // forgot to put field on UI -dunno if worth adding?
                             "COMMENT = '" + newMorgPay.sComment + "'," +              //
                             "PropertyId = " + newMorgPay.iPropID + "," +             //
                             "TAX_YEAR_ID = " + newMorgPay.iTaxID  +                   //
                             " WHERE ID = " + morgPay.iID + ";";                       // use ID from original object in list / memory

                RPASUI.logger.debug( "ADD EXPENSE SQL : " + sql );
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured updating mortgage entry in database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
                
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured updating mortgage entry in database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on Mortgage entry fields");
            bReturn = false;
        } 
        
        return bReturn;    
    }
    
    
    // called from addBUtonEvent Hander if dlg box is in DELETE mode
    private boolean deleteMorgEntry()
    {
        
        boolean bReturn = true;
        
        try
        {    
            // test SQL
            String sql = "DELETE FROM Mortgage WHERE ID = " + morgPay.iID + ";";
                 
            // RPASUI.logger.debug( "DELETE EXPENSE SQL : " + sql );
            int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );

            if ( iUpdateCount != 1 )
            {
                JOptionPane.showMessageDialog( this,                                //    
                                               "Error occured deleting mortgage entry from database", //
                                               "Database Error",                           //
                                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        catch( Exception ex )
        {    
            RPASUI.logger.error( ex.toString() );
            JOptionPane.showMessageDialog( this,                                //    
                           "Error occured deleting mortgage entry from database", //
                           "Database Error",                           //
                           JOptionPane.ERROR_MESSAGE);
            bReturn = false;
        }

        return bReturn;
    }
    
    // create mortagge entry
    private boolean createMorgEntry()
    {
             
        boolean bReturn = true;
        
        // validateFields not only validates the UI fields but populate the member var
        // newExpemnse with the value of those fields
        if ( validateFields() )
        {
            try
            {
                RPASUI.logger.debug( "Delete mortgage fields valid so attempting to create new expense record");
  
                // need to add the proprty ID to the expense obect
                String sProp = (String)propertyCombo.getSelectedItem();
                int iPropID = dbData.getMatchingPropertyID( sProp );
                newMorgPay.iPropID = iPropID;
                newMorgPay.sTaxYear = dbData.getTaxYearDesc( newMorgPay.iTaxID );
                RPASUI.logger.debug( newMorgPay.toString() );
                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                // test SQL
                String sql = "INSERT INTO Mortgage ( TAX_YEAR, DATE, VALUE, " + //
                             "INTEREST, PROVIDER, COMMENT, PropertyId, TAX_YEAR_ID )" +            //
                             "VALUES (" +                                                               //
                             "'" + newMorgPay.sTaxYear +"'," +                   //
                             "'" + dFormat.format( newMorgPay.payDate) + "'," +        //
                             newMorgPay.dPayment + "," +                    //
                             newMorgPay.dInterest + "," +                    //
                             "''," +                                 // PROVUDER field missied of UI - dunno if worth outting on!
                             "'" + newMorgPay.sComment + "'," +                  //
                             newMorgPay.iPropID + "," +                   //
                             newMorgPay.iTaxID + ");";                     
                
                // RPASUI.logger.debug( "ADD EXPENSE SQL : " + sql );
                
                
                int iUpdateCount = dbConnection.executeUpdateSQLStatement( sql );
                
                if ( iUpdateCount != 1 )
                {
                    JOptionPane.showMessageDialog( this,                                //    
                                                   "Error occured adding new mortgage entry to database", //
                                                   "Database Error",                           //
                                                   JOptionPane.ERROR_MESSAGE);
                    bReturn = false;
                }
                
            }
            catch( Exception ex )
            {    
                RPASUI.logger.error( ex.toString() );
                JOptionPane.showMessageDialog( this,                                //    
                               "Error occured adding new mortgage entry to database", //
                               "Database Error",                           //
                               JOptionPane.ERROR_MESSAGE);
                bReturn = false;
            }
        }
        else
        {
            RPASUI.logger.debug( "Validation failure on mortgage fields");
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
            java.util.logging.Logger.getLogger(MortgageDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MortgageDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MortgageDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MortgageDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MortgageDialog dialog = new MortgageDialog(new javax.swing.JFrame(), true, null, -1);
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
    private javax.swing.JTextField amountField;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea commentBox;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField interestField;
    private javax.swing.JLabel interestLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel mortgagePanel;
    private javax.swing.JComboBox<String> propertyCombo;
    private javax.swing.JLabel propertyLabel;
    // End of variables declaration//GEN-END:variables
}
