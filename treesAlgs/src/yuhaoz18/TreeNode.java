package yuhaoz18;

/**
 * TreeNode
 */
public class TreeNode {
    public TreeNode lChild = null;
    public TreeNode rChild = null;
    public TreeNode parent = null;
    public int key;
    public int height;

    public TreeNode(){

    }

    public TreeNode(int key){
        this.key = key;
    }
    

}