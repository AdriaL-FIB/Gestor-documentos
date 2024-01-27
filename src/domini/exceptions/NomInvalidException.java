package domini.exceptions;

public class NomInvalidException extends RuntimeException {
    public NomInvalidException() {
        super();
    }
    public NomInvalidException(String s) {
        super(s);
    }
}
