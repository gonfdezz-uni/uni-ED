package hash;

public class ClosedHashTable<T> extends AbstractHashTable<T> implements HashTable<T> {
	HashNode<T>[] associativeArray;
	private HashStrategy hashStrategy;

	public ClosedHashTable(int capacity, HashStrategy hashStrategy) {
		if (capacity < 3) {
			throw new IllegalArgumentException();
		}
		if (hashStrategy == null) {
			throw new NullPointerException();
		}
		if (!isPrimeNumber(capacity)) {
			capacity = getNextPrimeNumber(capacity);
		}
		this.capacityB = capacity;
		this.associativeArray = new HashNode[capacity];
		for (int i = 0; i < capacity; i++) {
			associativeArray[i] = new HashNode<T>();
		}
		this.hashStrategy = hashStrategy;
	}

	@Override
	public boolean add(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean search(T element) {
		// TODO Auto-generated method stub
		//HACER EN CASA
		return false;
	}

	@Override
	public boolean remove(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int hashFunction(T element, int attempts) {

		int hashCode = element.hashCode();

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
		return (hashCode + attemps) % capacityB;
	}

	protected int quadraticProbing(int hashCode, int attemps) {
		return (hashCode + attemps * attemps) % capacityB;
	}

	protected int doubleHashing(int hashCode, int attemps) {
		return (hashCode + attemps * jumpFunctionH(hashCode)) % capacityB;
	}

	protected int jumpFunctionH(int hashCode) {
		int r = getPreviousPrimeNumber(capacityB);
		return r - (hashCode % r);
	}

	protected HashNode<T> findMatchingNode(T element){
		for(int attemps = 0; attemps<capacityB; attemps++) {
			int indiceAProbar = hashFunction(element,attemps);
			HashNode<T> nodoAProbar = associativeArray[indiceAProbar];
			if(nodoAProbar.getStatus().equals(Status.EMPTY)) {
				return null;
			}if(nodoAProbar.getElement().equals(element)) {
				return nodoAProbar;
			}
		}
		return null;
	
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

}
