package yuhaoz18;

import java.util.Random;

/**
 * Test
 */
public class Test {
    public static void main(String[] args) {
        
        int[] arr;
        System.out.println(300 + " inserts, searches and deletes:");
        arr = setTestArray(300);
        test(arr);
        System.out.println(" ");
        System.out.println(500 + " inserts, searches and deletes:");
        arr = setTestArray(500);
        test(arr);
        System.out.println(" ");
        System.out.println(700 + " inserts, searches and deletes:");
        arr = setTestArray(700);
        test(arr);
        System.out.println(" ");
        System.out.println(900 + " inserts, searches and deletes:");
        arr = setTestArray(900);
        test(arr);
        System.out.println(" ");
        System.out.println(2000 + " inserts, searches and deletes:");
        arr = setTestArray(2000);
        test(arr);
        return;
    }

    private static int[] setTestArray(int len) {
        int[] arr = new int[len];
        Random r = new Random();
        for(int i = 0; i < len; i++) {
            arr[i] = r.nextInt(536871066);
        }
        return arr;
    }

    private static void test(int[] arr) {
        BinarySearchTree BST = BinarySearchTree.create();
        AVLTree AVL = AVLTree.create();
        BTree BT = BTree.create();
        RBTree RBT = RBTree.create();

        int len = arr.length;
        long time, newTime;

        //BST
        System.out.println("BST:");
        time = System.nanoTime();
        newTime = time;

        for (int i = 0; i < len; i++) {
            BST.insert(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of inserting: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            BST.search(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of searching: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            BST.delete(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of deleting: " + (newTime - time) + " nano-seconds\n");
        time = newTime;

        // AVLTree
        System.out.println("AVLTree:");
        time = System.nanoTime();
        newTime = time;

        for (int i = 0; i < len; i++) {
            AVL.insert(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of inserting: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            AVL.search(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of searching: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            AVL.delete(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of deleting: " + (newTime - time) + " nano-seconds\n");
        time = newTime;

        // BTree
        System.out.println("B-Tree:");
        time = System.nanoTime();
        newTime = time;

        for (int i = 0; i < len; i++) {
            BT.insert(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of inserting: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            BT.search(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of searching: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            BT.delete(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of deleting: " + (newTime - time) + " nano-seconds\n");
        time = newTime;

        // RBTree
        System.out.println("Red Black Tree:");
        time = System.nanoTime();
        newTime = time;

        for (int i = 0; i < len; i++) {
            RBT.insert(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of inserting: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            RBT.search(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of searching: " + (newTime - time) + " nano-seconds");
        time = newTime;

        for (int i = 0; i < len; i++) {
            RBT.delete(arr[i]);
        }
        newTime = System.nanoTime();
        System.out.println("time of deleting: " + (newTime - time) + " nano-seconds\n");
        time = newTime;
    }
}
