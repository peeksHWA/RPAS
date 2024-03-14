/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

import java.util.ArrayList;
import peeks.RPAS.dataobjects.Expense;
import peeks.RPAS.dataobjects.Property;
import peeks.RPAS.dataobjects.TaxYear;
import peeks.RPAS.dataobjects.MortgagePayment;
import peeks.RPAS.UI.RPASUI;


/**
 *
 * @author Peeks
 */
public class DatabaseData 
{

    // List of database records
    protected ArrayList     aExpenses       = new ArrayList( 150 );
    protected ArrayList     aProperties     = new ArrayList( 10 );
    protected ArrayList     aTaxYears       = new ArrayList( 10 );
    protected ArrayList     aMortgagePay    = new ArrayList( 150 );
    protected ArrayList     aRentalIncome   = new ArrayList( 150 );
    protected ArrayList     aSummary        = new ArrayList( 150 );    
    protected ArrayList     aTaxBands       = new ArrayList( 10 );    
    
    // ctor
    public void DatabaseData()
    {
    }
    
    /*
    * funcs to clear data objects
    */
    // clear all data in data objects
    public void clearAllData()
    {
        aExpenses.clear();
        aProperties.clear();
        aTaxYears.clear();
        aMortgagePay.clear();
        aRentalIncome.clear();
        aSummary.clear();
        aTaxBands.clear();
    }
  
    public void clearTaxBands()
    {
        aTaxBands.clear();
    }
      
    public void clearSummary()
    {
        aSummary.clear();
    }
    
    public void clearExpenses()
    {
        aExpenses.clear();
    }
    
    public void clearProperties()
    {
        aProperties.clear();
    }
    
    public void clearTaxYears()
    {
        aTaxYears.clear();
    }
    
    public void clearMortgagePayments()
    {
        aMortgagePay.clear();
    }
  
    public void clearRentalIncome()
    {
        aRentalIncome.clear();
    }
    /*
    * End of funcs to clear data objects
    */
   
    /*
    * Funcs to get data lists
    */
    // retrurn tax bands
    public ArrayList getTaxBands()
    {
        return aTaxBands;
    }
    
    // retrurn expenses
    public ArrayList getExpenses()
    {
        return aExpenses;
    }

    // get summary data
    public ArrayList getSummary()
    {
        return aSummary;
    }
    
    // retrurn properties
    public ArrayList getProperties()
    {
        return aProperties;
    }
    
    // retrurn taxyears
    public ArrayList getTaxYears()
    {
        return aTaxYears;
    }
    
    // retrurn mortgage payments
    public ArrayList getMortgagePayments()
    {
        return aMortgagePay;
    }
    
    // retrurn rental income
    public ArrayList getRentalIncome()
    {
        return aRentalIncome;
    }
    
    
    // set fo funcs to return the data object based on the selcted row in the 
    // UI table. iRowCount detail the selcted row
    public RentalIncome getRentalIncomeObject( int iRowCount )
    {
        RentalIncome incom = null;
        for ( int i = 0; i < aRentalIncome.size(); i++ )
        {
            incom = (RentalIncome)aRentalIncome.get( i );
            
            if ( iRowCount == incom.iRowID )
               break;
        }
        
        return incom;
    }
    
     // set fo funcs to return the data object based on the selcted row in the 
    // UI table. iRowCount detail the selcted row
    public MortgagePayment getMortgageObject( int iRowCount )
    {
        MortgagePayment morg = null;
        for ( int i = 0; i < aMortgagePay.size(); i++ )
        {
            morg = (MortgagePayment)aMortgagePay.get( i );
            
            if ( iRowCount == morg.iRowID )
               break;
        }
        
        return morg;
    }
    
    
         // set fo funcs to return the data object based on the selcted row in the 
    // UI table. iRowCount detail the selcted row
    public Expense getExpenseObject( int iRowCount )
    {
        Expense exp = null;
        for ( int i = 0; i < aExpenses.size(); i++ )
        {
            exp = (Expense)aExpenses.get( i );
            
            if ( iRowCount == exp.iRowID )
               break;
        }
        
        return exp;
    }
    
    
    // funcs set all data object row ids to -1.
    // as we change combo boxes and rerender the tavles row id say from last time
    // the tables were craeted need to reset
    public void resetAllDataObjectRowIDs()
    {

        resetRentalIncomeRowIDs();
        resetExpenseRowIDs();
        resetMortgageRowIDs();
    }
    
        // funcs set all data object row ids to -1.
    // as we change combo boxes and rerender the tavles row id say from last time
    // the tables were craeted need to reset
    public void resetExpenseRowIDs()
    {

        // clear expense objects row id
        Expense exp = null;
        for ( int i = 0; i < aExpenses.size(); i++ )
        {
            exp = (Expense)aExpenses.get( i );
            exp.iRowID = -2;
        }
    }
    
