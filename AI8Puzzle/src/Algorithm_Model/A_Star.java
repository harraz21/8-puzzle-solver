package Algorithm_Model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author Harraz21
 */
public class A_Star implements Algorithm {

    private GreedyNode initialState;
    private Heuristic heuristicType;
    private PriorityQueue<GreedyNode> frontier;
    private ArrayList<GreedyNode> frontierList;
    private ArrayList<GreedyNode> explored;
    private ArrayList<Node> path;
    private int pathCost;


    public A_Star(Heuristic heuristicType) {
        this.heuristicType = heuristicType;
    }

    @Override
    public ArrayList<Node> search(int[] board, Node parent) {
        initialState = new GreedyNode(board, null);
        frontier = new PriorityQueue<>();
        frontierList = new ArrayList<>();
        frontier.add(initialState);
        frontierList.add(initialState);
        explored = new ArrayList<>();
        while (!frontier.isEmpty()) {
            GreedyNode state = frontier.poll();
            frontierList.remove(state);
            explored.add(state);

            if (java.util.Arrays.equals(state.state, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})) {
                pathCost = state.getCost();
                return pathToGoal(state);
            }
            state.generateChildren();
            for (Node node : state.getChildren()) {
                if (node != null) {
                    node = new GreedyNode(node.state, state);
                    if (!containedInFrontier((GreedyNode) node) && !containedInExplored((GreedyNode) node)) {
                        calculateWeight((GreedyNode) node);
                        frontier.add((GreedyNode) node);
                        frontierList.add((GreedyNode) node);
                    } else {
                        for (GreedyNode test : frontierList) {
                            if (java.util.Arrays.equals(test.state, node.state)) {
                                calculateWeight((GreedyNode) node);
                                if (test.getWeight() > ((GreedyNode) node).getWeight()) {
                                    frontierList.remove(test);
                                    frontier.remove(test);
                                    frontier.add((GreedyNode) node);
                                    frontierList.add((GreedyNode) node);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }



    @Override
    public int getMaxDepth(Stack<Node> solutionPath, int[] goal) {
        int maxDepth = 0;
        for (GreedyNode node : frontierList) {
            if (node.getCost() > maxDepth)
                maxDepth = node.getCost();
        }
        for (GreedyNode node : explored) {
            if (node.getCost() > maxDepth)
                maxDepth = node.getCost();
        }
        return maxDepth;
    }

    private boolean containedInFrontier(GreedyNode node) {
        for (GreedyNode test : frontierList) {
            if (java.util.Arrays.equals(test.state, node.state)) {
                return true;
            }
        }
        return false;
    }

    private boolean containedInExplored(GreedyNode node) {
        for (GreedyNode test : explored) {
            if (java.util.Arrays.equals(test.state, node.state)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Node> pathToGoal(GreedyNode state) {
        Stack<Node> pathToGoal = new Stack<>();
        path = new ArrayList<>();
        Node node = state;
        pathToGoal.push(state);
        while (node.getParent() != null) {
            pathToGoal.push(node.getParent());
            node = node.getParent();
        }
        int size = pathToGoal.size();
        for (int i = size; i > 0; i--) {
            path.add(pathToGoal.pop());
        }
        return path;
    }

    private void calculateWeight(GreedyNode greedyNode) {
        greedyNode.setWeight(heuristicType.getHeuristic(greedyNode.getState()) + greedyNode.getCost());
    }

    public ArrayList<GreedyNode> getExploredgreedy() {
        return explored;
    }

    @Override
    public ArrayList<Node> getExplored() {
        ArrayList<Node> x = new ArrayList<Node>();
        for (GreedyNode i :
                explored) {
            x.add(i);
        }
        return x;
    }


    @Override
    public int getCostPath() {
        return pathCost;
    }

    @Override
    public int getSearchDepth() {
        int searchDepth = 0;

        for (GreedyNode node : frontierList) {
            if (node.getDepth() > searchDepth)
                searchDepth = node.getDepth();
        }
        for (GreedyNode node : explored) {
            if (node.getDepth() > searchDepth)
                searchDepth = node.getDepth();
        }
        return searchDepth;
    }


    public Heuristic getHeuristicType() {
        return heuristicType;
    }


}
