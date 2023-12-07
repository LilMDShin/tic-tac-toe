package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class MinMax {

    public static int maxDepth(int nbRealMovesMade) {
        int maxNbMoves = 9;
        int maxDepth;
        if (nbRealMovesMade < 4) {
            // Early game has a lot of possibilities
            // To simplify, only the max player at depth 0 will try to max their gain
            maxDepth = 1;
        }
        else if (nbRealMovesMade < 7) {
            maxDepth = 3;
        }
        else {
            maxDepth = 1;
        }
        return(maxDepth);
    }

    public static void evaluateMinMaxValues(GameTree root) {
        int temp;
        Deque<GameTree> stack = new ArrayDeque<>();
        stack.push(root);
        GameTree currentNode;
        GameTree parentNode;
        List<GameTree> childNodesWithMinMaxValue = new ArrayList<>();
        List<Integer> listGameValue = new ArrayList<>();
        List<GameTree> exploredNodes = new ArrayList<>();
        boolean isExplored;
        while (!stack.isEmpty()) {
            currentNode = stack.peek();
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
                    while (parentNode.parent != null && parentNode.parent.children.size() == 1) {
                        stack.pop();
                        temp = parentNode.minmaxValue;
                        parentNode = parentNode.parent;
                        parentNode.minmaxValue = temp;
                    }
                }
                if (!childNodesWithMinMaxValue.contains(parentNode)) {
                    childNodesWithMinMaxValue.add(parentNode);
                }
                listGameValue = new ArrayList<>();
            }
            exploredNodes.add(currentNode);
        }
        sortChildNoesWithMinMaxValue(childNodesWithMinMaxValue, listGameValue);
        listGameValue = new ArrayList<>();
        for (int int_index = 0; int_index < root.children.size(); int_index++) {
            listGameValue.add(root.children.get(int_index).gameValue);
        }
        root.minmaxValue = Collections.max(listGameValue);
    }

    private static void sortChildNoesWithMinMaxValue(@NotNull List<GameTree> childNodesWithMinMaxValue, List<Integer> listGameValue) {
        GameTree parentNode;
        GameTree currentNode = new GameTree(null);
        int index = 0;
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
        if (currentNode.parent == null && index == 1) {
            for (int int_index = 0; int_index < currentNode.children.size(); int_index++) {
                currentNode.children.get(int_index).minmaxValue = currentNode.children.get(int_index).gameValue;
            }
        }
    }

}
