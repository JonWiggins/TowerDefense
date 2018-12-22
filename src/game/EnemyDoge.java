package game;

import java.awt.*;

/**
 * An Enemy Doge moves down the path, removing itself and a life when it reaches the end
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/24/2017
 */
public class EnemyDoge extends Enemy {
    /**
     * Create a new Enemy Doge object
     *
     * @param gamePath  the path of the game
     * @param gameState the current gamestate
     */
    public EnemyDoge(Path gamePath, GameState gameState) {
        //initializes variables
        super(gamePath, gameState);
        speed = 0.002D;
        score = 2;
    }

    /**
     * Draw  at its point on the path
     * @param g a graphics object for the panel
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(ResourceLoader.getLoader().getImage("doge.png"), (int) getPosition().getX() - 23, (int) getPosition().getY() - 25, null);
    }
}
