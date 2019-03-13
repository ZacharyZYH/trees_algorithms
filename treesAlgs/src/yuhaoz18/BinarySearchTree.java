package yuhaoz18;

import java.util.ArrayList;

/**
 * BinarySearchTree
 */
public class BinarySearchTree {
    public class TreeNode {
        public TreeNode lChild = null;
        public TreeNode rChild = null;
        public TreeNode parent = null;
        public int key;
        public int height;
    
        public TreeNode(){}
    
        public TreeNode(int key){
            this.key = key;
        }
    }    
    public TreeNode root = null;

    public BinarySearchTree(){}

    public static BinarySearchTree create(){
        return new BinarySearchTree();
    }
    
    public boolean search(int key){
        return search(key, root);
    }

    private boolean search(int key, TreeNode node){
        if(node == null) return false;
        if(key == node.key) return true;
        if(key > node.key) return search(key, node.rChild);
        return search(key, node.lChild);
    }

    public void insert(int key){
        if(root == null)
            root = new TreeNode(key);
        else
            insert(key, root);
    }

    private void insert(int key, TreeNode r) {
        if(r.key < key){
            if(r.rChild == null){
                r.rChild = new TreeNode(key);
                r.rChild.parent = r;
            }
            else
                insert(key, r.rChild);
        }
        else {
            if(r.lChild == null){
                r.lChild = new TreeNode(key);
                r.lChild.parent = r;
            }
            else
                insert(key, r.lChild);
        }
    }

    public void delete(int key){
        root=delete(key, root);
    }

    private TreeNode delete(int key, TreeNode root){
        if(root == null) return root;
        int result = key - root.key;
        if(result<0){
            root.lChild=delete(key,root.lChild);
        }else if(result>0){
            root.rChild=delete(key,root.rChild);
        }else if(root.lChild!=null&&root.rChild!=null){
            root.key=findMin(root.rChild).key;
            root.rChild=delete(root.key, root.rChild);
        }else{
            root=(root.lChild!=null)?root.lChild:root.rChild;
        }
        return root;
    }

    
    public int findMin(){
    
        return  findMin(root).key;
    }
    private TreeNode findMin(TreeNode node) {
        if(node==null){
            return null;
        }else if(node.lChild==null){
            return node;
        }
        return findMin(node.lChild);
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
                    temp.add(frontier.get(i).lChild);
                    temp.add(frontier.get(i).rChild);
                }
            }  
            frontier = temp;
            System.out.print("\n");
        }
    }
}