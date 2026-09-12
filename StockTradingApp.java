package stocktrading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockTradingApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Map<String, Stock> market = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Market Stocks
        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500));
        market.put("INFY", new Stock("INFY", "Infosys", 1600));
        market.put("RELIANCE", new Stock("RELIANCE", "Reliance Industries", 2900));
        market.put("HDFC", new Stock("HDFC", "HDFC Bank", 1750));
        market.put("ITC", new Stock("ITC", "ITC Limited", 500));

        // User
        User user = new User("Tanmay", 100000);

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("       STOCK TRADING PLATFORM");
            System.out.println("================================");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. View Account Balance");
            System.out.println("7. Exit");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n----- MARKET DATA -----");

                    for (Stock stock : market.values()) {
                        stock.displayStock();
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    if (!market.containsKey(buySymbol)) {
                        System.out.println("Stock not found.");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int buyQuantity = sc.nextInt();

                    if (buyQuantity <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    Stock buyStock = market.get(buySymbol);
                    double buyAmount = buyStock.getPrice() * buyQuantity;

                    if (user.deductBalance(buyAmount)) {

                        user.getPortfolio().buyStock(buyStock, buyQuantity);

                        transactions.add(
                            new Transaction(
                                "BUY",
                                buySymbol,
                                buyQuantity,
                                buyStock.getPrice()
                            )
                        );

                        System.out.println("Stock purchased successfully!");
                        System.out.println("Amount paid: ₹" + buyAmount);

                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    if (!market.containsKey(sellSymbol)) {
                        System.out.println("Stock not found.");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int sellQuantity = sc.nextInt();

                    if (sellQuantity <= 0) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    Stock sellStock = market.get(sellSymbol);

                    if (user.getPortfolio().sellStock(sellStock, sellQuantity)) {

                        double sellAmount = sellStock.getPrice() * sellQuantity;

                        user.addBalance(sellAmount);

                        transactions.add(
                            new Transaction(
                                "SELL",
                                sellSymbol,
                                sellQuantity,
                                sellStock.getPrice()
                            )
                        );

                        System.out.println("Stock sold successfully!");
                        System.out.println("Amount received: ₹" + sellAmount);

                    } else {
                        System.out.println("You don't have enough shares.");
                    }
                    break;

                case 4:
                    user.getPortfolio().displayPortfolio(market);
                    break;

                case 5:
                    System.out.println("\n----- TRANSACTION HISTORY -----");

                    if (transactions.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (Transaction transaction : transactions) {
                            transaction.displayTransaction();
                        }
                    }
                    break;

                case 6:
                    user.displayUser();
                    break;

                case 7:
                    System.out.println("\nThank you for using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}