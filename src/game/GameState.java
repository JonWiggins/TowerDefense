package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creates and updates the game.
 * - Loads the path and the background image
 * - Updates the object
 * - Draws the object, the background image, and the path if needed
 * - Returns the size of the background image to set the size of the panel
 * - Control Credits and lives
 * - Display messages to the user at game start and end
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */


public class GameState {

    private BufferedImage backdrop; // the background image
    private Path p; //The game path
    private int menuWidth; //size of the menu


    private Font title; //will hold a bigger Comic Sans font for titles
    private Font description; //will hold a smaller Comic Sans font for descriptions
    private boolean isGameActive; // will be used in the future to denote if the game has ended
    private boolean hasPlayed; //this will denote if a game has been played, this is show a message can be shown after a game
    private int lives; // denotes the lives
    private int credits; // denotes the credits
    private long score; //denote the amount of enemies killed
    private long previousScore; // will denote the players score just after they lost
    private long highScore; // denotes the highest score recorded
    private ArrayList<Animatable> activeAnimatables; //holds all of the active objects
    private ArrayList<Animatable> expiredAnimatables; //holds all of the expired objects that need to be removed
    private ArrayList<Animatable> pending; // holds all of the pending objects to be added
    private int mouseX; // hold the current x cord of the mouse
    private int mouseY; // hold the current y cord of the mouse
    private boolean isMousePressed; // flag the mouse pressed


    /**
     * Creates a new GameState object.
     */
    public GameState() {
        ResourceLoader loader = ResourceLoader.getLoader();  // Gets the resource loader object

        backdrop = loader.getImage("path_2.jpg"); //Gets the background image
        p = loader.getPath("path_2.txt"); //Gets the path
        //make the fonts
        title = new Font("Comic Sans MS", Font.PLAIN, 13);
        description = new Font("Comic Sans MS", Font.PLAIN, 10);

        //initialize and set starting variables
        mouseX = 0;
        mouseY = 0;
        menuWidth = 150;
        activeAnimatables = new ArrayList<>();
        expiredAnimatables = new ArrayList<>();
        pending = new ArrayList<>();
        isGameActive = false;
        hasPlayed = false;
        setStartingValues();
    }

    /**
     * Set the starting values of the game, this will be called right when the game starts, and will be called again
     *  when the game is restarted after a loss.
     */
    public void setStartingValues(){

        //giving game initial values
        lives = 50;
        credits = 150;
        score = 0;

        //load highscore
        highScore = ResourceLoader.getLoader().getHighScore();

        //clear old values from the last game
        activeAnimatables.clear();
        expiredAnimatables.clear();
        pending.clear();
        isMousePressed = false;

        //add the towers to the side menu
        activeAnimatables.add(new JaunDeagMenu(this));
        activeAnimatables.add(new JuanDeagerMenu(this));
    }

    /**
     * - Update the positions of the objects
     * - Spawn enemies if needed
     * - End the game if there are no more lives
     * - Start the game if it is over and the user just clicked
     * - update the animatable lists
     */
    void update() {

        //In your GameState update method, remove all expired objects from the list of active objects, then clear the list of expired objects.
        activeAnimatables.removeAll(expiredAnimatables);
        expiredAnimatables.clear();

        //update each active animatables
        for (Animatable a : activeAnimatables) {
            a.update();
        }



        //choose weather or not the add a new enemy
        // Only spawn enemies if the game is active
        if (isGameActive) {
            //update spawn bias
            long spawnBias = score / 10;
            //Generate a random number and use that to spawn enemies
            // Note that the spawn bias is used to increase the spawn rate as the score rises
            int random = new Random().nextInt(1000) + 1;
            if (random >= (980 - spawnBias)) {
                if((988 - spawnBias) < 0) activeAnimatables.add(new EnemyDatBoi(p,this));
                else if (random < (985 - spawnBias)) activeAnimatables.add(new EnemyHarambe(p, this));
                else if(random < (990 - spawnBias)) activeAnimatables.add(new EnemyDoge(p, this));
                else if(random < (996 - (spawnBias / 2))) activeAnimatables.add(new EnemyLightHarambe(p, this));
                else if(random < (1000 - (spawnBias / 3))) activeAnimatables.add(new EnemyDatBoi(p,this));
            }
        }

        //add all of the pending animatables to the active list
        activeAnimatables.addAll(pending);
        //clear the pending list
        pending.clear();

        //If the user has just clicked their mouse of the start screen, begin the game
        if(!isGameActive && getMousePressed()) {
            isGameActive = true;
            setStartingValues();
        }

        //If the user has lost all of their lives, end the game
        if(lives <= 0){
            previousScore = score;
            isGameActive = false;
            activeAnimatables.clear();
            hasPlayed = true;
        }
    }


