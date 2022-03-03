package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
/** @author Neel Datta. */
public class KDTreeTest {
    /** Compares between NaivePointSet and KDTree's nearest method.
     * Using example from lecture. */
    @Test
    public void nearestComparedTest() {
        Point A = new Point(2, 3);
        Point B = new Point(4, 2);
        Point C = new Point(4, 5);
        Point D = new Point(3, 3);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);
        List<Point> points = new ArrayList<Point>();
        points.add(A);
        points.add(B);
        points.add(C);
        points.add(D);
        points.add(E);
        points.add(F);
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);
        assertEquals(naive.nearest(0, 7), kdt.nearest(0, 7));
    }
}
