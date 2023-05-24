public class bst {
    private node root;
    private int comparisons;

    public bst() {
        root = null;
        comparisons = 0;
     }

    public void insert(int key) {
        if (root == null) {
            root = new node(key);
            return;
        }

        node current = root;

        while (true) {
            if (key < current.key) {
                comparisons++;
                if (current.left == null) {
                    current.left = new node(key);
                    return;
                }
                current = current.left;
            } else if (key > current.key) {
                comparisons++;
                if (current.right == null) {
                    current.right = new node(key);
                    return;
                }
                current = current.right;
            } 
        }
    }

    public void find(int key) {
        node current = root;

        while (true) {
            if (key == current.key) {
                comparisons++;
                System.out.println("true");
                return;
            } else if (key < current.key) {
                comparisons++;
                if (current.left == null) {
                    System.out.println("false");
                    return;
                }
                current = current.left;
            } else if (key > current.key) {
                comparisons++;
                if (current.right == null) {
                    System.out.println("false");
                    return;
                }
                current = current.right;
            }
        }
    }
    
    public void delete(int key) {
        node current = root;
        node parent = null;

        while (true) {
            if (key == current.key) {
                comparisons++;
            } else if (key < current.key) {
            } else if (key > current.key) {
            }
        }
    }
    
    public void printPreOrder() { }
    
    public void printInOrder() { }
    
    public void printPostOrder() { }

    public void printComparisons() { System.out.println("COMPARISONS: " + comparisons); }

    private class node {
        public int key;
        public node left;
        public node right;

        public node(int key) { this.key = key; }
    }
}