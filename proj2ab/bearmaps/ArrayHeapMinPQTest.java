package bearmaps;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

/** @author Neel Datta.
 */

public class ArrayHeapMinPQTest {

    /** Random generator. */
    private static Random r = new Random();

    /** Random list generator. */
    private static List randomList(int n) {
        List<Point> random = new ArrayList<>();
        for (int i = 0; i < n; i += 1) {
            Point current = new Point(r.nextDouble(), r.nextDouble());
            random.add(current);
        }
        return random;
    }

    /** Random list test on removeSmallest(). */
    @Test
    public void testRemove() {
        NaiveMinPQ<Double> n = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Double> pq = new ArrayHeapMinPQ<>();
        List<Point> t = randomList(10000);
        for (Point p : t) {
            n.add(p.getX(), p.getY());
            pq.add(p.getX(), p.getY());
        }
        for (int i = 0; i < 10000; i += 1) {
            Double actual = pq.getSmallest();
            Double expected = n.getSmallest();
            if (!expected.equals(actual)) {
                actual = pq.removeSmallest();
                expected = n.removeSmallest();
            } else {
                actual = pq.removeSmallest();
                expected = n.removeSmallest();
            }
            assertEquals(expected, actual);
        }
    }

    /** Test edge cases. */
    @Test
    public void testEdges() {
        List<Point> tl = randomList(10000);
        ArrayHeapMinPQ<Double> t = new ArrayHeapMinPQ<>();
        for (Point p : tl) {
            t.add(p.getX(), p.getY());
        }
        Double item = 2.30;
        t.add(2.30, 0);
        assertEquals(item, t.getSmallest());
    }
}
