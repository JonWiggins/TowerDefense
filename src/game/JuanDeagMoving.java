package game;

import java.awt.*;

/**
 * A new moving Juan Deag, which follows the users mouse until placed, and then shoots at enemies
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/14/2017
 */
public class JuanDeagMoving extends Tower {

    /**
     * Create a new moving zookeeper
     * @param gs the current gamestate
     */
    public JuanDeagMoving(GameState gs){
        super(gs);
        range = 150.0D;
        fireRate = 40;
        cost = 100;
    }


    /**
     * Draw the Deag image at its current location, as well as a circle to show its range
     */
    @Override
    public void draw(Graphics g) {
        //If the tower is being dragged, display a circle around it showing its range to the user
        // The circle will be red if the location is invalid (over the menu or path)
        if(isBeingDragged) {
            if(gameState.checkLocation(this)){
                g.setColor(new Color(50,50,50,127));

            }else {
                g.setColor(new Color(255,50,50,127));
            }
            g.fillOval((int) location.getX() - 150, (int) location.getY() - 150, 300, 300);
            g.setColor(Color.black);
        }
        //Note: The image is offset from the mouse location, this is so that the image is drawn with the cursor at the center
        g.drawImage(ResourceLoader.getLoader().getImage("JuanDeag.png"), (int) location.getX() - 50, (int) location.getY() - 50,83, 100, null);
    }

    /**
     * Get the location of the Zookeeper
     * @return the location as a Point object
     */
    @Override
    public Point getPosition() {
        return location;
    }
}
