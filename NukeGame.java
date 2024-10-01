// Shivank Hali
// 05-19-22
// WorldLook.java
// Everything done by shivank 

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;  // awt imports
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Dimension;


import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;   // file io imports
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;  // swing imports
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;  // event listeners
import javax.swing.event.ChangeListener;
import java.awt.event.MouseListener;


public class NukeGame extends JPanel
{
	private int yCord;
	private NukeGamePanel instance;
	
	public NukeGame(NukeGamePanel instanceIn)
	{
		instance = instanceIn;
		setLayout(new BorderLayout());
		Color cyanBlue = new Color(40, 225, 244);
		Color beige = new Color(197, 159, 119);
		setBackground(cyanBlue);
		yCord = 0;
		NukeMover mover = new NukeMover();
		Timer timer = new Timer(35, mover);
		timer.start();
		Font headerFnt = new Font("SanSerif",Font.BOLD,48); 

		JButton startButton = new JButton("Start");
		startButton.setPreferredSize(new Dimension(90,20));
		startButton.addActionListener(instance);
		startButton.setBackground(beige);
		startButton.setOpaque(true);
		add(startButton, BorderLayout.EAST);

		JButton leaderButton = new JButton("Quiz");
		leaderButton.setPreferredSize(new Dimension(90,20));
		leaderButton.addActionListener(instance);
		leaderButton.setBackground(beige);
		leaderButton.setOpaque(true);
		add(leaderButton, BorderLayout.WEST);
		

		JLabel gameLabel = new JLabel("You Lost"); // header of game panel
		JPanel gameHeader = new JPanel(); 
		gameHeader.setBackground(beige);
		gameLabel.setFont(headerFnt);
		gameHeader.add(gameLabel);
		gameHeader.setPreferredSize(new Dimension(1200, 100));
       
		add(gameHeader, BorderLayout.NORTH); 
	}
	
	public Image getMyImage(String pictName) 
	{
		if(pictName.equals("NukeDown.png"))
		{
			Image picture = null;
			
			File imageFile = new File("NukeDown.png");
			try
			{
				picture = ImageIO.read(imageFile);
			}
			catch(IOException e)
			{
				System.err.println("\n" + pictName + " not can't be found.\n");
				e.printStackTrace();
			}
			return picture;
		}
		else
		{
			Image picture = null;
			
			File imageFile = new File("Explosion.png");
			try
			{
				picture = ImageIO.read(imageFile);
			}
			catch(IOException e)
			{
				System.err.println("\n" + pictName + " not can't be found.\n");
				e.printStackTrace();
			}
			return picture;
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if((yCord < 550-(getMyImage("NukeDown.png").getWidth(this))))
		{
			g.drawImage(getMyImage("NukeDown.png"), 530, yCord, 145, 300, this);
			g.drawImage(getMyImage("NukeDown.png"), 130, yCord, 145, 300, this);
			g.drawImage(getMyImage("NukeDown.png"), 930, yCord, 145, 300, this);
			yCord+=7;
		}
		else
			g.drawImage(getMyImage("Explosion.png"), 300, 120, 590, 550, this) ;
	}
	
   class NukeMover implements ActionListener
   {
       public void actionPerformed(ActionEvent evt)
       {
           repaint();
       }
   }
}	

