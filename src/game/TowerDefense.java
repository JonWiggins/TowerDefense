package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A Tower Defense game, consiting of enemies that move across a path.
 * The player places towers to stop the progression of the enemies down the path.
 *
 * @author Peter Jensen, Payne Hickok, and Jon Wiggins
 * @version 4/8/2017
 */
public class TowerDefense extends JPanel implements ActionListener, Runnable, MouseListener, MouseMotionListener {
    // This constant avoids an obnoxious warning, but it is totally unused by us.
    //   It would only be relevant if we were using object serialization.
    private static final long serialVersionUID = 42L;

    private GameState game; //holds the GameState Object


    /**
     * The application entry point.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        // Main runs in the 'main' execution thread, and the GUI
        //   needs to be built by the GUI execution thread.
        //   Ask the GUI thread to run our 'run' method (at some
        //   later time).
        SwingUtilities.invokeLater(new TowerDefense());
        // Done.  Let the main thread of execution finish.  All the
        //   remaining work will be done by the GUI thread.
    }

    /**
     * Builds the GUI for this application.  This method must
     * only be called/executed by the GUI thread.
     */
    public void run() {
        //Create JFrame
        JFrame f = new JFrame("Cincinnati Zoo Defense");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Set the content of the panel to be the path
        f.setContentPane(this);

        //Create new GameState
        game = new GameState();

        // Set the size of this panel to match the size of the backdrop image.
        Dimension panelSize = game.getSize();
        this.setMinimumSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);


        // Create a timer (for animation), have it call our actionPerformed
        //   method 60 times a second.
        new Timer(17, this).start();

        //display the frame
        f.pack();
        f.setLocationRelativeTo(null);  // Centers window
        f.setVisible(true);

        //add mouse listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Call the draw method in the GameState object
     *
     * @param g the graphics object for drawing on this panel
     */
    public void paint(Graphics g) {
        game.draw(g); //call the draw method in the GameState object
    }

    /**
     * Calls the update method in the GameState and redraws the panel.
     *
     * @param e the event object
     */
    public void actionPerformed(ActionEvent e) {
        game.update(); //update the drawing
        repaint(); //update the panel
    }

    //Required method for the mouse listener and motion listener, they each simply hand off information to the corresponding
    // gamestate methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        game.setMousePressed();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        game.clearMousePressed();
        game.setMousePos(new Point(e.getX(), e.getY()));
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        game.setMousePos(new Point(e.getX(), e.getY()));
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        game.setMousePos(new Point(e.getX(), e.getY()));
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        game.setMousePos(new Point(e.getX(), e.getY()));
    }
}