package org.group4.Exceptions;

public class OwnerException {

    public static class DuplicatedUniqueId extends Exception {
        public DuplicatedUniqueId() {
            super("ERROR: duplicate unique identifier");
        }
    }

    public static class OwnerDoesNotExist extends Exception {
        public OwnerDoesNotExist() { super("ERROR: owner does not exist"); }
    }
}
