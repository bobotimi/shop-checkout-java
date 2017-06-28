package com.olu.shiyanbade;


import java.util.List;
import java.util.Map;

public class ShopCheckout {
    private Map<String, Double> prices;

    public ShopCheckout(Map<String, Double> prices) {
        this.prices = prices;
    }

    public double checkout(List<String> items) {
        double cost = 0.0;
        if (items != null) {
            for (String item : items) {
                cost += prices.getOrDefault(item.toLowerCase(), 0.0);
            }
        }
        return cost;
    }
}
