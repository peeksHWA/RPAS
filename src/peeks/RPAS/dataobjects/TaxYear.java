/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Peeks
 */
public class TaxYear extends RPASdataObject
{

    public String       sDesc           = null;
    public Date         dStartdate      = null;
    public Date         dEndDate        = null;
    public String       sComment        = null;
    public float        sMorgTaxFactor  = 0;
    
    public String toString() 
    {
        
        // date pattern
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        String sRet = "TAX_YEAR RECORD : \n" +                       //
                      "ID = " + iID + "\n"                           //
                      + "TAX_YEAR_DESC = " + sDesc + "\n"    //
                      + "START_DATE    = " + simpleDateFormat.format( dStartdate ) + "\n"    //
                      + "END_DATE = " + simpleDateFormat.format( dEndDate ) + "\n"    //
                      + "COMMENT = " + sComment + "\n"          //
                      + "MORG TAX FACTOR = " + sMorgTaxFactor + "\n";
        
        return sRet;
    }        
}
