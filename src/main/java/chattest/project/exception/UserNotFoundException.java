package chattest.project.exception;

import chattest.project.error.exception.ErrorCode;
import chattest.project.error.exception.OhSoonException;

public class UserNotFoundException extends OhSoonException {

    public static final OhSoonException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
