// Shivank Hali
// 05-19-22
// WorldLook.java
// Everything done by shivank 

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
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
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class WorldLook extends JPanel
{
	private Font font;  // most fonts are the same, so there is one
	private PictPanel pp; // the variables in the RightControlPanel2 need access to use repaint
	private WorldLookPanel instance; // instance of world look panel, which will be used to allow the handlers to work
	private int width, height; // dimentions of image
	private int widthOfImage;  // stores the width of each image
	private int heightOfImage;  // stores the height of each image
	private double scaleFactor; // number that the pixels are multiplied by to get distance
	
	// holds 3 classes and ads them using borderlayout
	public WorldLook(WorldLookPanel instanceIn, double scaleFactorIn)
	{		
		scaleFactor = scaleFactorIn;
		instance = instanceIn;
		setLayout(new BorderLayout());
		GetAndSet gAs = new GetAndSet();
		RightControlPanel rcp = new RightControlPanel(gAs);
		add(rcp, BorderLayout.EAST);
		LowerControlPanel lcp = new LowerControlPanel(gAs);
		add(lcp, BorderLayout.SOUTH);
		pp = new PictPanel(gAs, lcp, scaleFactor);
		add(pp, BorderLayout.CENTER);
	}
	
	class PictPanel extends JPanel implements MouseListener
	{
		private Image image;
		private boolean preReq;
		private int counter, firstX, firstY, secondX, secondY;
		private GetAndSet gAs;
		private LowerControlPanel lcp;
		private double scaleFactor;
		
		// shows the picture of the world map
		public PictPanel(GetAndSet getAndSetIn, LowerControlPanel lcpIn, double scaleFactorIn)
		{	
			scaleFactor = scaleFactorIn;
			lcp = lcpIn;
			gAs = getAndSetIn;
			counter = firstX = firstY = secondX = secondY = 0;
			
			addMouseListener(this);
			
			setLayout(new BorderLayout());
			font = new Font("Serif", Font.BOLD, 20);
			
			image = getMyImage("WorldMap.gif"); 
			widthOfImage = image.getWidth(this);
			heightOfImage = image.getHeight(this);	
			
			width = 1000;
			height = 595; 
				
		}
		
		//stores where mouse clicked twice to get the distane between those points
		// sends the cords of points clicked to get my distance method for it to be processed
		public void mousePressed(MouseEvent evt)
		{
			requestFocusInWindow();
			preReq = gAs.getter();
			
			counter++;
			if((counter == 1) && (preReq))
			{
				firstX = evt.getX();
				firstY = evt.getY();
			}
			else if((counter == 2) && (preReq))
			{
				secondX = evt.getX();
				secondY = evt.getY();
				counter = 3;
				getMyDistance(firstX, firstY, secondX, secondY);
				lcp.srpCaller();
				
			}
		}
		
		// based on  scale factor, multiplies disatnce by a number to get distance in miles/kms
		// sets distance in getter and setter class 
		public void getMyDistance(int x1, int y1, int x2, int y2)
		{
			if(scaleFactor == 19.0)
			{
				int num = (int)(19*(Math.sqrt((secondY - firstY) * (secondY - firstY) + (secondX - firstX) * (secondX - firstX))));
				gAs.setterDis("The distance between those 2 places is around " + num + " miles.");
			}
			else
			{
				int num = (int)(30.36*(Math.sqrt((secondY - firstY) * (secondY - firstY) + (secondX - firstX) * (secondX - firstX))));
				gAs.setterDis("The distance between those 2 places is around " + num + " kilometers.");
			}
		}
		
		public void mouseReleased(MouseEvent evt){}
		public void mouseClicked(MouseEvent evt) {}
		public void mouseEntered(MouseEvent evt) {}
		public void mouseExited(MouseEvent evt) {}
		public void mouseMoved(MouseEvent evt) {}
			
		// polymorphic method to load images
		// uses try - catch to load, then returns that image
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
			
			return picture;
		}
		
		// draws map
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(image, 0, 0, width, height, this);
		}
	}	
	
	class RightControlPanel extends JPanel 
	{
		private GetAndSet gAs;
		
		public RightControlPanel(GetAndSet getAndSetIn)
		{
			gAs = getAndSetIn;
			setLayout(new BorderLayout());
			setBackground(Color.CYAN);
			setPreferredSize(new Dimension(200,0));
			RPanel rp = new RPanel(gAs);
			add(rp, BorderLayout.CENTER);
		}
	
		class RPanel extends JPanel implements MouseListener
		{			
			GetAndSet gAs;
			public RPanel(GetAndSet getAndSetIn)
			{
				gAs = getAndSetIn;
				setBackground(Color.RED);
				addMouseListener(this);
				requestFocusInWindow();		

			}
			
			// will only allow the distance to be shown after clicking on red
			// panel to make it green
			public void mousePressed(MouseEvent evt)
			{
				setBackground(Color.GREEN);
				GetAndSet setTrue = new GetAndSet();
				gAs.setter(true);
			}
				
			public void mouseReleased(MouseEvent evt){}
			public void mouseClicked(MouseEvent evt) {}
			public void mouseEntered(MouseEvent evt) {}
			public void mouseExited(MouseEvent evt) {}
			public void mouseMoved(MouseEvent evt) {}
			
		}
	}
	
	
	class LowerControlPanel extends JPanel
	{
		private GetAndSet gAs; //instance of GetAndSet to allow calling getter
		private SouthOfLPanel srp;  // instance of SouthOfLPanel, which is added to center of borderlayout
		
		public LowerControlPanel(GetAndSet getAndSetIn)
		{
			gAs = getAndSetIn;
			setLayout(new BorderLayout());
			setBackground(Color.CYAN);
			setPreferredSize(new Dimension(50,105));
			srp = new SouthOfLPanel(gAs);
			add(srp, BorderLayout.CENTER);
		}
		
		// calls repaint in SouthOfLPanel
		public void srpCaller()
		{
			srp.repaint();
		}
	
		class SouthOfLPanel extends JPanel
		{
			private GetAndSet gAs; //instance of GetAndSet to allow calling getter
			private String tfDisText; // string that will be stores in distance
			
			public SouthOfLPanel(GetAndSet getAndSetIn)
			{
				setLayout(new BorderLayout());
				gAs = getAndSetIn;
				setBackground(Color.CYAN);
				JButton goBackToGame = new JButton("Back To Game");
				goBackToGame.setPreferredSize(new Dimension(200,20));
				goBackToGame.addActionListener(instance);
				add(goBackToGame, BorderLayout.EAST);
			}
			
			//displays distance (only once)s
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				tfDisText = gAs.getterDis();
				JLabel distance = new JLabel(tfDisText);
				distance.setPreferredSize(new Dimension(100,20));
				distance.setBackground(Color.CYAN);
				add(distance, BorderLayout.CENTER);
				revalidate();
			}
		}
	}
	// getter and setter class
	class GetAndSet
	{
		private boolean preReq;
		private String disString;
		
		public void setter(boolean preReqIn)
		{
			preReq = preReqIn;
		}
		public boolean getter()
		{
			return preReq;
		}
		public void setterDis(String disStringIn)
		{
			disString = disStringIn;
		}
		public String getterDis()
		{
			return disString;
		}
	}
}
