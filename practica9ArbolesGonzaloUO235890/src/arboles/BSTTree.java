package arboles;

public class BSTTree<T extends Comparable<T>> implements Tree<T> {
	private TreeNode<T> root;

	@Override
	public TreeNode<T> getRoot() {
		return root;
	}

	@Override
	public void add(T element) {
		checkNull(element);
		root = add(root, element);
	}

	TreeNode<T> add(TreeNode<T> currentNode, T element) {
		if (currentNode == null) {
			return new TreeNode<>(element);
		}
		int comparacion = element.compareTo(currentNode.element);

		if (comparacion == 0) {
			throw new IllegalArgumentException("No se admiten repetidos.");
		} else if (comparacion < 0) {
			currentNode.left = add(currentNode.left, element);
		} else {
			currentNode.right = add(currentNode.right, element);
		}
		return currentNode;
	}

	@Override
	public boolean search(T element) {
		checkNull(element);
		return searchRecursive(root, element);
	}

	private boolean searchRecursive(TreeNode<T> currentNode, T element) {
		if (currentNode == null)
			return false;
		int comparacion = element.compareTo(currentNode.element);

		if (comparacion == 0) {
			return true;
		} else if (comparacion < 0) {
			return searchRecursive(currentNode.left, element);
		} else {
			return searchRecursive(currentNode.right, element);
		}
	}

	/*
	 * Hacerlo por desarrollo incremental. Seguir el orden de los tests.
	 */
	@Override
	public void remove(T element) {
		checkNull(element);
		root = removeRecursive(root, element);
	}

	TreeNode<T> removeRecursive(TreeNode<T> currentNode, T elementoAEliminar) {
		if (currentNode == null) {
			throw new IllegalArgumentException();
		}

		int comparacion = elementoAEliminar.compareTo(currentNode.element);

		if (comparacion == 0) {
			// Algoritmo de eliminación con los tres casos
			if (currentNode.right == null && currentNode.left == null) {
				return null;
			}
			if (currentNode.left == null) {
				return currentNode.right;
			}
			if (currentNode.right == null) {
				return currentNode.left;
			} else {
				// 1. Busco el mayor de menores
				TreeNode<T> mayorDeMenores = getMaxNode(currentNode.left);
				// 2. Susutituir la clave del nodo a borrar por la clave del mayor de menores
				currentNode.element = mayorDeMenores.element;
				// 3. Borrar el nodo cuya clave es ese máximo
				currentNode.left = removeRecursive(currentNode.left, mayorDeMenores.element);
				// 4. Devolver subárbol modificado
				
			}
		} else if (comparacion < 0) {
			currentNode.left = removeRecursive(currentNode.left, elementoAEliminar);
		} else {
			currentNode.right = removeRecursive(currentNode.right, elementoAEliminar);
		}
		return currentNode;
	}

	@Override
	public String preorderTraversal() {
		if (root == null) {
			return NodeFormat.BASIC.formatNullNode();
		}
		return root.preorderRecursive(NodeFormat.BASIC);
	}

	@Override
	public String preorderTraversalHeight() {
		if(root == null) {
			return NodeFormat.HEIGHT.formatNullNode();
		}
		return root.preorderRecursive(NodeFormat.HEIGHT);
	}

	@Override
	public String preorderTraversalHeightAndBalanceFactor() {
		if(root == null) {
			return NodeFormat.HEIGHT_AND_BALANCE_FACTOR.formatNullNode();
		}
		return root.preorderRecursive(NodeFormat.HEIGHT_AND_BALANCE_FACTOR);
	}

	private void checkNull(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
	}

	// Los métodos que devuelvan nodos tendrán siempre de paquete
	TreeNode<T> getMaxNode(TreeNode<T> currentNode) {
		// Muy importante poner caso base
		if (currentNode.right == null) {
			return currentNode;
		}
		return getMaxNode(currentNode.right);
	}

	TreeNode<T> getMaxNodeIterative(TreeNode<T> currentNode) {
		TreeNode<T> nextNode = currentNode.right;
		while (nextNode != null) {
			currentNode = nextNode;
			nextNode = currentNode.right;
		}
		return currentNode;
	}

}
