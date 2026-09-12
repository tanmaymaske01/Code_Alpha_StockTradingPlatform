package stocktrading;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private Map<String, Integer> holdings;

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();

        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public boolean sellStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();

        if (holdings.containsKey(symbol) && holdings.get(symbol) >= quantity) {

            int remaining = holdings.get(symbol) - quantity;

            if (remaining == 0) {
                holdings.remove(symbol);
            } else {
                holdings.put(symbol, remaining);
            }

            return true;
        }

        return false;
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public void displayPortfolio(Map<String, Stock> market) {

        System.out.println("\n----- YOUR PORTFOLIO -----");

        if (holdings.isEmpty()) {
            System.out.println("No stocks in portfolio.");
            return;
        }

        double totalValue = 0;

        for (String symbol : holdings.keySet()) {

            int quantity = holdings.get(symbol);
            Stock stock = market.get(symbol);

            if (stock != null) {

                double value = quantity * stock.getPrice();
                totalValue += value;

                System.out.println(
                    symbol + " | Quantity: " + quantity +
                    " | Current Price: ₹" + stock.getPrice() +
                    " | Value: ₹" + value
                );
            }
        }

        System.out.println("--------------------------");
        System.out.println("Total Portfolio Value: ₹" + totalValue);
    }
}