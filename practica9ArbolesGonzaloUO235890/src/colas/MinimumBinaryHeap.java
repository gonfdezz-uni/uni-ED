package colas;

import java.util.Arrays;

public class MinimumBinaryHeap<T extends Comparable<T>> implements PriorityQueue<T> {
	private T[] heap;
	private int size;

	public MinimumBinaryHeap(int capacity) {
		heap = (T[]) new Comparable[capacity];
		size = 0;
	}

	@Override
	public void insert(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (size >= heap.length) {
			throw new IllegalStateException();
		}
		heap[size] = element;
		size++;
		filterUp(size - 1);
	}

	@Override
	public T extract() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		T element = heap[0];
		size--;
		heap[0] = heap[size];
		if(!isEmpty()) {
			filterDown(0);
		}
		return element;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	int getParentIndex(int elementIndex) {
		checkIndex(elementIndex);
		if (elementIndex == 0) {
			return -1;
		}
		return (elementIndex - 1) / 2;
	}

	int getLeftChildIndex(int elementIndex) {
		checkIndex(elementIndex);
		int childIndex = elementIndex * 2 + 1;
		if (childIndex >= size) {
			return -1;
		}
		return childIndex;
	}

	int getRightChildIndex(int elementIndex) {
		checkIndex(elementIndex);
		int childIndex = elementIndex * 2 + 2;
		if (childIndex >= size) {
			return -1;
		}
		return childIndex;
	}

	boolean hasGreaterValueThan(int comparedIndex, int referencedIndex) {
		checkIndex(comparedIndex);
		checkIndex(referencedIndex);
		return heap[comparedIndex].compareTo(heap[referencedIndex]) > 0;
	}

	int getChildIndexWithLowestValue(int elementIndex) {
		checkIndex(elementIndex);
		int leftIndex = getLeftChildIndex(elementIndex);
		int rightIndex = getRightChildIndex(elementIndex);
		if (rightIndex == -1) {
			return leftIndex;
		}
		if (hasGreaterValueThan(leftIndex, rightIndex)) {
			return rightIndex;
		}
		return leftIndex;
	}

	private void checkIndex(int elementIndex) {
		if (elementIndex < 0 || elementIndex >= size) {
			throw new IllegalArgumentException();
		}
	}

	public String toString() {
		return Arrays.toString(Arrays.copyOf(heap, size));
	}

	void setHeap(T[] heap) {
		this.heap = heap;
		this.size = heap.length;
	}

	void swapElements(int index1, int index2) {
		checkIndex(index1);
		checkIndex(index2);
		T temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	void filterUp(int elementIndex) {
		checkIndex(elementIndex);
		int parentIndex = getParentIndex(elementIndex);
		if (parentIndex != -1 && hasGreaterValueThan(parentIndex, elementIndex)) {
			swapElements(parentIndex, elementIndex);
			filterUp(parentIndex);
		}

	}

	void filterDown(int elementIndex) {
		checkIndex(elementIndex);
		int lowestSonIndex = getChildIndexWithLowestValue(elementIndex);
		if (lowestSonIndex != -1) {
			boolean comparacion = hasGreaterValueThan(elementIndex, lowestSonIndex);
			if (lowestSonIndex != -1 && comparacion == true) {
				swapElements(lowestSonIndex, elementIndex);
				filterDown(lowestSonIndex);
			}
		}

		
	}
}
