package org.example;

import java.util.*;

public class MinMax {

    // GameTree treeRoot;

    public static int maxDepth(int nbRealMovesMade) {
        int maxNbMoves = 9;
        int maxDepth;
        if (nbRealMovesMade < 4) {
            // Early game has a lot of possibilities
            // To simplify, only the max player at depth 0 try will try to max their gain
            maxDepth = 1;
        }
        else if (nbRealMovesMade < 7) {
            maxDepth = 3;
        }
        else {
            maxDepth = maxNbMoves - nbRealMovesMade;
        }
        if (maxDepth < 0) {
            maxDepth = 0;
        }
        return(maxDepth);
    }

    // Need to correct, does not work as intended
    public void getMinMaxValues(GameTree root) {
        // Check GameTree change minMaXValue to Integer.Max ?
        int temp;
        // To really really really test
        Stack<GameTree> stack = new Stack<>();
        stack.push(root);
        GameTree currentNode;
        GameTree parentNode;
        // Check
        List<GameTree> childNodesWithMinMaxValue = new ArrayList<>();
        List<Integer> listGameValue = new ArrayList<>();
        List<GameTree> exploredNodes = new ArrayList<>();
        boolean isExplored;
        // exploredNodes.add(root);
        while (!stack.empty()) {
            currentNode = stack.peek();
            // exploredNodes.add(currentNode);
            isExplored = exploredNodes.contains(currentNode);
            if (!currentNode.children.isEmpty() && !isExplored) { // && !isInList(exploredNodes, currentNode)) {
                for (int i = 0; i < currentNode.children.size(); i++) {
                    stack.push(currentNode.children.get(i));
                }
                exploredNodes.add(currentNode);
            }
            else if (isExplored) {
                stack.pop();
            }
            else {
                parentNode = currentNode.parent;
                for (int i = 0; i < parentNode.children.size(); i++) {
                    listGameValue.add(stack.pop().gameValue);
                }
                // To verify
                if (parentNode.depth % 2 == 0) {
                    parentNode.minmaxValue = Collections.max(listGameValue);
                }
                else {
                    parentNode.minmaxValue = Collections.min(listGameValue);
                }
                if (parentNode.parent != null) {
                    while (parentNode.parent.children.size() == 1) {
                        stack.pop();
                        temp = parentNode.minmaxValue;
                        parentNode = parentNode.parent;
                        parentNode.minmaxValue = temp;
                        if (parentNode.parent == null) {
                            break;
                        }
                    }
                }
                /*
                while (parentNode.parent.children.size() == 1) {
                    stack.pop();
                    temp = parentNode.minmaxValue;
                    parentNode = parentNode.parent;
                    parentNode.minmaxValue = temp;
                    if (parentNode.parent == null) {
                        break;
                    }
                }

                */
                if (!childNodesWithMinMaxValue.contains(parentNode)) {
                    childNodesWithMinMaxValue.add(parentNode);
                }
                listGameValue = new ArrayList<>();
            }
            exploredNodes.add(currentNode);
        }
        int index = 0;
        // Create function to sort childNodesWithMinMaxValue by depth
        // Sort from smallest depth to highest depth
        // Collections.sort(childNodesWithMinMaxValue);
        // Should sort from highest depth to lowest
        childNodesWithMinMaxValue.sort(Collections.reverseOrder());
        while (index < childNodesWithMinMaxValue.size()) {
            currentNode = childNodesWithMinMaxValue.get(index);
            if (currentNode.parent != null) {
                parentNode = currentNode.parent;
                if (parentNode.minmaxValue == Integer.MAX_VALUE) {
                    for (int i = 0; i < parentNode.children.size(); i++) {
                        listGameValue.add(parentNode.children.get(i).minmaxValue);
                    }
                    // To verify
                    if (parentNode.depth % 2 == 0) {
                        parentNode.minmaxValue = Collections.max(listGameValue);
                    }
                    else {
                        parentNode.minmaxValue = Collections.min(listGameValue);
                    }
                    listGameValue = new ArrayList<>();
                }
            }
            index++;
        }

    }

}
