package game;

import java.awt.*;
import java.util.Scanner;

/**
 * Generates, holds, and manages the Tower Defense pathway.
 *
 * @author Payne Hickok and Jon Wiggins
 * @version 3/25/2017
 */
public class Path {
    public Point[] pathPoints;

    /**
     * This constructor does the following:
     * - It creates a new array (or ArrayList) to hold the path coordinates,
     * and stores it in the path variable.
     * - It reads a number of coordinates, n, from the scanner.
     * - It loops n times, each time scanning a coordinate x,y pair, creating an
     * object to represent the coordinate, and storing the coordinate object in the path.
     *
     * @param s a Scanner set up by the caller to provide a list of coordinates
     */

    public Path(Scanner s) {
        //get the size of the list
        int size = s.nextInt();
        int count = 0;

        //create a new list, with the correct size, to hold all of the points
        pathPoints = new Point[size];

        //scan in each of the points from the list, store then in the array
        while (count < size) {
            pathPoints[count] = new Point(s.nextInt(), s.nextInt());
            count++;
        }
    }

    /**
     * Draws the current path to the screen (to wherever the graphics object points)
     * using a highly-visible color.
     *
     * @param g a graphics object
     */
    public void draw(Graphics g) {
        //set the color of the path to gray
        g.setColor(Color.gray);

        //for each point in the array, draw a line between it and the previous point
        // Note: this begins with the second entry in the array because the first does not have a previous point
        for (int i = 1; i < pathPoints.length; i++) {
            g.drawLine((int) pathPoints[i - 1].getX(), (int) pathPoints[i - 1].getY(),
                    (int) pathPoints[i].getX(), (int) pathPoints[i].getY());
        }
    }

    /**
     * Returns the total length of the path. Since the path
     * is specified using screen coordinates, the length is
     * in pixel units (by default).
     *
     * @return the length of the path
     */
    public double getPathLength() {
        //set the initial length to zero. because if the path has no points, the length is zero
        double length = 0;

        //for each entry in the array, add the distance between it and the previous entry to the total length
        // Note: this begins with the second entry in the array because the first does not have a previous point
        for (int i = 1; i < pathPoints.length; i++) {
            length += pathPoints[i - 1].distance(pathPoints[i]);
        }
        //return the length of the path
        return length;
    }

    /**
     * Given a percentage between 0% and 100%, this method calculates
     * the location along the path that is exactly this percentage
     * along the path. The location is returned in a Point object
     * (int x and y), and the location is a screen coordinate.
     * <p>
     * If the percentage is less than 0%, the starting position is
     * returned. If the percentage is greater than 100%, the final
     * position is returned.
     * <p>
     * If students don't want to use Point objects, they may
     * write or use another object to represent coordinates.
     * <p>
     * Caution: Students should never directly return a Point object
     * from a path list. It could be changed by the outside caller.
     * Instead, always create and return new point objects as
     * the result from this method.
     *
     * @param percentTraveled a distance along the path
     * @return the screen coordinate of this position along the path
     */
    public Point getPathPosition(double percentTraveled) {
        //if it is at or less than 0%, return starting point
        if (percentTraveled <= 0.00D)
            //The contract says we cannot directly return a point object from the list, so we do not
            return new Point(pathPoints[0]);
        //if it is at or greater than the end, return end point
        if (percentTraveled >= 1.0D)
            //The contract says we cannot directly return a point object from the list, so we do not
            return new Point(pathPoints[pathPoints.length - 1]);

        int currentSegmentStart; // will denote the index of the start point for the current segment


        double pathLengthToCurrentSegment = 0.0D; //will denote the length off all of the segments already traversed

        //Get the length of the path that needs to be traveled
        double amountToBeTraveled = getPathLength() * percentTraveled;

        //Find the line segment that the object needs to be drawn on
        // Do this by summing up the lengths of the segments until the total is greater than the distance that
        // needs to be traveled, or until the end of the path is reached
        int index = 1;
        while (pathPoints[index - 1].distance(pathPoints[index]) + pathLengthToCurrentSegment <= amountToBeTraveled
                && index < pathPoints.length) {
            pathLengthToCurrentSegment += pathPoints [index - 1].distance(pathPoints[index]);
            index++;
        }
        currentSegmentStart = index - 1; //The starting point for the segment the object will be on
                                         // will be one less than the index when the above loop exits.

        //calculate the length into the segment that the object will be drawn
        double lengthIntoSegment = amountToBeTraveled - pathLengthToCurrentSegment;

        //calculate the total length of the segment
        double lengthOfSegment = pathPoints[currentSegmentStart].distance(pathPoints[currentSegmentStart + 1]);


        //Calculate the change in x over the length of the segment, then multiply it by the percent of the segment
        // that must be traversed, then add that to the starting point, this will generate an x position that represents
        // the desired location for the object to be drawn.
        int x = (int) (pathPoints[currentSegmentStart].getX() +
                ((pathPoints[currentSegmentStart + 1].getX() - pathPoints[currentSegmentStart].getX())
                        * (lengthIntoSegment / lengthOfSegment)));
        //Repeat the above calculation, but with and for the y position.
        int y = (int) (pathPoints[currentSegmentStart].getY() +
                ((pathPoints[currentSegmentStart + 1].getY() - pathPoints[currentSegmentStart].getY())
                        * (lengthIntoSegment / lengthOfSegment)));

        //return a point with the desired x and y positions
        return new Point(x, y);
    }
}
