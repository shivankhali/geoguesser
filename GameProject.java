// Shivank Hali
// 05-19-22
// HighScores.java
// Everything by Shivank excpet for InstructionsPanel, and half of PlayPanel and CustomizeUserPanel

// A holder class which uses card layout to show various panels in the  game.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;  // awt imports
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

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

public class GameProject
{
	//blank
	public GameProject()
	{
	}

	// creates instance of class, then calls run()
	public static void main(String [] args)
	{
		GameProject gp = new GameProject();
		gp.run();
	}

	// does all the frame building, then adds GameHolder
	public void run()
	{
		JFrame frame = new JFrame("Game Project");
		frame.setSize(1200, 700);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setLocation(0,0);
		frame.setResizable(true);
		GameHolder gph = new GameHolder();
		frame.getContentPane().add(gph);
		frame.setVisible(true);
	}
}

// this panel holds the panels in the cardLayout
class GameHolder extends JPanel
{
	public GameHolder()
	{
		CardLayout cards = new CardLayout();
		setLayout(cards);
		StartPanel startP = new StartPanel(cards, this);
		CustomizeUserPanel cup = new CustomizeUserPanel(cards, this);
		HighScoresPanel hp = new HighScoresPanel(cards, this);
		InstructionsPanel ip = new InstructionsPanel(cards, this);
		PlayPanel pp = new PlayPanel(cards, this, cup);
		WorldLookPanel wlp = new WorldLookPanel(cards, this, 0);
		SettingsPanel sp = new SettingsPanel(cards, this, wlp);
		NukeGamePanel ng = new NukeGamePanel(cards, this);
		GMF gmf = new GMF(cup);
		
		add(startP, "First");
		add(sp, "Settings Panel");
		add(cup, "Customize User Panel");
		add(hp, "High Score Panel");
		add(ip, "Instructions Panel");
		add(pp, "Game Panel");
		add(wlp, "World Look");
		add(ng, "You Lose");
		add(gmf, "Quiz Panel");
	}
}

class StartPanel extends JPanel
{
	private CardLayout cards; // insatnce of CardLayout which will help with buttons that switch panels
	private GameHolder pCards; // helps sort through cards in cardlayout
	private int xCords, yCords; // coordinates that will make the country animation move
	private boolean left, up; // boolean what will determine which direction the country moves
	private String picLoad;	// helps store the name of image that has to be loaded
	private String buttonChecker; // used to determine which image incon button was clicked
	private String[] contents; // array that holds all the country image files
	private int picCount; // helps systematically sort through array of country pictures
		
	// the start page to show up, has a bunch of image icon buttons for various actions
	// has a timer which animates the country moving at the bottom of the panel
	public StartPanel(CardLayout cardsIn, GameHolder pCardsIn)
	{
		File directoryPath = new File("GameCountryNames");
		contents = directoryPath.list();
		picCount = 0;
		picLoad = "Canada.png";
		yCords = 520;
		cards = cardsIn;
		pCards = pCardsIn;
		setLayout(null);
		Font myFont = new Font("Monotone", Font.BOLD, 42);
		Color earthGreen = new Color(14,192,166);
		Color earthBlue = new Color(40,122,184);
		
		Color pinkish = new Color(40, 225, 244);
		setBackground(pinkish);
		buttonChecker = "High Scores";
		StartPanelButtons spb1 = new StartPanelButtons(cards, pCards,buttonChecker);
		ImageIcon leaderBoardButtonIcon = new ImageIcon("leaderBoardButton.png");
		JButton highScoreButton = new JButton(leaderBoardButtonIcon);
		highScoreButton.setBounds(10, 10, 200, 113);
		highScoreButton.setBorder(null);
        highScoreButton.setBorderPainted(false);
        highScoreButton.setContentAreaFilled(false);
        highScoreButton.setOpaque(false);
		highScoreButton.addActionListener(spb1);

		buttonChecker = "Settings";
		StartPanelButtons spb2 = new StartPanelButtons(cards, pCards,buttonChecker);
		ImageIcon settButtonIcon = new ImageIcon("settingsButton.png");
		JButton settingsButton = new JButton(settButtonIcon);
		settingsButton.setBounds(890, 10, 200,115);
		settingsButton.setBorder(null);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setOpaque(false);
		settingsButton.addActionListener(spb2);

		buttonChecker = "Instructions";
		StartPanelButtons spb3 = new StartPanelButtons(cards, pCards,buttonChecker);
		ImageIcon instrButtonIcon = new ImageIcon("instructionButton.png");
		JButton instructionsButton = new JButton(instrButtonIcon);
		instructionsButton.setBounds(10, 300, 200, 122);
		instructionsButton.setBorder(null);
        instructionsButton.setBorderPainted(false);
        instructionsButton.setContentAreaFilled(false);
        instructionsButton.setOpaque(false);
		instructionsButton.addActionListener(spb3);

		buttonChecker = "Play Now!";
		StartPanelButtons spb4 = new StartPanelButtons(cards, pCards,buttonChecker);
		ImageIcon playButtonIcon = new ImageIcon("playButton.png");
		JButton playButton = new JButton(playButtonIcon);
		playButton.setBounds(400, 250, 350, 228); 
		playButton.setBorder(null);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);
		playButton.addActionListener(spb4);
		
