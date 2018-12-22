package game;

import java.awt.*;

/**
 * A menu item to display the tower before it has been placed on the game board
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/17/2017
 */
public class JuanDeagerMenu extends Tower {

    /**
     * Create a Juan Deager to display on the menu
     *
     * @param gs the current gamestate
     */
    public JuanDeagerMenu(GameState gs){
        super(gs);
        cost = 200;
    }


    /**
     * Continually check to see if the Deager menu item has been clicked, if it has, check the required
     * credits and create an instance of the moving zookeeper
     */
    @Override
    public void update() {

        //see if the click was on the menu item
        if (gameState.getMousePressed()
                && gameState.getMousePos().getX() > 710 && gameState.getMousePos().getX() < 840
                && gameState.getMousePos().getY() > 245 && gameState.getMousePos().getY() < 385) {
            //the menu item has been pressed
            //check to see if the user has enough credits to purchase the tower
            if (gameState.getCredits() >= cost) {
                //generate the tower, clear the mouse event, and deduct the credits
                gameState.addAnimatable(new JuanDeagerMoving(gameState));
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
        //Draws the Juan Deager image at the center of its menu box
        g.drawImage(ResourceLoader.getLoader().getImage("JuanDeager.png"), 730, 250, null);

    }

    /**
     * Gives the location of the item
     *
     * @return the location of the menu item as a Point
     */
    @Override
    public Point getPosition() {
        return new Point(730, 250);

    }
}
