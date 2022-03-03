package bearmaps.proj2ab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/** @author Neel Datta. */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /** Instance variable for Hashmap container. */
    private HashMap<T, Integer> container;
    /** Instance variable for ArrayList items. */
    private ArrayList<Node> items;

    /** Constructor. */
    public ArrayHeapMinPQ() {
        container = new HashMap<T, Integer>();
        items = new ArrayList<Node>();
    }

    /** Checks if item is in container.
     * @param item of T that is checked.
     * @return True if in container.
     */
    public boolean contains(T item) {
        return container.containsKey(item);
    }

    /** Adds item with priority input.
     * * @param item is T item added.
     * @param priority is value assigned.
     */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(item, priority);
        items.add(n);
        container.put(item, size() - 1);
        swim(size() - 1);
    }

    /** Return smallest item. */
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
    }

    /** Removes smallest.
     * @return item T.
     */
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T item = items.get(0).getItem();
        swap(0, size() - 1);
        items.remove(size() - 1);
        sink(0);
        container.remove(item);
        return item;
    }

    /** Return size. */
    public int size() {
        return items.size();
    }

    /** Change given item's priority.
     * @param item is item that is changed.
     * @param priority is new value.
     */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int k = container.get(item);
        items.get(k).nodeChangePriority(priority);
        swim(k);
        sink(k);
    }

    /** Swaps items.
     * @param first is int.
     * @param second is int.
     */
    private void swap(int first, int second) {
        Node n = items.get(first);
        container.replace(n.getItem(), second);
        container.replace(items.get(second).getItem(), first);
        items.set(first, items.get(second));
        items.set(second, n);
    }

    /** Swims item using swap.
     * @param k integer for swim.
     */
    private void swim(int k) {
        if (k == 0) {
            return;
        }
        if (items.get(k).getPriority() > items.get((k - 1) / 2).getPriority()) {
            return;
        }
        swap(k, ((k - 1) / 2));
        swim((k - 1) / 2);
    }

    /** Sinks item using swap.
     * @param k integer for sink.
     */
    private void sink(int k) {
        int r = 1;
        if (k * 2 + 1 >= size()) {
            return;
        }
        if (k * 2 + 2 >= size()) {
            if (items.get(k).getPriority()
                    > items.get(k * 2 + 1).getPriority()) {
                swap(k, k * 2 + 1);
            }
            return;
        }
        if (items.get(k * 2 + 2).getPriority() >= items.get(k).getPriority()
                && items.get(k * 2 + 1).getPriority()
                >= items.get(k).getPriority()) {
            return;
        }
        if (items.get(k * 2 + 1).getPriority()
                > items.get(k * 2 + 2).getPriority()) {
            r = 2;
        }
        swap(k, k * 2 + r);
        sink(k * 2 + r);
    }

    /** Private subclass for node. */
    private class Node {
        /** Item. */
        private T _item;
        /** Priority value. */
        private double _priority;

        /** Constructor.
         * @param item is T.
         * @param priority is a double.
         */
        Node(T item, double priority) {
            this._item = item;
            this._priority = priority;
        }

        /** Change priority.
         * @param changed is new priority value.
         */
        public void nodeChangePriority(double changed) {
            this._priority = changed;
        }

        /** Return item. */
        public T getItem() {
            return _item;
        }

        /**Return priority value. */
        public double getPriority() {
            return _priority;
        }
    }
}
