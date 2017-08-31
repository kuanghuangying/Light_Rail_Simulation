// Code borrowed from lecture


public class Q1<T> implements Q<T> {

    // constructor

    public Q1() {}

    // selectors

    public void add(T o) {

        if (size == 0) {
            front = new N<T>(o, null);
            rear = front;
        }
        else {
            rear.setNext(new N<T>(o, null));
            rear = rear.getNext();
        }
        size++;
    }

    public T remove() {

        T answer;

        if (size == 0)
            return null;

        answer = (T)front.getData();
        front = front.getNext();
        size--;
        if (size == 0)
            rear = null;
        return answer;
    }

    public int length() {
        return size;
    }

    public N getFront(){
        return front;
    }

    private int size;
    private N front;
    private N rear;

}  // Q1 class

