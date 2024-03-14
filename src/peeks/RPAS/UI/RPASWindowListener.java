/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Peeks
 */
public class RPASWindowListener implements WindowListener 
{

  public void windowClosing(WindowEvent arg0) 
  {
      
    System.out.println( "Insode window listener - app closing")  ;
    System.exit(0);
  }

  public void windowOpened(WindowEvent arg0) {}
  public void windowClosed(WindowEvent arg0) {}
  public void windowIconified(WindowEvent arg0) {}
  public void windowDeiconified(WindowEvent arg0) {}
  public void windowActivated(WindowEvent arg0) {}
  public void windowDeactivated(WindowEvent arg0) {}

}

    

