package com.olu.shiyanbade;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

public class ShopCheckout {
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private Map<String, BigDecimal> prices;

    public ShopCheckout(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    public BigDecimal checkout(List<String> items) {
        return ofNullable(items)
                .orElse(emptyList())
                .stream()
                .map(String::toLowerCase)
                .filter(prices::containsKey)
                .collect(groupingBy(String::toString, mapping(i -> i, counting())))
                .entrySet()
                .stream()
                .map(this::applyDiscount)
                .reduce(BigDecimal::add)
                .orElse(ZERO);
    }

    private BigDecimal applyDiscount(Entry<String, Long> itemAndFrequency) {
        String theItem = itemAndFrequency.getKey();
        BigDecimal discountedPrice = ZERO;
        if (APPLE.equalsIgnoreCase(theItem)) {
            Long frequency = itemAndFrequency.getValue();
            discountedPrice = prices.get(theItem).multiply(BigDecimal.valueOf(frequency - frequency / 2));
        } else if (ORANGE.equalsIgnoreCase(theItem)) {
            Long frequency = itemAndFrequency.getValue();
            discountedPrice = prices.get(theItem).multiply(BigDecimal.valueOf(frequency - frequency / 3));
        }
        return discountedPrice;
    }
}
