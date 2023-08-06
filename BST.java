import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Minkun Lei
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 *
 * Resources: none
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * This constructor should initialize an empty BST.
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("the input collection is null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("some data in the collection is null");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     * This must be done recursively.
     * The data becomes a leaf in the tree.
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to add to the BST is null");
        }
        root = helpAdd(root, data);
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * This must be done recursively.
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to remove is null");
        }
        BSTNode<T> remove = new BSTNode<T>(null);
        root = helpRemove(root, data, remove);
        return remove.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * This must be done recursively.
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to get is null");
        }
        BSTNode<T> get = helpGet(root, data);
        if (get == null) {
            throw new java.util.NoSuchElementException("the data that we try to get is not in the BST");
        }
        return get.getData();
    }

    /**
     * Returns whether data matching the given parameter is contained
     * within the tree.
     * This must be done recursively.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to find is null");
        }
        return helpFind(root, data);
    }

    /**
     * Generate a pre-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        LinkedList<T> haha = new LinkedList<>();
        helpPre(root, haha);
        return haha;
    }

    /**
     * Generate an in-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        LinkedList<T> haha = new LinkedList<>();
        helpIn(root, haha);
        return haha;
    }

    /**
     * Generate a post-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        LinkedList<T> haha = new LinkedList<>();
        helpPost(root, haha);
        return haha;
    }

    /**
     * Generate a level-order traversal of the tree.
     * This does not need to be done recursively.
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        LinkedList<T> haha = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> first = q.poll();
            if (first != null) {
                q.add(first.getLeft());
                q.add(first.getRight());
                haha.add(first.getData());
            }
        }
        return haha;
    }

    /**
     * Returns the height of the root of the tree.
     * This must be done recursively.
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return helpHeight(root);
    }

    /**
     * Clears the tree.
     * Clears all data and resets the size.
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * This must be done recursively.
     * This list should contain the last node of each level.
     * If the tree is empty, an empty list should be returned.
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        LinkedList<T> haha = new LinkedList<>();
        helpMax(haha, 0, root);
        return haha;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }




    /**
     * a recursive helper method that creates a new node in the BST using the data in the param
     * @param curr the current node, the node that we are currently at
     * @param data the data that we want to add to the BST
     * @return the current node after it goes through our add manipulation
     */
    private BSTNode<T> helpAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(helpAdd(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(helpAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * a recursive helper method that determine if there is a node from the BST matching the given data
     * @param curr the current node, the node that we are currently at
     * @param data the data of the Node that we want to find
     * @return (true or false) whether there is such a node in the BST
     */
    private boolean helpFind(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) > 0) {
            return helpFind(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return helpFind(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * a recursive helper method that finds and returns the node from the BST matching the given data
     * @param curr the current node, the node that we are currently at
     * @param data the data of the Node that we want to find
     * @return the node from the BST that matches the given data
     */
    private BSTNode<T> helpGet(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) > 0) {
            return helpGet(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return helpGet(curr.getRight(), data);
        } else {
            return curr;
        }
    }

    /**
     * a recursive helper method that remove the successor
     * @param curr the current node, the node that we are currently at
     * @param suc a dummy node that stores our successor
     * @return return the current node after we remove the successor
     */
    private BSTNode<T> removeSuc(BSTNode<T> curr, BSTNode<T> suc) {
        if (curr.getLeft() == null) {
            suc.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuc(curr.getLeft(), suc));
        }
        return curr;
    }

    /**
     * a recursive helper method that helps remove and return the data from the BST matching the given data.
     * @param curr the current node, the node that we are currently at
     * @param data the data that we try to find and remove
     * @param remove the node that we removed
     * @return return the current node after it goes through our remove manipulation
     */
    private BSTNode<T> helpRemove(BSTNode<T> curr, T data, BSTNode<T> remove) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("the data that we try to remove is not in this BST");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(helpRemove(curr.getLeft(), data, remove));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(helpRemove(curr.getRight(), data, remove));
        } else {
            size -= 1;
            remove.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> suc = new BSTNode<T>(null);
                curr.setRight(removeSuc(curr.getRight(), suc));
                curr.setData(suc.getData());
            }
        }
        return curr;
    }

    /**
     * a recursive helper method that obtain the pre-order traversal of the BST
     * @param curr the current node, the node that we are currently at
     * @param haha a linkedlist that represents the pre-order traversal of the BST
     */
    private void helpPre(BSTNode<T> curr, LinkedList<T> haha) {
        if (curr != null) {
            haha.add(curr.getData());
            helpPre(curr.getLeft(), haha);
            helpPre(curr.getRight(), haha);
        }
    }

    /**
     * a recursive helper method that obtain the in-order traversal of the BST
     * @param curr the current node, the node that we are currently at
     * @param haha a linkedlist that represents the in-order traversal of the BST
     */
    private void helpIn(BSTNode<T> curr, LinkedList<T> haha) {
        if (curr != null) {
            helpIn(curr.getLeft(), haha);
            haha.add(curr.getData());
            helpIn(curr.getRight(), haha);
        }
    }

    /**
     * a recursive helper method that obtain the post-order traversal of the BST
     * @param curr the current node, the node that we are currently at
     * @param haha a linkedlist that represents the post-order traversal of the BST
     */
    private void helpPost(BSTNode<T> curr, LinkedList<T> haha) {
        if (curr != null) {
            helpPost(curr.getLeft(), haha);
            helpPost(curr.getRight(), haha);
            haha.add(curr.getData());
        }
    }

    /**
     * a recursive helper method that finds the height of the root node
     * @param curr the current node, the node that we are currently at
     * @return the height of the root node
     */
    private int helpHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return 1 + Math.max(helpHeight(curr.getLeft()), helpHeight(curr.getRight()));
        }
    }

    /**
     * This is a recursive helper method that helps us find the maximum data at each level of the BST
     * @param haha a linkedlist that holds the maximum data found at each level of the BST
     * @param level the level of BST that we are currently at during the recursion progress
     * @param curr the current node, the node that we are currently looking at
     */
    private void helpMax(LinkedList<T> haha, int level, BSTNode<T> curr) {
        if (curr == null) {
            return;
        }
        if (haha.size() == level) {
            haha.add(curr.getData());
        }
        helpMax(haha, level + 1, curr.getRight());
        helpMax(haha, level + 1, curr.getLeft());
    }
}