    /**
     * Draw the game board, the menu, credits/lives, and all of the active animatables
     *
     * Show the message panels if the game is over
     *
     * @param g the graphics object for drawing on this panel
     */
    void draw(Graphics g) {
        // Draw the background.
        g.drawImage(backdrop, 0, 0, null);

        //Clear the side of the panel for the menu bar
        g.setColor(Color.lightGray);
        g.fillRect((int) getSize().getWidth() - menuWidth, 0, menuWidth, getSize().height);

        // Add outline rectangles
        g.setColor(Color.white);
        // One around lives, credits, and score
        g.fillRoundRect(backdrop.getWidth() + 10, 5, 130, 90, 10, 10);
        // One around Juan Deag
        g.fillRoundRect(backdrop.getWidth() + 10, 100, 130, 140, 10, 10);
        // One around Juan Deager
        g.fillRoundRect(backdrop.getWidth() + 10, 245, 130, 140, 10, 10);

        if (!isGameActive) {
            //the game is not active, show the start menu

            //draw a background rectangle
            g.setColor(Color.lightGray);
            g.fillRoundRect(((backdrop.getWidth()) / 2 )-105, (backdrop.getHeight() /2) - 105, 210,210,10,10);
            g.setColor(Color.white);
            g.fillRoundRect(((backdrop.getWidth()) / 2 )-100, (backdrop.getHeight() /2) - 100, 200,200,10,10);

            //show a zookeeper
            g.drawImage(ResourceLoader.getLoader().getImage("JuanDeag.png"),(backdrop.getWidth() / 2 )-150,(backdrop.getHeight() /2) - 105,null);

            //Check to see if a game has just been played
            if(hasPlayed){
                //if it has, show some info about the game
                g.setColor(Color.black);
                //give the user a message
                // Note: getting the username from the system here
                g.drawString("Nice Try " + System.getProperty("user.name")+ "!",(backdrop.getWidth() / 2 )-50, (backdrop.getHeight() /2) - 50 );
                g.drawString("Better luck next time!",(backdrop.getWidth() / 2 )-50, (backdrop.getHeight() /2) - 36 );

                //show them a message about their score
                //draw another rectangle for the message
                g.setColor(Color.lightGray);
                g.fillRoundRect(((backdrop.getWidth()) / 2 ) +110, (backdrop.getHeight() /2) - 105, 160,210,10,10);
                g.setColor(Color.white);
                g.fillRoundRect(((backdrop.getWidth()) / 2 ) + 115, (backdrop.getHeight() /2) - 100, 150,200,10,10);

                g.setColor(Color.black);
                g.drawString("Your Score was:" + previousScore, (backdrop.getWidth() / 2)  + 120, (backdrop.getHeight() /2) - 85);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
                g.drawString("That", (backdrop.getWidth() / 2)  + 120, (backdrop.getHeight() /2) - 40);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
                g.drawString("is", (backdrop.getWidth() / 2)  + 120, (backdrop.getHeight() /2) - 17);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
                g.drawString("pretty", (backdrop.getWidth() / 2)  + 120, (backdrop.getHeight() /2) + 10);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

                //tell them that their score was trash, ok, or great based on what it was
                if(previousScore < 1000) {
                    g.setColor(Color.red);
                    g.drawString("TRASH", (backdrop.getWidth() / 2) + 120, (backdrop.getHeight() / 2) + 55);
                }else if(previousScore < 10000){
                    g.setColor(Color.orange);
                    g.drawString("OKAY", (backdrop.getWidth() / 2) + 120, (backdrop.getHeight() / 2) + 55);
                }else{
                    g.setColor(Color.green);
                    g.drawString("GREAT", (backdrop.getWidth() / 2) + 120, (backdrop.getHeight() / 2) + 55);

                }
                g.setFont(title);
                g.setColor(Color.blue);
                //display a message at the bottom, telling them to click to play again
                g.drawString("Click anywhere to try again", (backdrop.getWidth() / 2 ) - 90, (backdrop.getHeight() /2) + 90);
            }else{
                //Show a message for the user
                // Note: This is the message to be displayed if the game has not been played yet
                g.setFont(title);
                g.setColor(Color.black);
                // Note: getting the username from the system here
                g.drawString("Help me, " + System.getProperty("user.name")+ "!",(backdrop.getWidth() / 2 )-50, (backdrop.getHeight() /2) - 50 );
                g.drawString("I need your assistance ",(backdrop.getWidth() / 2 )-50, (backdrop.getHeight() /2) - 30 );
                g.drawString("defending the Zoo!! ",(backdrop.getWidth() / 2 )-50, (backdrop.getHeight() /2) - 16 );
                g.setColor(Color.blue);
                g.drawString("Click anywhere to begin.", (backdrop.getWidth() / 2 ) - 90, (backdrop.getHeight() /2) + 90);
            }
        } else {
            //The game is active

            g.setColor(Color.black);
            g.setFont(title);

            // Draw lives, credits, and score in menu panel

            //adjust color of the lives section based on the amount of lives
            if (30 >= lives && lives >= 15) {
                g.setColor(Color.orange);
            } else if (lives < 15) {
                //lives must be less than 15
                g.setColor(Color.red);
            }
            g.drawString("Lives left: " + lives, backdrop.getWidth() + 17, 20);
            g.setColor(Color.black);
            g.drawString("Credits: " + credits, backdrop.getWidth() + 17, 36);
            g.drawString("Score: " + score, backdrop.getWidth() + 17, 52);
            g.drawString("HighScore:", backdrop.getWidth() + 17, 70);
            // if the highscore is bigger than the current score, display the highscore
            if (highScore >= score) {
                g.drawString(Long.toString(highScore), backdrop.getWidth() + 17, 87);
            } else {
                //if the score is greater than the highscore, display the current score
                //change the color so that it stands out
                g.setColor(Color.red);
                g.drawString(Long.toString(score), backdrop.getWidth() + 17, 87);
                g.setColor(Color.black);

                //update the highscore
                storeHighScore(score);
            }

            //Display the name of the first tower
            g.drawString("Juan Deag", backdrop.getWidth() + 50, 215);
            //Display the name of the second tower
            g.drawString("Juan Deager", backdrop.getWidth() + 45, 360);


            g.setFont(description);
            //Display cost of Juan Deag
            g.drawString("Cost: 100 Credits", backdrop.getWidth() + 43, 232);
            //Display cost of Juan Deager
            g.drawString("Cost: 200 Credits", backdrop.getWidth() + 43, 377);

            //draw each active animatable
            for (Animatable a : activeAnimatables) {
                a.draw(g);
            }
        }
    }

