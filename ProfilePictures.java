// Ali Malik
// 5-19-22
// ProfilePictures.java
// Entire class done by Ali, except for the username and about me.
// This class will create the profile pictures.

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class ProfilePictures extends JPanel 
{
	private PicPanel[] pps;	// array to hold profile pictures
	private int rows;	// number of rows of buttons
	private int cols;	// number of columns of buttons
	public final String[] imageNames = new String[] {"Putin______.jpeg","Xi_Jinping_.jpeg","Joe_Biden__.jpeg", "Elon_Musk__.jpeg", "Gengis_Khan.jpeg", "Ali________.jpeg"};
	public int profilePictureNumber = 0;

	// creates array of images, and then adds them to the screen
	public ProfilePictures(PicPanel[] ppsIn, int rowsIn, int colsIn, CustomizeUserPanel instanceIn) 
	{
		CustomizeUserPanel instance = instanceIn;
		pps = ppsIn;
		rows = rowsIn;
		cols = colsIn;
		setLayout(new GridLayout(rows,cols));

		for(int i = 0; i< cols; i++)
		{
			for(int j = 0; j< rows; j++)
			{
				setForeground(Color.BLACK);
				pps[(3*i)+j] = new PicPanel(imageNames[((3*i)+j)], instance);
				pps[(3*i)+j].setBackground(Color.BLACK);
				add(pps[(3*i)+j]);
			}
		}

	}


}
// called by ProfilePictures(...) to load the image
class PicPanel extends JPanel
{

	public PicPanel(String imgNameIn, CustomizeUserPanel instanceInPic)
	{
		String imgName = imgNameIn;
		ImageIcon pfp = new ImageIcon(imgName);
		JButton button = new JButton(imgName);
		button.setIcon(pfp);
		button.setForeground(Color.WHITE);
		button.setPreferredSize(new Dimension(290,300));
		button.addActionListener(instanceInPic);
		add(button);
	}
}

class InfoPanel extends JPanel
{	
	// Adds the user interactive fields, like the fu fact and name. 
	// Has a go back to start panel button

	public String imageLoad;
	public JLabel label;
	public JLabel label2;
	public JTextField username, aboutMe;
	private int imageNumber;

	public InfoPanel(CustomizeUserPanel instanceIn)
	{
		CustomizeUserPanel instance = instanceIn; // instance of CustomizeUserPanel for a button's action listener
		imageLoad = " ";
		JButton continueToGame;
		Font infoFont = new Font("Arial", Font.BOLD, 20);

		setBackground(Color.BLACK);
		
		username = new JTextField("Enter Username Here",20);
		username.setEditable(true);
		username.addActionListener(instance);
		username.setFont(infoFont);
		username.setVisible(true);
		add(username);

		aboutMe = new JTextField("Enter a short description about you",20);
		aboutMe.setEditable(true);
		aboutMe.setFont(infoFont);
		aboutMe.addActionListener(instance);
		aboutMe.setVisible(true);
		add(aboutMe);

		// ImageIcon labelIcon = new ImageIcon(imageLoad);
		label = new JLabel();
		JPanel imagePan = new JPanel();
		imagePan.setPreferredSize(new Dimension(600,500));
		imagePan.setBackground(Color.RED);
		//imagePan.setOpaque(true);
		add(imagePan);
		
		label2 = new JLabel();
		
		Font quoteFont = new Font("SanSerif",Font.ITALIC,20);
		label2.setFont(quoteFont);
	
		imagePan.add(label);
		imagePan.add(label2);

		add(imagePan);


		JButton goHome = new JButton("Go Home");
		goHome.addActionListener(instance);
		add(goHome);
		continueToGame = new JButton("Continue To Game");
		continueToGame.addActionListener(instance);
		add(continueToGame);
	}
	
	public String generateQuote(String imageForQuote)
	{
		String quote = " ";
		if(imageForQuote.equals("Putin______.jpeg"))
		{
			quote = "I am the president of Russia.";
		}
		else if(imageForQuote.equals("Xi_Jinping_.jpeg"))
		{
			quote = "I am the president of China.";
		}
		else if(imageForQuote.equals("Joe_Biden__.jpeg"))
		{
			quote = "I am the president of USA.";
		}
		else if(imageForQuote.equals("Elon_Musk__.jpeg"))
		{
			quote = "I am the richest person.";
		}
		else if(imageForQuote.equals("Gengis_Khan.jpeg"))
		{
			quote = "I am Gengis Khan.";
		}
		else
			quote = "Guest.";
		
		return quote;
	}
		

}
