package colas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExtractTests {

	@Test
	void testPop() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(10);

		// Starts empty
		assertTrue(heap.isEmpty());

		// Pop while empty
		assertThrows(IllegalStateException.class, () -> heap.extract());

		// Insert elements from 10 down to 1 in reverse order
		for (int i = 10; i > 0; i--) {
			heap.insert(i);
		}

		// Now pop elements one by one and check the state of the heap
		assertEquals("[1, 2, 5, 4, 3, 9, 6, 10, 7, 8]", heap.toString());
		assertEquals(10, heap.getSize());

		assertEquals(1, heap.extract());
		assertEquals("[2, 3, 5, 4, 8, 9, 6, 10, 7]", heap.toString());
		assertEquals(9, heap.getSize());

		assertEquals(2, heap.extract());
		assertEquals("[3, 4, 5, 7, 8, 9, 6, 10]", heap.toString());
		assertEquals(8, heap.getSize());

		assertEquals(3, heap.extract());
		assertEquals("[4, 7, 5, 10, 8, 9, 6]", heap.toString());
		assertEquals(7, heap.getSize());

		assertEquals(4, heap.extract());
		assertEquals("[5, 7, 6, 10, 8, 9]", heap.toString());
		assertEquals(6, heap.getSize());

		assertEquals(5, heap.extract());
		assertEquals("[6, 7, 9, 10, 8]", heap.toString());
		assertEquals(5, heap.getSize());

		assertEquals(6, heap.extract());
		assertEquals("[7, 8, 9, 10]", heap.toString());
		assertEquals(4, heap.getSize());

		assertEquals(7, heap.extract());
		assertEquals("[8, 10, 9]", heap.toString());
		assertEquals(3, heap.getSize());

		assertEquals(8, heap.extract());
		assertEquals("[9, 10]", heap.toString());
		assertEquals(2, heap.getSize());

		assertEquals(9, heap.extract());
		assertEquals("[10]", heap.toString());
		assertEquals(1, heap.getSize());

		assertEquals(10, heap.extract());
		assertEquals("[]", heap.toString());
		assertEquals(0, heap.getSize());

		assertTrue(heap.isEmpty());

		assertThrows(IllegalStateException.class, () -> heap.extract());
	}

	@Test
	void testPopWithDuplicates() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(10);

		// Insert duplicate elements
		for (int i = 0; i < 5; i++) {
			heap.insert(5);
		}

		assertEquals("[5, 5, 5, 5, 5]", heap.toString());

		// Pop all duplicates
		for (int i = 0; i < 5; i++) {
			assertEquals(5, heap.extract());
		}

		assertEquals("[]", heap.toString());
		assertTrue(heap.isEmpty());
	}

	@Test
	void testAlternatingPushAndPop() {
		PriorityQueue<Integer> heap = new MinimumBinaryHeap<>(3);

		heap.insert(5);
		heap.insert(3);
		assertEquals("[3, 5]", heap.toString());

		assertEquals(3, heap.extract());
		assertEquals("[5]", heap.toString());

		heap.insert(7);
		heap.insert(1);
		assertEquals("[1, 7, 5]", heap.toString());

		assertEquals(1, heap.extract());
		assertEquals("[5, 7]", heap.toString());

		assertEquals(5, heap.extract());
		assertEquals("[7]", heap.toString());

		heap.insert(2);
		assertEquals("[2, 7]", heap.toString());

		assertEquals(2, heap.extract());
		assertEquals("[7]", heap.toString());

		assertEquals(7, heap.extract());
		assertEquals("[]", heap.toString());

		assertTrue(heap.isEmpty());
	}

	@Test
	void testPopCharacters() {
		PriorityQueue<Character> heap = new MinimumBinaryHeap<>(5);

		heap.insert('d');
		heap.insert('a');
		heap.insert('c');
		heap.insert('b');

		assertEquals("[a, b, c, d]", heap.toString());

		assertEquals('a', heap.extract());
		assertEquals("[b, d, c]", heap.toString());

		assertEquals('b', heap.extract());
		assertEquals("[c, d]", heap.toString());

		assertEquals('c', heap.extract());
		assertEquals("[d]", heap.toString());

		assertEquals('d', heap.extract());
		assertEquals("[]", heap.toString());

		assertTrue(heap.isEmpty());
	}

}
