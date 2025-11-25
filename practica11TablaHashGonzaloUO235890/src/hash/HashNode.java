package hash;

public class HashNode<T> {
	private T element;
	private Status status;

	public HashNode() {
		element = null;
		status = Status.EMPTY;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{").append(status.getStatusInitial()).append("|");

		if (element == null) {
			sb.append("-");
		} else {
			sb.append(element);
		}
		sb.append("}");

		return sb.toString();
	}

	public T getElement() {
		return element;
	}

	public Status getStatus() {
		return status;
	}

	void setStatus(Status status2) {
		this.status = status2;
	}

	void setElement(T element2) {
		this.element = element2;
	}
}
