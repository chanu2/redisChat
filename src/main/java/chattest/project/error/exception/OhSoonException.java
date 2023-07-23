package chattest.project.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OhSoonException extends RuntimeException{

    private ErrorCode errorCode;
}
