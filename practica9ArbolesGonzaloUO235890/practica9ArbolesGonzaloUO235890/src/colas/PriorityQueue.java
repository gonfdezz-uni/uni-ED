package colas;

public interface PriorityQueue<T extends Comparable<T>> {
	void insert(T element);
	T extract();
	boolean isEmpty();
	int getSize();
}
