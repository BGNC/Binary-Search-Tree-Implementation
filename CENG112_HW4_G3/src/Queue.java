public class Queue<T>{

    private T[] queue;
    private int frontIndex;
    private int backIndex;
    private final int DEFAULT_CAPACITY=1000;
    private final int MAX_CAPACITY=10000;

    public Queue(){
        T[] tempQueue=(T[]) new Object[DEFAULT_CAPACITY];
        queue=tempQueue;
        frontIndex=0;
        backIndex=DEFAULT_CAPACITY-1;

    }


    public void enqueue(T newEntry) {
        ensureCapacity();
        backIndex=(backIndex+1)% queue.length;

        queue[backIndex]=newEntry;
        backIndex+=1;



    }


    public T getFront() {

        return queue[frontIndex];
    }


    public T dequeue() {
        T front = queue[frontIndex];
        queue[frontIndex]=null;
        frontIndex=(frontIndex+1)% queue.length;
        frontIndex+=1;
        return front;
    }


    public void ensureCapacity() {
        if (frontIndex==((backIndex+2)% queue.length)){
            T[] oldQueue=queue;
            int oldSize= oldQueue.length;
            int newSize= 2*oldSize;
            if (checkCapacity(newSize)){
                T[] tempQueue=(T[]) new Object[newSize];
                queue=tempQueue;
                for (int i = 0; i < oldSize-1 ; i++) {
                    queue[i]=oldQueue[frontIndex];
                    frontIndex=(frontIndex +1)%oldSize;

                }
                frontIndex=0;
                backIndex=oldSize-2;
            }
        }

    }


    public boolean isEmpty() {

        return frontIndex==((backIndex+1)%queue.length);
    }



    public boolean checkCapacity(int newSize) {
        return MAX_CAPACITY>newSize;
    }


}