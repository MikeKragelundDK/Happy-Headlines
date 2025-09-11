package CommentService.exceptions;

public class ProfanityServiceUnavailableException extends RuntimeException{
    public ProfanityServiceUnavailableException(String message, Throwable t) {
        super(message, t);
    }
}
