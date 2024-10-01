// Ali Malik
// 5-19-22
// Instructions.java
// Everything by Ali

// This class will create the instructions panel.

/// imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// Class is responsible for displaying the instructions
class Instructions extends JPanel
{
   private String text; // Text on the instructions
   private Image image; // Image of game to give better understanding to user
   private Color color; // color of the background
   private InstructionsPanel instance;

	// Constructor of instructors contains text parameter, color parameter, and image paramater
	// to determine what type of instruction panel it will be
	public Instructions(String textIn, Color colorIn, InstructionsPanel instanceIn)
	{
	    instance = instanceIn;
        text = textIn;
        color = colorIn;
        setBackground(color);
        
		Font fnt = new Font("Arial", Font.BOLD, 20);
        JTextArea textIns = new JTextArea(text,25,25);
        textIns.setEditable(false);
        textIns.setLineWrap(true);
        textIns.setWrapStyleWord(true);
        textIns.setFont(fnt);
        textIns.setVisible(true);
        textIns.setOpaque(true);
        textIns.setBackground(color);
        add(textIns);
		JButton goHome = new JButton("Go Home");
		goHome.addActionListener(instance);
		add(goHome);
      }
     
    // Constructor of instructors contains text parameter, color parameter, and image paramater
	//to determine what type of instruction panel it will be
   public Instructions(Image imageIn, Color colorIn, InstructionsPanel instanceIn)
   {
		image = imageIn;
		color = colorIn;
   }
   
   // paint component for drawing the image
   public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(color);
		g.drawImage(image, 10,20,500,500,this);
	}
	
}
