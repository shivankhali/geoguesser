// Shivank Hali
// 5-19-22
// Quiz.java
// Everything done by shivank 
// This class is a copy class if GameModuleFiles, and will 
// take the quiz of the user after the regular mode has ended.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridLayout; 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;


import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;


import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

class GMF extends JPanel
{
	private CardLayout listOfCards; // used to move through different panels in card layout
	private GameData data; // instance of GameData to grab its questions
	private CustomizeUserPanel cup; // instance of CustomizeUserPanel used to call its getter method
	
	// creates instace of QuestionsPanel, and HighScoresPanelGMF, then adds them 
	// to the card layout. 
	public GMF (CustomizeUserPanel cupIn)
	{
		cup = cupIn;
		String name = cup.returnString(1);
		data = new GameData(name);
		data.grabQuestionFromFile();
		
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		listOfCards = new CardLayout();
		setLayout(listOfCards);
		
		QuestionsPanel second = new QuestionsPanel(data, listOfCards, this);
		add(second, "2");
		
		HighScoresPanelGMF third = new HighScoresPanelGMF(data, listOfCards, this);
		add(third, "3");
	}
}

class QuestionsPanel extends JPanel implements ActionListener
{
	private GameData data; // instance of GameData to grab its questions
	private CardLayout listOfCards; // used to move through different panels in card layout
	private GMF primaryPanel; // used to show a certain card from card layout
	private ButtonGroup group; // holds the options
	private JTextArea questionArea; // area where flag will be displayed
	private JRadioButton [] answer; // array that holds answer for question.
	private JButton submit, nextQuestion, nextPanel; // 3 buttons at the bottom of the JPanel.
	private ImageIcon icon; // holds flag
	private Image picture; // used to getMyImage to load image
	private JLabel logo; // holds the image icon

	// constructor which adds all the compnents for first panel, along with loading the Q and As.
	public QuestionsPanel(GameData d, CardLayout c, GMF p)
	{
		data = d;
		listOfCards = c;
		primaryPanel = p;
		
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(10, 10));
		Font myFont = new Font("Tahoma", Font.BOLD, 22);
		
		answer = new JRadioButton[4];
		
		JPanel question = new JPanel();
		question.setPreferredSize(new Dimension(1100,200));
        question.setBackground(Color.WHITE);
        question.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        question.setLayout(new BorderLayout());

        String questionFlag = data.getQuestion();
        questionFlag = questionFlag.substring(3, questionFlag.length());

		ImageIcon logoIcon  = new ImageIcon(getMyImage(questionFlag));
		logo = new JLabel(logoIcon);

		question.add(logo, BorderLayout.CENTER);
		add(question, BorderLayout.NORTH);
		
		JPanel answers = new JPanel();
		answers.setBackground(Color.GRAY);
		answers.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		answers.setLayout(new GridLayout(2, 2, 20, 20));
		add(answers, BorderLayout.CENTER);
		
