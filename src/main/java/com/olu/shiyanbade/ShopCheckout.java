package com.olu.shiyanbade;


import java.util.List;
import java.util.Map;

public class ShopCheckout {
    public static final String APPLE = "apple";
    private Map<String, Double> prices;

    public ShopCheckout(Map<String, Double> prices) {
        this.prices = prices;
    }

    public double checkout(List<String> items) {
        double cost = 0.0;
        int apples = 0;
        if (items != null) {
            for (String item : items) {
                String priceKey = item.toLowerCase();
                cost += prices.getOrDefault(priceKey, 0.0);

                if (APPLE.equals(priceKey)) {
                    ++apples;
                    if (apples % 2 == 0) {
                        cost -= prices.get(priceKey);
                    }
                }
            }
        }
        return Double.parseDouble(String.format("%.2f", cost));
    }
}
