/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;



import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import peeks.RPAS.UI.RPASUI;


/**
 *
 * @author Peeks
 */
public class SummaryTableCellRenderer extends DefaultTableCellRenderer
{

  
    public SummaryTableCellRenderer() 
    {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {

        // COLUMN 5 IS "TAXBALE EXPOSURE"
        String s = (String)table.getModel().getValueAt(row, RPASUI.SUMMARY_TAX_INCOME_COL );
        Float fTaxbale = Float.parseFloat(  s );
        // COLUMN 6 IS "EXPOSURE"
        s = (String)table.getModel().getValueAt(row, RPASUI.SUMMARY_EXP_COL );
        Float fExposure = Float.parseFloat(  s );
 
        // if total turn row text blue
        s = (String)table.getModel().getValueAt(row, RPASUI.SUMMARY_TABLE_DATE_COL );
        if ( s.equals("TOTALS"))
        {
            setBackground(Color.yellow);
        }
        else
        {
            setBackground(null);
            // if not totals row and -ve value turn row text red
            if( fTaxbale < 0 || fExposure < 0 ) 
            {
                 setForeground(Color.red);
            }else 
            {
                 setForeground(null);
            }
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    }
} 
    

