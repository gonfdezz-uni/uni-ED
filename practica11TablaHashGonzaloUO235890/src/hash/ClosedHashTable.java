package hash;

public class ClosedHashTable<T> extends AbstractHashTable<T> implements HashTable<T> {
	HashNode<T>[] associativeArray;
	private HashStrategy hashStrategy;

	double maxLoadFactor;
	double minLoadFactor;

	public ClosedHashTable(int capacity, HashStrategy hashStrategy, double max, double min) {
		if (hashStrategy == null) {
			throw new NullPointerException();
		}
		initializeEmptyAssociativeArray(capacity);
		this.hashStrategy = hashStrategy;
		this.maxLoadFactor = max;
		this.minLoadFactor = min;
	}

	public ClosedHashTable(int capacity, HashStrategy hashStrategy, double max) {
		this(capacity, hashStrategy, max, -1);
	}

	public ClosedHashTable(int capacity, HashStrategy hashStrategy) {
		this(capacity, hashStrategy, 2);
	}

	protected void initializeEmptyAssociativeArray(int newCapacityB) {
		if (newCapacityB < 3) {
			throw new IllegalArgumentException();
		}
		if (!isPrimeNumber(newCapacityB)) {
			newCapacityB = getNextPrimeNumber(newCapacityB);
		}
		this.capacityB = newCapacityB;
		this.associativeArray = new HashNode[newCapacityB];
		for (int i = 0; i < newCapacityB; i++) {
			associativeArray[i] = new HashNode<T>();
		}
		this.elementNumber = 0;
		
	}

	@Override
	public boolean add(T element) {
		// No se admiten duplicados -> devuelve false
		// Si encuentro un deleted (primer eliminado) hay que guardar esa posicion y
		// comprobar
		// la búsqueda hasta el final para cpmprobar si hay repetidos.
		// Conviene tener la mayoría de emptys
		// Si encuentro un empty se añade y se pone valid
		if (elementNumber == capacityB) {
			throw new IllegalStateException();
		}

		HashNode<T> nodo = findAvailableNodeFor(element);
		if (nodo == null) {
			return false;
		} else {
			addElementToNode(nodo, element);
			dynamicResize();
			return true;
		}
	}

