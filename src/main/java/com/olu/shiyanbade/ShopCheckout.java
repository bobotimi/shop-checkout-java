package com.olu.shiyanbade;


import java.util.List;
import java.util.Map;

public class ShopCheckout {
    public static final String APPLE = "apple";
    public static final String ORANGE = "orange";
    private Map<String, Double> prices;

    public ShopCheckout(Map<String, Double> prices) {
        this.prices = prices;
    }

    public double checkout(List<String> items) {
        double cost = 0.0;
        int apples = 0;
        int oranges = 0;
        if (items != null) {
            for (String item : items) {
                String priceKey = item.toLowerCase();
                cost += prices.getOrDefault(priceKey, 0.0);

                if (APPLE.equals(priceKey)) {
                    ++apples;
                    cost = applyAppleDiscount(cost, apples, priceKey);
                } else if (ORANGE.equals(priceKey)) {
                    ++oranges;
                    cost = applyOrangeDiscount(cost, oranges, priceKey);
                }
            }
        }
        return Double.parseDouble(String.format("%.2f", cost));
    }

    private double applyOrangeDiscount(double cost, int oranges, String priceKey) {
        if (oranges % 3 == 0) {
            cost -= prices.get(priceKey);
        }
        return cost;
    }

    private double applyAppleDiscount(double cost, int apples, String priceKey) {
        if (apples % 2 == 0) {
            cost -= prices.get(priceKey);
        }
        return cost;
    }
}
