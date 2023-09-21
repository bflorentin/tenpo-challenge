package ar.com.florentin.tenpo.challenge.exception;

public class PercentageClientException extends RuntimeException {
    public PercentageClientException(String message) {
        super(message);
    }
    public PercentageClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
