package org.group4;public class NoSpaceException extends Exception {
    public NoSpaceException() {
        super("Restaurant has no space right now");
    }
}