		group = new ButtonGroup();
		
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = new JRadioButton("" + (char)(65 + i) + ". " + data.getAnswer(i));
			group.add(answer[i]);
			answer[i].setBackground(new Color(230, 230, 230));
			answer[i].setFont(myFont);
			answer[i].addActionListener(this);
			answers.add(answer[i]);
			answer[i].setOpaque(true); 
			group.add(answer[i]); 
		} 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		add(buttonPanel, BorderLayout.SOUTH);
		
		submit = new JButton("SUBMIT");
		submit.setFont(myFont);
		submit.addActionListener(this);
		submit.setEnabled(false);
		buttonPanel.add(submit);
		
		nextQuestion = new JButton("NEXT QUESTION");
		nextQuestion.setFont(myFont);
		nextQuestion.addActionListener(this);
		nextQuestion.setEnabled(false);
		buttonPanel.add(nextQuestion);
		
		nextPanel = new JButton("NEXT PANEL");
		nextPanel.setFont(myFont);
		nextPanel.addActionListener(this);
		nextPanel.setEnabled(false);
		buttonPanel.add(nextPanel);
	}
	
	// a polymorphic method (for the flags only) which returns an image (a resized image)
	public Image getMyImage(String pictName) 
	{
		Image picture = null;
		String trimmedPic = (pictName);
		trimmedPic = trimmedPic.trim();
		File imageFile = new File("FlagNames/" + trimmedPic);
		try
		{
			picture = ImageIO.read(imageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n:" + trimmedPic + ": not can't be found.\n");
			e.printStackTrace();
		}
		
		Image newImage = picture.getScaledInstance(200, 142, Image.SCALE_DEFAULT); //source: stack overflow
		return newImage;
	}
	
	// deals with the buttons on the first panel
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		
		if(group.getSelection() != null)
		{
			submit.setEnabled(true);
		}
		
		if(command.equals("SUBMIT"))
		{	
			answer[data.getCorrectAnswer()].setBackground(Color.GREEN);
			for(int i = 0; i < answer.length; i++)
			{
				if(answer[i].isSelected())
				{
					if(i != data.getCorrectAnswer())
					{
						answer[i].setBackground(Color.RED);
					}
					else
					{
						data.addOneToCorrectCount();
					}
				}
			}
			group.clearSelection();
			for(int i = 0; i < answer.length; i++)
			{
				answer[i].setEnabled(false);
			}
			submit.setEnabled(false);
			if(data.getQuestionCount() == 10)
			{
				nextPanel.setEnabled(true);
			}
			else
			{
				nextQuestion.setEnabled(true);
			}
		}
		else if(command.equals("NEXT QUESTION"))
		{
			resetQuestion();
			nextQuestion.setEnabled(false);
		}
		else if(command.equals("NEXT PANEL"))
		{
			data.resetAll();
			resetQuestion();
			nextPanel.setEnabled(false);
			listOfCards.next(primaryPanel);
		}
	}
	
	// used to reset which flag is being displayed.
	public void resetQuestion ( )
	{
		data.grabQuestionFromFile();
		
		String questionFlag = data.getQuestion();
        questionFlag = questionFlag.substring(3, questionFlag.length());
		ImageIcon logoIcon  = new ImageIcon(getMyImage(questionFlag));
		logo.setIcon(logoIcon);
		
		answer[0].setText("A. " + data.getAnswer(0));
		answer[1].setText("B. " + data.getAnswer(1));
		answer[2].setText("C. " + data.getAnswer(2));
		answer[3].setText("D. " + data.getAnswer(3));
		for(int i = 0; i < answer.length; i++)
		{
			answer[i].setEnabled(true);
			answer[i].setBackground(new Color(230, 230, 230));
		}
	}
}

class HighScoresPanelGMF extends JPanel implements ActionListener
{
	private GameData data; // instance of GameData to grab its questions
	private CardLayout listOfCards; // used to move through different panels in card layout
	private GMF primaryPanel; // used to show a certain card from card layout
	private JTextArea scoreInfo, highScoresArea;  // text areas that display score/have something to do with it
	
	//constructor which adds components to quiz panel like the buttons, ta, and imageicons
	public HighScoresPanelGMF(GameData gd, CardLayout cl, GMF gmf)
	{
		data = gd;
		listOfCards = cl;
		primaryPanel = gmf;
		Font myFont = new Font("Tahoma", Font.BOLD, 22);
		
		setLayout(new BorderLayout(20, 20));
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setFont(myFont);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, 10, 10));
		add(centerPanel, BorderLayout.CENTER);
		
		JPanel leftSidePanel = new JPanel();
		leftSidePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		leftSidePanel.setLayout(new BorderLayout());
		centerPanel.add(leftSidePanel, BorderLayout.CENTER);
		
		scoreInfo = new JTextArea("" + data.getCorrectCount(), 10, 20);
		scoreInfo.setFont(myFont);
		scoreInfo.setLineWrap(true);
		scoreInfo.setWrapStyleWord(true);
		scoreInfo.setOpaque(false);
		scoreInfo.setEditable(false);
		leftSidePanel.add(scoreInfo);
		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		rightSidePanel.setLayout(new BorderLayout());
		centerPanel.add(rightSidePanel, BorderLayout.CENTER);
		
		highScoresArea = new JTextArea("" + data.getHighScores(), 10, 20);
		highScoresArea.setFont(myFont);
		highScoresArea.setLineWrap(true);
		highScoresArea.setWrapStyleWord(true);
		highScoresArea.setOpaque(false);
		highScoresArea.setEditable(false);
		highScoresArea.setMargin(new Insets(10,10,10,10));
		JScrollPane scroller = new JScrollPane(highScoresArea);
		rightSidePanel.add(scroller);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(buttonPanel, BorderLayout.SOUTH);
		
		JButton playAgain = new JButton("PLAY AGAIN");
		playAgain.setFont(myFont);
		playAgain.addActionListener(this);
		buttonPanel.add(playAgain);
		
		JButton exit = new JButton("EXIT");
		exit.setFont(myFont);
		exit.addActionListener(this);
		buttonPanel.add(exit);
	}
	
	// sets text in score Info, and then gets high scores
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		scoreInfo.setText("" + data.toString());
		highScoresArea.setText("" + data.getHighScores());
		highScoresArea.setCaretPosition(0);
	}
	
	// deals with the buttons on the quiz panel
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		
		if(command.equals("PLAY AGAIN"))
		{
			data.saveToHighScores();
			listOfCards.previous(primaryPanel);
		}
		else if(command.equals("EXIT"))
		{
			data.saveToHighScores();
			System.exit(0);
		}
	}
}

