package com.gaoge;

import java.util.*;

/**
 * @author gaoge
 */
//@Data
public class Node {
    public int data;
    public Node left;
    public Node right;
    public int count;
    private int level;

    public Node(int data) {
        this.data = data;
        left = null;
        right = null;
        this.level = 1;
        this.count = 1;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }

    public void addNode(Node node) {
        addNode(this, node);
    }

    private void addNode(Node current, Node sub) {
        if (current.data == sub.data) {
            current.count++;
        } else if (current.data > sub.data) {
            if (current.left == null) {
                current.left = sub;
                sub.level = current.level + 1;
            } else {
                addNode(current.left, sub);
            }
        } else {
            if (current.right == null) {
                current.right = sub;
                sub.level = current.level + 1;
            } else {
                addNode(current.right, sub);
            }
        }
    }

    public void lvrOrder() {
        lvrRec(this);
        System.out.println();
    }

    private void lvrRec(Node node) {
        if (node != null) {
            lvrRec(node.left);
            System.out.print(node.data + ", ");
            lvrRec(node.right);
        }
    }

    public void rvlOrder() {
        rvlRec(this);
        System.out.println();
    }

    private void rvlRec(Node node) {
        if (node != null) {
            rvlRec(node.right);
            System.out.print(node.data + ",");
            rvlRec(node.left);
        }
    }

    public List<Integer> layerRec() {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(this);
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            result.add(tmp.data);

            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
        }
        return result;
    }

    public int getHigh() {
        return getHigh(this);
    }

    private int getHigh(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHigh, rightHigh;
        return (leftHigh = getHigh(node.left)) > (rightHigh = getHigh(node.right)) ? ++leftHigh : ++rightHigh;
    }

    public void convert2list() {
        reset(this, null);
    }

    private Node reset(Node node, Node last) {
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            last = reset(node.left, last);
        }
        node.left = last;
        if (last != null) {
            last.right = node;
        }

        if (node.right != null) {
            return reset(node.right, node);
        }
        return node;
    }

    public void printLeftList() {
        Node tmp = this;
        while (true) {
            if (tmp.left == null) {
                break;
            }
            tmp = tmp.left;
        }
        while (tmp != null) {
            System.out.print(tmp.data + " -> ");
            tmp = tmp.right;
        }
    }

    public void printRightList() {
        Node tmp = this;
        while (true) {
            if (tmp.right == null) {
                break;
            }
            tmp = tmp.right;
        }
        while (tmp != null) {
            System.out.print(tmp.data + " <- ");
            tmp = tmp.left;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int currentData;
        Node root = new Node(currentData = random.nextInt(20));
        System.out.println("\n----------Numbers----------");
        System.out.print(currentData + ", ");
        int numCount = 8;
        if (args != null && args.length >= 1) {
            try {
                numCount = Integer.valueOf(args[0]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        for (int i = 0; i < numCount; i++) {
            root.addNode(new Node(currentData = random.nextInt(20)));
            System.out.print(currentData + ", ");
        }
        System.out.println("\n----------LVR Order traverse----------");
        root.lvrOrder();
        System.out.println("\n----------RVL Order traverse----------");
        root.rvlOrder();
        System.out.println("\n----------Level Order traverse----------");
        System.out.println(root.layerRec());
        System.out.println("\n----------Height---------");
        System.out.println(root.getHigh());

        //Convert to double directions list
        root.convert2list();

        System.out.println("\n---------Left direction print-----------");
        root.printLeftList();
        System.out.println("\n\n---------Right direction print-----------");
        root.printRightList();
        System.out.println();
    }
}
