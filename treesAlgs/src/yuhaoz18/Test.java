package yuhaoz18;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(11);
        tree.insert(3);
        tree.insert(19);
        tree.insert(2);
        tree.insert(5);
        tree.insert(17);
        tree.insert(23);
        tree.insert(29);
        tree.delete(11);
        // tree.root = new TreeNode(1);
        // tree.root.rChild = new TreeNode(3);
        System.out.println(tree.search(109));
        tree.printTree();
    }
}