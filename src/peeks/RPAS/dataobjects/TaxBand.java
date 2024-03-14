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
public class TaxBand extends RPASdataObject
{


    public int      iTaxRate  = 0;
    public String   sComment  = null;
    
    
     public String toString()
    {

        
        String sRet = "TAX BAND RECORD :\n" +                //
                      "ID = " + iID + "\n" +                 //
                      "TAX BAND = " + iTaxRate + "\n"                  //
                      + "COMMENT = " + sComment + "\n";
                     
        return sRet;
    }
}