		buttonChecker = "Quit";
		StartPanelButtons spb5 = new StartPanelButtons(cards, pCards,buttonChecker);
		ImageIcon quitButtonIcon = new ImageIcon("quitButton.png");
		JButton exitButton = new JButton(quitButtonIcon);
		exitButton.setBounds(870, 300, 200, 106);
		exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setOpaque(false);
		exitButton.addActionListener(spb5);

		JTextArea area = new JTextArea("Shaping The Globe");
		area.setBounds(350, 10, 430, 60);
		area.setFont(myFont);
		area.setBackground(earthBlue);

		add(highScoreButton);
		add(settingsButton);
		add(area);
		add(instructionsButton);
		add(playButton);
		add(exitButton);
		
	
       CountryMover mover = new CountryMover(); // timer which will animate the country on the start screen
       Timer timer = new Timer(35, mover); // called every 35 ms
       timer.start();
	}
	
	// loads image by taking in image name, then adding the path in front of it
	// uses traditionl try-catch
	public Image getMyImage(String pictName) 
	{
		if(pictName.equals("BackgroundGame.png"))
		{
			Image picture = null;
			
			File imageFile = new File("BackgroundGame.png");
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
			
			File imageFile = new File("GameCountryNames/" + pictName);
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
	
	// after loading picture, after the country mover calls repaint, image startes to move
	// after hitting a certain point, the country image changes to a new one (sequenctially)
	// after all images have been played, it restarts the animation
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(getMyImage("BackgroundGame.png"), 0, 0,1200, 475, this);
		g.setColor(Color.BLACK);
		g.fillRect(0, 475, 1200, 725);
		Color cyanBlue = new Color(40, 225, 244);
		g.setColor(cyanBlue);
		g.fillRect(20, 495, 1160, 160);
		
		Image picture = null;
		String pictureString;
		int picWidth;
		int picHeight;

		picture = getMyImage(picLoad);
		
		picWidth = (int)((picture.getWidth(this))*.3);
		picHeight = (int)((picture.getHeight(this))*.3);

		g.drawImage(picture, xCords, yCords, picWidth, picHeight, this);

		if (left == false && xCords < getWidth()-picWidth) 
		{
			xCords+=10;
		}
		else 
		{ 
			if (xCords < getWidth() - picWidth + 65 && left == false)
			{
				picLoad = contents[picCount];
				picCount++;
				if (picCount == contents.length)
					picCount = 0;
			}
			left = true; 
			xCords-=10; 			
		}
		
		if (left && xCords > 0 ) 
		{
			xCords-=10;
		}
		else 
		{ 
			if (xCords < picWidth - 65 && left == true)
			{
				picLoad = contents[picCount];
				picCount++;
				if (picCount == contents.length)
					picCount = 0;				
			}
			left = false; 
			xCords+=10; 
		}
	}
	
   class CountryMover implements ActionListener
   {
	   //calls reapint() to move the country on the screen
       public void actionPerformed(ActionEvent evt)
       {
           repaint();
       }
   }

	// deals with all of the buttons on start panel; opens respective panel to button
	class StartPanelButtons implements ActionListener
	{
		private CardLayout cards; // insatnce of CardLayout which will help with buttons that switch panels
		private GameHolder pCards; // helps sort through cards in cardlayout
		private String cmd; // string which will recieve the string associated with the button pressed
		
		// stores in the recieving parameters as FVs
		public StartPanelButtons(CardLayout cardsIn, GameHolder pCardsIn, String buttonCheckerIn)
		{
			cards = cardsIn;
			pCards = pCardsIn;
			cmd = buttonCheckerIn; 
		}

		// stores action command, then uses a conditional to open respective panel to button clicked
		// has a special exit button which quits code
		public void actionPerformed(ActionEvent evt)
		{
			if (cmd.equals("Settings"))
			{
				cards.show(pCards, "Settings Panel");
			}
			else if (cmd.equals("High Scores"))
			{
				cards.show(pCards, "High Score Panel");
			}
			else if (cmd.equals("Play Now!"))
			{
				cards.show(pCards, "Customize User Panel");
			}
			else if (cmd.equals("Instructions"))
			{
				cards.show(pCards, "Instructions Panel");
			}
			else if (cmd.equals("Quit"))
			{
				System.exit(1);
			}
		}
	}
}
class SettingsPanel extends JPanel implements ActionListener
{
	private CardLayout cards; // insatnce of CardLayout which will help with buttons that switch panels
	private GameHolder pCards; // helps sort through cards in cardlayout
	private double scaleFactor; // number that the pixels are multiplied by to get distance
	private WorldLookPanel wlp; // used to call world look panel to pass in scale f
	// stores in the recieving parameters as FVs
	
