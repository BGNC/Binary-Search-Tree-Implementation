import java.util.Iterator;

public class RestaurantBST<T extends Comparable<T>> {
    private int NodeCounter=0;
    private boolean removalSuccesful;

    private static class BinaryNode<T>{
        private T data;
        private BinaryNode<T> left;
        private BinaryNode<T> right;

        private BinaryNode(T value){
            data    =   value;
            left    =   null;
            right   =   null;
        }
        private BinaryNode(T value,BinaryNode<T> l,BinaryNode<T>r){
            data=value;
            left=l;
            right=r;
        }
        public boolean hasLeft() {
            return left != null;
        }
        public boolean hasRight() {
            return right != null;
        }
        public void setLeft(BinaryNode<T> newLeft) {
            left = newLeft;
        }
        public void setRight(BinaryNode<T> newRight){
            right = newRight;
        }
    }
    public BinaryNode<T> root;

    public RestaurantBST(){
        root=null;
    }

    public void add(T value){
        // value = restaurant name , rating , deliverytime , cuisine II  root = null
        // to given an example : burgerKing , 7.8 , 45 , burger
        root = add(value,root);
    }
    private BinaryNode<T> add(T value,BinaryNode<T> node){

        if(node==null){
            return new BinaryNode<T>(value);
        }

        int comparision = value.compareTo(node.data);

        if(comparision==0){
            node.data=value;
        }
        else{
            if(comparision<0)
                node.left=add(value,node.left);
            else
                node.right=add(value,node.right);
        }
        return node;


    }
    private T get(T key) {
        BinaryNode<T> node = root;
        while (node != null) {
            if (key.compareTo(node.data) == 0) {
                return node.data;
            }
            if (key.compareTo(node.data) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }


    public boolean remove(T elem)
    {
        removalSuccesful = true;
        root = remove(root, elem);
        return removalSuccesful;
    }
    public BinaryNode remove(BinaryNode<T> start, T elem)
    {
        // If the element we want to delete wasn't found
        if (start == null)
        {
            // Go back up the recursive loop, but let our object know that the
            // element we wanted to delete wasn't found
            removalSuccesful = false;
            return null;
        }

        // Compare the current node's element to the element we're looking for
        int comparison = start.data.compareTo(elem);

        // If the deletion will happen somewhere down the left tree
        if (comparison > 0)
        {
            // Attempt to delete down the left tree
            start.left = remove(start.left, elem);
        }
        // If the deletion will happen somewhere down the right tree
        else if (comparison < 0)
        {
            // Attempt to delete down the right tree
            start.right = remove(start.right, elem);
        }
        // If we are at the element we want to delete
        else
        {
            // If the node we want to delete has two children
            if (start.left != null && start.right != null)
            {
                // Back up pointers
                BinaryNode<T> left = start.left;
                BinaryNode<T> right = start.right;

                // Replace the current element with the smallest element in the
                // right subtree
                start = removeMin(start.right, start);

                // Back up pointer
                BinaryNode<T> minRight = start.right;

                // Fix pointers
                start.left = left;
                start.right = right;

                // We need to fix start.right if it points to the node we just
                // moved
                if (start.right.data == start.data)
                {
                    start.right = minRight;
                }
            }
            // If the node we want to delete is a leaf
            else if (start.left == null && start.right == null)
            {
                // Delete the current node from the tree
                start = null;
            }
            // If the node we want to delete just has a left child
            else if (start.left != null)
            {
                start = start.left;
            }
            // If the node we want to delete just has a right child
            else
            {
                start = start.right;
            }
        }

        return start;
    }

    public BinaryNode<T> removeMin(BinaryNode<T> start, BinaryNode<T> parent)
    {
        // If there is nothing to traverse or remove
        if (start == null)
        {
            return null;
        }

        // If we've found the minimum node
        if (start.left == null)
        {
            // Save the important values from the node


            // Rewire nodes
            if (parent != root)
            {
                parent.left = start.right; //deletedRight;
            }

            //start = null;

            // Return a new node that follows this method's specifications
            return start;
        }

        // Recurse until we get to the minimum node
        return removeMin(start.left, start);
    }

    public boolean contains(T item) {
        return get(item) != null;
    }

    public T getRightMost(BinaryNode<T> node){
        assert(node!=null);
        BinaryNode<T> right= node.right;
        if(right==null)
            return node.data;
        else
            getRightMost(right);
        return null;

    }
    public Iterator<T> iterator(){ return new TreeIterator(root); }
    public Iterator<T> preIterator() {
        return new TreeIterator(root, true);
    }
    public Iterator<T> postIterator() {
        return new TreeIterator(root, false);
    }
    public Iterator<T> levelOrder(){ return new TreeIterator(root);}

    public String toString(){
        return toString(root);
    }
    public String toString(BinaryNode<T>node){
        if(node==null){
            return "";
        }
        return node.data.toString()+","+toString(node.left)+","+toString(node.right);
    }

    private class TreeIterator implements Iterator<T> {

        Stack<BinaryNode<T>> visited = new Stack<>();
        Queue<BinaryNode<T>> queue = new Queue<>();
        public BinaryNode<T> root = null;
        Stack<Boolean> checkChild = new Stack<>();
        boolean levelOrder=false;
        boolean preorder=false;
        boolean inorder = true;
        boolean postorder=false;
        public TreeIterator(BinaryNode<T> root){
            this.root = root;
            visited=new Stack<BinaryNode<T>>();
            checkChild=new Stack<Boolean>();
            preorder=false;
            inorder=true;
            postorder=false;
        }
        public TreeIterator(BinaryNode<T> root , boolean inPreOrder){
            this.root=root;
            visited=new Stack<BinaryNode<T>>();
            checkChild=new Stack<Boolean>();
            preorder=inPreOrder;
            inorder=false;
            postorder=! preorder;
        }

        @Override
        public boolean hasNext() {
            return (root != null);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            if (preorder)
                return preOrderNext();
            else if (inorder)
                return inorderNext();
            else if(postorder)
                return postOrderNext();
            else if(levelOrder)
                return levelOrderNext();
            else {
                assert(false);
                return null;
            }
        }
        private T preOrderNext(){
            if(visited.isEmpty()){
                visited.push(root);
            }
            BinaryNode<T> node = visited.pop();
            T result = node.data;
            if(node.right!=null)
                visited.push(node.right);
            if(node.left!=null)
                visited.push(node.left);
            if(visited.isEmpty())
                root=null;
            return result;
        }
        private void pushLeftMostNode(BinaryNode<T> node){
            if(node!=null){
                visited.push(node);
                pushLeftMostNode(node.left);
            }
        }
        private T inorderNext(){
            if(visited.isEmpty()){
                pushLeftMostNode(root);
            }
            BinaryNode<T> node = visited.pop();
            T result = node.data;
            if(node.right!=null){
                BinaryNode<T> right = node.right;
                pushLeftMostNode(right);
            }
            if(visited.isEmpty())
                root=null;

            return result;
        }
        private T postOrderNext(){
            if(visited.isEmpty()){
                pushLeftmostNodeRecord(root);
            }
            if((visited.peak().right==null) || (checkChild.peak())){
                T result = visited.pop().data;
                checkChild.pop();
                if(visited.isEmpty()){
                    root=null;
                }
                return result;
            }
            else{
                if(checkChild.pop())
                {
                    assert(false);
                }
                checkChild.push(true);
                BinaryNode<T> right = visited.peak().right;
                assert (right!=null);
                pushLeftmostNodeRecord(right);
                return postOrderNext();
            }

        }
        private T levelOrderNext(){
            if(root==null){
                root=null;
            }
            queue.enqueue(root);
            while(!queue.isEmpty()){
                BinaryNode<T> currentNode = queue.dequeue();
                T result = (T) currentNode;
                if(currentNode.left!=null)
                    queue.enqueue(currentNode.left);
                if(currentNode.right!=null)
                    queue.enqueue(currentNode.right);

                return result;

            }
            return null;

        }

        private void pushLeftmostNodeRecord(BinaryNode<T> node) {
            // find the leftmost node
            if (node != null) {
                visited.push(node); // push this node
                checkChild.push(false); // record that it is on the left
                pushLeftmostNodeRecord(node.left); // continue looping
            }
        }

        public String toString(){
            if(preorder)
                return toString(root)+visited;
            if(inorder)
                return toString(root)+visited;
            if(postorder)
                return toString(root)+visited+checkChild;
            return "none of them" ;
        }
        private String toString(BinaryNode<T> node){
            if(node!=null)
                return "";
            else
                return node.toString()+toString(node.left)+toString(node.right);

        }
    }

}