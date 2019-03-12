package yuhaoz18;

import java.util.List;
import java.util.ArrayList;

public class BTree { 
    
    private final int m;           
    private final int hm; 


    private abstract class Node { 
        List<Integer> numbers = new ArrayList<Integer>(m);     
        List<Node> children = new ArrayList<Node>(m + 1); 
        abstract Node deactivate();
        Node trim() { return children.size() == 1 ? children.get(0) : this; }
    }

    public BTree(int m) {
        this.m = m;
        hm = (m + 1) / 2; 
    }

    public BTree(){
        this.m = 5;
        hm = 3;
    }

    public static BTree create() {
        return new BTree();
    }

    public static BTree create(int m){
        return new BTree(m);
    }
    
    private Node root = null; 

    private class NodeA extends Node { 
        NodeA(int key, Node l, Node r) { numbers.add(key); children.add(l); children.add(r); }
        Node deactivate() { return new NodeN(numbers.get(0), children.get(0), children.get(1)); }
    }

    private class NodeN extends Node { 
        NodeN() {}
        NodeN(int key, Node l, Node r) { numbers.add(key); children.add(l); children.add(r); }
        Node deactivate() { return this; }
    }

    private boolean activeI(Node t) { return t instanceof BTree.NodeA; }    
    private boolean activeD(Node t) { return t != null && t.children.size() < hm; }
    
    public void insert(int key) {
        root = insert(root, key).deactivate();
    }
    
    private Node insert(Node t, int key) {
        if (t == null) return new NodeA(key, null, null);
        int i;
        for (i = 0; i < t.numbers.size(); i++) {
            final int cmp = key - t.numbers.get(i);
            if (cmp < 0) {
                t.children.set(i, insert(t.children.get(i), key));
                return balance(t, i);
            }
            else if (cmp == 0) {
                t.numbers.set(i, key);
                return t;
            }
        }
        t.children.set(i, insert(t.children.get(i), key));
        return balance(t, i);
    }
    
    private Node balance(Node t, int i) {
        Node ni = t.children.get(i);
        if (!activeI(ni)) return t;
        
        t.numbers.add(i, ni.numbers.get(0));
        t.children.set(i, ni.children.get(1));
        t.children.add(i, ni.children.get(0));
        return t.numbers.size() < m ? t : split(t);
    }

    private Node split(Node t) {
        int j = hm, i = j - 1; 
        Node r = new NodeN();
        r.numbers.addAll(t.numbers.subList(j, m));
        r.children.addAll(t.children.subList(j, m + 1));
        t.numbers.subList(j, m).clear();
        t.children.subList(j, m + 1).clear();
        return new NodeA(t.numbers.remove(i), t, r);
    }
    
    public void delete(int key) {
        if (root == null) return;
        delete(root, key);
        root = root.trim();
    }
    
    private void delete(Node t, int key) {
        if (t == null) return;
        int i;
        for (i = 0; i < t.numbers.size(); i++) {
            final int cmp = key - t.numbers.get(i);
            if (cmp < 0) {
                delete(t.children.get(i), key);
                balanceL(t, i);
                return;
            }
            else if (cmp == 0) {
                if (t.children.get(i) == null) {
                    
                    t.numbers.remove(i);
                    t.children.remove(i);
                }
                else {
                    
                    t.numbers.set(i, deleteMax(t.children.get(i)));
                    balanceL(t, i);
                }
                return;
            }
        }
        delete(t.children.get(i), key);
        balanceR(t, i);
    }
    
    private int deleteMax(Node t) {
        int j = t.numbers.size(), i = j - 1;
        if (t.children.get(j) != null) {
            int key = deleteMax(t.children.get(j));
            balanceR(t, j);
            return key;
        }
        else {
            t.children.remove(j);
            return t.numbers.remove(i);
        }
    }

    private void balanceL(Node t, int i) {
        Node ni = t.children.get(i);
        if (!activeD(ni)) return;
        
        int j = i + 1;
        int key  = t.numbers.get(i);
        Node nj = t.children.get(j);
        if (nj.children.size() == hm) {
            
            ni.numbers.add(key);
            ni.numbers.addAll(nj.numbers);
            ni.children.addAll(nj.children);
            t.numbers.remove(i);
            t.children.remove(j);
        }
        else t.numbers.set(i, moveRL(key, ni, nj)); 
    }
    
    private void balanceR(Node t, int j) {
        Node nj = t.children.get(j);
        if (!activeD(nj)) return;
        
        int i = j - 1;
        int key  = t.numbers.get(i);
        Node ni = t.children.get(i);
        if (ni.children.size() == hm) {
            
            ni.numbers.add(key);
            ni.numbers.addAll(nj.numbers);
            ni.children.addAll(nj.children);
            t.numbers.remove(i);
            t.children.remove(j);
        }
        else t.numbers.set(i, moveLR(key, ni, nj)); 
    }

    private int moveRL(int key, Node l, Node r) {
        l.numbers.add(key);
        l.children.add(r.children.remove(0));
        return r.numbers.remove(0);
    }
    
    private int moveLR(int key, Node l, Node r) {
        int j = l.numbers.size(), i = j - 1;
        r.numbers.add(0, key);
        r.children.add(0, l.children.remove(j));
        return l.numbers.remove(i);
    }

    public boolean search(int key) {
        Node t = root;
        while (t != null) {
            int i;
            for (i = 0; i < t.numbers.size(); i++) {
                final int cmp = key - t.numbers.get(i);
                if      (cmp <  0) break;
                else if (cmp == 0) return true;
            }
            t = t.children.get(i);
        }
        return false;
    }
    
    public void printTree() {
        System.out.println(toGraph("", root).replaceAll("\\s+$", ""));
    }

    private static final String nl = System.getProperty("line.separator");
    private String toGraph(String head, Node t) {
        if (t == null) return "";
        int i = t.children.size();
        String graph = toGraph(head + "    ", t.children.get(--i));
        graph += head + "∧" + nl;
        do {
            graph += head + t.numbers.get(--i) + nl;
            if (i == 0) graph += head + "∨" + nl;
            graph += toGraph(head + "    ", t.children.get(i));
        } while (i > 0);
        return graph;
    }
}