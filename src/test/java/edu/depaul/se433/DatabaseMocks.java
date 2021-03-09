package edu.depaul.se433;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseMocks {
    @Test
    @DisplayName("Testing purchaseAgent averagePurchase functionality")
    void testAveragePurchaseMock() {
        PurchaseDBO purchaseDBO = mock(PurchaseDBO.class);
        PurchaseAgent purchaseAgent = new PurchaseAgent(purchaseDBO);

        List<Purchase> list = new ArrayList<>();
        list.add(Purchase.make("Cup", LocalDate.now(), 10, "IL", "STANDARD"));
        list.add(Purchase.make("Spoon", LocalDate.now(), 25, "WA", "STANDARD"));
        when(purchaseDBO.getPurchases(anyString())).thenReturn(list);

        double value = purchaseAgent.averagePurchase("agaspari");
        assertEquals(17.5, value);
        verify(purchaseDBO).getPurchases("agaspari");
    }

    @Test
    @DisplayName("Testing DBO savePurchase runs")
    void testDBOSaveMock() {
        PurchaseDBO purchaseDBO = mock(PurchaseDBO.class);
        PurchaseAgent purchaseAgent = new PurchaseAgent(purchaseDBO);

        Purchase purchase = Purchase.make("Cup", LocalDate.now(), 10, "IL", "STANDARD");

        purchaseAgent.save(purchase);

        verify(purchaseDBO).savePurchase(purchase);
    }
}
