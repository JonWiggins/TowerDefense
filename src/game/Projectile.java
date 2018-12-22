package game;

import java.awt.*;

/**
 * A Projectile moves from a point towards an enemy, and then remove the enemy from the game
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class Projectile extends Effect {

    private Point origin; //denotes the point where the projectile is being sent from
    private Enemy destination; //denotes the enemy object that the projectile is moving towards
    private double traveled; //denotes the percent that the projectile has traveled towards its destination
    private double dx; //denotes the change in x for the projectiles trajectory
    private double dy; //deontes the change in y for the projectiles trajectory
    GameState gameState; //holds a reference to the gamestate

    /**
     *  Create a new projectile moving from a point to an Enemy
     *
     * @param destination the Enemy that the projectile should move toward and remove
     * @param origin the Point where the projectile is originating from
     * @param gameState the GameState
     */
    public Projectile(Animatable destination, Point origin, GameState gameState) {
        //adjust the initial position so it is over the gun and not over the center of the tower
        this.origin = new Point((int) origin.getX() - 60, (int) origin.getY() - 30);
        this.destination = (Enemy) destination;
        this.gameState = gameState;
        traveled = 0;

    }

    /**
     * Advance the projectile 34% in the direction of it's destination
     *  If it has reached or passed the destination, remove it and the Enemy
     *  Also add an amount of credits and score (Gotten from Enemy object)
     */
    @Override
    public void update(){
        // Advance the projectile 20%
        //update trajectory
        dx =  (destination.getPosition().getX() ) - origin.getX() ;
        dy =  (destination.getPosition().getY() ) - origin.getY();
        //if it is past the end, remove it
        if (traveled > 1.0D) {
            //show hitmarker
            gameState.addAnimatable(new Hitmarker(destination.getPosition(),gameState));
            //add to score and get credits
            gameState.adjustCredits(destination.getScore() /2);
            gameState.adjustScore(destination.getScore());
            //remove the enemy and itself
            gameState.removeAnimatable(destination);
            //commit soduku
            gameState.removeAnimatable(this);

        } else
            //otherwise, advance
            traveled += 0.34D;
    }

    /**
     * Draw the projectile as a black circle at its current location
     * @param g the Graphics object
     */
    @Override
    public void draw(Graphics g){
        //draw the projectile as a black circle
        g.fillOval((int) (origin.getX() + (dx * traveled)) ,
                (int) (origin.getY() + (dy * traveled)) ,
                10,10);
    }

    /**
     * Returns the position of the projectile as a Point
     * @return the position as a point
     */
    @Override
    public Point getPosition() {
        return new Point((int) (origin.getX() + (dx * traveled)),
                (int) (origin.getY() + (dy * traveled)));
    }
}
