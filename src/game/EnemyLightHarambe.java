package game;

import java.awt.*;

/**
 * An Enemy Light Harambe moves down the path, removing itself and a life when it reaches the end
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/14/2017
 */
public class EnemyLightHarambe extends Enemy {

    /**
     * Create a new Enemy Light Harambe object
     *
     * @param gamePath the path of the game
     * @param gameState the current gamestate
     */
    public EnemyLightHarambe(Path gamePath, GameState gameState){
        //initializes variables
        super(gamePath, gameState);
        speed = 0.002D;
        score = 2;
    }

    /**
     * Draw this light Harambe at its point on the path
     * @param g the graphics object for the current panel
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(ResourceLoader.getLoader().getImage("AngelHarambe.png"), (int) getPosition().getX() - 25, (int) getPosition().getY() - 19, null);
    }


}
