package game;

import java.awt.*;

/**
 * Outlines the Tower object
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public abstract class Tower implements Animatable{
    public boolean isBeingDragged; // marks if the zookeeper is being dragged or has been placed
    public Point location; // holds the location of the moving zookeeper
    public GameState gameState; // holds the current gamestate
    public double range; // denotes the range of the tower, set to 100 for Juan Deag and 200 for Juan Deager
    public double fireRate; // denotes the rate of fire of the tower, set to 20 for Juan Deag and 10 for Juan Deager
    public int cost; // denotes the cost of the tower, set to 100 for Juan Deag and 200 for Juan Deager
    public long frameCounter; //denotes the life of the tower

    public Tower(GameState gs){
        isBeingDragged = true;
        gameState = gs;
        location = gameState.getMousePos();
        frameCounter = 0;
    }

    /**
     *  Update the location of the zookeeper to the mouse, but only if it has not been placed
     */
    @Override
    public void update() {

        //if the zookeeper is still being dragged, update the location to the location of the mouse
        if(isBeingDragged)
            location = gameState.getMousePos();

        //if the mouse has been pressed, set down the zookeeper
        if(gameState.getMousePressed() && isBeingDragged) {
            //check to see if it is being place in a valid location
            // if it is not, credit the player and remove this object from the active animatables
            if (!gameState.checkLocation(this)) {
                gameState.adjustCredits(cost);
                gameState.removeAnimatable(this);
            }
            isBeingDragged = false;
        }

        if(frameCounter < 0){
            frameCounter = 0;
        }else{
            frameCounter++;
        }

        if(!isBeingDragged && frameCounter % fireRate == 0) {
            //fire at closest enemy, but only if it is less than 100 away
            Enemy furthestInRange = gameState.getFurthestEnemyWithinRange(this);
            if (furthestInRange != null) {
                    gameState.addAnimatable(new Projectile(furthestInRange, this.getPosition(), gameState));
            }
        }
    }
}
