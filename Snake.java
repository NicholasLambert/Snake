package Snake;

import javax.swing.JFrame;

public class Snake extends JFrame
{
	public Snake()
	{
		add(new Board());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 340);
		setLocationRelativeTo(null);
		setTitle("SNAKE");
		
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Snake();
	}
	
}
