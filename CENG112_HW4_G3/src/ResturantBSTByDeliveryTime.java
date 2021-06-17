import java.util.Iterator;

public class ResturantBSTByDeliveryTime<T extends Comparable<T>>{
    private int NodeCounter=0;



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

    public ResturantBSTByDeliveryTime(){
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

    private BinaryNode<T> findLargest(BinaryNode<T> rootNode){
        while (rootNode.left != null)
            rootNode = rootNode.left;
        return rootNode;
    }
    private BinaryNode<T> findSmallest(BinaryNode<T> rootNode){
        while (rootNode.right != null)
            rootNode = rootNode.right;
        return rootNode;
    }
    public T findSmallest(){
        root = findSmallest(root);
        return root.data;
    }

    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode){
        if (rootNode.hasRight()){
            BinaryNode<T> rightChild = rootNode.right;
            rightChild=removeLargest(rightChild);
            rootNode.setRight(rightChild);

        }
        else{
            rootNode=rootNode.left;

        }
        return rootNode;
    }
    private BinaryNode<T> removeSmallest(BinaryNode<T> rootNode){
        if (rootNode.hasLeft()){
            BinaryNode<T> leftChild = rootNode.left;
            leftChild=removeSmallest(leftChild);
            rootNode.setLeft(leftChild);

        }
        else{
            rootNode=rootNode.right;

        }
        return rootNode;
    }

    public boolean remove(T elem) {

        // Make sure the node we want to remove
        // actually exists before we remove it
        if (contains(elem)) {
            root = remove(root, elem);
            NodeCounter--;
            return true;
        }
        return false;
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
