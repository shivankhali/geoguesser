// Ali Malik
// 5-19-22
// GamePanel.java
// Entire class done by Ali
// This class will create the game panel and will contain listeners that will accept
// answers from the user

///imports
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.CardLayout;


import javax.swing.ImageIcon;
import java.io.File;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class GamePanel extends JPanel
{
	public int pointsNumber = 0;
	public JTextField guessText;
	public JPanel countryDisplay;
	public Font infoFont;
	public JLabel points;
	public JLabel country;
	File countryDirectory = new File("GameCountryNames");
	public String[] countryArray;
	public String countryImageString;
	public ImageIcon countryIcon;
	public String countryImageAns;
	public int randLength;
	CustomizeUserPanel cup;
	
	// uses borderlayout for adding label on top, then for now, has a jbutton in center
	public GamePanel(PlayPanel instanceIn, CustomizeUserPanel cupIn, String us, String pfp) 
	{
		cup = cupIn;
		PlayPanel instance = instanceIn; // instance of PlayPanel for a button's action listen
		countryArray = countryDirectory.list();
		countryImageString = generateCountryImage();
		countryIcon = new ImageIcon();
		setLayout(new BorderLayout());
		randLength = countryArray.length-1;
	   
		Font headerFnt = new Font("SanSerif",Font.BOLD,48); // font for the header of the game panel
		JLabel gameLabel = new JLabel("Shaping The Globe"); // header of game panel
	   
		JPanel gameHeader = new JPanel(); // generic JPanel which will be part of the north in border layout
		gameHeader.setBackground(Color.GREEN);
		gameLabel.setFont(headerFnt);
		gameHeader.add(gameLabel);
		gameHeader.setPreferredSize(new Dimension(1200, 100));
       
		add(gameHeader, BorderLayout.NORTH); 
       
		// country display is the panel which is the center of the border layout and
		// has a border layout of its own where points is in north. distance is is in west and
		// the country image is in the center and text field is on south
		countryDisplay = new JPanel();
		countryDisplay.setLayout(new BorderLayout());
		countryDisplay.setPreferredSize(new Dimension(1200,500));
		country = new JLabel();			
		
		Font textFont = new Font("Serif",Font.PLAIN,48);
		guessText = new JTextField("Make guess here",10);
		guessText.setEditable(true);
        guessText.setFont(textFont);
        guessText.setVisible(true);
        guessText.setOpaque(true);
        guessText.addActionListener(instance);
        countryDisplay.add(guessText,BorderLayout.SOUTH); 
        
        points = new JLabel();
		
		countryDisplay.add(country,BorderLayout.CENTER); 
 
		updateAns(); 
		repaint();
        
		countryDisplay.add(points, BorderLayout.NORTH);

		JLabel distance = new JLabel("		Your guess was x miles away	");
		infoFont = new Font("SanSerif", Font.BOLD, 24);
		distance.setFont(infoFont);
		distance.setVisible(true);
		countryDisplay.add(distance,BorderLayout.WEST);
		
		countryDisplay.setVisible(true);
		add(countryDisplay, BorderLayout.CENTER);
		
		// Feature display is a flow layout with the user's profile picture and username which
		// is next to the flag look, world look, and go home button.
		JPanel featureDisplay = new JPanel();
		featureDisplay.setLayout(new FlowLayout());
		featureDisplay.setBackground(Color.GREEN);
		featureDisplay.setPreferredSize(new Dimension(1200,500));
		
		JLabel username = new JLabel();
		username.setFont(new Font("SanSerif",Font.BOLD,24));
		String usernameName = cup.returnString(1);
		username.setText(usernameName);
		
		JLabel profile = new JLabel();
		String profileImageName = cupIn.returnString(3);
		ImageIcon profileImage = new ImageIcon(profileImageName);
		profile.setIcon(profileImage);
		
		featureDisplay.add(username, FlowLayout.LEFT);
		featureDisplay.add(profile);
		
		JButton flagLook = new JButton("Flag look");
		JButton worldLook = new JButton("World Look");
		worldLook.addActionListener(instance);

		JButton goHome = new JButton("Go Home");
		goHome.addActionListener(instance);
		add(goHome);		
		
		flagLook.setPreferredSize(new Dimension(100,50));
		worldLook.setPreferredSize(new Dimension(100,50));
		goHome.setPreferredSize(new Dimension(100,50));
		
		featureDisplay.add(flagLook);
		featureDisplay.add(worldLook);
		featureDisplay.add(goHome);     
		
		add(countryDisplay,BorderLayout.CENTER);
		add(featureDisplay,BorderLayout.SOUTH);
	}

	public boolean isCorrect(String guess)
	{
			if(guess.equals(countryImageAns))
				return true;
			
			else 
				return false;
	}
	
	public String generateCountryImage()
	{	
		
		int rand = 0;
		rand = (int)(Math.random()*randLength);
		String countryGen = " ";
		countryGen = "GameCountryNames/" + countryArray[rand];
		String endTemp = countryArray[randLength];
		String randTemp = countryArray[rand];
		countryArray[rand] = endTemp;
		countryArray[randLength] = randTemp;
		randLength--;
		if(randLength == 0)
		{
			randLength = countryArray.length;
		}
		
		return countryGen;
	}
			
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		String pointsString = "\t Points: " + pointsNumber;
		points.setFont(infoFont);
		points.setText(pointsString);
		
		countryIcon = new ImageIcon(countryImageString);
		country.setIcon(countryIcon);   
		
		
		revalidate();
	}
	
	public void updateAns()
	{
		int firstIndex = countryImageString.indexOf("/")+1;
		int secondIndex = countryImageString.indexOf(".png");
		countryImageAns = countryImageString.substring(firstIndex,secondIndex);
	}
}
