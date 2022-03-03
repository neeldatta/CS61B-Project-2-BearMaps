package bearmaps;
import java.util.List;
/** @author Neel Datta. */
public class KDTree implements PointSet {
    /** Instance variables. **/
    private Node root;

    /** Constructor; points is at least size 1.
     * @param points is the input List of points.
     */
    public KDTree(List<Point> points) {
        Point in = points.get(0);
        root = new Node(in);
        if (points.size() > 1) {
            for (int i = 1; i < points.size(); i++) {
                in = points.get(i);
                Node pt = new Node(in);
                this.insert(pt, root);
            }
        }
    }

    /** Insert method, adds node to KDTree. Like BSTMap insert but must keep
     * track of which dimension is to be compared.
     * @param n is the node to be inserted.
     * @param curr is the node that is the current potential parent.
     */
    public void insert(Node n, Node curr) {
        if (n.compareLess(curr)) {
            if (curr.leftChild == null) {
                n.parent = curr;
                curr.leftChild = n;
                n.assignComp();
            } else {
                insert(n, curr.leftChild);
            }
        } else {
            if (curr.rightChild == null) {
                n.parent = curr;
                curr.rightChild = n;
                n.assignComp();
            } else {
                insert(n, curr.rightChild);
            }
        }
    }

    /** Return the closest point. Should take O(logN) time.
     * @param x is double representing x.
     * @param y is double representing y.
     * @return closest Point.
     */
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node bestNode = closestNode(root, goal, root);
        return bestNode.getPoint();
    }

    /** Recursive helper function for nearest.
     * @param n is the node currently being compared.
     * @param goal is the point to which the closest value is being
     * sought.
     * @param best is the currently perceived closest node.
     * @return the closest node.
     */
    public Node closestNode(Node n, Point goal, Node best) {
        Node g = new Node(goal);
        Node goodSide;
        Node badSide;
        if (n == null) {
            return best;
        }
        if (Math.sqrt(Point.distance(n.getPoint(), goal))
                < Math.sqrt(Point.distance(best.getPoint(), goal))) {
            best = n;
        }
        if (g.compareLess(n)) {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }
        best = closestNode(goodSide, goal, best);
        if (n.compDist(g) <  Math.sqrt(Point.distance(best.getPoint(), goal))) {
            best = closestNode(badSide, goal, best);
        }
        return best;
    }



    /** Node subclass for the tree. **/
    private class Node {
        /** Stored data point. */
        private Point pt;
        /** Parent node. */
        private Node parent;
        /** Left child branch of node. */
        private Node leftChild = null;
        /** Right child branch of node. */
        private Node rightChild = null;
        /** Value used to track comparison dimension. */
        private int comparator;

        /** Constructor for node.
         * @param in is inputted data point.
         */
        Node(Point in) {
            pt = in;
        }

        /** Assigns comparator dimension to node. **/
        public void assignComp() {
            if (parent == null) {
                comparator = 0;
            } else {
                comparator = parent.comparator + 1;
            }
        }

        /** Compute distance between two nodes in terms of the
         * comparator dimension.
         * @param goal is the input Node to be subtracted.
         * @return
         */
        public double compDist(Node goal) {
            if ((comparator % 2) == 0) {
                return Math.abs(this.pt.getX() - goal.getPoint().getX());
            }
            return Math.abs(this.pt.getY() - goal.getPoint().getY());
        }

        /** Return the data of the node.
         * @return Point data.
         */
        public Point getPoint() {
            return pt;
        }

        /** Less than comparison of node with another node depending
         * on comparator.
         * @param curr is the inputted Node.
         * @return true if node should move down the left of the tree.
         */
        public boolean compareLess(Node curr) {
            if ((curr.comparator % 2) == 0) {
                return (this.pt.getX() < curr.getPoint().getX());
            }
            return (this.pt.getY() < curr.getPoint().getY());
        }
    }
}
