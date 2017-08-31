// Code borrowed from lecture

public class N<T> {

    // constructors

    public N() {}

    public N(T o, N link) {
        data = o;
        next = link;
    }

    // selectors

    public T getData() {
        return data;
    }

    public void setData(T o) {
        data = o;
    }

    public N getNext() {
        return next;
    }

    public void setNext(N link) {
        next = link;
    }

    // instance variables

    private T data;
    private N next;

}  // N class
