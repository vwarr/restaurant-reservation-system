package org.group4;

public record Address(String streetName, String stateAbbreviation, String zipCode) {
    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", stateAbbreviation='" + stateAbbreviation + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