	// calls Setting's constructor and passes in this class' instance so that actionPerformed can be used
	public SettingsPanel(CardLayout cardsIn, GameHolder pCardsIn, WorldLookPanel wlpIn)
	{
		wlp = wlpIn;
		setLayout(new BorderLayout());
		Settings pAdder = new Settings(this);
		setLayout(new BorderLayout());
		cards = cardsIn;
		pCards = pCardsIn;
		add(pAdder);       
	}

	// based on which menu item in settings is pressed, a certain action will follow
	// if one of the units are clicked, passes a scale factor to setter method in world look panel
	public void actionPerformed(ActionEvent evt)
	{
		String menuChoice = evt.getActionCommand();
		if (menuChoice.equals("Start"))
		{
			cards.show(pCards, "First");
		}
		else if(menuChoice.equals("Instructions"))
		{
			cards.show(pCards, "Instructions Panel");
		}
		else if(menuChoice.equals("Leaderboard"))
		{
			cards.show(pCards, "High Score Panel");
		}
		else if(menuChoice.equals("Quit"))
		{
			System.exit(2);
		}
		else if(menuChoice.equals("Miles"))
		{
			scaleFactor = 19.0;
			wlp.setUnit(scaleFactor);
		}
		else if(menuChoice.equals("Kilometers"))
		{
			scaleFactor = 30.36;
			wlp.setUnit(scaleFactor);
		}
			
	}
}

class HighScoresPanel extends JPanel implements ActionListener
{
	private CardLayout cards; // insatnce of CardLayout which will help with buttons that switch panels
	private GameHolder pCards; // helps sort through cards in cardlayout
	HighScores highScores; // instance of HighScore, which will be used to display the hs panel using card layout

	// stores in the recieving parameters as FVs
	// calls HighScores' constructor and passes in this class' instance so that actionPerformed can be used
	public HighScoresPanel(CardLayout cardsIn, GameHolder pCardsIn)
	{
		setLayout(new BorderLayout());
		highScores = new HighScores(this, "", "", -1, null);
		setLayout(new BorderLayout());
		cards = cardsIn;
		pCards = pCardsIn;
		add(highScores, BorderLayout.CENTER);       
	}

	// shows home page, if home button is pressed
	public void actionPerformed(ActionEvent evt)
	{
		String goHome = evt.getActionCommand();
		if(goHome.equals("Go Home"))
		{
			cards.show(pCards, "First");
		}
	}
}
class InstructionsPanel extends JPanel implements ActionListener
{
	private CardLayout cards; // card layout 
	private GameHolder pCards; // GameHolder instance for suffling through cards

