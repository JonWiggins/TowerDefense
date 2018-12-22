package game;

import java.awt.*;

/**
 * Outlines an Enemy object
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public abstract class Enemy implements Animatable {
    private double traveled; //denotes how far the enemy has traveled down the path (as a percent)
    private Path gameP; //holds the game path
    public GameState game; //holds a reference to the gamestate
    double speed; //denotes the speed of the enemy
    int score; //denotes how many score is given for removing the enemy

    /**
     * Create a new Enemy object
     *
     * @param gamePath the path of the game
     * @param gameState the current gamestate
     */
    public Enemy(Path gamePath, GameState gameState){
        traveled = 0.0D; //set the enemy to have traveled 0% down the path
        gameP = gamePath;
        game = gameState;
    }

    /**
     * Get the current position of this Light Harambe object
     * @return the current location as a Point
     */
    public Point getPosition() {
        return gameP.getPathPosition(traveled); //get the location
    }

    /**
     * Returns the percent of the map that has been traveled as a double
     *
     * @return percent traveled as a double
     */
    public double getTraveled(){
        return traveled;
    }


    /**
     * Update the position of this Enemy by it's speed
     * If it has reached the end of the path, remove this enemy and one life
     */
    public void update() {
        // Advance the Enemy by it's speed
        //   along the path, and redraw the screen.

        //if it is past the end, remove one life, and this instance of the Enemy
        if (traveled > 1.0D) {
            //take away one life
            game.adjustLives(-1);
            game.removeAnimatable(this);
        } else
            //otherwise, advance
            traveled += speed;
    }

    /**
     * Get the score awarded for killing this enemy
     * @return the score as an int
     */
    public int getScore(){
        return score;
    }

}
