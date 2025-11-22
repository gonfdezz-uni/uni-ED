package colas;

import java.util.Arrays;

public class MinimumBinaryHeap<T extends Comparable<T>> implements PriorityQueue<T> {
	private T[] heap;
	private int size;

	/**
	 * Constructor
	 * 
	 * @param capacity
	 */
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
		if (!isEmpty()) {
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

	/**
	 * @param elementIndex
	 * @return posición del padre del elemento cuya posición es elementIndex
	 */
	int getParentIndex(int elementIndex) {
		checkIndex(elementIndex);
		if (elementIndex == 0) {
			return -1;
		}
		return (elementIndex - 1) / 2;
	}

	/**
	 * @param elementIndex
	 * @return posición del hijo izquierdo del elemento cuya posición es
	 *         elementIndex
	 */
	int getLeftChildIndex(int elementIndex) {
		checkIndex(elementIndex);
		int childIndex = elementIndex * 2 + 1;
		if (childIndex >= size) {
			return -1;
		}
		return childIndex;
	}

	/**
	 * 
	 * @param elementIndex
	 * @return posición del hijo izquierdo del elemento cuya posición es
	 *         elementIndex
	 */
	int getRightChildIndex(int elementIndex) {
		checkIndex(elementIndex);
		int childIndex = elementIndex * 2 + 2;
		if (childIndex >= size) {
			return -1;
		}
		return childIndex;
	}

	/**
	 * @param comparedIndex
	 * @param referencedIndex
	 * @return true si comparedIndex tiene más valor que referencedIndex, false en
	 *         caso contrario
	 */
	boolean hasGreaterValueThan(int comparedIndex, int referencedIndex) {
		checkIndex(comparedIndex);
		checkIndex(referencedIndex);
		return heap[comparedIndex].compareTo(heap[referencedIndex]) > 0;
	}

	/**
	 * @param elementIndex
	 * @return de los hijos cuyo padre está en la posición elementIndex, el de menos
	 *         valor
	 */
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

	/**
	 * Método que establece el aray interno que representa la estructura del heap y
	 * actualiza el tamaño del heap según la longitud del array recibido como
	 * parámetro
	 * 
	 * @param heap
	 */
	void setHeap(T[] heap) {
		this.heap = heap;
		this.size = heap.length;
	}

	/**
	 * Método que intercambia los elementos cuyas posiciones son index1 e index2
	 * 
	 * @param index1
	 * @param index2
	 */
	void swapElements(int index1, int index2) {
		checkIndex(index1);
		checkIndex(index2);
		T temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	/**
	 * Método encargado del filtrado ascendente. Esto consiste en ir comparando el
	 * nodo cuya posición es elementIndex con su padre y, si es menor, los cambia.
	 * 
	 * @param elementIndex
	 */
	void filterUp(int elementIndex) {
		checkIndex(elementIndex);
		int parentIndex = getParentIndex(elementIndex);
		if (parentIndex != -1 && hasGreaterValueThan(parentIndex, elementIndex)) {
			swapElements(parentIndex, elementIndex);
			filterUp(parentIndex);
		}

	}

	/**
	 * Método encargado del filtrado descendente. Esto consiste en ir comparando el
	 * nodo cuya posición es elementIndex con sus hijos y, si es mayor, lo cambia
	 * por el menor de sus hijos.
	 * 
	 * @param elementIndex
	 */
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

	/**
	 * Método que comprueba si elementIndex es una posición válida para trabajar con
	 * ella en este contexto de árboles AVL
	 * 
	 * @param elementIndex
	 */
	private void checkIndex(int elementIndex) {
		if (elementIndex < 0 || elementIndex >= size) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Método que devuelve una representación en forma de cadena del arreglo interno
	 * del heap, mostrando únicamente los elementos válidos (hasta el tamaño actual)
	 * usando el formato estándar de arrays.
	 */
	public String toString() {
		return Arrays.toString(Arrays.copyOf(heap, size));
	}
}
