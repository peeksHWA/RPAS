/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

import java.text.SimpleDateFormat;

/**
 *
 * @author Peeks
 */
public class TaxYearSummary
{

    public int      iTaxYearID         = 0;
    public int      iPropertyID        = 0;
    public String   sTaxYear          = null;
    public float    fIncome           = 0;
    public float    fMorgPay          = 0;
    public float    fMorgInt          = 0;
    public float    fExpenses         = 0;
    public float    fTaxableIncome    = 0;
    public float    fTaxEposure       = 0;
    public String   sComment          = null;
    public float    fMorgTaxFactor    = 0;
    

   public String toString()
    {
        
        String sRet = "SUMMARY RECORD :\n"                 //
                      + "TAX_YEAR_ID = " + iTaxYearID + "\n"           //
                      + "PROPERTY_ID = " + iPropertyID + "\n"           //
                      + "TAX_YEAR = " + sTaxYear + "\n"           //
                      + "INCOME = " + fIncome + "\n"           //
                      + "MORTGAGE PAY = " + fMorgPay + "\n"          //
                      + "MORTGAGE INT = " + fMorgInt + "\n"          //
                      + "EXPENSES = " + fExpenses + "\n"             //
                      + "TAXABLE INCOME = " + fTaxableIncome + "\n"    //
                      + "TAX EXPOSURE = " + fTaxEposure + "\n"             //
                      + "MORG_TAX_FACTOR = " + fMorgTaxFactor + "\n"             //
                      + "COMMENT = " + sComment + "\n";
                  
        return sRet;
    }
}
