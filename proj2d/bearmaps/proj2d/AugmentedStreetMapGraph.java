package bearmaps.proj2d;

import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Neel Datta
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    /** Set of coordinates. */
    private WeirdPointSet vertices;
    /** List of nodes. */
    private List<Node> nodes;
    /** Map points to their nodes. */
    private HashMap<Point, Node> points = new HashMap<>();
    /** Map location nodes to their names. */
    private HashMap<String, List<Node>> locations = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        nodes = this.getNodes();
        List<Point> intersections = new ArrayList<>();
        for (Node n : nodes) {
            Point p = new Point(n.lon(), n.lat());
            if (n.name() != null) {
                if (!locations.containsKey(cleanString(n.name()))) {
                    locations.put(cleanString(n.name()), new LinkedList<>());
                }
                locations.get(cleanString(n.name())).add(n);
            }
            if (neighbors(n.id()).size() > 0) {
                intersections.add(p);
                points.put(p, n);
            }
        }
        vertices = new WeirdPointSet(intersections);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return points.get(vertices.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return new LinkedList<>();
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
