import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Database {
    Map<String, Integer> map = new HashMap<>();

    public Database(Map<String, Integer> map) {
        this.map = map;
    }

    public void set(String name, int value) {
        map.put(name, value);
    }

    public int get(String name) {
        return map.get(name);
    }

    public void unset(String name) {
        map.remove(name);
    }

    public Map<String, Integer> setMap(Map<String, Integer> map) {
        HashMap<String, Integer> copy = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    public int numEqualTo(int value) {
        Collection<Integer> list = map.values();
        int size = 0;
        for (int a : list) {
            if (a == value) {
                size++;
            }
        }
        return size;
    }

    public Map<String, Integer> end() {
        return map;
    }
}
