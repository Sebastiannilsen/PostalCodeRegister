package no.ntnu.idata2001.PostalCodeRegister.Exceptions;

/**
 * The type Remove exception.
 * This class is used to throw an exception when trying to remove an object from a list.
 */
public class RemoveException extends Exception {
    /**
     * Instantiates a new Remove exception.
     *
     * @param s the s
     */
    public RemoveException(String s){
        super(s);
    }
}