class GameData
{
	private String question; // String version of flag
	private String [] answerSet; // options; A,B,C,D
	private int correctAnswer; // can store in either 0,1,2,3 (0->A) to determine correct answer
	private boolean [] chosenQuestions; // num questions 
	private int questionCount; // amount of questions played
	private int correctCount, lastGameCorrectCount; //used to determine high scores
	
	public GameData (String nameIn)
	{
		correctCount = 0;
		resetAll();
	}
	
	// resets everything (variables) when game is over
	public void resetAll ( )
	{
		lastGameCorrectCount = correctCount;
		answerSet = new String[4];
		question = "";
		for(int i = 0; i < answerSet.length; i++)
		{
			answerSet[i] = "";
		}
		correctAnswer = -1;
		chosenQuestions = new boolean[38];
		questionCount = correctCount = 0;
	}

	// uses a scanner and try catch to get questions
	// stores each 6th line after 1 because that is the line with the question
	// gets the corresponding options/answer set
	public void grabQuestionFromFile()
    {
        Scanner inFile = null;
        String fileName = "computerQuestions.txt";
        File inputFile = new File(fileName);
        try
        {
            inFile = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.err.printf("ERROR: Cannot open %s/n", fileName);
            System.out.println(e);
            System.exit(1);
        }
        int questionNumber = (int)(Math.random() * 38);
        while(chosenQuestions[questionNumber] == true)
        {
            questionNumber = (int)(Math.random() * 38);
        }
        chosenQuestions[questionNumber] = true;
        questionCount++;
        int counter = 0;
        while(inFile.hasNext() && counter < 6 * questionNumber)
        {
            String line = inFile.nextLine();
            counter++;
        }
        question = inFile.nextLine();

        counter = 0;
        while(inFile.hasNext() && counter < 4)
        {
            answerSet[counter] = inFile.nextLine();
            counter++;
        }
        correctAnswer = inFile.nextInt();
        inFile.close();
    }
	
	// a group of getter and setter methods which return various variables ot store them
	public String getQuestion ( )
	{
		return "" + questionCount + ". " + question;
	}
	
	public String getAnswer(int index)
	{
		return answerSet[index];
	}
	
	public int getCorrectAnswer ( )
	{
		return correctAnswer;
	}
	
	public int getQuestionCount ( )
	{
		return questionCount;
	}
	
	public int getCorrectCount ( )
	{
		return lastGameCorrectCount;
	}
	
	public void addOneToCorrectCount ( )
	{
		correctCount++;
	}
	
	// based on the amount of questions user got correct, returns a message
	public String toString ( )
	{
		if(lastGameCorrectCount >= 9)
		{
			return "Congratulations, you answered " + lastGameCorrectCount +
				" out of 10 of the questions correctly.  Your name will be added to the list of high scores, shown to the right.  Good work!";
		}
		return "Good try, you answered " + lastGameCorrectCount +
			" out of 10 of the questions correctly.  Keep working at it, and maybe next time your name will be added to the list of high scores!";
	}
	
	// simply reads the high score files and saves everything line by line to be displayed
	public String getHighScores ( )
	{
		String result = "";
		String fileName = "highScores.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
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
		while(inFile.hasNext()) 
		{
			String line = inFile.nextLine();
			result += line + "\n";
		}
		return result;
	}
	
	// displayes result from the method above (everything in high score txt)
	public void saveToHighScores ( )
	{
		if(lastGameCorrectCount >= 9)
		{
			String result = "";
			boolean hasBeenAdded = false;
			String fileName = "highScores.txt";
			Scanner inFile = null;
			File inputFile = new File(fileName);
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
			while(inFile.hasNext()) 
			{
				String line = inFile.nextLine();
				if(!hasBeenAdded && Integer.parseInt("" + line.charAt(line.indexOf("/") - 1)) <= lastGameCorrectCount)
				{
					result += lastGameCorrectCount + "/10\n";
					hasBeenAdded = true;
				}
				result += line + "\n";
			}
			if(!hasBeenAdded)
			{
				result += lastGameCorrectCount + "/10\n";
			}
			inFile.close();
			
			File ioFile = new File("highScores.txt");
			PrintWriter outFile = null;
			try
			{
				outFile = new PrintWriter(ioFile);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			outFile.print(result);
			outFile.close();
		}
	}
}
