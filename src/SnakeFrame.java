import javax.swing.JFrame;

// this is the Jframe class or main class thats builds the frame of the program

public class SnakeFrame extends JFrame{
//Constructor for the Frame
	SnakeFrame(){
		// Sets the Frame to the Panel 
		this.add(new SnakePanel()); 
		this.setTitle("SNAKE");
		this.pack();
		this.setVisible(true);
	}
}