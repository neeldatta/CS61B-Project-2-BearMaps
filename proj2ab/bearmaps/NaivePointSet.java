package bearmaps;
import java.util.List;
/** @author Neel Datta. */
public class NaivePointSet implements PointSet {

    /** Instance variables. PSet is an array.
     */
    private Point[] pset;

    /** Constructor, contains points to put in set.
     * @param points is a List.
     */
    public NaivePointSet(List<Point> points) {
        pset = new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            pset[i] = points.get(i);
        }
    }

    /** Return closest point in PointSet to inputted coordinate. Should take
     * Theta(N) time.
     * @param x is double for x position.
     * @param y is double for y position.
     * @return Point that is closest.
     */
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = pset[0];
        for (int i = 1; i < pset.length; i++) {
            if (Point.distance(pset[i], goal) < Point.distance(best, goal)) {
                best = pset[i];
            }
        }
        return best;
    }

}