	@Override
	public boolean search(T element) {
		for (int attemps = 0; attemps < capacityB; attemps++) {
			// Posición en la que comprobaré si el elemento de ese nodo == element
			// y está en un estado en el que pueda ser encontrado
			int indexToCheck = hashFunction(element, attemps);
			// Nodo de la lista en la posición de comprobación
			// Comparo el elemento y tengo en cuenta el estado del nodo
			HashNode<T> nodeToCheck = associativeArray[indexToCheck];
			if (nodeToCheck.getStatus().equals(Status.EMPTY)) {
				return false;
			}
			if (nodeToCheck.getElement() == element && nodeToCheck.getStatus().equals(Status.DELETED)) {
				return false;
			}
			if (nodeToCheck.getElement() == element) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(T element) {
		if (this.elementNumber == 0) {
			throw new IllegalStateException();
		}
		if (findMatchingNode(element) == null) {
			return false;
		}
		if (findMatchingNode(element).getStatus().equals(Status.DELETED)) {
			return false;
		}
		if (findMatchingNode(element).getStatus().equals(Status.VALID)) {
			removeElementFromNode(findMatchingNode(element));
			InverseDynamicResize();
			return true;
		}

		return false;

	}

	@Override
	protected int hashFunction(T element, int attempts) {

		int hashCode = element.hashCode();
		hashCode = Math.abs(hashCode);

		switch (hashStrategy) {
		case LINEAR_PROBING -> {
			return linearProbing(hashCode, attempts);
		}
		case QUADRATIC_PROBING -> {
			return quadraticProbing(hashCode, attempts);
		}
		case DOUBLE_HASHING -> {
			return doubleHashing(hashCode, attempts);
		}
		}
		return -1;
	}

	protected int linearProbing(int hashCode, int attemps) {
		if (hashCode < 0 || attemps < 0 || attemps > capacityB) {
			throw new IllegalArgumentException();
		}

		return (hashCode + attemps) % capacityB;
	}

	protected int quadraticProbing(int hashCode, int attemps) {
		checkIfNegative(hashCode);
		checkIfNegative(attemps);
		if (attemps > capacityB) {
			throw new IllegalArgumentException();
		}
		return (hashCode + attemps * attemps) % capacityB;
	}

	protected int doubleHashing(int hashCode, int attemps) {
		checkIfNegative(hashCode);
		checkIfNegative(attemps);
		if (attemps > capacityB) {
			throw new IllegalArgumentException();
		}
		return (hashCode + attemps * jumpFunctionH(hashCode)) % capacityB;
	}

	protected int jumpFunctionH(int hashCode) {
		checkIfNegative(hashCode);
		int r = getPreviousPrimeNumber(capacityB);
		return r - (hashCode % r);
	}

	private void checkIfNegative(int hashCode) {
		if (hashCode < 0) {
			throw new IllegalArgumentException();
		}
	}

	protected HashNode<T> findMatchingNode(T element) {
		for (int attemps = 0; attemps < capacityB; attemps++) {
			int indiceAProbar = hashFunction(element, attemps);
			HashNode<T> nodoAProbar = associativeArray[indiceAProbar];
			if (nodoAProbar.getStatus().equals(Status.EMPTY)) {
				return null;
			}
			if (nodoAProbar.getElement().equals(element)) {
				return nodoAProbar;
			}
		}
		return null;

	}

	protected void removeElementFromNode(HashNode<T> node) {
		if (this.elementNumber == 0) {
			throw new IllegalStateException();
		}
		if (node == null) {
			throw new NullPointerException();
		}
		if (node.getStatus().equals(Status.DELETED)) {
			throw new IllegalArgumentException();
		}
		node.setStatus(Status.DELETED);
		elementNumber--;
	}

	/**
	 * Este método obtiene el hash del elemento y resuelve la collision hasta
	 * encontrar un nodo válido para añadir o null si no lo hay: Si encuentro un
	 * nodo eliminado (el primero) guardo el índice para el futuro. Nodo empty lo
	 * puedo añadir sin problemas: -En el primer deleted que haya encontrado -En el
	 * empty si no encontré un nodp deleted antes Si encuentro un nodo con el
	 * contenido igual depende: -Si tiene estado VALID no puedo, return null -Si
	 * tiene estado DELETED me comporto como un empty Si encuentro un nodo deleted,
	 * lo guardo para más tarde Si excedo el número máximo de intentos: -En el
	 * primer nodo delelted que haya encontrado -Null si no hay otro
	 * 
	 * @param element
	 * @return
	 */
	protected HashNode<T> findAvailableNodeFor(T element) {

		int posicionDelPrimerNodoEncontrado = -1;

		for (int attemps = 0; attemps < capacityB; attemps++) {
			int posicion = hashFunction(element, attemps);
			HashNode<T> nodo = associativeArray[posicion];

			if (nodo.getStatus() == Status.EMPTY
					|| nodo.getStatus() == Status.DELETED && nodo.getElement().equals(element)) {
				if (posicionDelPrimerNodoEncontrado != -1) {
					return associativeArray[posicionDelPrimerNodoEncontrado];
				}
				return nodo;
			}
			if (nodo.getStatus() == Status.VALID && nodo.getElement().equals(element)) {
				return null;
			}
			if (nodo.getStatus() == Status.DELETED && posicionDelPrimerNodoEncontrado == -1) {
				posicionDelPrimerNodoEncontrado = posicion;
			}

		}
		if (posicionDelPrimerNodoEncontrado != -1) {
			return associativeArray[posicionDelPrimerNodoEncontrado];
		}

		return null;

	}

	protected void addElementToNode(HashNode<T> node, T element) {
		if (elementNumber == capacityB) {
			throw new IllegalStateException();
		}
		if (node == null) {
			throw new NullPointerException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (node.getStatus().equals(Status.VALID)) {
			throw new IllegalArgumentException();
		}
		node.setElement(element);
		node.setStatus(Status.VALID);
		elementNumber++;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < capacityB; i++) {
			if (i != 0) {
				sb.append("   ");
			}
			sb.append(i).append(":").append(associativeArray[i].toString());
		}
		return sb.toString();
	}

	// REDIMENSIONES

	protected void updateCapacity(int newCapacity) {
		// Faltan algunas comprobaciones
		if (newCapacity < elementNumber) {
			throw new IllegalArgumentException();
		}
		HashNode<T>[] old = associativeArray;
		initializeEmptyAssociativeArray(newCapacity);
		for (HashNode<T> nodo : old) {
			if (nodo!=null && nodo.getStatus() == Status.VALID) {
				add(nodo.getElement());
			}
		}
	}

	protected void dynamicResize() {
		if (getLoadFactor() > maxLoadFactor) {
			int newCapacity = getNextPrimeNumber(capacityB * 2);
			updateCapacity(newCapacity);
		}
	}

	protected void InverseDynamicResize() {
		if (getLoadFactor() < minLoadFactor) {
			int newCapacity = capacityB / 2;
			if (newCapacity <= 3) {
				newCapacity = 3;
			}else {
				newCapacity = getPreviousPrimeNumber(newCapacity);
			}
			updateCapacity(newCapacity);
		}
	}

}
