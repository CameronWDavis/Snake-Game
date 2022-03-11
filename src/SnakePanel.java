//@author Cdavis
//imports swing random and awt to create graphics 


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
//main class extends ActionListener (Action listener is used to show button is pressed) 
public class SnakePanel extends JPanel implements ActionListener{ 
	//the first two global variables set the screen size
	static final int WIDTH = 800;
	static final int HEIGHT = 800;
	// the block is the space in the grid for the snake game 
	static final int BLOCK = 25; 
	static final int GRID_BLOCKS = (WIDTH * HEIGHT)/BLOCK;
	// how fast the game is running 
	static final int gameRun = 80; 
	final int x[] = new int [GRID_BLOCKS];
	final int y[] = new int [GRID_BLOCKS]; 
	// length of snake to start 
	int snakeLength = 4; 
	//global variables I set for the rest of the program 
	int snakeGrowth;
	int appleX, appleY;  
	char point = 'R'; 
	// the start is set to true or false IE False will not run true it will 
	boolean start = false; 
	Timer timer;
	Random random; 
	
	// this is my constructor method it creates the size of hte screen and constructs the variables to be passed down 
	public SnakePanel() { 
	random = new Random(); 
	this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	this.setBackground(Color.blue); 
	this.setFocusable(true); 
	this.addKeyListener(new MyKeyAdapter()); 
	// start game method is called once all things are set
	startGame(); 
	}
	// timer begings which just means game starts and apple method is called to draw apple to the screen 
	public void startGame() {
		createApple();
		start = true; 
		timer = new Timer(gameRun,this);
		timer.start(); 
	}
	// this method supers the paintcoments and graphic library (Literally nothing will print to screen without this) 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		if(start) {
			
		// Draws a grid to the screen
		for (int i = 0; i < HEIGHT/BLOCK;i++) {
			g.drawLine(i*BLOCK,0,i*BLOCK,HEIGHT); 
			g.drawLine(0,i*BLOCK,WIDTH, i*BLOCK); 
		}
		g.setColor(Color.orange); 
		g.fillOval(appleX, appleY, BLOCK , BLOCK);
		
		// when snake eats a apple length grows by 1 
		
		for(int i = 0; i < snakeLength; i++) {
			if(i == 0) {
				g.setColor(Color.magenta); 
				g.fillRect(x[i],y[i], BLOCK , BLOCK); 
			}
			else {
				g.setColor(new Color(138,43,226)); 
				g.fillRect(x[i],y[i],BLOCK, BLOCK); 
			}
		}
		}else {
			gameTermination(g); 
		} 
		}
	//will randomly generate a apple on one of the grid spaces 
	public void createApple() {
		appleX = random.nextInt((int)(WIDTH/BLOCK))*BLOCK; 
		appleY=random.nextInt((int)(WIDTH/BLOCK))*BLOCK; 
       
	}
	// using chars to set the direction the snake will go and how it will interact 
	public void movement() {
		for(int i = snakeLength; i > 0; i--) {
			x[i] = x[i -1]; 
			y[i] = y[i-1]; 
		}
		switch(point) { 
		case 'U':
			y[0] = y[0] - BLOCK; 
			break; 
		case 'D':
			y[0] = y[0] +BLOCK; 
			break; 
		case 'L':
			x[0] = x[0] - BLOCK;
			break; 
		case 'R':
			x[0] = x[0] + BLOCK; 
			break; 
		}
	}
	// if the snake hits the apple object will increase by 1 and score will also grow by one 
	public void hitApple() { 
		if((x[0] == appleX) && (y[0] == appleY)) {
			snakeLength++;
			snakeGrowth++; 
			createApple(); 
		}
	}
	public void hitWall() {
		// if head touches body game will end 
		for(int i = snakeLength; i > 0; i--) {
			if ((x[0] == x[i])&&(y[0] == y[i] )) {
				start = false; 
			}
			}
		//checks if snake touches right wall
		if(x[0] > WIDTH) {
			start = false;
		}
		
		//if snake hits left wall
		if(x[0] < 0) {
			start = false; 
		}
		// if snake hits top border
		if(y[0] < 0) {
			start = false; 
		}
		//if snake hits bottom wall
		if(y[0] > HEIGHT) {
			start = false; 
				}
		if(!start) {
			timer.stop(); 
		}
		
		
	}
	
	
	public void gameTermination(Graphics g) { 
		// when the game ends this text displays method is called
		// when start = false
		//shows the user there score
		g.setColor(Color.green); 
		g.setFont(new Font("TimesRoman",Font.BOLD,80)); 
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + snakeGrowth, (WIDTH - metrics.stringWidth("Score: " + snakeGrowth)) / 2,
				g.getFont().getSize()); 
		// game over text 
		g.setColor(Color.pink);
		g.setFont( new Font("TimesRoman", Font.BOLD, 80)); 
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over ;(", (WIDTH - metrics2.stringWidth("Game Over"))/2, HEIGHT/2);
	}
	
	
	// this is a action event as part of the interface monitors what will happen and how it happens 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(start) {
			movement(); 
			hitApple();
			hitWall(); 
		}
		repaint(); 
	}
	
	//key adaptor method sets the arrow keys to allow movment of the snake around the map
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				if(point != 'D') { 
					point = 'U'; 
				}
				break;
			case KeyEvent.VK_A:
				if(point != 'R') {
					point = 'L'; 
				}
				break;
			case KeyEvent.VK_S:
				if(point != 'U') { 
					point = 'D'; 
				}
				break;
			case KeyEvent.VK_D:
				if(point != 'L') {
					point = 'R'; 
				}
				break;
				
				
			}
			
		}
	}
}
