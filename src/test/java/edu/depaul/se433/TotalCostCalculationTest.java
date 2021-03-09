package edu.depaul.se433;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TotalCostCalculationTest {
    // Equivalence Testing
    // Strong Normal test
    @Test
    @DisplayName("Testing Standard Shipping w/ Sales Tax & Free Shipping")
    void testStandardTaxAbove50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 3);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);

        assertEquals(63.6, bill.total());
    }

    @Test
    @DisplayName("Testing Standard Shipping w/ Sales Tax & No Free Shipping")
    void testStandardTaxBelow50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);

        assertEquals(31.8, bill.total());
    }

    @Test
    @DisplayName("Testing Next Day Shipping w/ Sales Tax & Free Shipping")
    void testNextDayTaxAbove50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 3);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);

        assertEquals(90.1, bill.total());
    }

    @Test
    @DisplayName("Testing Next Day Shipping w/ Sales Tax & No Free Shipping")
    void testNextDayTaxBelow50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);

        assertEquals(47.7, bill.total());
    }

    @Test
    @DisplayName("Testing Standard Shipping w/ No Sales Tax & Free Shipping")
    void testStandardNoTaxAbove50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 3);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "AK", shipping);

        assertEquals(60, bill.total());
    }

    @Test
    @DisplayName("Testing Standard Shipping w/ No Sales Tax & No Free Shipping")
    void testStandardNoTaxBelow50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "AK", shipping);

        assertEquals(20, bill.total());
    }

    @Test
    @DisplayName("Testing NextDay Shipping w/ No Sales Tax & Free Shipping")
    void testNextDayNoTaxAbove50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 3);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "AK", shipping);

        assertEquals(85, bill.total());
    }

    @Test
    @DisplayName("Testing NextDay Shipping w/ No Sales Tax & No Free Shipping")
    void testNextDayNoTaxBelow50() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);

        Bill bill = TotalCostCalculator.calculate(cart, "AK", shipping);

        assertEquals(45, bill.total());
    }

    // Weak Robust Tests

    @Test
    @DisplayName("Testing Invalid Tax Code")
    void testInvalidTaxCode() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        assertAll(
                () -> assertThrows(Exception.class, () -> TotalCostCalculator.calculate(cart, null, shipping)),
                () -> assertThrows(Exception.class, () -> TotalCostCalculator.calculate(cart, "ABC", shipping))
        );
    }

    @Test
    @DisplayName("Testing Invalid Shipping Option")
    void testInvalidShipping() {
        PurchaseItem item = new PurchaseItem("Cup", 20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        cart.addItem(item);

        assertThrows(Exception.class, () -> TotalCostCalculator.calculate(cart, "IL", shipping));
    }

    @Test
    @DisplayName("Testing Invalid Price")
    void testInvalidPrice() {
        PurchaseItem item = new PurchaseItem("Cup", -20, 1);

        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);

        assertThrows(Exception.class, () -> TotalCostCalculator.calculate(cart, "IL", shipping));
    }

    //Boundary Tests
    @Test
    @DisplayName("Testing Below Free Shipping")
    void testFreeShippingBelow50() {
        double cost = TotalCostCalculator.calculate(50.00, "IL", ShippingType.STANDARD);
        assertEquals(60.00, cost);
    }

    @Test
    @DisplayName("Testing Free Shipping")
    void testFreeShippingAbove50() {
        double cost = TotalCostCalculator.calculate(50.01, "IL", ShippingType.STANDARD);
        assertEquals(50.01, cost);
    }

    @Test
    @DisplayName("Testing Invalid Price")
    void testInvalidPriceBoundary() {
        assertThrows(IllegalArgumentException.class, () -> TotalCostCalculator.calculate(-0.01, "IL", ShippingType.STANDARD));
    }

    @Test
    @DisplayName("Testing Valid Price")
    void testValidPriceBoundary() {
        double cost = TotalCostCalculator.calculate(0.01, "IL", ShippingType.STANDARD);
        assertEquals(10.01, cost);
    }
}
