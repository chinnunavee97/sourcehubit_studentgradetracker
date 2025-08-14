                    Stock Trading Mini
Programme

import java.util.*;

public class StockTradingMini {

    // ----- Simple Stock model -----
    static class Stock {
        String name;
        double price;
        Stock(String name, double price) { this.name = name; this.price = price; }
    }

    // ----- Data Stores -----
    static final Map<String, Stock> MARKET = new HashMap<>();
    static final Map<String, Integer> PORTFOLIO = new HashMap<>();
    static final Random R = new Random();

    public static void main(String[] args) {
        // Mock market data
        MARKET.put("AAPL", new Stock("AAPL", randomPrice()));
        MARKET.put("TSLA", new Stock("TSLA", randomPrice()));
        MARKET.put("AMZN", new Stock("AMZN", randomPrice()));
        MARKET.put("GOOG", new Stock("GOOG", randomPrice()));

        Scanner sc = new Scanner(System.in);
        while (true) {
            updatePrices(); // simulate market move
            System.out.println("\n1. View Market  2. Buy  3. Sell  4. Portfolio  5. Exit");
            System.out.print("Choose: ");
            int choice = safeInt(sc);

            switch (choice) {
                case 1:
                    showMarket();
                    break;
                case 2:
                    trade(sc, true);
                    break;
                case 3:
                    trade(sc, false);
                    break;
                case 4:
                    showPortfolio();
                    break;
                case 5:
                    System.out.println("Thank you for trading!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ----- Helpers -----
    static double randomPrice() { return 100 + R.nextDouble() * 900; } // 100..1000

    static void updatePrices() {
        for (Stock s : MARKET.values()) {
            double delta = R.nextDouble() * 20 - 10; // -10 .. +10
            s.price = Math.max(1, s.price + delta);
        }
    }

    static void showMarket() {
        System.out.println("\n--- Market Prices ---");
        for (Stock s : MARKET.values()) {
            System.out.printf("%s: %.2f%n", s.name, s.price);
        }
    }

    static void trade(Scanner sc, boolean buy) {
        System.out.print("Enter stock symbol: ");
        String sym = sc.next().trim().toUpperCase(Locale.ROOT);

        if (!MARKET.containsKey(sym)) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = safeInt(sc);
        if (qty <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        if (buy) {
            PORTFOLIO.put(sym, PORTFOLIO.getOrDefault(sym, 0) + qty);
            System.out.println("Bought " + qty + " of " + sym);
        } else {
            int owned = PORTFOLIO.getOrDefault(sym, 0);
            if (owned == 0) {
                System.out.println("You don't own " + sym);
                return;
            }
            int sellQty = Math.min(qty, owned);
            int remaining = owned - sellQty;
            if (remaining == 0) PORTFOLIO.remove(sym); else PORTFOLIO.put(sym, remaining);
            System.out.println("Sold " + sellQty + " of " + sym + (qty > owned ? " (clamped to owned)" : ""));
        }
    }

    static void showPortfolio() {
        System.out.println("\n--- Your Portfolio ---");
        if (PORTFOLIO.isEmpty()) {
            System.out.println("No holdings yet.");
            return;
        }
        double total = 0.0;
        for (Map.Entry<String, Integer> e : PORTFOLIO.entrySet()) {
            String sym = e.getKey();
            int qty = e.getValue();
            double val = qty * MARKET.get(sym).price;
            System.out.printf("%s: %d shares | Value: %.2f%n", sym, qty, val);
            total += val;
        }
        System.out.printf("Total Portfolio Value: %.2f%n", total);
    }

    // Safe integer read (avoids InputMismatchException)
    static int safeInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
        return sc.nextInt();
    }
}


OUTPUT

View Market 2. Buy 3. Sell 4. Portfolio 5. Exit
1

--- Market Prices ---
AAPL: 536.48
TSLA: 753.19
AMZN: 621.73

View Market 2. Buy 3. Sell 4. Portfolio 5. Exit
2
Enter stock symbol: AAPL
Enter quantity: 5
Bought 5 of AAPL

View Market 2. Buy 3. Sell 4. Portfolio 5. Exit
4

--- Your Portfolio ---
AAPL: 5 shares | Value: 2682.40
Total Portfolio Value: 2682.40