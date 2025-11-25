package hash;

public enum Status {
	DELETED,EMPTY,VALID;
	
	public String getStatusInitial() {
		switch(this) {
		case EMPTY:
			return "E";
		case VALID:
			return "V";
		case DELETED:
			return "D";
		default:
			return "?";
		}
	}

}
