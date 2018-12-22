package game;

import java.awt.*;

/**
 * A menu item to display the tower before it has been placed on the game board
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/14/2017
 */
public class JaunDeagMenu extends Tower {

    /**
     * Create a Juan Deag to display on the menu
     *
     * @param gs the current gamestate
     */
    public JaunDeagMenu(GameState gs) {
       super(gs);
       cost = 100;
    }


    /**
     * Continually check to see if the Deag menu item has been clicked, if it has, check the required
     * credits and create an instance of the moving zookeeper
     */
    @Override
    public void update() {
        //see if the click was on the menu item
        if (gameState.getMousePressed()
                && gameState.getMousePos().getX() > 710 && gameState.getMousePos().getX() < 840
                && gameState.getMousePos().getY() > 100 && gameState.getMousePos().getY() < 240) {
            //the menu item has been pressed
            //check to see if the user has enough credits to purchase the tower
            if (gameState.getCredits() >= cost) {
                //generate the tower, clear the mouse event, and deduct the credits
                gameState.addAnimatable(new JuanDeagMoving(gameState));
                gameState.clearMousePressed();
                gameState.adjustCredits(cost * -1);
            }
        }
    }

    /**
     * Draw the menu item
     *
     * @param g the graphics object for drawing on this panel
     */
    @Override
    public void draw(Graphics g) {
        //Draws the Juan Deag image at the center of its menu box
        g.drawImage(ResourceLoader.getLoader().getImage("JuanDeag.png"), 730, 105, null);

    }

    /**
     * Gives the location of the item
     *
     * @return the location of the menu item as a Point
     */
    @Override
    public Point getPosition() {
        return new Point(730, 105);
    }
}
