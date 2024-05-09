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
	public Image lavaMonsterPic;
	public Image meteoritePic;
	public Image astronautPic;
	public Image gameoverPic;
	public Image winPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	public Fireball[] aFireball;
	private Fireball fireball;
	public LavaMonster[] aLavaMonster;
	private LavaMonster lavaMonster;
	private Meteorite meteorite;
	private Astronaut astronaut;
	public int score = 0;
//
   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}

	public void Display(){
		System.out.println("Hit the Space Bar to start, and immediately use the arrow keys to move the astronaut from the bottom to top of the screen by avoiding the fireballs, lava monsters, and ");
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
		lavaMonsterPic = Toolkit.getDefaultToolkit().getImage("LavaMonster.png"); //load the picture
		meteoritePic = Toolkit.getDefaultToolkit().getImage("Meteorite.png"); //load the picture
		background = Toolkit.getDefaultToolkit().getImage("Background.png"); //load the picture
		gameoverPic = Toolkit.getDefaultToolkit().getImage("GameOver.png"); //load the picture
		winPic = Toolkit.getDefaultToolkit().getImage("Win.png"); //load the picture
		astronautPic = Toolkit.getDefaultToolkit().getImage("Astronaut.png"); //load the picture

		fireball = new Fireball(400,1000,100,100,10);
		aFireball = new Fireball[5];
		for(int i = 0; i < aFireball.length; i++){
			aFireball[i] = new Fireball((int)(Math.random() * 1000), (int) (Math.random()* 700), 50, 100, 10);
		}
			lavaMonster = new LavaMonster(200,1,75,100,10,10);
		aLavaMonster = new LavaMonster[2];
		for(int j = 0; j < aLavaMonster.length; j++){
			aLavaMonster[j] = new LavaMonster((int)(Math.random() * 1000), (int) (Math.random()* 700), 50, 100, 2, 2);
		}
		meteorite = new Meteorite(400,100,50,75,15,15);
		astronaut = new Astronaut(WIDTH/2,500,63,63,10,10);

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
		for(int i = 0; i < aFireball.length; i++){
			aFireball[i].move();
		}
		for(int i = 0; i < aLavaMonster.length; i++){
			aLavaMonster[i].move();
		}
		fireball.move();
		lavaMonster.move();
		meteorite.move();
		astronaut.move();
	}

	public void checkIntersections(){
		if(fireball.rec.intersects(astronaut.rec)){
			fireball.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(meteorite.rec.intersects(astronaut.rec)){
			meteorite.isAlive = false;
			score +=1;
			System.out.println("Score: " + score);
		}
		if(lavaMonster.rec.intersects(astronaut.rec)){
			lavaMonster.isAlive = false;
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
	//	if(fireball.isAlive == false){

			if(score >= 5){
				g.drawImage(gameoverPic,0,0,1000,1000, null);
			}else {
			if(astronaut.ypos == 1){
					g.drawImage(winPic,0,0,1000,1000, null);
			}else{
					g.drawImage(background,0,0,1000,1000, null);
					g.drawImage(fireballPic, fireball.xpos, fireball.ypos, fireball.width, fireball.height, null);
					g.drawImage(lavaMonsterPic, lavaMonster.xpos, lavaMonster.ypos, lavaMonster.width, lavaMonster.height, null);
					g.drawImage(meteoritePic, meteorite.xpos, meteorite.ypos, meteorite.width, meteorite.height,null);
					g.drawImage(astronautPic, astronaut.xpos, astronaut.ypos, astronaut.width, astronaut.height,null);

					for(int i = 0; i < aFireball.length; i++){
						g.drawImage(fireballPic, aFireball[i].xpos, aFireball[i].ypos, aFireball[i].width, aFireball[i].height, null);
					}

					for(int i = 0; i < aLavaMonster.length; i++){
						g.drawImage(lavaMonsterPic, aLavaMonster[i].xpos, aLavaMonster[i].ypos, aLavaMonster[i].width, aLavaMonster[i].height, null);
					}
					g.setColor(Color.ORANGE);
					g.fillRect(715,5,200,200);
					g.setColor(Color.BLACK);
					g.drawString("SCORE: " + score,725,20);
					g.drawString("Hit any arrow key and then",725,40);
					g.drawString("the spacebar to start the game!",725,60);
					g.drawString("Use the arrow keys to move up",725,80);
					g.drawString("the screen to the top by ",725,100);
					g.drawString("avoiding the objects, and",725,120);
					g.drawString("then attempt to go back to",725,140);
					g.drawString("the bottom! The game is never",725,160);
					g.drawString("ending unless you hit an object!",725,180);
				}
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
		if(e.getKeyCode() == 32){
			astronaut.xpos = 500;
			astronaut.ypos = 615;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
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