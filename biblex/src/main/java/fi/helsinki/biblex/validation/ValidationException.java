package fi.helsinki.biblex.validation;

/**
 * Encapsulates an exception message.
 */
public class ValidationException extends Exception {
    public ValidationException(String msg) {
        super(msg);
    }
}
