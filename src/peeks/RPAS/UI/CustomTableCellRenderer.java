/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Peeks
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer
{

    public CustomTableCellRenderer() 
    {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {


        String sDateCol = (String)table.getModel().getValueAt(row, RPASUI.NON_SUMMARY_TABLE_DATE_COL );
 
        if( sDateCol.equals("TOTALS") ) 
        {
             setBackground(Color.yellow);
        }else 
        {
             setBackground(null);
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    }    
}
