import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Fireball {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Fireball(int pXpos, int pYpos, int pHeight, int pWidth, int pDx) {
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = 0;
        width = pWidth;
        height = pHeight;
        rec = new Rectangle(xpos, ypos, height, width);
        isAlive = true;
 
    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        rec = new Rectangle(xpos, ypos, height, width);

        xpos = xpos + dx;
        ypos = ypos+ dy;
//        if (xpos >= 990) {
//            dx = -10;
//            //width = width +20;
//            //height = height +20;
//        }
//        if (xpos <= 0) {
//            dx = 10;
//        }
        if(xpos > 990){
            xpos = 0;
            //width = width +20;
            //height = height +20;
        }
        if(xpos<0){
            xpos = 1000;
        }
        if(ypos> 990){
            ypos = 0;
            //width = width +20;
            //height = height +20;
        }
        if(ypos<0){
            ypos = 1000;
        }
        //if(ypos<=0){
        //dy=10;
        //width+=30;
        //height+=30;
        //}
        //if(ypos>=690){
        // dy=-10;
        //width+=30;
        //width+=30;
        //}
    }
}






