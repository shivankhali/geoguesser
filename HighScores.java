// Shivank Hali
// 05-19-22
// HighScores.java
// Everything by Shivank 

// This class will create the High score panel.
// Hs panel will display the top 5 users' profile picture, and their name, fact, and score

import java.awt.*;
import javax.swing.*;
import java.io.*;

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
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

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
import java.util.Arrays;


class HighScores extends JPanel
{
	private String result, resultSorted; // result is a String which stores everything 
	//from gamePlayes.txt, and resultSorted stores the sorted version of the (in order of heights to lowest scores). 
	private String fact, name, pfp, imageString; // user's personalized name, fact, etc... will be stored in these
	private int points; // amount of points user scored will be stored in this.
	private JTextArea nameSorted, factSorted, pointsSorted; // sorted version of user's info (similar to resultSorted)
	private HighScoresPanel instance; // insatnce of HighScoresPanel to allow for the button to work
	private JTextArea highScore; // displays hs into this
	private User[] users; // top 5 users will be stored in this (1st in 0 index, and so on)
	private Image pfpSorted; // sorted version of pfp (similar to resultSorted)
			
	// if user has played, it calls the high score processing methods
	public HighScores(HighScoresPanel instanceIn, String nameIn, String factIn, int pointsIn, String pfpIn)
	{
		setLayout(new BorderLayout());
		setBackground(Color.GREEN);
		instance = instanceIn;
		points = pointsIn;
		name = nameIn;
		fact = factIn;
		pfp = pfpIn;
		
		instance = instanceIn; // instance of HighScorePanels for a button's action listener
		
		JButton goHome = new JButton("Go Home");
		goHome.addActionListener(instance);
		add(goHome, BorderLayout.EAST);
		
		if (points != -2) 
		{
			saveToHighScores();
			showToHighScores();
		}
	}

