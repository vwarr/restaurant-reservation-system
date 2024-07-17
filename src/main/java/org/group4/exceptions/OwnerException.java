package org.group4.exceptions;

public class OwnerException {

    public static class DuplicatedUniqueId extends Exception {
        public DuplicatedUniqueId() {
            super("ERROR: duplicate unique identifier");
        }
    }

    public static class DoesNotExist extends Exception {
        public DoesNotExist() { super("ERROR: owner does not exist"); }
    }
}
