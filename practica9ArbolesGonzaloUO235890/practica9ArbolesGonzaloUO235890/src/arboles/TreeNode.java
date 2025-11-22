package arboles;

class TreeNode<T extends Comparable<T>> {
	T element;
	TreeNode<T> left;
	TreeNode<T> right;

	/**
	 * Constructor
	 * 
	 * @param element
	 */
	public TreeNode(T element) {
		super();
		this.element = element;
	}

	/**
	 * @return altura del TreeNode
	 */
	int getHeight() {
		return heightRecursive(this) - 1;
	}

	/**
	 * Método auxiliar para sacar la altura del TreeNode
	 * 
	 * @param t
	 * @return Máximo de altura de los dos hijos
	 */
	private int heightRecursive(TreeNode<T> t) {
		if (t == null) {
			return 0;
		} else {
			int izq = heightRecursive(t.left);
			int der = heightRecursive(t.right);
			return Math.max(izq, der) + 1;
		}

	}

	/**
	 * @return factor de balance del TreeNode
	 */
	int getBalanceFactor() {
		if (left == null && right == null) {
			return 0;
		} else {
			if (right == null) {
				return -1 - left.getHeight();
			}
			if (left == null) {
				return right.getHeight() + 1;
			}
		}
		return right.getHeight() - left.getHeight();
	}

	/**
	 * Este método realiza un recorrido preorder recursivo sobre un nodo de un árbol
	 * binario y construye una cadena que representa el recorrido.
	 * 
	 * @param formato
	 * @return adena completa que representa el recorrido preorder del subárbol
	 *         formado por este nodo.
	 */
	String preorderRecursive(NodeFormat formato) {
		String cadena = formato.format(this);
		if (left != null) {
			cadena += left.preorderRecursive(formato);
		} else
			cadena += formato.formatNullNode();
		if (right != null) {
			cadena += right.preorderRecursive(formato);
		} else
			cadena += formato.formatNullNode();
		return cadena;
	}

}
