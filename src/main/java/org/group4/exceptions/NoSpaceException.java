package org.group4.exceptions;public class NoSpaceException extends Exception {
    public NoSpaceException() {
        super("Seats not available - Request denied");
    }
}
