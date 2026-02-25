package Sigebi.domain.exeption;
import Sigebi.domain.common.exception.InValidationException;

public class InvalidStateException extends InValidationException {
    public InvalidStateException(String message) {
        super(message);
    }
}
