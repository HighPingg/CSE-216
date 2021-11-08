import java.util.ArrayList;
import java.util.List;

public class Triangle implements TwoDShape, Positionable {

    List<TwoDPoint> vertices;

    public Triangle(List<TwoDPoint> vertices) {
        this.setPosition(vertices);
    }

    /**
     * Sets the position of this triangle according to the first three elements in
     * the specified list of points. The triangle is formed on the basis of these
     * three points taken in a clockwise manner on the two-dimensional x-y plane,
     * starting with the point with the least x-value. If the input list has more
     * than three elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {

        for (Point point : points) {
            if (!(point instanceof TwoDPoint))
                throw new IllegalArgumentException("Points must be of type TwoDPoint");
        }

        vertices = new ArrayList<>();

        vertices.add((TwoDPoint) points.get(0));
        vertices.add((TwoDPoint) points.get(1));
        vertices.add((TwoDPoint) points.get(2));

        /*
         * Finds the index of the left most vertex, or the vertex if the smallest x
         * value. If there is another point with the same index, then we will take the
         * point with the smallest y value.
         */
        int indexMinium = 0;
        for (int i = 1; i < 3; i++) {
            if (vertices.get(i).getX() < vertices.get(indexMinium).getX()
                    || (vertices.get(i).getX() == vertices.get(indexMinium).getX()
                            && (vertices.get(i).getY() < vertices.get(indexMinium).getY()))) {

                indexMinium = i;
            }
        }

        // Swap the first vertex with the index of the smallest x value.
        TwoDPoint swapTemp = vertices.get(0);
        vertices.set(0, vertices.get(indexMinium));
        vertices.set(indexMinium, swapTemp);

        // Find the slopes of the 2 points from the first. The steeper slope is the one
        // next in clockwise order.
        double slope1 = TwoDPoint.slope(vertices.get(1), vertices.get(0));
        double slope2 = TwoDPoint.slope(vertices.get(2), vertices.get(0));

        if (slope2 > slope1) {
            // Swap 2 and 1
            swapTemp = vertices.get(1);
            vertices.set(1, vertices.get(2));
            vertices.set(2, swapTemp);
        }

        // System.out.println(getPosition());
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
     * Returns the left-most "root" vertex.
     * 
     * @return the "root" vertex.
     */
    @Override
    public TwoDPoint getRoot() {
        return vertices.get(0);
    }

    /**
     * @return the number of sides of this triangle, which is always set to three
     */
    @Override
    public int numSides() {
        return 3;
    }

    /**
     * Checks whether or not a list of vertices forms a valid triangle. The
     * <i>trivial</i> triangle, where all three corner vertices are the same point,
     * is considered to be an invalid triangle.
     *
     * @param vertices the list of vertices to check against, where each vertex is a
     *                 <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of
     *         points for a triangle, and <code>false</code> otherwise. For example,
     *         three vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {

        for (Point vertex : vertices) {
            if (!(vertex instanceof TwoDPoint))
                throw new IllegalArgumentException("Points must be of type TwoDPoint");
        }

        ArrayList<TwoDPoint> temp = new ArrayList<>();
        temp.add((TwoDPoint) vertices.get(0));
        temp.add((TwoDPoint) vertices.get(1));
        temp.add((TwoDPoint) vertices.get(2));

        // If any 2 points are equal to the first, then this is not a triangle.
        if (temp.get(0).equals(temp.get(1)) || temp.get(0).equals(temp.get(2)))
            return false;

        // If their slopes are equal, then this is just a line.
        double slope1 = TwoDPoint.slope(temp.get(1), temp.get(0));
        double slope2 = TwoDPoint.slope(temp.get(2), temp.get(0));

        return !(slope1 == slope2);
    }

    /**
     * This method snaps each vertex of this triangle to its nearest integer-valued
     * x-y coordinate. For example, if a corner is at (0.8, -0.1), it will be
     * snapped to (1,0). The resultant triangle will thus have all three vertices in
     * positions with integer x and y values. If the snapping procedure described
     * above results in this triangle becoming invalid (e.g., all corners collapse
     * to a single point), then it is left unchanged. Snapping is an in-place
     * procedure, and the current instance is modified.
     */
    public void snap() {
        List<TwoDPoint> snapped = new ArrayList<>();
        snapped.add(vertices.get(0).clone());
        snapped.add(vertices.get(1).clone());
        snapped.add(vertices.get(2).clone());

        for (TwoDPoint twoDPoint : snapped) {
            twoDPoint.setX(Math.round(twoDPoint.getX()));
            twoDPoint.setY(Math.round(twoDPoint.getY()));
        }

        if (isMember(snapped)) {
            vertices = snapped;
        }

    }

    /**
     * @return the area of this triangle
     */
    public double area() {
        double term1 = (vertices.get(1).getX() - vertices.get(0).getX())
                * (vertices.get(2).getY() - vertices.get(0).getY());
        double term2 = (vertices.get(2).getX() - vertices.get(0).getX())
                * (vertices.get(1).getY() - vertices.get(0).getY());

        return 0.5 * (term2 - term1);
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this
     *         triangle
     */
    public double perimeter() {
        double side1 = TwoDPoint.distance(vertices.get(1), vertices.get(0));
        double side2 = TwoDPoint.distance(vertices.get(2), vertices.get(0));
        double side3 = TwoDPoint.distance(vertices.get(2), vertices.get(1));

        return side1 + side2 + side3;
    }

    /**
     * @return the String representation of this Triangle.
     */
    @Override
    public String toString() {
        return "Triangle" + vertices;
    }

    @Override
    public int compareTo(TwoDShape o) {
        return ((Double) this.area()).compareTo(o.area());
    }
}
