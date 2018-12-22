package game;

import java.awt.*;

/**
 * A new moving Juan Deager, which follows the users mouse until placed, and then shoots at enemies
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class JuanDeagerMoving extends Tower{

    /**
     * Create a new moving Juan Deager
     *
     * @param gs the current gamestate
     */
    public JuanDeagerMoving(GameState gs){
        super(gs);
        range = 200.0D;
        fireRate = 20;
        cost = 200;
    }
    /**
     * Draw the Deager image at its current location, along with a circle to show its range
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
            g.fillOval((int) location.getX() - 200, (int) location.getY() - 200, 400, 400);
            g.setColor(Color.black);
        }

        //Note: The image is offset from the mouse location, this is so that the image is drawn with the cursor at the center
        g.drawImage(ResourceLoader.getLoader().getImage("JuanDeager.png"), (int) location.getX() - 50, (int) location.getY() - 50,83, 100, null);
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
