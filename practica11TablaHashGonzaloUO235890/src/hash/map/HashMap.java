package hash.map;

import hash.ClosedHashTable;
import hash.HashNode;
import hash.HashStrategy;

public class HashMap<K, V> extends ClosedHashTable<MapEntry<K, V>> {

	public HashMap(int capacity, HashStrategy hashStrategy, double max, double min) {
		super(capacity, hashStrategy, max, min);
	}
	/**
     * Retorna el valor V asociado a la clave K.
     * @param key La clave que queremos buscar.
     * @return El valor V si existe, o null si no se encuentra.
     */
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser nula");
		}
		MapEntry<K, V> entryToSearch = new MapEntry<>(key, null);
		HashNode<MapEntry<K, V>> node = super.findMatchingNode(entryToSearch);
		if (node != null) {
			return node.getElement().getValue();
		}
		return null;
	}
}