     public void resetMortgageRowIDs()
    {
        // clear MortgagePayment objects row id
        MortgagePayment morg = null;
        for ( int i = 0; i < aMortgagePay.size(); i++ )
        {
            morg = (MortgagePayment)aMortgagePay.get( i );
            morg.iRowID = -2;
        }
    }
     
        // funcs set all data object row ids to -1.
    // as we change combo boxes and rerender the tavles row id say from last time
    // the tables were craeted need to reset
    public void resetRentalIncomeRowIDs()
    {

        // clear Income objects row id
        RentalIncome incom = null;
        for ( int i = 0; i < aRentalIncome.size(); i++ )
        {
            incom = (RentalIncome)aRentalIncome.get( i );
            incom.iRowID = -2;
        }
    }
    
    /*
    * End of funcs to get data lists
    */
    public void addTaxBand( TaxBand taxBand )
    {
        
       aTaxBands.add( taxBand );
    }
    
    public void addSummaryYear( TaxYearSummary summary )
    {
        
       aSummary.add( summary );
    }
    // add a mortgage payment row
    public void addMortgagePayment( MortgagePayment morgPay )
    {
        aMortgagePay.add( morgPay );
                
    }
    
    // add an taxyear
    public void addTaxYear( TaxYear taxYear )
    {
        aTaxYears.add( taxYear );
                
    }

    // add an property
    public void addProperty( Property property )
    {
        aProperties.add( property );
                
    }
    // add an expense
    public void addExpense( Expense expense )
    {
        aExpenses.add( expense );
                
    }
    
     // add an rentalIncome
    public void addRentalIncome( RentalIncome income )
    {
        aRentalIncome.add( income );
                
    }
    
    
    /*
    * method takes the value of the combo box selector and finds the ID for
    * that object within the array of TaxYear objects. This allows the UI
    * to filter on tax year wgen rendering data, that is, 
    * if data object (Expense, MortgagePayemnt, etc taxYear ID = ID for 
    * selected taxyear in combo box
    * returns -1 if no match found
    */
    public int getMatchingTaxYearID( String sTaxYear )
    {
        
        int iRet = -1;
        TaxYear tYear = null;
        int iSize = aTaxYears.size();
        
        for (int iCount = 0; iCount < iSize; iCount++ )
        {
           tYear = (TaxYear)aTaxYears.get( iCount ); 
           
           if ( tYear.sDesc.equalsIgnoreCase(sTaxYear) )
           {
               iRet = tYear.iID;
               break;
           } 
        }
        
        return iRet;
    }


    /*
    * method takes the value of the combo box selector (ADDRESS_LINE1) and finds the ID for
    * that object within the array of Property objects. This allows the UI
    * to filter on tax year wgen rendering data, that is, 
    * if data object (Expense, MortgagePayemnt, etc Property ID = ID for 
    * selected Property in combo box
    * returns -1 if no match found
    */
    public int getMatchingPropertyID( String sAddressLine1 )
    {
        
        int iRet = -1;
        Property prop = null;
        int iSize = aProperties.size();
        
        for (int iCount = 0; iCount < iSize; iCount++ )
        {
           prop = (Property)aProperties.get( iCount ); 
           if ( prop.sAddressLine1.equalsIgnoreCase(sAddressLine1) )
           {
               iRet = prop.iID;
               break;
           } 
        }
        
        return iRet;
    }   
    
    /* method taxed a proprty ID and return the Address_line1 fort hat propID
    */
    public String getPropertyAddressLine1( int iPropId )
    {
        
        String addrLine1 = null;
        Property prop = null;
        int iSize = aProperties.size();
        
        for (int iCount = 0; iCount < iSize; iCount++ )
        {
           prop = (Property)aProperties.get( iCount ); 
           if ( prop.iID == iPropId )
           {
               addrLine1 = prop.sAddressLine1;
               break;
           } 
        }
        
        return addrLine1;
    }  
    
    // return the string representation for the supplied tax year ID
    public String getTaxYearDesc( int iTaxID )
    {
        
        String sReturnStr = "";
        TaxYear tYear = null;
        int iSize  = aTaxYears.size();
        
        // go through each tax year until we find the right one
        for ( int i = 0; i < iSize; i++)
        {
            tYear = (TaxYear)aTaxYears.get( i );
            if ( tYear.iID == iTaxID )
            {
                // found it
                sReturnStr = tYear.sDesc;
                break;
            }
        }    
        
        return sReturnStr;
    }
    
