package game;

import java.awt.*;

/**
 * Show a hitmarker effect at a given point for 5 frames
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class Hitmarker extends Effect {


    /**
     * Create a new hitmarker to be drawn at a given point
     *
     * @param location the location for a hitmarker to be shown, given as a Point
     * @param gameState a reference to the current gamestate
     */
    public Hitmarker(Point location, GameState gameState){
        this.location = location;
        this.gameState = gameState;
        frames = 0;
    }

    /**
     * Remove the hitmarker when it has existed for 5 frames
     */
    @Override
    public void update() {
        //remove the hitmarker when it is 5 frames old
        if(frames > 5)
            gameState.removeAnimatable(this);
        //otherwise, just increment the framecounter
        frames++;

    }

    /**
     * Draw the hitmarker at the given point
     * @param g the graphics object
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(ResourceLoader.getLoader().getImage("HITMARKER.png"),(int) location.getX() - 13,(int) location.getY() - 13, null);
    }


}
