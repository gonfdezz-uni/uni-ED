package colas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InsertTests {

	@Test
	void testSwapElements() {
		MinimumBinaryHeap<Integer> heap = new MinimumBinaryHeap<>(5);

		Integer[] initialArray = { 1, 2, 3, 4, 5 };
		heap.setHeap(initialArray);

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> heap.swapElements(-1, 2));
		assertThrows(IllegalArgumentException.class, () -> heap.swapElements(2, -1));
		assertThrows(IllegalArgumentException.class, () -> heap.swapElements(5, 2));
		assertThrows(IllegalArgumentException.class, () -> heap.swapElements(2, 5));

		// Test valid swaps
		heap.swapElements(1, 3);
		assertEquals("[1, 4, 3, 2, 5]", heap.toString());

		heap.swapElements(0, 4);
		assertEquals("[5, 4, 3, 2, 1]", heap.toString());

		heap.swapElements(2, 2); // Swapping the same index
		assertEquals("[5, 4, 3, 2, 1]", heap.toString());
	}

	@Test
	void testPushWithNoFilterUp() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(10);
		assertEquals("[]", heap.toString());
		assertEquals(0, heap.getSize());
		
		heap.insert(1);
		assertEquals("[1]", heap.toString());
		assertEquals(1, heap.getSize());

		heap.insert(2);
		assertEquals("[1, 2]", heap.toString());
		assertEquals(2, heap.getSize());

		heap.insert(3);
		assertEquals("[1, 2, 3]", heap.toString());
		assertEquals(3, heap.getSize());

		heap.insert(4);
		assertEquals("[1, 2, 3, 4]", heap.toString());
		assertEquals(4, heap.getSize());

		heap.insert(5);
		assertEquals("[1, 2, 3, 4, 5]", heap.toString());
		assertEquals(5, heap.getSize());

		heap.insert(6);
		assertEquals("[1, 2, 3, 4, 5, 6]", heap.toString());
		assertEquals(6, heap.getSize());

		heap.insert(7);
		assertEquals("[1, 2, 3, 4, 5, 6, 7]", heap.toString());
		assertEquals(7, heap.getSize());

		heap.insert(8);
		assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", heap.toString());
		assertEquals(8, heap.getSize());

		heap.insert(9);
		assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", heap.toString());
		assertEquals(9, heap.getSize());

		heap.insert(10);
		assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", heap.toString());
		assertEquals(10, heap.getSize());

		assertThrows(IllegalStateException.class, () -> heap.insert(11));
	}

	@Test
	void testPushWithFilterUp() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(10);

		heap.insert(10);
		assertEquals("[10]", heap.toString());

		heap.insert(9);
		assertEquals("[9, 10]", heap.toString());

		heap.insert(8);
		assertEquals("[8, 10, 9]", heap.toString());

		heap.insert(7);
		assertEquals("[7, 8, 9, 10]", heap.toString());

		heap.insert(6);
		assertEquals("[6, 7, 9, 10, 8]", heap.toString());

		heap.insert(5);
		assertEquals("[5, 7, 6, 10, 8, 9]", heap.toString());

		heap.insert(4);
		assertEquals("[4, 7, 5, 10, 8, 9, 6]", heap.toString());

		heap.insert(3);
		assertEquals("[3, 4, 5, 7, 8, 9, 6, 10]", heap.toString());

		heap.insert(2);
		assertEquals("[2, 3, 5, 4, 8, 9, 6, 10, 7]", heap.toString());

		heap.insert(1);
		assertEquals("[1, 2, 5, 4, 3, 9, 6, 10, 7, 8]", heap.toString());

		assertThrows(IllegalStateException.class, () -> heap.insert(0));
	}

	@Test
	void testRepeatedElements() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(10);

		// Duplicates are valid.
		for (int i = 0; i < 10; i++) {
			heap.insert(1);
		}

		assertEquals("[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]", heap.toString());

		assertThrows(IllegalStateException.class, () -> heap.insert(1));
	}

	@Test
	void testPushNullElement() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(5);

		assertThrows(NullPointerException.class, () -> heap.insert(null));
	}

	@Test
	void testWithCharacters() {
		PriorityQueue<Character> heap = new MinimumBinaryHeap<>(5);

		heap.insert('d');
		assertEquals("[d]", heap.toString());

		heap.insert('a');
		assertEquals("[a, d]", heap.toString());

		heap.insert('c');
		assertEquals("[a, d, c]", heap.toString());

		heap.insert('b');
		assertEquals("[a, b, c, d]", heap.toString());
		
		heap.insert('e');
		assertEquals("[a, b, c, d, e]", heap.toString());
		
		assertThrows(IllegalStateException.class, () -> heap.insert('f'));
	}

}
