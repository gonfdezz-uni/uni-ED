package hash;

import java.util.ArrayList;

public class OpenHashTable<T> extends AbstractHashTable<T> implements HashTable<T> {
	ArrayList<T>[] associativeArray;

	public OpenHashTable(int capacity) {
		this.capacityB = capacity;
		this.associativeArray = new ArrayList[capacity];
	}

	@Override
	public boolean add(T element) {
		if (element == null) throw new NullPointerException("No se puede añadir nulo");
        int index = hashFunction(element, 0);
        associativeArray[index].add(element);
        return true;
	}

	@Override
	public boolean search(T element) {
		if (element == null) throw new NullPointerException();
		int index = hashFunction(element, 0);
		for(int i = 0; i< associativeArray[index].size();i++) {
			if(associativeArray[i].equals(element)) {
				return true;
			}
		}
		return false;
		
	}

	@Override
	public boolean remove(T element) {
		if (element == null) throw new NullPointerException();
		int index = hashFunction(element, 0);
		for(int i = 0; i< associativeArray[index].size();i++) {
			if(associativeArray[i].equals(element)) {
				associativeArray[i].remove(element);
				return true;
			}
		}
		return false;

		
	}

	@Override
	protected int hashFunction(T element, int attempts) {
		int hashCode = element.hashCode();
		return Math.abs(hashCode) % capacityB;
	}

}
