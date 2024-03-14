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
public class RentalIncome extends RPASdataObject
{

    public String       sTaxYear            = null;
    public Date         incomeDate          = null;
    public float        dBankIncome         = 0;
    public float        dRentalIncome       = 0;
    public String       sPayMode            = null;
    public String       sPaySource          = null;
    public String       sTenant             = null;
    public String       sComment            = null;
    public int          iPropID             = 0;
    public int          iTaxID              = 0;
    
    
    public String toString()
    {
        
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        String sRet = "INCOME RECORD :\n" +                //
                      "ID = " + iID + "\n"                  //
                      + "TAX_YEAR = " + sTaxYear + "\n"           //
                      + "Date = " + simpleDateFormat.format( incomeDate ) + "\n"     //
                      + "BAMNK INCOME = " + dBankIncome + "\n"          //  
                      + "RENTAL INCOME = " + dRentalIncome + "\n"           //
                      + "PAYMENT MODE = " + sPayMode + "\n"          //
                      + "PAYMENT SOURCE = " + sPaySource + "\n"          //
                      + "TENANT = " + sTenant + "\n"          //
                      + "COMMENT = " + sComment + "\n"             //
                      + "PROPERTY_ID = " + iPropID + "\n"   //
                      + "TAX_YEAR_ID = " + iTaxID + "\n";   //
                  
        return sRet;
    }
    
    public void clear()
    {
        sTaxYear            = null;
        incomeDate          = null;
        dBankIncome         = 0;
        dRentalIncome       = 0;
        sPayMode            = null;
        sPaySource          = null;
        sTenant             = null;
        sComment            = null;
        iPropID             = 0;
        iTaxID              = 0;
    }
    
}
