/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Peeks
 */
public class Expense extends RPASdataObject
{

    public String       sTaxYear        = null;
    public Date         expDate         = null;
    public String       sDesc           = null;
    public String       sSupplier       = null;
    public float        dValue          = 0;
    public String       sReciept        = null;
    public String       sReceiptLink    = null;
    public String       sComment        = null;
    public int          iPropID         = 0;
    public int          iTaxID          = 0;
    
    
    public String toString()
    {
        
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        String sRet = "\nEXPENSE RECORD :\n" +                //
                      "ID = " + iID + "\n"                  //
                      + "TAX_YEAR = " + sTaxYear + "\n"           //
                      + "Date = " + simpleDateFormat.format( expDate ) + "\n"     //
                      + "DESCRIPTION = " + sDesc + "\n"           //
                      + "SUPPLIER = " + sSupplier + "\n"          //
                      + "VALUE = " + dValue + "\n"          //
                      + "RECEIPT = " + sReciept + "\n"             //
                      + "RECEIPT_LINK = " + sReceiptLink + "\n"    //
                      + "COMMENT = " + sComment + "\n"             //
                      + "PROPERTY_ID = " + iPropID + "\n"   //
                      + "TAX_YEAR_ID = " + iTaxID + "\n";   //
                  
        return sRet;
    }
    
    public void clear()
    {
        sTaxYear        = null;
        expDate         = null;
        sDesc           = null;
        sSupplier       = null;
        dValue          = 0;
        sReciept        = null;
        sReceiptLink    = null;
        sComment        = null;
        iPropID         = 0;
        iTaxID          = 0;
    }
}
