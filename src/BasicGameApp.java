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
	public Image astroPic;
	public Image background;
	public Image alienPic;
	public Image meteoritePic;

	public Image rocketPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	public Astronaut[] aAstro;
	private Astronaut astro;
	private Alien alien;
	private Meteorite meteorite;
	private Rocket rocket;
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
		astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
		alienPic = Toolkit.getDefaultToolkit().getImage("download.png"); //load the picture
		meteoritePic = Toolkit.getDefaultToolkit().getImage("download.jpg"); //load the picture
		rocketPic = Toolkit.getDefaultToolkit().getImage("Unknown.png"); //load the picture
		background = Toolkit.getDefaultToolkit().getImage("Space-Background-Images.jpg"); //load the picture
		astro = new Astronaut(400,100,100,100,10,10);
		aAstro = new Astronaut[500];
		for(int i = 0; i < 500; i++){
			aAstro[i] = new Astronaut((int)(Math.random() * 500), (int) (Math.random()* 400), 50, 100, 4, 5);
		}
		alien = new Alien(50,40,50,50,10,10);
		meteorite = new Meteorite(400,100,50,75,10,10);
		rocket = new Rocket(100,40,50,50,10,10);

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
			aAstro[i].move();
		}
		astro.move();
		alien.move();
		meteorite.move();
		rocket.move();
	}

	public void checkIntersections(){
		if(astro.rec.intersects(alien.rec)){
			astro.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(astro.rec.intersects(meteorite.rec)){
			astro.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(alien.rec.intersects(meteorite.rec)){
			astro.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(astro.rec.intersects(rocket.rec)){
			astro.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(rocket.rec.intersects(meteorite.rec)){
			astro.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(alien.rec.intersects(rocket.rec)){
			astro.isAlive = false;
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
		if(astro.isAlive == false){
			g.drawImage(background,0,0,1000,1000, null);
			g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
			g.drawImage(alienPic, alien.xpos, alien.ypos, alien.width, alien.height, null);
			g.drawImage(meteoritePic, meteorite.xpos, meteorite.ypos, meteorite.width, meteorite.height,null);
			g.drawImage(rocketPic, rocket.xpos, rocket.ypos, rocket.width, rocket.height,null);

			for(int i = 0; i < 500; i++){
				g.drawImage(astroPic, aAstro[i].xpos, aAstro[i].ypos, aAstro[i].width, aAstro[i].height, null);
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
			astro.isAlive = false;
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
			rocket.dx = -10;
			rocket.dy = 0;
		}
		if(e.getKeyCode() == 38){
			rocket.dx = 0;
			rocket.dy = -10;
		}
		if(e.getKeyCode() == 39){
			rocket.dx = 10;
			rocket.dy = 0;
		}
		if(e.getKeyCode() == 40){
			rocket.dx = 0;
			rocket.dy = 10;
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
			rocket.dx = 0;
			rocket.dy = 0;
		}
		if(e.getKeyCode() == 38){
			rocket.dx = 0;
			rocket.dy = 0;
		}
		if(e.getKeyCode() == 39){
			rocket.dx = 0;
			rocket.dy = 0;
		}
		if(e.getKeyCode() == 40){
			rocket.dx = 0;
			rocket.dy = 0;
		}
	}
}