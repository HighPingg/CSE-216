import java.util.ArrayList;
import java.util.List;

public class Quadrilateral implements TwoDShape, Positionable {

    List<TwoDPoint> vertices;

    public Quadrilateral(List<TwoDPoint> vertices) {
        this.setPosition(vertices);
    }

    /**
     * Sets the position of this quadrilateral according to the first four elements
     * in the specified list of points. The quadrilateral is formed on the basis of
     * these four points taken in a clockwise manner on the two-dimensional x-y
     * plane, starting with the point with the least x-value. If the input list has
     * more than four elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        vertices = new ArrayList<>();

        ArrayList<TwoDPoint> tempList = new ArrayList<>();

        tempList.add((TwoDPoint) points.get(0));
        tempList.add((TwoDPoint) points.get(1));
        tempList.add((TwoDPoint) points.get(2));
        tempList.add((TwoDPoint) points.get(3));

        /*
         * Finds the index of the left most vertex, or the vertex if the smallest x
         * value. If there is another point with the same index, then we will take the
         * point with the smallest y value.
         */
        int indexMinium = 0;
        for (int i = 1; i < 4; i++) {
            if (tempList.get(i).getX() < tempList.get(indexMinium).getX()
                    || (tempList.get(i).getX() == tempList.get(indexMinium).getX()
                            && (tempList.get(i).getY() < tempList.get(indexMinium).getY()))) {

                indexMinium = i;
            }
        }

        // Remove the rightmost vertex and add it to final list
        vertices.add(tempList.remove(indexMinium));

        /*
         * Store each TwoDPoint using its slope to the rightmost vertex as its value and
         * the point as the index. We will then add from the list to the list of
         * vertices in order of highest to lowest slope.
         */
        ArrayList<Double> slopes = new ArrayList<>();
        for (TwoDPoint element : tempList) {
            slopes.add(TwoDPoint.slope(element, vertices.get(0)));
        }

        while (!slopes.isEmpty()) {

            // Find the highest slope's index
            int highestSlope = 0;

            for (int i = 1; i < slopes.size(); i++) {
                if (slopes.get(highestSlope) < slopes.get(i)) {
                    highestSlope = i;
                }

                // Else if 2 values have the same slope, the one with the smaller x value is
                // kept.
                else if (slopes.get(highestSlope).equals(slopes.get(i))
                        && (tempList.get(i).getX() <= tempList.get(highestSlope).getX())) {

                    // If the x values are equal, then we take the one with the highest y value
                    if ((tempList.get(i).getX() != tempList.get(highestSlope).getX())
                            || (tempList.get(i).getY() > tempList.get(highestSlope).getY()))
                        highestSlope = i;
                }
            }

            // Adds that vertex to the final list and remove it from the temp and slope
            // lists.
            vertices.add(tempList.remove(highestSlope));
            slopes.remove(highestSlope);
        }
    }

    /**
     * Retrieve the position of an object as a list of points. The points are be
     * retrieved and added to the returned list in a clockwise manner on the
     * two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts
     * with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends Point> getPosition() {
        return new ArrayList<>(vertices);
    }

    /**
     * @return the number of sides of this quadrilateral, which is always set to
     *         four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The
     * <i>trivial</i> quadrilateral, where all four corner vertices are the same
     * point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a
     *                 <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of
     *         points for a quadrilateral, and <code>false</code> otherwise. For
     *         example, if three of the four vertices are in a straight line is
     *         invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        return false; // TODO
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest
     * integer-valued x-y coordinate. For example, if a corner is at (0.8, -0.1), it
     * will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure
     * described above results in this quadrilateral becoming invalid (e.g., all
     * four corners collapse to a single point), then it is left unchanged. Snapping
     * is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        // TODO
    }

    /**
     * @return the area of this quadrilateral
     */
    public double area() {
        return 0; // TODO
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this
     *         quadrilateral
     */
    public double perimeter() {
        return 0; // TODO
    }

    public static void main(String[] args) {
        ArrayList<TwoDPoint> points = new ArrayList<>();

        points.add(new TwoDPoint(16, 1));
        points.add(new TwoDPoint(16, 10));
        points.add(new TwoDPoint(1, 10));
        points.add(new TwoDPoint(1, 1));
        points.add(new TwoDPoint(9, 10));

        Quadrilateral quad = new Quadrilateral(points);

        System.out.println(quad.getPosition());
    }
}
