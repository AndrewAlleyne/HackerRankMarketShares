import java.util.*;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        Quotes currentMarketQuote = new Quotes();

        List<Position> position = account.getPosition();
        HashMap<String, List<HashMap<String, Integer>>> portfolioStats = new HashMap<>();
        List<Quote> quotesList = currentMarketQuote.getQuotes();

        //Place all items in the hashmap
        for (Position p : position) {
            if (!portfolioStats.containsKey(p.getAccountId())) {
                List<HashMap<String, Integer>> innerList = new ArrayList<>();
                HashMap<String, Integer> innerMap = new HashMap<>();

                innerMap.put(p.marker, p.shares);
                innerList.add(innerMap);
                portfolioStats.put(p.getAccountId(), innerList);
            } else {
                HashMap<String, Integer> innerMap = new HashMap<>();
                innerMap.put(p.marker, p.shares);
                List<HashMap<String, Integer>> list = portfolioStats.get(p.getAccountId());
                list.add(innerMap);
                portfolioStats.put(p.accountId, list);
            }
        }

        HashMap<String, Integer> marketQuotes = new HashMap<>();
        for (Quote quote : quotesList) {
            if (!marketQuotes.containsKey(quote.marker)) {
                marketQuotes.put(quote.marker, quote.price);
            }
        }

        HashMap<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, List<HashMap<String, Integer>>> entry : portfolioStats.entrySet()) {
            String key = entry.getKey();
            List<HashMap<String, Integer>> value = entry.getValue();

            //Loop through array list to get hashmap
            for (HashMap<String, Integer> map : value) {
                for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                    String stock = entry1.getKey();
                    Integer shares = entry1.getValue();

                    if (marketQuotes.containsKey(stock)) {
                        Integer marketPrice = marketQuotes.get(stock);
                        int currentPrice = marketPrice * shares;

                        //Place in results hashmap
                        if (!result.containsKey(key)) {
                            result.put(key, currentPrice);
                        } else {
                            int prevPrice = result.get(key) + currentPrice;
                            result.put(key, prevPrice);
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
}

class Account {
    public List<Position> getPosition() {
        return Arrays.asList(
                        new Position("1234", "ace", 234),
                        new Position("1234", "google", 12),
                        new Position("342", "spade", 120),
                        new Position("342", "microsoft", 10)
                )
                .stream().toList();
    }
}

class Quote {
    String marker;
    int price;

    public Quote(String marker, int price) {
        this.marker = marker;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "marker='" + marker + '\'' +
                ", price=" + price +
                '}';
    }
}

class Quotes {
    public List<Quote> getQuotes() {
        return Arrays.asList(
                new Quote("ace", 10),
                new Quote("spade", 20),
                new Quote("google", 30),
                new Quote("microsoft", 40));
    }
}

class Position {
    String accountId;
    String marker;
    int shares;

    public Position(String accountId, String marker, int shares) {
        this.accountId = accountId;
        this.marker = marker;
        this.shares = shares;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "Position{" +
                "accountId='" + accountId + '\'' +
                ", marker='" + marker + '\'' +
                ", shares=" + shares +
                '}';
    }
}

