package propios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import arboles.BSTTree;

class RecorridoTestClase {

	@Test
	void testRecorridoPizarra() {
		BSTTree<Integer> arbol = new BSTTree<Integer>();
		
		arbol.add(10);
		arbol.add(5);
		arbol.add(15);
		arbol.add(3);
		arbol.add(8);
		arbol.add(12);
		arbol.add(20);
		
		assertEquals("{10} {5} {3} {8} {15} {12} {20} ", arbol.preorderTraversal());
	}

}