    /**
     * Returns the size of the image background. This is used to set the size of the panel
     *
     * @return a dimension object representing the size of the image background
     */
    public Dimension getSize() {
        //return the size of the backdrop as a Dimension
        return new Dimension(backdrop.getWidth() + menuWidth, backdrop.getHeight());
    }

    /**
     * Given an animatable object it adds it to the list of expired animatables to be removed later
     *
     * @param a an Animatable
     */
    public void removeAnimatable(Animatable a) {
        expiredAnimatables.add(a);
    }

    /**
     * Add an animatable to pending
     *
     * @param a an animatable
     */
    public void addAnimatable(Animatable a) {
        pending.add(a);
    }


    /**
     * Get the number of credits
     *
     * @return credits as an int
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Adjust the current number of credits
     * - Send a positive number to give credits
     * - Send a negative number to remove credits
     *
     * @param amount an int
     */
    public void adjustCredits(int amount) {
        // Adjusts the credits by the given amount
        credits += amount;
    }

    /**
     * Adjust the current number of lives
     * - Send a positive number to give lives
     * - Send a negative number to remove live
     *
     * @param amount an int
     */
    public void adjustLives(int amount) {
        // Adjusts lives by the given amount
        lives += amount;
    }

    /**
     * Adjust the current score
     * - Send a positive number to give score
     * - Send a negative number to remove score
     *
     * @param amount an int
     */
    public void adjustScore(int amount) {
        // Adjusts lives by the given amount
        score += amount;
    }


