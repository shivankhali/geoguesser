// Shivank Hali
// 5-19-22
// Settings.java
// Everything by Shivank 
// This class will create the Settings panel.

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


/* does 4 main things
 * 1: adds label to panel
 * 2: in the first grid (3 total), includes a brightness adjuster
 * 3: in the second grid (3 total), includes radio buttons to adjust units of distance
 * 4: in the third grid (3 total), includes menu bar to help navigate through the game
 */ 
class Settings extends JPanel
{
   public Settings(SettingsPanel instanceIn)
   {
		setLayout(new BorderLayout());
		SettingsPanel instance = instanceIn; // instance of SettingsPanel for a button's action listener
		Font myFont = new Font("Monotone", Font.BOLD, 20);

		JLabel settingsLabel = new JLabel("Welcome to Settings Panel");

		JPanel settingsText = new JPanel();
		settingsText.setBackground(Color.RED);
		settingsLabel.setFont(myFont);
		settingsText.add(settingsLabel);
		settingsText.setPreferredSize(new Dimension(1200, 100));

		add(settingsText, BorderLayout.NORTH); 

		JPanel settingsOptions = new JPanel();
		settingsOptions.setLayout(new GridLayout(1,3));
		settingsOptions.setBackground(Color.GREEN);


		JPanel units = new JPanel();
		units.setLayout(new FlowLayout(FlowLayout.CENTER, 200,100));
		JLabel unitLabel = new JLabel("Change units through the buttons.");
		units.add(unitLabel);
		units.setBackground(Color.MAGENTA);
		ButtonGroup bg = new ButtonGroup();
		JRadioButton kilometers = new JRadioButton("Kilometers");	  
		bg.add(kilometers);						
		kilometers.addActionListener(instance); 	
		units.add(kilometers);				
		JRadioButton miles = new JRadioButton("Miles");	  
		bg.add(miles);		
		miles.addActionListener(instance);
		units.add(miles);
		settingsOptions.add(units); 

		JPanel menu = new JPanel();
		menu.setLayout(new FlowLayout(FlowLayout.CENTER,200,100));
		menu.setBackground(Color.GREEN);
		JLabel menuLabel = new JLabel("Navigate through our game with the options menu.");
		menu.add(menuLabel);
		JMenuBar JMB = new JMenuBar();
		JMenu menuOptions = new JMenu("Options");
		JMenuItem backToStart = new JMenuItem("Start");
		JMenuItem goToInstructions = new JMenuItem("Instructions");
		JMenuItem goToLeaderboard = new JMenuItem("Leaderboard");
		JMenuItem quitGame = new JMenuItem("Quit");
		backToStart.addActionListener(instance);
		goToInstructions.addActionListener(instance);
		goToLeaderboard.addActionListener(instance);
		quitGame.addActionListener(instance);
		menuOptions.add(backToStart);
		menuOptions.add(goToInstructions);
		menuOptions.add(goToLeaderboard);
		menuOptions.add(quitGame);
		JMB.add(menuOptions);
		menu.add(JMB);
		settingsOptions.add(menu); 

		add(settingsOptions, BorderLayout.CENTER); 


   }

}
