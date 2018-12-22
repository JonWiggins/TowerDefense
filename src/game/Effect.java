package game;

import java.awt.*;

/**
 * Outlines Effect objects
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public abstract class Effect implements Animatable{
    Point location; //denotes the location of the effect
    int frames; //holds framecounter
    GameState gameState; //hold refrence to GameState


    /**
     * Returns the position of the effect
     *
     * @return the position as a point
     */
    @Override
    public Point getPosition() {
        return location;
    }
}
