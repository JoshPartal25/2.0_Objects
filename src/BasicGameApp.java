//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener{

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image fireballPic;
	public Image background;
	public Image alienPic;
	public Image meteoritePic;

	public Image astronautPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	public Fireball[] aFireball;
	private Fireball fireball;
	private Alien alien;
	private Meteorite meteorite;
	private Astronaut astronaut;
	public int score = 0;

   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		fireballPic = Toolkit.getDefaultToolkit().getImage("Fireball.png"); //load the picture
		alienPic = Toolkit.getDefaultToolkit().getImage("download.png"); //load the picture
		meteoritePic = Toolkit.getDefaultToolkit().getImage("download.jpg"); //load the picture
		astronautPic = Toolkit.getDefaultToolkit().getImage("Astronaut.png"); //load the picture
		background = Toolkit.getDefaultToolkit().getImage("Background.png"); //load the picture
		fireball = new Fireball(400,100,100,100,10,10);
		aFireball = new Fireball[500];
		for(int i = 0; i < 500; i++){
			aFireball[i] = new Fireball((int)(Math.random() * 100), (int) (Math.random()* 80), 50, 100, 4, 5);
		}
		alien = new Alien(50,40,50,50,10,10);
		meteorite = new Meteorite(400,100,50,75,10,10);
		astronaut = new Astronaut(100,40,50,50,10,10);

	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		 checkIntersections();
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		for(int i = 0; i < 500; i++){
			aFireball[i].move();
		}
		fireball.move();
		alien.move();
		meteorite.move();
		astronaut.move();
	}

	public void checkIntersections(){
		if(fireball.rec.intersects(alien.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(fireball.rec.intersects(meteorite.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(alien.rec.intersects(meteorite.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(fireball.rec.intersects(astronaut.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(astronaut.rec.intersects(meteorite.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(alien.rec.intersects(astronaut.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addKeyListener(this);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		if(fireball.isAlive == false){
			g.drawImage(background,0,0,1000,1000, null);
			g.drawImage(fireballPic, fireball.xpos, fireball.ypos, fireball.width, fireball.height, null);
			g.drawImage(alienPic, alien.xpos, alien.ypos, alien.width, alien.height, null);
			g.drawImage(meteoritePic, meteorite.xpos, meteorite.ypos, meteorite.width, meteorite.height,null);
			g.drawImage(astronautPic, astronaut.xpos, astronaut.ypos, astronaut.width, astronaut.height,null);

			for(int i = 0; i < 500; i++){
				g.drawImage(fireballPic, aFireball[i].xpos, aFireball[i].ypos, aFireball[i].width, aFireball[i].height, null);
			}

			g.setColor(Color.ORANGE);
			g.fillRect(715,5,75,20);
			g.setColor(Color.BLACK);
			g.drawString("SCORE: " + score,725,20);
		}
      //draw the image of the astronaut
		g.dispose();

		bufferStrategy.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 13){
			fireball.isAlive = false;
		}
		if(e.getKeyCode() == 68){
			alien.dx = 10;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 87){
			alien.dx = 0;
			alien.dy = -10;
		}
		if(e.getKeyCode() == 65){
			alien.dx = -10;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 83){
			alien.dx = 0;
			alien.dy = 10;
		}
		if(e.getKeyCode() == 37){
			astronaut.dx = -10;
			astronaut.dy = 0;
		}
		if(e.getKeyCode() == 38){
			astronaut.dx = 0;
			astronaut.dy = -10;
		}
		if(e.getKeyCode() == 39){
			astronaut.dx = 10;
			astronaut.dy = 0;
		}
		if(e.getKeyCode() == 40){
			astronaut.dx = 0;
			astronaut.dy = 10;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 68){
			alien.dx = 0;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 87){
			alien.dx = 0;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 65){
			alien.dx = 0;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 83){
			alien.dx = 0;
			alien.dy = 0;
		}
		if(e.getKeyCode() == 37){
			astronaut.dx = 0;
			astronaut.dy = 0;
		}
		if(e.getKeyCode() == 38){
			astronaut.dx = 0;
			astronaut.dy = 0;
		}
		if(e.getKeyCode() == 39){
			astronaut.dx = 0;
			astronaut.dy = 0;
		}
		if(e.getKeyCode() == 40){
			astronaut.dx = 0;
			astronaut.dy = 0;
		}
	}
}