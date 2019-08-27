package Algorithm_Model;

import java.util.ArrayList;
import java.util.Stack;

public interface Algorithm {

    ArrayList<Node> search(int[] board, Node parent);



    int getMaxDepth(Stack<Node> solutionPath, int[] goal);

    int getCostPath();

    int getSearchDepth();

    ArrayList<Node> getExplored();
}
