package exercise;

import java.util.*;

public class BinaryTree {

    // 방향 정의
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public Node root;

    public class Node{

        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }

        // 특정 값을 가지는 좌측 또는 우측 자식노드를 생성
        public Node addNodeWithValue(int direction, int value) {
            if (direction == LEFT) {
                return this.left = new Node(value);
            } else {
                return this.right = new Node(value);
            }
        }
    }

    // 간단한 Queue
    public class Q<E>{
        private List<E> queue = new ArrayList<E>();

        public void add(E value) {
            queue.add(value);
        }

        public E remove() {
            if (queue.size() == 0) {
                return null;
            }
            return queue.remove(0);
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // for common use
    // root부터 내려가면서 좌->우로 값 채우기
    public void addValuesLeftToRightFromRoot(List<Integer> values) {
        root = new Node(values.remove(0));
        Q<Node> queue = new Q<Node>();
        queue.add(root);
        addValuesLeftToRightSub(queue, values);
    }

    // for common use
    private void addValuesLeftToRightSub(Q<Node> queue, List<Integer> values) {
        if (queue.isEmpty() || values.size() == 0) {
            return;
        }
        Node cursor = queue.remove();
        queue.add(cursor.addNodeWithValue(LEFT, values.remove(0)));
        if (values.size() > 0) {
            queue.add(cursor.addNodeWithValue(RIGHT, values.remove(0)));
            addValuesLeftToRightSub(queue, values);
        }
    }

    // for common use
    // root부터 내려가면서 좌->우로 값 표시
    public void printTreeLeftToRightFromRoot() {
        Q<Node> queue = new Q<Node>();
        queue.add(root);
        printLeftToRightSub(queue);
    }

    // for common use
    private void printLeftToRightSub(Q<Node> queue) {
        if (queue.isEmpty()) {
            return;
        }
        Node cursor = queue.remove();
        System.out.print("value : " + cursor.value);
        if (cursor.left != null) {
            System.out.print(", left : " + cursor.left.value);
            queue.add(cursor.left);
        }
        if (cursor.right != null) {
            System.out.print(", right : " + cursor.right.value);
            queue.add(cursor.right);
        }
        System.out.println();
        printLeftToRightSub(queue);
    }

    // for the question 1
    // 트리 좌우 뒤집기.
    public void mirror() {
        mirrorSub(root);
    }

    // for the question 1
    private void mirrorSub(Node node) {
        if (node == null || (node.left == null && node.right == null)) {
            return;
        }
        Node temp = node.left;
        node.left = node.right;
        node.right = temp;
        mirrorSub(node.left);
        mirrorSub(node.right);
    }

    // for the question 2
    public void setupWithArray(int[] values) {
        int centerIndex = values.length / 2;
        root = new Node(values[centerIndex]);
        setupWithArraySub(values, 0, centerIndex - 1, centerIndex + 1, values.length - 1, root);
    }

    // for the question 2
    public void setupWithArraySub(int[] values, int parentLLB, int parentLRB,
                                  int parentRLB, int parentRRB, Node node) {

        if (parentLLB <= parentLRB) {
            int childCenter = (int)Math.ceil((parentLRB - parentLLB) / 2.0) + parentLLB;
            //System.out.println(String.format("LEFT  :: LB, CENTER, RB : %d, %d, %d", parentLLB, childCenter, parentLRB));
            Node childNode = node.addNodeWithValue(LEFT, values[childCenter]);
            setupWithArraySub(values, parentLLB, childCenter-1, childCenter+1, parentLRB, childNode);
        }

        if (parentRLB <= parentRRB) {
            int childCenter = (int)Math.ceil((parentRRB - parentRLB) / 2.0) + parentRLB;
            //System.out.println(String.format("RIGHT :: LB, CENTER, RB : %d, %d, %d", parentRLB, childCenter, parentRRB));
            Node childNode = node.addNodeWithValue(RIGHT, values[childCenter]);
            setupWithArraySub(values, parentRLB, childCenter-1, childCenter+1, parentRRB, childNode);
        }

    }

    // for the question 3
    public void printTreeRightMostOnlyFromRoot() {
        Set<Integer> printedDepthSet = new HashSet<Integer>();
        printTreeRightMostOnlyFromRootSub(root, printedDepthSet, 1);
    }

    // for the question 3
    private void printTreeRightMostOnlyFromRootSub(Node cursor, Set<Integer> printedDepthSet, int depth) {
        if (!printedDepthSet.contains(depth)) {
            printedDepthSet.add(depth);
            System.out.println("depth : " + depth + " => value : " + cursor.value);
        }
        if (cursor.right != null) {
            printTreeRightMostOnlyFromRootSub(cursor.right, printedDepthSet, depth + 1);
        }
        if (cursor.left != null) {
            printTreeRightMostOnlyFromRootSub(cursor.left, printedDepthSet, depth + 1);
        }
    }

    public static void main(String args[]) {

        BinaryTree tree = new BinaryTree();

        /* 문제 1 풀이 : 샘플 값을 채우고, 트리의 좌우를 뒤집기 */
        /*
        List<Integer> sampleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        tree.addValuesLeftToRightFromRoot(sampleValues);

        tree.printTreeLeftToRightFromRoot();
        System.out.println();

        tree.mirror();
        tree.printTreeLeftToRightFromRoot();
        */

        /* 문제 2 풀이 : 정렬된 배열을 바이너리 서치 트리로 만들기
            Convert sorted array to binary search tree
            Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
        */
        /*
        int[] sampleValueArray = new int[]{1,2,3,4,5,6,7,8,9,10};
        tree.setupWithArray(sampleValueArray);
        tree.printTreeLeftToRightFromRoot();
        */

        /* 문제 3 풀이 : 샘플 값을 채우고, 루트 노드 (depth 1)부터 마지막 depth까지 depth별 가장 우측 노드 값을 순서대로 보여주기
            Binary Tree Right Side View

            Given a binary tree, imagine yourself standing on the right side of it,
            return the values of the nodes you can see ordered from top to bottom.
            For example, given the following binary tree,
              1            <---
            /   \
            2     3         <---
            \
             5             <---
            You can see [1, 3, 5].
        */
        List<Integer> sampleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        tree.addValuesLeftToRightFromRoot(sampleValues);
        tree.printTreeRightMostOnlyFromRoot();
    }

}
