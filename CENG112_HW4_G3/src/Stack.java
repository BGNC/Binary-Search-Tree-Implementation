import java.util.Arrays;

public class Stack<T> {
    private T[] stack;
    private int topIndex;
    private int DEFAULT_CAPACITY = 1000;
    private int MAX_CAPACITY = 10000;

    public Stack(){
        T[] tempStack=(T[]) new Object[DEFAULT_CAPACITY];
        stack=tempStack;
        topIndex=0;
    }


    public void push(T newEntry) {
        ensureCapacity();
        stack[topIndex+1]=newEntry;
        topIndex++;

    }


    public void ensureCapacity() {
        if(topIndex==stack.length-1){
            int newLength=2*stack.length;
            checkCapacity(newLength);
            stack= Arrays.copyOf(stack, newLength);
        }

    }


    public T peak() {
        if (!isEmpty())
            return stack[topIndex];
        else
            return null;
    }


    public T pop() {
        if (!isEmpty()) {
            T top = stack[topIndex];
            stack[topIndex]=null;
            topIndex--;
            return top;
        }
        else
            return null;
    }


    public boolean isEmpty() {
        return stack[topIndex]==null;

    }
    public boolean checkCapacity(int newSize) {
        return MAX_CAPACITY>newSize;
    }
}
