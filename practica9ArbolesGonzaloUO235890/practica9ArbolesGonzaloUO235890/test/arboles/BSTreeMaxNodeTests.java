package arboles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BSTreeMaxNodeTests {

	@Test
	void testGetMax() {
		BSTTree<Character> a = new BSTTree<Character>();

		a.add('b');
		assertEquals('b', (char) a.getMaxNode(a.getRoot()).element);
		a.add('a');
		assertEquals('b', (char) a.getMaxNode(a.getRoot()).element);
		a.add('d');
		assertEquals('d', (char) a.getMaxNode(a.getRoot()).element);
		a.add('c');
		assertEquals('d', (char) a.getMaxNode(a.getRoot()).element);
		a.add('g');
		assertEquals('g', (char) a.getMaxNode(a.getRoot()).element);
		a.add('i');
		assertEquals('i', (char) a.getMaxNode(a.getRoot()).element);
		a.add('h');
		assertEquals('i', (char) a.getMaxNode(a.getRoot()).element);
	}

}
