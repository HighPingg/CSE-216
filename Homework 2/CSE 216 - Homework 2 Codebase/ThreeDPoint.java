/**
 * An unmodifiable point in the three-dimensional space. The coordinates are
 * specified by exactly three doubles (its <code>x</code>, <code>y</code>, and
 * <code>z</code> values).
 */
public class ThreeDPoint implements Point {

    double x, y, z;

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        return new double[] { x, y, z };
    }

    /**
     * @return the x value.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * @return the y value.
     */
    public double getY() {
        return y;
    }

    /**
     * @return the z value.
     */
    public double getZ() {
        return z;
    }

    /**
     * Calculates the distance between 2 points using the formula
     * sqrt((x2-x1)^2+(y2-y1)^2).
     * 
     * @param point1 the first 2Dpoint.
     * @param point2 the second 2Dpoint.
     * @return the distance between the first and second 2Dpoints.
     */
    public static double distance(ThreeDPoint point1, ThreeDPoint point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2)
                + Math.pow(point1.getZ() - point2.getZ(), 2));
    }

    /**
     * @return the distance between this point and the origin.
     */
    @Override
    public double distanceFromOrigin() {
        return distance(this, new ThreeDPoint(0, 0, 0));
    }

    /**
     * Returns a string representation of this point.
     * 
     * @return the string representation of this point.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
