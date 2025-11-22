package arboles;

public class AVLTree<T extends Comparable<T>> extends BSTTree<T> {

	@Override
	TreeNode<T> add(TreeNode<T> currentNode, T element) {
		TreeNode<T> current = super.add(currentNode, element);
		current = comprobarYBalancearSiHaceFalta(current);
		return current;

	}

	/**
	 * Método que comprueba el estado del AVL y los balancea si fuese necesario
	 * 
	 * @param current
	 * @return la estructura del subarbol desde el nodo que se comprueba
	 */
	private TreeNode<T> comprobarYBalancearSiHaceFalta(TreeNode<T> current) {
		if (current.getBalanceFactor() > 1) {
			if (current.right.getBalanceFactor() < 0) {
				return doubleRightRotation(current);
			} else {
				return singleRightRotation(current);
			}
		}

		if (current.getBalanceFactor() < -1) {
			if (current.left.getBalanceFactor() > 0) {
				return doubleLeftRotation(current);
			} else {
				return singleLeftRotation(current);
			}
		}
		return current;
	}

	@Override
	TreeNode<T> removeRecursive(TreeNode<T> currentNode, T elementoAEliminar) {
		TreeNode<T> current = super.removeRecursive(currentNode, elementoAEliminar);
		if (current != null) {
			current = comprobarYBalancearSiHaceFalta(current);
		}

		return current;
	}

	/**
	 * Método encargado de hacer rotaciones simples a la derecha
	 * 
	 * @param currentNode
	 * @return la nueva estrcutura del árbol que se rotó
	 */
	private TreeNode<T> singleRightRotation(TreeNode<T> currentNode) {
		TreeNode<T> aux = currentNode.right;
		currentNode.right = aux.left;
		aux.left = currentNode;
		return aux;
	}

	/**
	 * Método encargado de hacer rotaciones simples a la izquierda
	 * 
	 * @param currentNode
	 * @return la nueva estrcutura del árbol que se rotó
	 */
	private TreeNode<T> singleLeftRotation(TreeNode<T> currentNode) {
		TreeNode<T> aux = currentNode.left;
		currentNode.left = aux.right;
		aux.right = currentNode;
		return aux;
	}

	/**
	 * Método encargado de hacer rotaciones dobles a la derecha
	 * 
	 * @param currentNode
	 * @return la nueva estrcutura del árbol que se rotó
	 */
	private TreeNode<T> doubleRightRotation(TreeNode<T> currentNode) {
		currentNode.right = singleLeftRotation(currentNode.right);
		return singleRightRotation(currentNode);
	}

	/**
	 * Método encargado de hacer rotaciones dobles a la izquierda
	 * 
	 * @param currentNode
	 * @return la nueva estrcutura del árbol que se rotó
	 */
	private TreeNode<T> doubleLeftRotation(TreeNode<T> currentNode) {
		currentNode.left = singleRightRotation(currentNode.left);
		return singleLeftRotation(currentNode);
	}

}
