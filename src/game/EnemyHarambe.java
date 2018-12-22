package game;

import java.awt.*;

/**
 * An Enemy Harambe moves down the path, removing itself and a life when it reaches the end
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class EnemyHarambe extends Enemy {

    /**
     * Create a new Enemy Harambe object
     *
     * @param gamePath the path of the game
     * @param gameState the current gamestate
     */
    public EnemyHarambe(Path gamePath, GameState gameState){
        //initializes variables
        super(gamePath, gameState);
        speed = 0.001D;
        score = 1;
    }

    /**
     * Draw Harambe at its point on the path
     * @param g a graphics object for the panel
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(ResourceLoader.getLoader().getImage("Harambe.png"), (int) getPosition().getX() - 18, (int) getPosition().getY() - 22, null);
    }

}
