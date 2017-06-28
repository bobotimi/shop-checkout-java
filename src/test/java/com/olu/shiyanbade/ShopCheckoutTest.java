package com.olu.shiyanbade;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ShopCheckoutTest {

    private static final Map<String, Double> PRICES = new HashMap<>();

    private ShopCheckout shopCheckout;

    @BeforeClass
    public static void runBeforeAllTests() {
        PRICES.put("orange", 0.25);
        PRICES.put("apple", 0.60);
    }

    @Before
    public void runBeforeTest() {
        shopCheckout = new ShopCheckout(PRICES);
    }

    @Test
    public void shouldReturnZeroWhenShoppingCartIsEmpty() {

        double cost = shopCheckout.checkout(emptyList());
        assertThat(cost, is(equalTo(0.0)));
    }

    @Test
    public void shouldReturnZeroWhenShoppingCartIsNull() {
        double cost = shopCheckout.checkout(null);
        assertThat(cost, is(equalTo(0.0)));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsApplesOnly() {
        double cost = shopCheckout.checkout(asList("Apple", "apple", "apple"));
        assertThat(cost, is(equalTo(1.2)));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsOrangesOnly() {
        double cost = shopCheckout.checkout(asList("Orange", "Orange"));
        assertThat(cost, is(equalTo(0.50)));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsApplesAndOranges() {
        double cost = shopCheckout.checkout(asList("Orange", "Apple", "Orange"));
        assertThat(cost, is(equalTo(1.10)));
    }

    @Test
    public void shouldReturnZeroForUnknownItemsInShoppingCart() {
        double cost = shopCheckout.checkout(asList("pear", "coconut", "peach"));
        assertThat(cost, is(equalTo(0.0)));
    }

    @Test
    public void shouldIgnoreUnknownItemsInShoppingCart() {
        double cost = shopCheckout.checkout(asList("apple", "coconut", "orange"));
        assertThat(cost, is(equalTo(0.85)));
    }

    @Test
    public void shouldNotChargeForSecondAppleWhenCartContainsTwoApplesOnly() {
        double cost = shopCheckout.checkout(asList("apple", "apple"));
        assertThat(cost, is(equalTo(0.60)));
    }

    @Test
    public void shouldNotChargeForEverySecondAppleWhenCartContainsMoreThanTwoApples() {
        double cost = shopCheckout.checkout(asList("apple", "apple", "apple", "APPLE"));
        assertThat(cost, is(equalTo(1.20)));

        cost = shopCheckout.checkout(asList("apple", "apple", "apple"));
        assertThat(cost, is(equalTo(1.20)));
    }

    @Test
    public void shouldNotChargeForThirdOrangeWhenCartContainaThreeOranges() {
        double cost = shopCheckout.checkout(asList("orange", "orange", "orange"));
        assertThat(cost, is(equalTo(0.50)));
    }

    @Test
    public void shouldNotChargeForEveryThirdOrangeWhenCartContainsMoreThanThreeOranges() {
        double cost = shopCheckout.checkout(asList("orange", "orange", "orange", "orange", "orange", "orange"));
        assertThat(cost, is(equalTo(1.00)));

        cost = shopCheckout.checkout(asList("orange", "orange", "orange", "orange"));
        assertThat(cost, is(equalTo(0.75)));
    }
}