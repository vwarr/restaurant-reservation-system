package org.group4;

import java.time.LocalDate;
import java.util.List;

class Owner {
    private LocalDate startDate;
    private List<String> licenses;

    public Owner(LocalDate startDate, List<String> licenses) {
        this.startDate = startDate;
        this.licenses = licenses;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void addLicense(String license) {
        // Need to validate the liscenses
        licenses.add(license);
    }

}