    /**
     * Update the current location of the mouse
     *
     * @param p the mouse location as a point
     */
    public void setMousePos(Point p) {
        // Records the current mouse position
        mouseY = (int) p.getY();
        mouseX = (int) p.getX();

    }

    /**
     * Get the current mouse location
     *
     * @return the mouse location as a point
     */
    public Point getMousePos() {
        // Returns the current mouse position
        return new Point(mouseX, mouseY);
    }

    /**
     * Set the mouse pressed flag to true
     */
    public void setMousePressed() {
        // Sets a boolean flag to true (to indicate a mouse press)
        isMousePressed = true;
    }

    /**
     * Set the mouse pressed flat to false
     */
    public void clearMousePressed() {
        // Clears the mouse press boolean flag
        isMousePressed = false;
    }

    /**
     * Get the mouse pressed flag
     *
     * @return true if the mouse is pressed, false otherwise
     */
    public boolean getMousePressed() {
        // Returns the value of the mouse press flag
        return isMousePressed;
    }

    /**
     *  Given a tower, this method returns the active enemy object that has traveled the most distance,
     *   and is still within the range of the given tower.
     *   Note: This will return null if their are no enemies within the range of the tower.
     * @param t a tower object
     * @return an enemy object
     */
    public Enemy getFurthestEnemyWithinRange(Tower t){

        Enemy furthestEnemyInRange = null; //will denote the enemy that has traveled the most, wile still in range of the tower
        double furthestDistance = 0; // will denote the enemies traveled distance

        //search each active animatable
        for (Animatable element : activeAnimatables) {
            //check if the animatable is an enemy
            if (element instanceof Enemy) {
                //check to see if the enemy is within range
                if (t.getPosition().distance(element.getPosition()) <= t.range) {
                    //if this is the first enemy found to be within range, set it to be the furthest
                    if (furthestEnemyInRange == null) {
                        furthestEnemyInRange = (Enemy) element;
                        furthestDistance = furthestEnemyInRange.getTraveled();
                    } else {
                        //there is an enemy, and it is within range
                        // test to see if it is the furthest in range
                        if (((Enemy) element).getTraveled() > furthestDistance) {
                            furthestEnemyInRange = (Enemy) element;
                            furthestDistance = furthestEnemyInRange.getTraveled();
                        }
                    }
                }
            }
        }
        //Note that this will be null if there are no enemies within range
        return furthestEnemyInRange;
    }

    /**
     * Stores the score given to the text file named Data.txt in the users home directory
     *
     * @param toStore a long to be stored
     */
    public void storeHighScore(long toStore) {

        //Attempt to access and write to the Data.txt file
        try {
            PrintWriter writer =
                    new PrintWriter(
                            new File(System.getProperty("user.home").replaceAll("%20", " ").concat("/Data.txt")));
            writer.println(toStore);
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            //If the file cannot be accessed, print the error message
            e.printStackTrace();
        }

    }


    /**
     * Returns false if the animatable is over the menu or over the path, returns true otherwise
     * @param a an animatable
     * @return true if the animatiable's location is valid
     */
    public boolean checkLocation(Animatable a) {
        //Check to see if the animatable in question is currently over the menu, or is over the path
        // Note: Path detection is done by comparing the RBG value of the path at the location to -5024757
        return !(a.getPosition().getX() >= backdrop.getWidth() || backdrop.getRGB((int) a.getPosition().getX(), (int) a.getPosition().getY()) >= -5024757);

    }

}
