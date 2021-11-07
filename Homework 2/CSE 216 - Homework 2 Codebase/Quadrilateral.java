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
        /*
         * We can use the Triangle class's isMember to help us here. All quadrilaterals
         * can be split into 2 triangles. If we are given a quadrilateral ABCD, the 4
         * triangles formed are ABC, ACD, BCD, ABD. As long as those 2 triangles are valid,
         * then our quadrilateral is valid. (Essentially we're testing if any 3 points
         * are in a straight line)
         */

        ArrayList<TwoDPoint> triangle = new ArrayList<>();

        // Triangle ABC
        triangle.add((TwoDPoint) vertices.get(0));
        triangle.add((TwoDPoint) vertices.get(1));
        triangle.add((TwoDPoint) vertices.get(2));

        if (!(new Triangle(triangle).isMember(triangle)))
            return false;

        // Triangle ACD
        triangle.set(0, (TwoDPoint) vertices.get(0));
        triangle.set(1, (TwoDPoint) vertices.get(2));
        triangle.set(2, (TwoDPoint) vertices.get(3));

        if (!(new Triangle(triangle).isMember(triangle)))
            return false;
            
        // Triangle BCD
        triangle.set(0, (TwoDPoint) vertices.get(1));
        triangle.set(1, (TwoDPoint) vertices.get(2));
        triangle.set(2, (TwoDPoint) vertices.get(3));

        if (!(new Triangle(triangle).isMember(triangle)))
            return false;
            
        // Triangle ABD
        triangle.set(0, (TwoDPoint) vertices.get(0));
        triangle.set(1, (TwoDPoint) vertices.get(1));
        triangle.set(2, (TwoDPoint) vertices.get(3));

        return new Triangle(triangle).isMember(triangle);
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
        ArrayList<TwoDPoint> snapped = new ArrayList<>();
        snapped.add(vertices.get(0).clone());
        snapped.add(vertices.get(1).clone());
        snapped.add(vertices.get(2).clone());
        snapped.add(vertices.get(3).clone());

        for (TwoDPoint twoDPoint : snapped) {
            twoDPoint.setX(Math.round(twoDPoint.getX()));
            twoDPoint.setY(Math.round(twoDPoint.getY()));
        }

        if (isMember(snapped)) {
            vertices = snapped;
        }
    }

    /**
     * @return the area of this quadrilateral
     */
    public double area() {

        /*
         * We will again split this quadrilateral into triangles and find the area of
         * each triangle. We will then add up the area of these 2 triangles.
         */


        // Triangle ABC
        ArrayList<TwoDPoint> triangle1 = new ArrayList<>();
        triangle1.add(vertices.get(0));
        triangle1.add(vertices.get(1));
        triangle1.add(vertices.get(2));

        // Triangle ACD
        ArrayList<TwoDPoint> triangle2 = new ArrayList<>();
        triangle2.add(vertices.get(0));
        triangle2.add(vertices.get(2));
        triangle2.add(vertices.get(3));

        return new Triangle(triangle1).area() + new Triangle(triangle2).area();
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this
     *         quadrilateral
     */
    public double perimeter() {
        
        double side1 = TwoDPoint.distance(vertices.get(0), vertices.get(1));
        double side2 = TwoDPoint.distance(vertices.get(1), vertices.get(2));
        double side3 = TwoDPoint.distance(vertices.get(2), vertices.get(3));
        double side4 = TwoDPoint.distance(vertices.get(3), vertices.get(0));

        return side1 + side2 + side3 + side4;
    }
}
