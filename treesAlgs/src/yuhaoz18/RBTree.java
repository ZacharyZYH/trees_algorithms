package yuhaoz18;

public class RBTree { 

    private static enum Color {
        RED, BLACK, ERROR
    } 

    private class Node { 
        Color color; 
        int key; 
        Node lChild = null; 
        Node rChild = null; 

        Node(Color color, int key) {
            this.color = color;
            this.key = key;
        }
    }

    private Node root = null; 
    private boolean change; 
    private int lmax; 

    RBTree(){}

    public static RBTree create(){
        return new RBTree();
    }
    
    private boolean isRed(Node n) {
        return n != null && n.color == Color.RED;
    }
    
    private boolean isBlack(Node n) {
        return n != null && n.color == Color.BLACK;
    }
    
    private Node rotateL(Node v) {
        Node u = v.rChild, t2 = u.lChild;
        u.lChild = v;
        v.rChild = t2;
        return u;
    }
    
    private Node rotateR(Node u) {
        Node v = u.lChild, t2 = v.rChild;
        v.rChild = u;
        u.lChild = t2;
        return v;
    }

    private Node rotateLR(Node t) {
        t.lChild = rotateL(t.lChild);
        return rotateR(t);
    }

    private Node rotateRL(Node t) {
        t.rChild = rotateR(t.rChild);
        return rotateL(t);
    }

    public boolean search(int key) {
        Node t = root;
        while (t != null) {
            if (key < t.key)
                t = t.lChild;
            else if (key > t.key)
                t = t.rChild;
            else
                return true;
        }
        return false;
    }

    public void insert(int key) {
        root = insert(root, key);
        root.color = Color.BLACK;
    }

    private Node insert(Node t, int key) {
        if (t == null) {
            change = true;
            return new Node(Color.RED, key);
        } else if (key < t.key) {
            t.lChild = insert(t.lChild, key);
            return balance(t);
        } else if (key > t.key) {
            t.rChild = insert(t.rChild, key);
            return balance(t);
        } else {
            return t;
        }
    }

    private Node balance(Node t) {
        if (!change)
            return t;
        else if (!isBlack(t))
            return t; 
        else if (isRed(t.lChild) && isRed(t.lChild.lChild)) {
            t = rotateR(t);
            t.lChild.color = Color.BLACK;
        } else if (isRed(t.lChild) && isRed(t.lChild.rChild)) {
            t = rotateLR(t);
            t.lChild.color = Color.BLACK;
        } else if (isRed(t.rChild) && isRed(t.rChild.lChild)) {
            t = rotateRL(t);
            t.rChild.color = Color.BLACK;
        } else if (isRed(t.rChild) && isRed(t.rChild.rChild)) {
            t = rotateL(t);
            t.rChild.color = Color.BLACK;
        } else
            change = false;
        return t;
    }

    public void delete(int key) {
        root = delete(root, key);
        if (root != null)
            root.color = Color.BLACK;
    }

    private Node delete(Node t, int key) {
        if (t == null) {
            change = false;
            return null;
        } else if (key < t.key) {
            t.lChild = delete(t.lChild, key);
            return balanceL(t);
        } else if (key > t.key) {
            t.rChild = delete(t.rChild, key);
            return balanceR(t);
        } else {
            if (t.lChild == null) {
                switch (t.color) {
                case RED:
                    change = false;
                    break;
                case BLACK:
                    change = true;
                    break;
                }
                return t.rChild; 
            } else {
                t.lChild = deleteMax(t.lChild); 
                t.key = lmax; 
                return balanceL(t);
            }
        }
    }

    private Node deleteMax(Node t) {
        if (t.rChild != null) {
            t.rChild = deleteMax(t.rChild);
            return balanceR(t);
        } else {
            lmax = t.key; 
            switch (t.color) {
            case RED:
                change = false;
                break;
            case BLACK:
                change = true;
                break;
            }
            return t.lChild; 
        }
    }

    private Node balanceL(Node t) {
        if (!change)
            return t; 
        else if (isBlack(t.rChild) && isRed(t.rChild.lChild)) {
            Color rb = t.color;
            t = rotateRL(t);
            t.color = rb;
            t.lChild.color = Color.BLACK;
            change = false;
        } else if (isBlack(t.rChild) && isRed(t.rChild.rChild)) {
            Color rb = t.color;
            t = rotateL(t);
            t.color = rb;
            t.lChild.color = Color.BLACK;
            t.rChild.color = Color.BLACK;
            change = false;
        } else if (isBlack(t.rChild)) {
            Color rb = t.color;
            t.color = Color.BLACK;
            t.rChild.color = Color.RED;
            change = (rb == Color.BLACK);
        } else if (isRed(t.rChild)) {
            t = rotateL(t);
            t.color = Color.BLACK;
            t.lChild.color = Color.RED;
            t.lChild = balanceL(t.lChild);
            change = false;
        } else { 
            System.err.println("(Left) This program is buggy");
            System.exit(1);
        }
        return t;
    }
    
    private Node balanceR(Node t) {
        if (!change)
            return t; 
        else if (isBlack(t.lChild) && isRed(t.lChild.rChild)) {
            Color rb = t.color;
            t = rotateLR(t);
            t.color = rb;
            t.rChild.color = Color.BLACK;
            change = false;
        } else if (isBlack(t.lChild) && isRed(t.lChild.lChild)) {
            Color rb = t.color;
            t = rotateR(t);
            t.color = rb;
            t.lChild.color = Color.BLACK;
            t.rChild.color = Color.BLACK;
            change = false;
        } else if (isBlack(t.lChild)) {
            Color rb = t.color;
            t.color = Color.BLACK;
            t.lChild.color = Color.RED;
            change = (rb == Color.BLACK);
        } else if (isRed(t.lChild)) {
            t = rotateR(t);
            t.color = Color.BLACK;
            t.rChild.color = Color.RED;
            t.rChild = balanceR(t.rChild);
            change = false;
        } else { 
            System.err.println("(Right) This program is buggy");
            System.exit(1);
        }
        return t;
    }

    public void printTree() {
        System.out.println(toGraph("", "", root).replaceAll("\\s+$", ""));
    }

    private String toGraph(String head, String bar, Node t) {
        String graph = "";
        if (t != null) {
            graph += toGraph(head + "    ", "/", t.rChild);
            String node = t.color == Color.RED ? "RED" : "BLACK";
            node += ":" + t.key;
            graph += String.format("%s%s%s%n", head, bar, node);
            graph += toGraph(head + "    ", "\\", t.lChild);
        }
        return graph;
    }
}