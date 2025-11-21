package propios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import arboles.BSTTree;

class AddTests {

	@Test
	void testAddPizarra() {
		BSTTree<Integer> arbol = new BSTTree<Integer>();
		
		arbol.add(10);
		arbol.add(5);
		arbol.add(15);
		arbol.add(3);
		arbol.add(8);
		arbol.add(12);
		arbol.add(20);
		
	}

}