	// stores in the recieving parameters as FVs
	// calls Instructions's constructor and passes in:
	// this class' instance so that actionPerformed can be used
	// image name to be displayed
	// the string of instructions
	// color for background
	// class implements polymorphism
	public InstructionsPanel(CardLayout cardsIn, GameHolder pCardsIn)
	{
		Instructions ip1,ip2;
		setLayout(new GridLayout(1,2));
		cards = cardsIn;
		pCards = pCardsIn;
		Image img1 = null;
		String imgName = "ImageTestingGame.png";
		File imgFile = new File(imgName);

		try
		{
			img1 = ImageIO.read(imgFile);
		}
		catch (IOException e)
		{
			System.err.println("\n\n" + imgName + " can't be found.\n\n");
			e.printStackTrace();
		}
		String line1 = "How To Play:\n\n";
		String line2 = "The user has 3 attemps to guess the country based on its outline\n\n";
		String line3 = "Points are awarded based on how many guesses it takes for the user to solve\n\n";
		String linePoints = "1st guess - 15 points\n2nd guess - 10 points\n3rd guess - 5 points";
		String lineDistance = "If you guess incorrectly, the distance between your incorrect guess and the correct answer is displayed\n\n";
		String line4 = "\n\n\n Advanced Controls:\n\n";
		String feat1 = "*|World Look|* : A map of the world is shown. To find the distance click two " +
				"places and the distance in km or miles will be displayed. \n*Only one per game, and units must be selected first in settings*";
		String feat2 = "\n\n*|Flag Look|* : The flag of the country is displayed. \n *Enabled after 100 points*";
		String text1 = line1 + line2 + lineDistance + line3 + linePoints + line4 + feat1 + feat2;

		ip1 = new Instructions(text1,Color.GREEN,this);
		ip2 = new Instructions(img1,Color.BLUE,this);

		add(ip1);
		add(ip2);     
	}


	// returns to home panel when button is pressed
	public void actionPerformed(ActionEvent evt)
	{
		String goHome = evt.getActionCommand();
		if(goHome.equals("Go Home"))
		{
			cards.show(pCards, "First");
		}
	}

}
class CustomizeUserPanel extends JPanel implements ActionListener 
{
	private String username = "";
	private String aboutMe = "";
	private CardLayout cards; // card layout 
	private GameHolder pCards; // GameHolder instance for suffling through cards
	PicPanel[] pps1 = new PicPanel[6];
	InfoPanel infp;
	private int points;
	private String pfp;
	
	// stores in the recieving parameters as FVs
	// calls ProfilePictures's constructor and passes in either this class' 
	// instance so that actionPerformed can be used or
	// the array of images(blank), num rows, and num columns
	public CustomizeUserPanel(CardLayout cardsIn, GameHolder pCardsIn)
	{
		cards = cardsIn;
		pCards = pCardsIn;
		setBackground(Color.BLACK); // color will change later

		final int ROWS = 3;
		final int COLS = 2;
		ProfilePictures pPics = new ProfilePictures(pps1,ROWS,COLS, this);		
		infp = new InfoPanel(this);
		setLayout(new GridLayout(1,2));

		add(pPics);
		add(infp);
	}

	// if button pressed to go home, goes home
	// if button pressed to advance to game, advances to game
	public void actionPerformed(ActionEvent evt)
	{
		String profileButtons = evt.getActionCommand();
		String iconName = profileButtons;
		ImageIcon imgDisp = null;
		PlayPanel instanceIn = new PlayPanel(cards, pCards, this);
		infp.label2.setText(infp.generateQuote(iconName));

		
		if(profileButtons.equals("Go Home"))
			cards.show(pCards, "First");
		else if(profileButtons.equals("Continue To Game"))
		{
			GamePanel gameP = new GamePanel(instanceIn, this, username, pfp);
			cards.show(pCards, "Game Panel");
		}
			//setName(firstNameField.getText(), fact.getText());	
		else if(profileButtons.contains(".jpeg"))
		{
			iconName = profileButtons;
			//infp.label2.setText(infp.generateQuote(iconName));
			pfp = iconName;
			imgDisp  = new ImageIcon(iconName);
			infp.label.setIcon(imgDisp);
		}
		else
		{
			username = infp.username.getText();
			aboutMe = infp.aboutMe.getText();
			
		}
	}
	
	public String returnString(int selectIn)
	{
		int select = selectIn;
		if(select == 1)
			return username;
		else if(select == 2)
			return aboutMe;
		else
			return pfp;
	}
}

class PlayPanel extends JPanel implements ActionListener 
{
	private int points;
	private String username = "";
	private String aboutMe = "";
	private CardLayout cards; // card layout 
	private GameHolder pCards; // GameHolder instance for suffling through cards
	private GamePanel gameP;
	HighScores highScores;
	HighScoresPanel hsp;	public int amtOfGuesses = 0;
	public int pointsNum = 0;
	CustomizeUserPanel cup;
	public String textInput = " ";

