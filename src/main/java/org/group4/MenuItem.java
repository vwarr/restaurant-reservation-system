package org.group4;

import org.group4.exceptions.MenuItemException;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private final String name;
    private final String[] ingredients;
    private final List<Restaurant> offeredAt;
    private double averagePrice = 0;

    public MenuItem(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.offeredAt = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public List<Restaurant> getOfferedAt() {
        return offeredAt;
    }

    public void addOfferingRestaurant(Restaurant restaurant, int price) {
        this.offeredAt.add(restaurant);
        double n = offeredAt.size();
        // newAverage = oldAverage + (newValue - oldAverage) / newSize
        // from: https://math.stackexchange.com/questions/22348/how-to-add-and-subtract-values-from-an-average
        this.averagePrice = this.averagePrice + (price - this.averagePrice) / n;
    }

    public double getAveragePrice() throws MenuItemException.NeverAdded {
        if (offeredAt.isEmpty()) {
            throw new MenuItemException.NeverAdded();
        }
        return averagePrice;
    }

    public int calculatePopularity() throws MenuItemException.NeverAdded {
        if (offeredAt.isEmpty()) {
            throw new MenuItemException.NeverAdded();
        }
        int popularity = 0;
        for (Restaurant restaurant : offeredAt) {
            if (restaurant.getRestaurantMenuItems().containsKey(this.name)) {
                popularity++;
            }
        }
        return popularity;
    }

    public static class Builder {
        private String name;
        private String[] ingredients;
        private final List<Restaurant> offeredAt = new ArrayList<>();
        private int averagePrice = 0;

        public Builder(String name) {
            this.name = name;
        }

        public Builder ingredients(String[] ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder offeredAt(Restaurant restaurant) {
            this.offeredAt.add(restaurant);
            return this;
        }

        public MenuItem build() {
            return new MenuItem(name, ingredients);
        }
    }
}