    // dont got to DB but build summary view
    public boolean buildSummaryData ( int iTaxID, int iPropertyID, int iTaxRate )
    {
        
        boolean bReturn = true;
        ArrayList aTaxList = getTaxYears();
        ArrayList aRental = getRentalIncome();
        ArrayList aMorgs = getMortgagePayments();
        ArrayList aExpenses = getExpenses();
        int iNumYears = aTaxList.size();
        int iObjCount = 0;
        TaxYear tYear = null;
        TaxYearSummary summary = null;
        RentalIncome income = null;
        MortgagePayment morg = null;
        Expense exp = null;
        float fTotal1 = 0;
        float fTotal2 = 0;
        
        // clear existing summary data
        this.clearSummary();
        
        RPASUI.logger.debug("Called BuildSummaryData. Selcted taxID = " + iTaxID);
        
        // iterate through each tax year obj.
        for ( int iCount = 0; iCount < iNumYears; iCount++  )
        {
            
            tYear = (TaxYear)aTaxList.get( iCount );
            
            // build summary obj for yeat
            summary = new TaxYearSummary();
            summary.sTaxYear = tYear.sDesc;
            summary.iTaxYearID = tYear.iID;
            summary.sComment = tYear.sComment;
            summary.fMorgTaxFactor = tYear.sMorgTaxFactor;
        
            // now iterate through income objects to build totals per tax
            iObjCount = aRental.size();
            for ( int incomCount = 0; incomCount < iObjCount; incomCount++  )
            {
                income = (RentalIncome)aRental.get( incomCount );
                if ( income.iTaxID == summary.iTaxYearID )
                    /*
                    &&       //
                     ( (iPropertyID == income.iPropID) || ( iPropertyID == 99 ) )            //
                   ) */
                {
                    fTotal1 += income.dBankIncome;
                    fTotal2 += income.dRentalIncome;
                }
            }
            // set totals in summary object for year
            summary.fIncome = fTotal2;
            
            // clear totals
            fTotal1 = 0;
            fTotal2 = 0;
            
            // now iterate through mortage objects to build totals per tax
            iObjCount = aMorgs.size();
            for ( int morgCount = 0; morgCount < iObjCount; morgCount++  )
            {
                morg = (MortgagePayment)aMorgs.get( morgCount );
                if ( morg.iTaxID == summary.iTaxYearID )
                {
                    fTotal1 += morg.dPayment;
                    fTotal2 += morg.dInterest;
                }
            }
            // set totals in summary object for year
            summary.fMorgPay = fTotal1;
            summary.fMorgInt = fTotal2;
        
            // clear totals
            fTotal1 = 0;
            fTotal2 = 0;
            
             // now iterate through Expense objects to build totals per tax
            iObjCount = aExpenses.size();
            for ( int expCount = 0; expCount < iObjCount; expCount++  )
            {
                exp = (Expense)aExpenses.get( expCount );
                if ( exp.iTaxID == summary.iTaxYearID )
                {
                    fTotal1 += exp.dValue;
                }
            }
            // set totals in summary object for year
            summary.fExpenses = fTotal1;
            
            // do calcs on taxbale income and exposure
            summary.fTaxableIncome = summary.fIncome - ( summary.fMorgInt * summary.fMorgTaxFactor) - summary.fExpenses;
            summary.fTaxEposure = ((summary.fTaxableIncome / 100 ) * iTaxRate );
            addSummaryYear( summary );
            
            // RPASUI.logger.debug( summary.toString() );
        }
        
        return bReturn;
    }
    
    // print data out
    public String toString()
    {
        // return string
        String str = new String();
        
        // iterate through expenses and print out
        int iSize = aExpenses.size();
        Expense exp = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            exp = (Expense)aExpenses.get(i);
            
            //print out
            //RPASUI.logger.debug( exp.toString() );
            str.concat( exp.toString() );
        }
        
        // iterate through properties and print out
        iSize = aProperties.size();
        Property prop = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            prop = (Property)aProperties.get(i);
            
            //print out
            // RPASUI.logger.debug( prop.toString() );
            str.concat( prop.toString() );
        }
       
        // iterate through tax yaers and print out
        iSize = aTaxYears.size();
        TaxYear taxYear = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            taxYear = (TaxYear)aTaxYears.get(i);
            
            //print out
            // RPASUI.logger.debug( prop.toString() );
            str.concat( taxYear.toString() );
        }
        
        // iterate through mortgage payments and print out
        iSize = aMortgagePay.size();
        MortgagePayment morgPay = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            morgPay = (MortgagePayment)aMortgagePay.get(i);
            
            //print out
            // RPASUI.logger.debug( prop.toString() );
            str.concat( morgPay.toString() );
        }
        
        // iterate through rental income and print out
        iSize = aRentalIncome.size();
        RentalIncome income = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            income = (RentalIncome)aRentalIncome.get(i);
            
            //print out
            // RPASUI.logger.debug( prop.toString() );
            str.concat( income.toString() );
        }
        
        // iterate through summary objects
        iSize = aSummary.size();
        TaxYearSummary sum = null;
        
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            sum = (TaxYearSummary)aSummary.get(i);
            
            //print out
            //RPASUI.logger.debug( exp.toString() );
            str.concat( sum.toString() );
        }

                // iterate through summary objects
        iSize = aTaxBands.size();
        TaxBand band = null;
        // iterate through the array
        for ( int i = 0; i < iSize; i++ )
        {
            band = (TaxBand)aTaxBands.get(i);
            
            //print out
            //RPASUI.logger.debug( exp.toString() );
            str.concat( band.toString() );
        }
        return str;
     }
            
}
