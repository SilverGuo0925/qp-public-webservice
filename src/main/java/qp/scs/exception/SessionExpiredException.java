package qp.scs.exception;

public class SessionExpiredException extends RuntimeException {
	public SessionExpiredException() {
		super("Session expired, please login again");
	}
}

