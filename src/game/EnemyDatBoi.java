package game;

import java.awt.*;

/**
 * An Enemy Dat Boi moves down the path, removing itself and a life when it reaches the end
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class EnemyDatBoi extends Enemy {

    /**
     * Create a new Enemy Dat Boi object
     *
     * @param gamePath  the path of the game
     * @param gameState the current gamestate
     */
    public EnemyDatBoi(Path gamePath, GameState gameState) {
        //initializes values
        super(gamePath, gameState);
        speed = 0.004D;
        score = 4;
    }

    /**
     * Draw Dat Boi at its point on the path
     * @param g a graphics object for the panel
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(ResourceLoader.getLoader().getImage("DatBoi.png"), (int) getPosition().getX() - 18, (int) getPosition().getY() - 30, null);
    }
}
