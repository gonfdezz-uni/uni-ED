package arboles;

//Ocultacion de paquete para que no se sepa como funcionan los arboles, que seran publico
//Desde fuera del paquete, esta clase no existe, es estructura interna
class TreeNode<T extends Comparable<T>> {
	T element;
	TreeNode<T> left;
	TreeNode<T> right;

	public TreeNode(T element) {
		super();
		this.element = element;
	}

	int getHeight() {
		return heightRecursive(this)-1;
	}

	private int heightRecursive(TreeNode<T> t) {
		if (t == null) {
            return 0;
        } else {
            int izq = heightRecursive(t.left);
            int der = heightRecursive(t.right);
            return Math.max(izq, der)+1;
        }

	}

	int getBalanceFactor() {
		if(left == null && right == null) {
			return 0;
		}else {
			if(right ==null) {
				return -1-left.getHeight();
			}
			if(left==null) {
				return right.getHeight()+1;
			}
		}
		return right.getHeight() - left.getHeight();
	}

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
