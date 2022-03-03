package bearmaps.proj2c;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/** @author Neel Datta. Memory optimized implementation of A* algorithm. */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    /** MinPQ for fringe. */
    private DoubleMapPQ<Vertex> fringe;
    /** List of solution. */
    private ArrayList<Vertex> solution;
    /** Map for distTo. */
    private HashMap<Vertex, Double> distTo;
    /** Map for edgeTo. */
    private HashMap<Vertex, Vertex> edgeTo;
    /** Time on stopwatch. */
    private double expTime;
    /** Weight of solution. */
    private double solWeight;
    /** Number of states explored. */
    private int numStates;
    /** Outcome (solved, unsolved, or timeout). */
    private SolverOutcome outcome;

    /** Constructor which finds solution, computing all methods in constant time.
     * @param input is the inputted AStarGraph.
     * @param start is the initial point from which the shortest path is computed.
     * @param end is the goal point towards which the shortest path is computed.
     * @param timeout is the time in seconds at which the algorithm will stop and
     * report solution was unable to be found.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch s = new Stopwatch();
        fringe = new DoubleMapPQ<Vertex>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        numStates = 0;
        solution = new ArrayList<>();
        solWeight = 0;
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        expTime = s.elapsedTime();

        while (fringe.size() > 0) {
            if (fringe.getSmallest().equals(end)) {
                Vertex v = end;
                while (!(v.equals(start))) {
                    solution.add(0, v);
                    v = edgeTo.get(v);
                    if (s.elapsedTime() > timeout) {
                        outcome = SolverOutcome.TIMEOUT;
                        expTime = s.elapsedTime();
                        return;
                    }
                }
                solWeight = distTo.get(end);
                solution.add(0, start);
                outcome = SolverOutcome.SOLVED;
                expTime = s.elapsedTime();
                return;
            }

            if (s.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                expTime = s.elapsedTime();
                return;
            }

            numStates++;
            Vertex p = fringe.removeSmallest();
            /** Relax outgoing edges from p. */
            for (WeightedEdge<Vertex> edge : input.neighbors(p)) {
                Vertex e = edge.to();
                double w = edge.weight();
                if (!distTo.containsKey(e) || distTo.get(e) > distTo.get(p) + w) {
                    distTo.put(e, distTo.get(p) + w);
                    edgeTo.put(e, p);
                    if (fringe.contains(e)) {
                        fringe.changePriority(e, distTo.get(e)
                                + input.estimatedDistanceToGoal(e, end));
                    } else {
                        fringe.add(e, distTo.get(e)
                                + input.estimatedDistanceToGoal(e, end));
                    }
                }
            }
        }

        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        expTime = s.elapsedTime();
        if (expTime > timeout) {
            outcome = SolverOutcome.TIMEOUT;
            return;
        }
    }

    /** The outcome.
     * @return SolverOutcome.SOLVED, .TIMEOUT, or .UNSOLVABLE.
     */
    public SolverOutcome outcome() {
        return outcome;
    }

    /** List of vertices representing the solution.
     * @return empty if result was TIMEOUT or UNSOLVABLE.
     */
    public List<Vertex> solution() {
        return solution;
    }

    /** Total weight of solution.
     * @return 0 if result was TIMEOUT or UNSOLVABLE.
     */
    public double solutionWeight() {
        return solWeight;
    }

    /** Total number of PQ dequeue operations.
     * @return int corresponding to number.
     */
    public int numStatesExplored() {
        return numStates;
    }

    /** Total time spent by constructor.
     * @return time in seconds.
     */
    public double explorationTime() {
        return expTime;
    }
}
