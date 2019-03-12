package yuhaoz18;

import java.util.ArrayList;

/**
* AVLTree
*/
class AVLTree{
    public class TreeNode {
        private int h;
        private int balance;
        int key;
        private TreeNode left, right, parent;
        public TreeNode (int key, TreeNode parent) {
            this.key = key;
            this.left = this.right = null;
            this.parent = parent;
            this.h = 1;
            this.balance = 0;
        }
        public TreeNode next(){
            return getHigherNode(this.key);
        }
        public TreeNode previus(){
            return getLowerNode(this.key);
        }
    }

    public AVLTree(){}
    
    public static AVLTree create(){
        return new AVLTree();
    }
    private TreeNode root;
    
    private TreeNode getHigherNode(int key) {
        TreeNode p = root;
        while (p != null) {
            int cmp = key - p.key;
            if (cmp < 0) {
                if (p.left != null)
                p = p.left;
                else
                return p;
            } else {
                if (p.right != null) {
                    p = p.right;
                } else {
                    TreeNode parent = p.parent;
                    TreeNode ch = p;
                    while (parent != null && ch == parent.right) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent;
                }
            }
        }
        return null;
    }
    
    private TreeNode getLowerNode(int key) {
        TreeNode p = root;
        while (p != null) {
            int cmp = key - p.key;
            if (cmp > 0) {
                if (p.right != null)
                p = p.right;
                else
                return p;
            } else {
                if (p.left != null) {
                    p = p.left;
                } else {
                    TreeNode parent = p.parent;
                    TreeNode ch = p;
                    while (parent != null && ch == parent.left) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent;
                }
            }
        }
        return null;
    }
    
    public TreeNode getFirstNode(){
        return min(root);
    }
    
    public TreeNode getLastNode(){
        return max(root);
    }
    
    private int height(TreeNode x, TreeNode y){
        if(x == null && y == null) return 0;
        else if(x == null) return y.h;
        else if(y == null) return x.h;
        else return Math.max(x.h, y.h);
    }
    
    private int balance(TreeNode x, TreeNode y){
        if(x == null && y == null) return 0;
        else if(x == null) return - y.h;
        else if(y == null) return x.h;
        else return x.h - y.h;
    }
    
    private TreeNode insert (TreeNode node,int key, TreeNode parent){
        if (node == null){
            TreeNode newnode = new TreeNode(key, parent);
            return newnode;
        }
        int compareResult = key - node.key;
        if (compareResult > 0){node.right = insert(node.right, key, node); node.h = height(node.left, node.right) + 1;}
        else if(compareResult < 0){node.left = insert(node.left, key, node); node.h = height(node.left, node.right) + 1;}
        
        node.balance = balance(node.left, node.right);
        if(node.balance == -2){
            node = leftRotation(node);
        }else if(node.balance == 2){
            node = rightRotation(node);
        }
        return node;
    }
    
    private TreeNode leftRotation(TreeNode node) {
        if(node.right.right == null && node.right.left != null){
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }else if(node.right.left == null || node.right.left.h <= node.right.right.h){
            TreeNode newnode = node.right;
            newnode.parent = node.parent;
            node.right = newnode.left;
            if(node.right != null)
            node.right.parent = node;
            node.h = height(node.left, node.right)+1;
            node.parent = newnode;
            node.balance = balance(node.left, node.right);
            newnode.left = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        }else{
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }
        return node;
    }
    private TreeNode rightRotation(TreeNode node){
        if(node.left.right != null && node.left.left == null){
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }else if(node.left.right == null || node.left.right.h <= node.left.left.h){
            TreeNode newnode = node.left;
            newnode.parent = node.parent;
            node.left = newnode.right;
            if(node.left != null)
            node.left.parent = node;
            node.h = height(node.left, node.right)+1;
            node.parent = newnode;
            node.balance = balance(node.left, node.right);
            newnode.right = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        }else{
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }
        return node;
    }
    
    public void insert(int key) {
        root = insert(root, key, null);
    }
    
    private TreeNode delete(TreeNode node, int key){
        if(node == null) return null;
        int compareResult = key - node.key;
        if(compareResult > 0){
            node.right = delete(node.right, key);
        }else if(compareResult < 0){
            node.left = delete(node.left, key);
        }else{
            if(node.right == null && node.left == null){
                node = null;
            }else if(node.right == null){
                node.left.parent = node.parent;
                node = node.left;
            }else if(node.left == null){
                node.right.parent = node.parent;
                node = node.right;
            }else{
                if(node.right.left == null){
                    node.right.left = node.left;
                    node.right.parent = node.parent;
                    node.right.parent = node.parent;
                    node.left.parent = node.right;
                    node = node.right;
                }else{
                    TreeNode res = min(node.right);
                    node.key = res.key;
                    delete(node.right, node.key);
                }
            }
        }
        if(node != null) {
            node.h = height(node.left, node.right) + 1;
            node.balance = balance(node.left, node.right);
            if (node.balance == -2) {
                node = leftRotation(node);
            } else if (node.balance == 2) {
                node = rightRotation(node);
            }
        }
        return node;
    }
    
    public void delete(int key) {
        root = delete(root, key);
    }
    
    public int minKey(){
        return min(root).key;
    }
    
    public int maxKey(){
        return max(root).key;
    }
    
    private TreeNode min(TreeNode node){
        if(node.left == null) return node;
        return min(node.left);
    }
    
    private TreeNode max(TreeNode node){
        if(node.right == null) return node;
        return min(node.right);
    }
    
    private boolean search(TreeNode node, int key){
        if(node == null) return false;
        int compareResult = key - node.key;
        if(compareResult == 0){
            return true;
        }else if(compareResult > 0){
            return search(node.right, key);
        }else{
            return search(node.left, key);
        }
    }
    
    public boolean search(int key) {
        return search(root, key);
    }
    
    public void printTree(){
        ArrayList<TreeNode> frontier = new ArrayList<>();
        frontier.add(root);
        while(!frontier.isEmpty()){
            ArrayList<TreeNode> temp = new ArrayList<>();
            for(int i = 0; i < frontier.size(); i++){
                System.out.print(" ");
                if(frontier.get(i) == null) System.out.print("null");
                else {
                    System.out.print(frontier.get(i).key);
                    temp.add(frontier.get(i).left);
                    temp.add(frontier.get(i).right);
                }
            }  
            frontier = temp;
            System.out.print("\n");
        }
    }
    
}