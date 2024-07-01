package org.group4;

public class OwnerException {

    public static class DuplicatedUniqueId extends Exception {
        public DuplicatedUniqueId() {
            super("ERROR: duplicate unique identifier");
        }
    }
}