	// loads image by taking in image name, then adding the path in front of it
	// uses traditionl try-catch
	public Image getMyImage(String pictName) 
	{
		Image picture = null;
		File imageFile = new File(pictName);
		try
		{
			picture = ImageIO.read(imageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n" + pictName + " not can't be found.\n");
			e.printStackTrace();
		}
		Image newImage = picture.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		return newImage;
	}
	
	// writes user's info to gamePlayers.txt regardless of score
	public void saveToHighScores()
	{
		if(!name.equals("") && points != -1)
		{	
			File ioFile = new File("gamePlayers.txt");
			PrintWriter outFile = null;
			try
			{
				outFile = new PrintWriter(new FileWriter(ioFile, true));
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			
			outFile.print("Name: " + name + "\tFact About Me: " + fact + "\tScore: " + points + "*\tPfp: " + pfp + "\n");
			outFile.close();
		}
	}
	
	// sorts through file, and filter's string to get user's info to be displayed
	// reads file through a scanner in try ~ catch
	// class also constructs the high score panel, ie: adds title, and pfps
	public void showToHighScores( )
	{
		result = "";
		int num = 0, i;
		String fileName = "gamePlayers.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
		users = new User[50]; // max users are set to 50, but can be easily changed to desired amount
					
		for(i = 0; i < 50; i++)
		{
			users[i] = new User("", 0);
		}
		
		try 
		{
			inFile = new Scanner(inputFile);
		} 
		catch(FileNotFoundException e) 
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
			System.exit(1);
		}
		
		while(inFile.hasNextLine()) 
		{
			String line = inFile.nextLine();
			result += line + "\n";			
			users[num].setName(line.substring(6, line.indexOf("Fact About Me:")).trim());
			users[num].setFact(line.substring(line.indexOf("Me:")+3, line.indexOf("Score:")).trim());
			users[num].setScore(Integer.parseInt(line.substring(line.indexOf("Score: ")+7, line.lastIndexOf("*"))));
			users[num].setPfp(line.substring(line.indexOf("Pfp: ")+5, line.indexOf(".jpeg")+5));
			num++;			
		}
		Arrays.sort(users); // source stack overflow
		
		JPanel highPanel = new JPanel();
		highPanel.setBackground(Color.GREEN);
		highPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		Font myFont = new Font("Monotone", Font.BOLD, 20);
		
		JPanel westSide = new JPanel(new FlowLayout());
		westSide.setPreferredSize(new Dimension(100, 700));
		westSide.setBackground(Color.GREEN);
		
		Font headerFnt = new Font("SanSerif",Font.BOLD,48); 
		JLabel gameLabel = new JLabel("Top 5 High Scores: "); 
		JPanel gameHeader = new JPanel(); 
		gameHeader.setBackground(Color.GREEN);
		gameLabel.setFont(headerFnt);
		gameHeader.add(gameLabel);
		gameHeader.setPreferredSize(new Dimension(1200, 100));
		add(gameHeader, BorderLayout.NORTH); 
		
		// each JPanel holds 1 pfp
		JPanel panel1 = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel(new BorderLayout());
		JPanel panel3 = new JPanel(new BorderLayout());
		JPanel panel4 = new JPanel(new BorderLayout());
		JPanel panel5 = new JPanel(new BorderLayout());
		
		int count = 0;
		// loops 5 times to get the top 5 user's info, then adds it onto the panel
		for(i = 0; i < 5 && i < num + 1 && num != 0; i++)
		{
			count++;

			resultSorted = (users[i].getName() + "\t" + users[i].getFact() + "\t" + users[i].getScore() + "\n");
			
			nameSorted = new JTextArea(users[i].getName(), 5, 10);
			nameSorted.setEditable(false);
			nameSorted.setFont(myFont);
			nameSorted.setOpaque(false);
			highPanel.add(nameSorted);
			
			factSorted = new JTextArea(users[i].getFact(), 5, 20);
			factSorted.setFont(myFont);
			factSorted.setEditable(false);
			factSorted.setLineWrap(false);
			factSorted.setEditable(false);
			factSorted.setOpaque(false);
			highPanel.add(factSorted);
			
			pointsSorted = new JTextArea("" + users[i].getScore(), 5, 20);
			pointsSorted.setFont(myFont);
			pointsSorted.setEditable(false);
			pointsSorted.setLineWrap(false);
			pointsSorted.setEditable(false);
			pointsSorted.setOpaque(false);
			highPanel.add(pointsSorted);
			
			imageString = users[i].getPfp();
			if(name == null)
				pfpSorted = getMyImage("Putin______.jpeg");
			else
				pfpSorted = getMyImage(imageString);
			
			if(count == 1)	
			{
				ImageIcon image = new ImageIcon(pfpSorted);
				JLabel label = new JLabel("", image, JLabel.CENTER);
				panel1.add(label, BorderLayout.CENTER );
				westSide.add(panel1);
			}
			
			else if(count == 2)	
			{
				ImageIcon image1 = new ImageIcon(pfpSorted);
				JLabel label1 = new JLabel("", image1, JLabel.CENTER);
				panel2.add(label1, BorderLayout.CENTER );
				westSide.add(panel2);
			}
			
			else if(count == 3)	
			{
				ImageIcon image2 = new ImageIcon(pfpSorted);
				JLabel label2 = new JLabel("", image2, JLabel.CENTER);
				panel3.add(label2, BorderLayout.CENTER );
				westSide.add(panel3);
			}
			
			else if(count == 4)	
			{
				ImageIcon image3 = new ImageIcon(pfpSorted);
				JLabel label3 = new JLabel("", image3, JLabel.CENTER);
				panel4.add(label3, BorderLayout.CENTER );
				westSide.add(panel4);
			}
			
			else if(count == 5)	
			{
				ImageIcon image4 = new ImageIcon(pfpSorted);
				JLabel label4 = new JLabel("", image4, JLabel.CENTER);
				panel5.add(label4, BorderLayout.CENTER );
				westSide.add(panel5);
			}
			
		}
		add(highPanel, BorderLayout.CENTER);
		add(westSide, BorderLayout.WEST);
		repaint();
	}
	
	// blanks canvas
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}

// this class will sort through to get the top 5 scorers
// also a getter and setter class which holds all of user's data
class User implements Comparable<User> // source stack overflow (entire class)
{
    private String name, factAboutMe, pfp; // user's info holders
    private int score; // user's score holder
 
    public User(String name, int score)
    {
        this.name = name;
        this.score = score;
    }
 
    @Override
    public String toString()
    {
        return "\n\n" + name + "\n" + factAboutMe+"\n" + score + "\n\n";
    }
 
    public String getName() 
    {
        return name;
    }
 
    public int getScore() 
    {
        return score;
    }
    
    public String getFact() 
    {
        return factAboutMe;
    }
    
    public String getPfp() 
    {
        return pfp;
    }
    
    public void setName(String userName) 
    {
        name = userName;
    }
 
    public void setScore(int userScore) 
    {
        score = userScore;
    }
    
	public void setFact(String userFact)
	{
		factAboutMe = userFact;
	}
	
	
    public void setPfp(String userPfp) 
    {
        pfp = userPfp;
    }
    
    @Override
    public int compareTo(User o)
    {
        if (this.score != o.getScore()) 
        {
            return o.getScore() - this.score;
        }
        return this.name.compareTo(o.getName());
    }
}
