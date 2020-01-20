package qp.scs.exception;

public class LoginFailedException extends RuntimeException {
	public LoginFailedException() {
		super("Please verify your username, password and try again");
	}
}

