/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

/**
 *
 * @author Peeks
 */
public class Property extends RPASdataObject
{


    public String       sAddressLine1   = null;
    public String       sAddressLine2   = null;
    public String       sAddressLine3   = null;
    public String       sCounty         = null;
    public String       sCountry        = null;
    public String       sPostCode       = null;    
    
    public String toString() 
    {
        String sRet = "PROPERYT RECORD : \n" +                      //
                      "ID = " + iID + "\n"                           //
                      + "ADDRESS_LINE1 = " + sAddressLine1 + "\n"    //
                      + "ADDRESS_LINE2 = " + sAddressLine2 + "\n"    //
                      + "ADDRESS_LINE3 = " + sAddressLine3 + "\n"    //
                      + "COUNTY = " + sCounty + "\n"                 //
                      + "COUNTRY = " + sCountry + "\n"               //
                      + "POSTCODE = " + sPostCode + "\n";
                    
        return sRet;
    }    
}
