package org.group4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Owner implements Person {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final LocalDate startDate;
    private final List<License> licenses;

    public Owner(LocalDate startDate, List<License> licenses, String uniqueId, String firstName, String lastName,
                 Address address) {
        this.id = (uniqueId == null) ? UUID.randomUUID().toString() : uniqueId;
        this.startDate = startDate;
        this.licenses = licenses;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License license) {
        this.licenses.add(license);
    }

    public String getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public static class Builder {
        private String id;
        private String firstName = "Unnamed Owner";
        private String lastName = "Guy";
        private Address address = new Address("123", "Unnamed Street", 12345);
        private LocalDate startDate = LocalDate.parse("2024-06-05");
        private List<License> licenses = new ArrayList<>();

        public Builder(String id) {
            this.id = id;
        }

        public Builder setLicenses(List<License> licenses) {
            this.licenses = licenses;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder address(String street, String city, int zip) {
            this.address = new Address(street, city, zip);
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Owner build() {
            return new Owner(startDate, licenses, id, firstName, lastName, address);
        }
    }
}

