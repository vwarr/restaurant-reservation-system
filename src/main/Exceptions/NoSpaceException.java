package main.Exceptions;public class NoSpaceException extends Exception {
    public NoSpaceException() {
        super("Restaurant has no space right now");
    }
}
