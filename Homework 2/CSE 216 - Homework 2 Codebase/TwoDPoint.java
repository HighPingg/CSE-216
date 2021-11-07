import java.util.ArrayList;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The
 * coordinates of such a point is given by exactly two doubles specifying its
 * <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point {

    private double x, y;

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value of this object.
     * 
     * @return x.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y value of this object.
     * 
     * @return y.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x value of this object to x.
     * 
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y value of this object to y.
     * 
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        return new double[] { x, y };
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of
     * doubles. A valid argument must always be an even number of doubles so that
     * every pair can be used to form a single <code>TwoDPoint</code> to be added to
     * the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of
     *                                  doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {

        if (coordinates.length % 2 != 0) {
            throw new IllegalArgumentException("Input List Must Be of Even Size");
        }

        ArrayList<TwoDPoint> list = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i += 2) {
            list.add(new TwoDPoint(coordinates[i], coordinates[i + 1]));
        }

        return list;
    }

    /**
     * Calculates the slope of a line between 2 points using the formula (y2 -
     * y1)/(x2 - x1).
     * 
     * @param point1 the first 2Dpoint.
     * @param point2 the second 2Dpoint.
     * @return the slope between the first and second 2Dpoints.
     */
    public static double slope(TwoDPoint point1, TwoDPoint point2) {
        return (point1.getY() - point2.getY()) / (point1.getX() - point2.getX());
    }

    /**
     * Calculates the distance between 2 points using the formula
     * sqrt((x2-x1)^2+(y2-y1)^2).
     * 
     * @param point1 the first 2Dpoint.
     * @param point2 the second 2Dpoint.
     * @return the distance between the first and second 2Dpoints.
     */
    public static double distance(TwoDPoint point1, TwoDPoint point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    }

    /**
     * Deep clones this point.
     * 
     * @return the clone of this point.
     */
    @Override
    public TwoDPoint clone() {
        return new TwoDPoint(x, y);
    }

    /**
     * Returns whether or not a given object is equal or not to this object.
     * 
     * @param the object we want to compare to.
     * 
     * @return true if it is equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TwoDPoint) {
            TwoDPoint point = (TwoDPoint) obj;

            return point.getX() == getX() && point.getY() == getY();
        }

        return false;
    }

    /**
     * Returns a string representation of this point.
     * 
     * @return the string representation of this point.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
