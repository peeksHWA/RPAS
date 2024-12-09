/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peeks.RPAS.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import peeks.RPAS.DatabaseConfig;
import static peeks.RPAS.UI.RPASUI.logger;
import peeks.RPAS.dataobjects.DatabaseData;
import peeks.database.DatabaseConnection;
import peeks.database.DatabaseConnectionParams;

/**
 *
 * @author peeks
 */
public class MenuListener implements ActionListener 
{
   
    
    // mmeber vars
   private int iItem = -1;    // 0 means no item
   private JMenuBar menuBar = null;
   private RPASUI RPAS = null;
   
  // ctor
  public MenuListener ( RPASUI RPAS, JMenuBar menu, int iMenu )
  {
      iItem = iMenu;
      menuBar = menu;
      this.RPAS = RPAS;
  }
 
  public void actionPerformed(ActionEvent e) 
  {
    
    // turn all menuItems black so selcted one cna be set to blue below
    // if menu item red them means failed ot connect to DB when last attempted
    // so leave red util successfully connected to. It selected and works logic below will change to blue
    int iCount = menuBar.getMenuCount();
    for (int i = 0; i < iCount; i++) 
    {
        JMenu menu1 = menuBar.getMenu(i);
        System.out.println("Menu:" + menu1.getText());
        for (int j = 0; j < menu1.getMenuComponentCount(); j++) 
        {
            java.awt.Component comp = menu1.getMenuComponent(j);
            if (comp instanceof JMenuItem) 
            {
                JMenuItem menuItem1 = (JMenuItem) comp;
                System.out.println("MenuItem:" + menuItem1.getText());
                
                // turn black if not red
                if ( !(menuItem1.getForeground() == Color.red) )
                menuItem1.setForeground(Color.black);
            }
        }
    }  
    
    JMenuItem menuItem = (JMenuItem)e.getSource();
    //Code which you want to be executed when the MenuItem is clicked
    System.out.println("Menu Item Clicked " + iItem + " turn blue" );
    menuItem.setForeground(Color.blue);
    
    // ok here is where we need to do database reload as changed DB
    if ( !RPAS.changeDbandUpdateView( iItem ) )
    {
        // failed so gret out of the menu optin for this Db
        menuItem.setForeground(Color.red);
    }
        
  }    
  
  
  /*
  private boolean changeDbandUpdateView()
  {
      
    boolean bReturn = true;  
    
    // get db JSON loaded object that relates to index
    DatabaseConfig dbConfig = RPAS.dbList.getConfigItem(iItem);
    
    DatabaseConnectionParams dbParams = new DatabaseConnectionParams( dbConfig.getJdbcDriver(),    //
                                                                      dbConfig.getConnectionString(), //
                                                                      dbConfig.getUsername(),          //
                                                                      dbConfig.getPassword());
            
    System.out.println( "Attempting to connect to : ");dbParams.toString();
    // creat to db and connect
    RPAS.dbConnection = new DatabaseConnection( dbParams );
    if ( !RPAS.dbConnection.connect() )
    {    
        logger.error("Failed to connect to database");
        JOptionPane.showMessageDialog( RPAS, "Failed to connect to Database", //
                       "Startup Error",                           //
                       JOptionPane.ERROR_MESSAGE);
        bReturn = false;
    }
    else
    {
        // load the data
        RPAS.dbData = new DatabaseData();
        RPAS.dbConnection.loadDatabaseData( RPAS.dbData );
        RPAS.dbConnection.disConnect();
    }           
    return bReturn;
  }
*/
  
}
  
    

