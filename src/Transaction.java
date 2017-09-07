import java.util.Map;

public class Transaction {
    Map<String, Integer> originalMap;
    Map<String, Integer> newMap;
    Database data;
    Transaction withinTransaction;

    public Transaction(Database data) {
        this.data = data;
    }

    public void begin(Map<String, Integer> map) {
        originalMap = map;
    }

    public Map<String, Integer> rollback() {
        return originalMap;
    }

    public void commit() {
        originalMap = newMap;
    }
}
