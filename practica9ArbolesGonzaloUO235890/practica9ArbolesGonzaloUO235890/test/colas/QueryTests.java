package colas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryTests {

	final static int NO_CHILD = -1;
	final static int NO_PARENT = -1;

	MinimumBinaryHeap<Integer> createHeap() {
		Integer[] heap = { 1, 3, 2, 7, 4, 5, 6, 15, 10, 8, 14, 13, 11, 12, 9, 16, 18, 17 };
		MinimumBinaryHeap<Integer> binaryHeap = new MinimumBinaryHeap<Integer>(heap.length);

		// Directly set the heap array for testing purposes
		// No need for a push method
		binaryHeap.setHeap(heap);
		return binaryHeap;
	}

	@Test
	void testGetParentIndex() {
		MinimumBinaryHeap<Integer> binaryHeap = createHeap();

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getParentIndex(-1));

		// The root has no parent
		assertEquals(NO_PARENT, binaryHeap.getParentIndex(0));

		// Test valid indexes
		assertEquals(0, binaryHeap.getParentIndex(1));
		assertEquals(0, binaryHeap.getParentIndex(2));

		assertEquals(1, binaryHeap.getParentIndex(3));
		assertEquals(1, binaryHeap.getParentIndex(4));

		assertEquals(2, binaryHeap.getParentIndex(5));
		assertEquals(2, binaryHeap.getParentIndex(6));

		assertEquals(3, binaryHeap.getParentIndex(7));
		assertEquals(3, binaryHeap.getParentIndex(8));

		assertEquals(4, binaryHeap.getParentIndex(9));
		assertEquals(4, binaryHeap.getParentIndex(10));

		assertEquals(5, binaryHeap.getParentIndex(11));
		assertEquals(5, binaryHeap.getParentIndex(12));

		assertEquals(6, binaryHeap.getParentIndex(13));
		assertEquals(6, binaryHeap.getParentIndex(14));

		assertEquals(7, binaryHeap.getParentIndex(15));
		assertEquals(7, binaryHeap.getParentIndex(16));

		// Last valid index
		assertEquals(8, binaryHeap.getParentIndex(17));

		// Test invalid index
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getParentIndex(18));
	}

	@Test
	void testGetLeftChildIndex() {
		MinimumBinaryHeap<Integer> binaryHeap = createHeap();

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getLeftChildIndex(-1));

		// Test valid indexes
		assertEquals(1, binaryHeap.getLeftChildIndex(0));
		assertEquals(3, binaryHeap.getLeftChildIndex(1));
		assertEquals(5, binaryHeap.getLeftChildIndex(2));
		assertEquals(7, binaryHeap.getLeftChildIndex(3));
		assertEquals(9, binaryHeap.getLeftChildIndex(4));
		assertEquals(11, binaryHeap.getLeftChildIndex(5));
		assertEquals(13, binaryHeap.getLeftChildIndex(6));
		assertEquals(15, binaryHeap.getLeftChildIndex(7));
		assertEquals(17, binaryHeap.getLeftChildIndex(8));

		// Test indexes with no left child (all leaves)
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(9));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(10));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(11));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(12));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(13));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(14));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(15));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(16));
		assertEquals(NO_CHILD, binaryHeap.getLeftChildIndex(17));

		// Test invalid index
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getLeftChildIndex(18));
	}

	@Test
	void testGetRightChildIndex() {
		MinimumBinaryHeap<Integer> binaryHeap = createHeap();

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getRightChildIndex(-1));

		// Test valid indexes
		assertEquals(2, binaryHeap.getRightChildIndex(0));
		assertEquals(4, binaryHeap.getRightChildIndex(1));
		assertEquals(6, binaryHeap.getRightChildIndex(2));
		assertEquals(8, binaryHeap.getRightChildIndex(3));
		assertEquals(10, binaryHeap.getRightChildIndex(4));
		assertEquals(12, binaryHeap.getRightChildIndex(5));
		assertEquals(14, binaryHeap.getRightChildIndex(6));
		assertEquals(16, binaryHeap.getRightChildIndex(7));

		// Test indexes with no right child (has left child only)
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(8));

		// Test indexes with no right child (all leaves)
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(9));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(10));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(11));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(12));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(13));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(14));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(15));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(16));
		assertEquals(NO_CHILD, binaryHeap.getRightChildIndex(17));

		// Test invalid index
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getRightChildIndex(18));
	}

	@Test
	void testIsGreaterThan() {
		MinimumBinaryHeap<Integer> binaryHeap = createHeap();

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.hasGreaterValueThan(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.hasGreaterValueThan(0, -1));
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.hasGreaterValueThan(0, 18));
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.hasGreaterValueThan(18, 0));

		// It receives indexes and compares the elements at those indexes
		assertTrue(binaryHeap.hasGreaterValueThan(1, 0)); // 3 > 1
		assertFalse(binaryHeap.hasGreaterValueThan(0, 1)); // 1 > 3

		assertTrue(binaryHeap.hasGreaterValueThan(2, 0)); // 2 > 1
		assertFalse(binaryHeap.hasGreaterValueThan(0, 2)); // 1 > 2

		assertTrue(binaryHeap.hasGreaterValueThan(5, 2)); // 5 > 2
		assertFalse(binaryHeap.hasGreaterValueThan(2, 5)); // 2 > 5

		assertTrue(binaryHeap.hasGreaterValueThan(14, 6)); // 9 > 6
		assertFalse(binaryHeap.hasGreaterValueThan(6, 14)); // 6 > 9
	}

	@Test
	void getLowestChildIndex() {

		MinimumBinaryHeap<Integer> binaryHeap = createHeap();

		// Test invalid indexes
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getChildIndexWithLowestValue(-1));
		assertThrows(IllegalArgumentException.class, () -> binaryHeap.getChildIndexWithLowestValue(18));

		// Test valid indexes
		assertEquals(2, binaryHeap.getChildIndexWithLowestValue(0)); // min(3,2) = 2
		assertEquals(4, binaryHeap.getChildIndexWithLowestValue(1)); // min(7,4) = 4
		assertEquals(5, binaryHeap.getChildIndexWithLowestValue(2)); // min(5,6) = 5
		assertEquals(8, binaryHeap.getChildIndexWithLowestValue(3)); // min(15,10) = 8
		assertEquals(9, binaryHeap.getChildIndexWithLowestValue(4)); // min(8,14) = 9
		assertEquals(12, binaryHeap.getChildIndexWithLowestValue(5)); // min(13,11) = 11
		assertEquals(14, binaryHeap.getChildIndexWithLowestValue(6)); // min(12,9) = 14
		assertEquals(15, binaryHeap.getChildIndexWithLowestValue(7)); // min(16,18) = 15

		// Test index with only left child
		assertEquals(17, binaryHeap.getChildIndexWithLowestValue(8)); // min(17,-) = 17

		// Test indexes with no children (all leaves)
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(9));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(10));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(11));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(12));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(13));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(14));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(15));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(16));
		assertEquals(NO_CHILD, binaryHeap.getChildIndexWithLowestValue(17));
	}

	@Test
	void testToString() {
		MinimumBinaryHeap<Integer> binaryHeap = createHeap();
		assertEquals("[1, 3, 2, 7, 4, 5, 6, 15, 10, 8, 14, 13, 11, 12, 9, 16, 18, 17]", binaryHeap.toString());
	}

}
