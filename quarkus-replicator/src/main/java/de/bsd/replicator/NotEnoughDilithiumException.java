package de.bsd.replicator;

/**
 * @author hrupp
 */
public class NotEnoughDilithiumException extends RuntimeException {
    public NotEnoughDilithiumException(String tea) {
        super("Not enough dilithium available to make " + tea + " tea") ;
    }
}
