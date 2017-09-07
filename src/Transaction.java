import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private Map<String, Integer> originalMap = null;
    protected Database data;

    public Transaction(Database data) {
        this.data = data;
        mapclone();
    }

    private void mapclone() {
        originalMap = (HashMap<String, Integer>) ((HashMap<String, Integer>)data.map).clone();
    }

    public Database rollback() {
        data.setMap(originalMap);
        return data;
    }

    public Database commit() {
        return data;
    }
}
