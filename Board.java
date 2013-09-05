package Snake;

//import needed libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener
{
	//set width and height of window
	private final int WIDTH = 300;
	private final int HEIGHT = 300;
	//set size of objective dot
	private final int DOT_SIZE= 10;
	//
	private final int ALL_DOTS = 900;
	//set delay to 140 ms
	private final int RAND_POS = 29;
	private final int DELAY = 60;
	//
	private int x[] = new int[ALL_DOTS];
	private int y[] = new int[ALL_DOTS];
	//
	private int dots;
	private int apple_x;
	private int apple_y;
	//
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;
	//
	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	
	//
	public Board()
	{
		addKeyListener(new TAdapter());
		//set gameboard background to black
		setBackground(Color.BLACK);
		//calls image 'dot.png' as the ball
		ImageIcon iid =new ImageIcon(this.getClass().getResource("dot.png"));
		ball = iid.getImage();
		//call image 'apple.png' as the objective
		ImageIcon iia =new ImageIcon(this.getClass().getResource("apple.png"));
		apple = iia.getImage();
		//call image 'head.png' as the snake head
		ImageIcon iih =new ImageIcon(this.getClass().getResource("head.png"));
		head = iih.getImage();
		
		//
		setFocusable(true);
		initGame();
	}
	
	public void initGame()
	{
		//sets starting number of dots on snake to 3
		dots = 3;
		
		for (int z= 0; z < dots; z++)
		{
			x[z] = 50-z*10;
			y[z] = 50;
		}
		
		locateApple();
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if (inGame)
		{
			g.drawImage(apple, apple_x, apple_y, this);
			
			for (int z = 0; z < dots; z++)
			{
				if (z == 0)
					g.drawImage(head, x[z], y[z], this);
				else g.drawImage(ball,  x[z], y[z], this);
			}
			
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
			
		}
		
		else
		{
			//call the game over function
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g)
	{
		String msg = "Game Over";
		//set font of 'Game Over' text
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		//set font color to white #FFFFFF
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
		
	}
	
	public void checkApple()
	{
		if ((x[0] == apple_x) && (y[0] == apple_y))
		{
			dots++;
			locateApple();
		}
	}
	
	
	
	public void move()
	{
		for (int z = dots; z > 0; z--)
		{
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}
		
		if (left)
		{
			x[0] -= DOT_SIZE;
		}
		
		if (right)
		{
			x[0] += DOT_SIZE;
		}
		
		if (up)
		{
			y[0] -= DOT_SIZE;
		}
		
		if (down)
		{
			y[0] += DOT_SIZE;
		}
	}
	
	public void checkCollision()
	{
		for (int z = dots; z> 0; z--)
		{
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z]))
			{
				inGame = false;
			}
		}
		
		if (y[0] > HEIGHT)
		{
			inGame = false;
		}
		
		if (y[0] < 0)
		{
			inGame = false;
		}
		
		if (x[0] > WIDTH)
		{
			inGame = false;
		}
		
		if ( x[0] < 0)
		{
			inGame = false;
		}
	}
	
	
	public void locateApple()
	{
		int r = (int) (Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));
		r = (int) (Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (inGame)
		{
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}
	
	
	private class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			//sets left arrow to move snake left
			if ((key == KeyEvent.VK_LEFT) && (!right))
			{
				left = true;
				up = false;
				down = false;
			}
			//sets right arrow to move snake right
			if ((key == KeyEvent.VK_RIGHT) && (!left))
			{
				right = true;
				up = false;
				down = false;
			}
			//set up arrow to move snake up
			if ((key == KeyEvent.VK_UP) && (!down))
			{
				up = true;
				right = false;
				left = false;
			}
			//set down arrow to move snake down
			if ((key == KeyEvent.VK_DOWN) && (!up))
			{
				down = true;
				right = false;
				left = false;
			}
		}
	}
	
	
}