	// stores in the recieving parameters as FVs
	// calls GamePanel's constructor and passes in either this class' instance so that actionPerformed can be used 

	public PlayPanel(CardLayout cardsIn, GameHolder pCardsIn, CustomizeUserPanel cupIn)
	{
		cards = cardsIn;
		cup = cupIn;
		pCards = pCardsIn;
		setBackground(Color.BLACK); // color will change later
		hsp = new HighScoresPanel(cards, pCards);
		highScores = new HighScores(hsp, "", "", -1, null);
		gameP = new GamePanel(this, cup, null, null);
		add(gameP);

	}
	
	int count;
	// if button pressed to go home, goes home
	public void actionPerformed(ActionEvent evt)
	{		
		textInput = gameP.guessText.getText();
		String profileButtons = evt.getActionCommand();
		
		String temp = gameP.generateCountryImage();
		String pict;
		
		if (profileButtons.equals("Go Home"))
		{
			cards.show(pCards, "First");
		}
		else if (profileButtons.equals("World Look")&& count<=0)
		{
			count++;
			cards.show(pCards, "World Look");
		}
		else
        {
            if(amtOfGuesses < 3)
            {
                if(gameP.isCorrect(textInput) == false)
                {
                    amtOfGuesses++;

                }
                else if(gameP.isCorrect(textInput) == true)
                {
                    gameP.pointsNumber += 15 - (5*amtOfGuesses);
                    pointsNum = gameP.pointsNumber;
                    amtOfGuesses = 0;
					gameP.countryImageString = temp;
                    gameP.updateAns();
                    gameP.repaint();

                }
            }
            
            
            else
            {
				username = cup.returnString(1);
				aboutMe = cup.returnString(2);
				pict = cup.returnString(3);
				points = pointsNum;
				highScores = new HighScores(hsp,username, aboutMe, points, pict);
				cards.show(pCards, "You Lose");
			}

        }
	}
}
class NukeGamePanel extends JPanel implements ActionListener
{
	private CardLayout cards; // card layout 
	private GameHolder pCards; // GameHolder instance for suffling through cards
	private NukeGame ng; // instance of nuke game, which will be used to display the nuke panel using card layout
	
	//sets layout to borderlayout, then adds NukeGame to the center of it
	public NukeGamePanel(CardLayout cardsIn, GameHolder pCardsIn)
	{
		setLayout(new BorderLayout());
		cards = cardsIn;
		pCards = pCardsIn;
		ng = new NukeGame(this);
		add(ng, BorderLayout.CENTER);

	}

	// if button pressed to go home, goes home
	// if button pressed to quiz, displays the quiz
	public void actionPerformed(ActionEvent evt)
	{
		String button = evt.getActionCommand();
		
		if (button.equals("Start"))
		{
			cards.show(pCards, "First");
		}
		else if(button.equals("Quiz"))
		{
			cards.show(pCards, "Quiz Panel");
		}
	}
	
}

class WorldLookPanel extends JPanel implements ActionListener
{
	private CardLayout cards; // card layout 
	private GameHolder pCards; // GameHolder instance for suffling through cards
	private WorldLook wP; // instance of world look, which will be used to display the world look panel using card layout
	private double sf; // number that the pixels are multiplied by to get distance
	
	//sets layout to borderlayout and initializes fvs
	public WorldLookPanel(CardLayout cardsIn, GameHolder pCardsIn, double sfIn)
	{
		sf = sfIn;
		setLayout(new BorderLayout());
		cards = cardsIn;
		pCards = pCardsIn;

	}

	// if button pressed to go home, goes home
	public void actionPerformed(ActionEvent evt)
	{
		String button = evt.getActionCommand();
		
		if (button.equals("Back To Game"))
		{
			cards.show(pCards, "Game Panel");
		}
	}
	
	// method which displays the world look class when unit is chosen, otherwise panel is blank
	public void showWl(double sf)
	{
		if(sf!=0.0)
			{
				wP = new WorldLook(this, getUnit());
				add(wP, BorderLayout.CENTER);
			}
	}
		
	// setter method for world look panel which sets unit that it recieves
	public void setUnit(double unitIn)
	{
		sf = unitIn;
		showWl(sf);
	}
	
	// returns sf when called (getter method)
	public double getUnit()
	{
		return sf;
	}
}
