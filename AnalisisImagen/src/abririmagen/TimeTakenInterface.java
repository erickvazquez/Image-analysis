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
import java.awt.Color.*;

public class TimeTakenInterface {

  private long time_msec;
  private JPanel p;
  private JLabel l;
  private JTextField t;

  public JPanel createPanel(){
    p = new JPanel();
    p.setOpaque(false);
    l = new JLabel("Time Taken (msecs):");
    t = new JTextField(String.valueOf(time_msec),5);
    t.setEditable(false);
    t.setBackground(Color.white);

    p.add(l);
    p.add(t);
    return p;
  }

  public void updateTime(long old_time, long new_time){
    t.setText(""+String.valueOf(new_time-old_time));
  }
}

