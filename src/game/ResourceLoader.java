package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Holds and manages all game resources.
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/23/2017
 */
public class ResourceLoader {

    public Map<String, BufferedImage> resourceMap; //holds all of the image resources, and maps them to their name
    public Path gamePath; //holds the game path
    public long highScore; //holds High Score
    static private ResourceLoader instance; // holds the single instance of this class

    /**
     * Constructs the resourceMap map.
     * Note: This method is private, this is so that only once instance of this class
     * can be made. To construct this class the first time, call the method getLoader.
     * After that, call it again to get the same object.
     */
    private ResourceLoader(){
        resourceMap = new HashMap<>(); //make the resource map
        highScore = 0;
    }

    /**
     * To ensure that only one instance of the ResourceLoader class is ever made,
     *  the constructor is private, and can only be accessed one time through this method
     *
     * @return the ResourceLoader instance if it has been made, a new instance if it has not
     */
    public static ResourceLoader getLoader()
    {
        //if a ResourceLoader object has not yet been created, create one
        if (instance == null)
            instance = new ResourceLoader ();

        //return the ResourceLoader object
        return instance;
    }

    /**
     * Check to see if this image has been already loaded (by looking for an entry with that name in a map).
     * If the image has not been loaded before, it is loaded and stored in a map of loaded images.  (Use the name as the key and the image as the value.)
     * Either way, the image is retrieved and returned to the caller.
     *
     * @param name name of the resource, as well as it's file extension
     * @return the requested resource
     */
    public BufferedImage getImage(String name){
        //If the image has already been loaded, return it
        if(resourceMap.containsKey(name))
            return resourceMap.get(name);

        //If the image has not already been loaded, load it and return it

        try {
            ClassLoader myLoader = this.getClass().getClassLoader(); //create loader
            InputStream imageStream = myLoader.getResourceAsStream("resources/" + name); //load the image
            BufferedImage resource = javax.imageio.ImageIO.read(imageStream);  // A handy helper method
            //add the resource to the map
            resourceMap.put(name,resource);
            return resource; //return the resource
        } catch (Exception e) {
            // On error, just print a message and exit.
            //   (You should make sure the files are in the correct place.)
            System.err.print("Could not load one of the files.");
            System.exit(0); //exits the program if the required files cannot be reached
            return null; // this never runs
        }
    }

    /**
     * Check to see if this path has been already loaded (by looking for an entry with that name in a map).
     * If the path has not been loaded before, a new Path object is built from that path file, and the Path object is stored in a map of Path objects.  (Again, use the name as the key and the Path object as the value.)
     * Either way, a Path object (that corresponds to that path file) is returned to the caller
     *
     * @param name the name of the resource, as well as it's file extension
     * @return the requested object
     */
    public Path getPath(String name){
        //If the path has already been made, return it
        if(gamePath != null)
            return gamePath;
        //If the path has not been made, make it

        try {
            ClassLoader myLoader = this.getClass().getClassLoader();
            // Create a scanner that points to our text file (with the path points in it)
            InputStream pointStream = myLoader.getResourceAsStream("resources/" + name);
            Scanner in = new Scanner(pointStream);  // Scan from the text file.
            // Build the path object (using the scanner).
            return new Path(in); // return the path
        }catch (Exception e){
            // On error, just print a message and exit.
            //   (You should make sure the files are in the correct place.)
            System.err.print("Could not load one of the files.");
            System.exit(0); //exits the program if the required files cannot be reached
            return null; // this never runs
        }
    }

    /**
     * Load the Data.txt file in the users home directory and return it
     *  Note: returns 0 if the file does not exist
     * @return a long representing the highscore, 0 if there is none
     */
    public long getHighScore(){
        if(highScore != 0)
            return highScore;
        //If the path has not been made, make it

        try {
            // Create a scanner that points to our text file (with the highscore in it)
            Scanner in = new Scanner(new File(System.getProperty("user.home").replaceAll("%20", " ").concat("/Data.txt")));  // Scan from the text file.
            // Build the path object (using the scanner).
            return in.nextLong(); // return the path
        }catch (Exception e){
            // On error, return 0
            //  This will happen if the file is empty
            return 0;
        }
    }
}
