package game;

import java.awt.*;

/**
 * Outline an Animatable Object
 * - Must have an update method
 * - Must have a draw method
 * - Must have a getPosition method
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/14/2017
 */
public interface Animatable  {
    public void update();
    public void draw(Graphics g); //or Graphics2D game
    public Point getPosition();
}
