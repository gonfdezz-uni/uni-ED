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

	/**
	 * Método auxiliar recursivo usado para añadir elementos al árbol de acuerdo al
	 * comportamiento de BSTTree
	 * 
	 * @param currentNode
	 * @param element
	 * @return el nodo añadido
	 */
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

	/**
	 * Método auxiliar recursivo usado para la búsqueda de nodos en el árbol
	 * 
	 * @param currentNode
	 * @param element
	 * @return true si lo encuentra, false en caso contrario
	 */
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

	@Override
	public void remove(T element) {
		checkNull(element);
		root = removeRecursive(root, element);
	}

	/**
	 * Método auxiliar recursivo usado para el borrado de nodos en el árbol
	 * 
	 * @param currentNode
	 * @param elementoAEliminar
	 * @return el elemento borrado
	 */
	TreeNode<T> removeRecursive(TreeNode<T> currentNode, T elementoAEliminar) {
		if (currentNode == null) {
			throw new IllegalArgumentException();
		}

		int comparacion = elementoAEliminar.compareTo(currentNode.element);

		if (comparacion == 0) {
			if (currentNode.right == null && currentNode.left == null) {
				return null;
			}
			if (currentNode.left == null) {
				return currentNode.right;
			}
			if (currentNode.right == null) {
				return currentNode.left;
			} else {
				TreeNode<T> mayorDeMenores = getMaxNode(currentNode.left);
				currentNode.element = mayorDeMenores.element;
				currentNode.left = removeRecursive(currentNode.left, mayorDeMenores.element);
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
		if (root == null) {
			return NodeFormat.HEIGHT.formatNullNode();
		}
		return root.preorderRecursive(NodeFormat.HEIGHT);
	}

	@Override
	public String preorderTraversalHeightAndBalanceFactor() {
		if (root == null) {
			return NodeFormat.HEIGHT_AND_BALANCE_FACTOR.formatNullNode();
		}
		return root.preorderRecursive(NodeFormat.HEIGHT_AND_BALANCE_FACTOR);
	}

	/**
	 * Metodo encargado de comprobar si element es null
	 * 
	 * @param element
	 * @throws NullPointerException
	 */
	private void checkNull(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Método que encuentra el nodo máximo del árbol de manera recursiva
	 * 
	 * @param currentNode
	 * @return nodo máximo
	 */
	TreeNode<T> getMaxNode(TreeNode<T> currentNode) {
		if (currentNode.right == null) {
			return currentNode;
		}
		return getMaxNode(currentNode.right);
	}

	/**
	 * Método que encuentra el nodo máximo del árbol de manera iterativa
	 * 
	 * @param currentNode
	 * @return nodo máximo
	 */
	TreeNode<T> getMaxNodeIterative(TreeNode<T> currentNode) {
		TreeNode<T> nextNode = currentNode.right;
		while (nextNode != null) {
			currentNode = nextNode;
			nextNode = currentNode.right;
		}
		return currentNode;
	}

}
