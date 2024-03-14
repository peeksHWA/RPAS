/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Peeks
 */
public class MortgagePayment extends RPASdataObject
{

    public String       sTaxYear            = null;
    public Date         payDate             = null;
    public float        dPayment            = 0;
    public float        dInterest           = 0;
    public String       sMortgageProvider   = null;
    public String       sComment            = null;
    public int          iPropID             = 0;
    public int          iTaxID              = 0;
    
    
    public String toString()
    {
        
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        String sRet = "MORTGAGE RECORD :\n" +                //
                      "ID = " + iID + "\n"                  //
                      + "TAX_YEAR = " + sTaxYear + "\n"           //
                      + "Date = " + simpleDateFormat.format( payDate ) + "\n"     //
                      + "PAYMENT AMOUNT = " + dPayment + "\n"          //  
                      + "INTEREST = " + dInterest + "\n"           //
                      + "MORTGAGE SUPPLIER = " + sMortgageProvider + "\n"          //
                      + "COMMENT = " + sComment + "\n"             //
                      + "PROPERTY_ID = " + iPropID + "\n"   //
                      + "TAX_YEAR_ID = " + iTaxID + "\n";   //
                  
        return sRet;
    }
    
    public void clear()
    {
        sTaxYear            = null;
        payDate             = null;
        dPayment            = 0;
        dInterest           = 0;
        sMortgageProvider   = null;
        sComment            = null;
        iPropID             = 0;
        iTaxID              = 0;
    }
}
