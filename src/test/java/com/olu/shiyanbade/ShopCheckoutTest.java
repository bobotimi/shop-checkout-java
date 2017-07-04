package com.olu.shiyanbade;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ShopCheckoutTest {

    private static final Map<String, BigDecimal> PRICES = new HashMap<>();
    private static final int TWO_DECIMAL_PLACES = 2;

    private ShopCheckout shopCheckout;

    @BeforeClass
    public static void runBeforeAllTests() {
        PRICES.put("orange", BigDecimal.valueOf(0.25));
        PRICES.put("apple", BigDecimal.valueOf(0.60));
    }

    @Before
    public void runBeforeTest() {
        shopCheckout = new ShopCheckout(PRICES);
    }

    @Test
    public void shouldReturnZeroWhenShoppingCartIsEmpty() {

        BigDecimal cost = shopCheckout.checkout(emptyList());
        assertThat(cost, is(equalTo(ZERO)));
    }

    @Test
    public void shouldReturnZeroWhenShoppingCartIsNull() {
        BigDecimal cost = shopCheckout.checkout(null);
        assertThat(cost, is(equalTo(ZERO)));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsApplesOnly() {
        BigDecimal cost = shopCheckout.checkout(asList("Apple", "apple", "apple"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(1.2))));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsOrangesOnly() {
        BigDecimal cost = shopCheckout.checkout(asList("Orange", "Orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(0.50).setScale(TWO_DECIMAL_PLACES))));
    }

    @Test
    public void shouldReturnCostWhenShoppingCartContainsApplesAndOranges() {
        BigDecimal cost = shopCheckout.checkout(asList("Orange", "Apple", "Orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(1.10).setScale(TWO_DECIMAL_PLACES))));
    }

    @Test
    public void shouldReturnZeroForUnknownItemsInShoppingCart() {
        BigDecimal cost = shopCheckout.checkout(asList("pear", "coconut", "peach"));
        assertThat(cost, is(equalTo(ZERO)));
    }

    @Test
    public void shouldIgnoreUnknownItemsInShoppingCart() {
        BigDecimal cost = shopCheckout.checkout(asList("apple", "coconut", "orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(0.85))));
    }

    @Test
    public void shouldNotChargeForSecondAppleWhenCartContainsTwoApplesOnly() {
        BigDecimal cost = shopCheckout.checkout(asList("apple", "apple"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(0.60))));
    }

    @Test
    public void shouldNotChargeForEverySecondAppleWhenCartContainsMoreThanTwoApples() {
        BigDecimal cost = shopCheckout.checkout(asList("apple", "apple", "apple", "APPLE"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(1.20))));

        cost = shopCheckout.checkout(asList("apple", "apple", "apple"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(1.20))));
    }

    @Test
    public void shouldNotChargeForThirdOrangeWhenCartContainaThreeOranges() {
        BigDecimal cost = shopCheckout.checkout(asList("orange", "orange", "orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(0.50).setScale(TWO_DECIMAL_PLACES))));
    }

    @Test
    public void shouldNotChargeForEveryThirdOrangeWhenCartContainsMoreThanThreeOranges() {
        BigDecimal cost = shopCheckout.checkout(asList("orange", "orange", "orange", "orange", "orange", "orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(1.00).setScale(TWO_DECIMAL_PLACES))));

        cost = shopCheckout.checkout(asList("orange", "orange", "orange", "orange"));
        assertThat(cost, is(equalTo(BigDecimal.valueOf(0.75))));
    }
}