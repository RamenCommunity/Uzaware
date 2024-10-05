package net.minearchive.util.pathFinder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AStarPathFinder {

    private final Node[] direction = new Node[] {
            //-1
            new Node(-1, -1, -1), new Node(-1, -1, 0), new Node(-1, -1, 1),
            new Node(0 , -1, -1), new Node(0 , -1, 0), new Node(0 , -1, 1),
            new Node(1 , -1, -1), new Node(1 , -1, 1), new Node(1 , -1, 0),

            //0
            new Node(-1, 0, -1),  new Node(-1, 0, 0),  new Node(-1, 0, 1),
            new Node(0 , 0, -1), /* Player Position */ new Node(0 , 0, 1),
            new Node(1 , 0, -1),  new Node(1 , 0, 0),  new Node(1 , 0, 1),

            //+1
            new Node(-1, 1, -1),  new Node(-1, 1, 0),  new Node(-1, 1, 1),
            new Node(0 , 1, -1),  new Node(0 , 1, 0),  new Node(0 , 1, 1),
            new Node(1 , 1, -1),  new Node(1 , 1, 0),  new Node(1 , 1, 1),
    }; // 26

    private final Map<Node, Boolean> map = new ConcurrentHashMap<>();

    public List<Node> find(Node start, Node end) {
        if (start.equals(end)) {
            return List.of();
        }
        map.clear();

        map.putAll(collect());

        List<Node> open = new LinkedList<>();
        List<Node> close = new LinkedList<>();
        open.add(start);
        Node current;

        while (true) {
            current = fLowest(open);
            open.remove(current);
            close.add(current);

            boolean x = current.getX() == end.getX();
            boolean y = current.getY() == end.getY();
            boolean z = current.getZ() == end.getZ();

            if (x && y && z) {
                break;
            }

            List<Node> adjacent = getAdjacent(current, close);
            for (Node node : adjacent) {

                if (!open.contains(node)) {
                    node.setParent(current);
                    node.calcH(end);
                    open.add(node);
                } else if (node.getG() > node.calcG(current)) {
                    node.setParent(current);
                }
            }

            if (open.isEmpty())
                return new LinkedList<>();
        }
        return calc(start, current);
    }

    private List<Node> calc(Node start, Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node node = goal;
        boolean done = false;

        while (!done) {
            path.addFirst(node);
            node = node.getParent();
            if (node.equals(start)) done = true;
        }

        return path;
    }

    private List<Node> getAdjacent(Node current, List<Node> closed) {
        List<Node> adjacent = new ArrayList<>();
        for (Node node : direction) {
            //一旦斜めを除外//
            if (Math.abs(node.getX()) + Math.abs(node.getZ()) == 2) continue;
            Node next = current.add(node);
            next.setParent(current);
            if (map.getOrDefault(next, true) && isPassable(next) && !closed.contains(next)) {
                next.setG(current, 1);
                adjacent.add(next);
            }
        }

        return adjacent;
    }

    private Node fLowest(List<Node> nodes) {
        return nodes.stream().min(Comparator.comparing(node -> node.getG() + node.getH())).orElse(null);
    }

    public abstract boolean isPassable(Node next);

    public abstract Map<Node, Boolean> collect();
}
