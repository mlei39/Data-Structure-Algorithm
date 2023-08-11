import java.util.Collection;

/**
 * Your implementation of an AVL.
 *
 * @author Minkun Lei
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 * Resources: none
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * This constructor should initialize an empty AVL.
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
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
     * Adds the element to the tree.
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to add to the AVL is null");
        }
        root = helpAdd(root, data);
    }

    /**
     * a recursive helper method that creates a new node in the AVL using the data passed in
     * @param curr the current node, the node that we are currently at
     * @param data the data that we want to add to the AVL
     * @return the current node after it goes through our add manipulation
     */
    private AVLNode<T> helpAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(helpAdd(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(helpAdd(curr.getRight(), data));
        }
        curr = balance(curr);
        return curr;
    }

    /**
     * this method determines which type of rotation is needed and calls that rotation.
     * @param curr the node that we want to balance/call rotation on
     * @return the current node after it goes through the balance manipulation
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        update(curr);
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotate(curr.getRight()));
            }
            return leftRotate(curr);
        }

        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotate(curr.getLeft()));
            }
            return rightRotate(curr);
        }

        return curr;
    }

    /**
     * this method performs a left rotation on the current node
     * @param curr the node that we want to perform the rotation on
     * @return the current node after it goes through the rotation
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> node = curr.getRight();
        AVLNode<T> dummy = curr.getRight().getLeft();
        node.setLeft(curr);
        curr.setRight(dummy);
        update(curr);
        update(node);
        return node;
    }

    /**
     * this method performs a right rotation on the current node
     * @param curr the node that we want to perform the rotation on
     * @return the current node after it goes through the rotation
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> node = curr.getLeft();
        AVLNode<T> dummy = curr.getLeft().getRight();
        node.setRight(curr);
        curr.setLeft(dummy);
        update(curr);
        update(node);
        return node;
    }

    /**
     * this method updates the height and balanceFactor of the node we passed in
     * @param node the node of which we want to update the height and balanceFactor
     */
    private void update(AVLNode<T> node) {
        node.setHeight(1 + Math.max(heightH(node.getLeft()), heightH(node.getRight())));
        node.setBalanceFactor(heightH(node.getLeft()) - heightH(node.getRight()));
    }

    /**
     * this method gives us the height of the current node; if the node is null, it returns -1
     * @param node the node that we want to find the height of
     * @return the height of the node we passed in
     */
    private int heightH(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to remove is null");
        }
        AVLNode<T> remove = new AVLNode<T>(null);
        root = helpRemove(root, data, remove);
        return remove.getData();
    }

    /**
     * a recursive helper method that helps remove and return the target data from the AVL.
     * @param curr the current node, the node that we are currently at
     * @param data the data that we try to find and remove
     * @param remove the node that we removed
     * @return return the current node after it goes through this remove manipulation
     */
    private AVLNode<T> helpRemove(AVLNode<T> curr, T data, AVLNode<T> remove) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("the data that we try to remove is not in this AVL");
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
                AVLNode<T> pre = new AVLNode<T>(null);
                curr.setLeft(removePre(curr.getLeft(), pre));
                curr.setData(pre.getData());
            }
        }
        curr = balance(curr);
        return curr;
    }

    /**
     * a recursive helper method that removes the predecessor of the target node
     * @param curr the current node, the node that we are currently at
     * @param pre this  stores the predecessor node of the target node
     * @return return the current node after we remove the predecessor
     */
    private AVLNode<T> removePre(AVLNode<T> curr, AVLNode<T> pre) {
        if (curr.getRight() == null) {
            pre.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePre(curr.getRight(), pre));
            return balance(curr);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * Hint: Should you use value equality or reference equality?
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the param data is null");
        }
        return getH(root, data).getData();
    }

    /**
     * this is a recursive helper method that returns a node containing a specific data
     * @param curr the current node, the node that we are currently at
     * @param data the data that we try to get from the AVL
     * @return the AVL node that contains our target data
     */
    private AVLNode<T> getH(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("the data that we try to find is not in the AVL");
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (curr.getData().compareTo(data) < 0) {
            return getH(curr.getRight(), data);
        } else {
            return getH(curr.getLeft(), data);
        }
    }

    /**
     * Returns whether data matching the given parameter is contained
     * within the tree.
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we want to find is null");
        }
        return containH(root, data);
    }

    /**
     * this is a recursive helper method that checks if the AVL contains our target data
     * @param curr the current node, the node that we are currently at
     * @param data the target data, which we want to check if the AVL contains
     * @return true if the data can be found in the AVL, false otherwise
     */
    private boolean containH(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) > 0) {
            return containH(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return containH(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     * Should be O(1).
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightH(root);
    }

    /**
     * Clears the tree.
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth.
     * Should be recursive.
     * Must run in O(log n) for all cases.
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        return helpDeep(root).getData();
    }

    /**
     * this is a recursive helper method that returns the deepest node in the AVL
     * @param curr the current node, the node that we are currently at
     * @return the deepest node in the AVL
     */
    private AVLNode<T> helpDeep(AVLNode<T> curr) {
        if (curr.getHeight() == 0) {
            return curr;
        }
        if (curr.getBalanceFactor() > 0) {
            return helpDeep(curr.getLeft());
        } else {
            return helpDeep(curr.getRight());
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data.
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     * This should NOT be used in the remove method.
     * Should be recursive.
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the input data is null");
        }
        AVLNode<T> temp = helpSuc(root, data);
        if (temp == null) {
            return null;
        }
        return temp.getData();
    }

    /**
     * this is a recursive helper method that finds the successor of the data passed in
     * @param curr the current node, the node that we are currently at
     * @param data the data which we want to find the successor of
     * @return the successor node of the target node
     */
    private AVLNode<T> helpSuc(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("the data is not in the AVL");
        } else if (curr.getData().compareTo(data) > 0) {
            AVLNode<T> temp = helpSuc(curr.getLeft(), data);
            if (temp != null) {
                return temp;
            }
        } else if (curr.getData().compareTo(data) < 0) {
            AVLNode<T> temp = helpSuc(curr.getRight(), data);
            if (temp != null) {
                return temp;
            }
        }
        if (curr.getData().compareTo(data) == 0 && curr.getRight() != null) {
            return goLeft(curr.getRight());
        }
        if (curr.getData().compareTo(data) > 0) {
            return curr;
        }
        return null;
    }

    /**
     * this method finds the successor node by keeping going to the left child of the current node
     * @param curr the current node, the node that we are currently at
     * @return the successor node of the target data
     */
    private AVLNode<T> goLeft(AVLNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr;
        }
        return goLeft(curr.getLeft());
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
}
