/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abririmagen;

/**
 *
 * @author Maria Mireya
 */

//package code.iface.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;

public class StartStopInterface {

  public JPanel p;
  public JButton start;
  public JButton stop;
  public JLabel status;

  public JPanel createPanel(){
    
    
    p = new JPanel();
    p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
    p.setOpaque(false);

    start = new JButton("Start");
    start.setToolTipText("Click here to begin");
    stop = new JButton("Stop");
    stop.setToolTipText("Click here to abort a running operator");
    start.setBackground(Color.green);
    stop.setBackground(Color.red);

    p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    p.add(start);
    p.add(Box.createRigidArea(new Dimension(20,10)));
    p.add(stop);
    //p.add(Box.createRigidArea(new Dimension(10,10)));

    return p;
  }
}
