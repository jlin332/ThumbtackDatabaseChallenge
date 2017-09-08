import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class TestRunner {
    private static DatabaseCommands command;
    private static Transactions transaction;

    public static void main(String args[] ) throws Exception {
        command = new DatabaseCommands();
        transaction = new Transactions();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) { // Traverse every line in STDIN
            String line = scan.nextLine();
            System.out.println(line);
            String[] separated = line.split(" "); // Split line using space delimiter
            String first = separated[0].trim();
            if (separated.length == 1) {
                if (first.equals("BEGIN")) {
                    command = transaction.begin(command);
                } else if (first.equals("COMMIT")) {
                    command = transaction.commit(command);
                } else if (first.equals("ROLLBACK")) {
                    command = transaction.rollback(command);
                }
            } else if (first.equals("SET")) {
                if (separated[1] == null || separated[2] == null) {
                    System.out.println("INVALID OPERATION");
                } else {
                    String name = separated[1];
                    String value = separated[2];
                    command.set(name, Integer.parseInt(value));
                }
            } else if (first.equals("GET")) {
                if (separated[1] == null) {
                    System.out.println("INVALID OPERATION");
                } else {
                    String name = separated[1];
                    command.get(name);
                }
            } else if (first.equals("UNSET")) {
                if (separated[1] == null) {
                    System.out.println("INVALID OPERATION");
                } else {
                    String name = separated[1];
                    command.unset(name);
                }
            } else if (first.equals("NUMEQUALTO")) {
                if (separated[1] == null) {
                    System.out.println("INVALID OPERATION");
                } else {
                    String value = separated[1];
                    command.numEqualTo(Integer.parseInt(value));
                }
            }
        }
    }
}

class DatabaseCommands {
    private HashMap<String, Integer> map; //keeps track of key value pairs for database
    private HashMap<Integer, Integer> valuesMap; //keeps track of number of values

    public DatabaseCommands() {
        map = new HashMap<String, Integer>();
        valuesMap = new HashMap<Integer, Integer>();
    }

    public DatabaseCommands(DatabaseCommands nest) { //To create nested database commands
        this.map = new HashMap<String, Integer>(nest.getMap());
        this.valuesMap = new HashMap<Integer, Integer>(nest.getValuesMap());
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public HashMap<Integer, Integer> getValuesMap() {
        return valuesMap;
    }
    public void get(String name) {
        if (map.containsKey(name)) {
            int value = map.get(name);
            System.out.println("> "+ value);
        } else {
            System.out.println("> NULL");
        }
    }

    public void set(String name, int value) {
        //account for overriding
        if (map.containsKey(name)) {
            int oldvalue = map.get(name);
            valuesMap.put(oldvalue, valuesMap.get(oldvalue) - 1);
        }
        map.put(name, value);
        if (valuesMap.containsKey(value)) {
            valuesMap.put(value, valuesMap.get(value) + 1);
        } else {
            valuesMap.put(value, 1);
        }
    }

    public void unset(String name) {
        int value = map.get(name);
        map.remove(name);
        valuesMap.put(value, valuesMap.get(value) - 1);
    }

    public void numEqualTo(int value){
        int size = 0;
        if (valuesMap.get(value) != null) {
            size = valuesMap.get(value);
        }
        System.out.println("> " + size);
    }

}

class Transactions {
    LinkedList<DatabaseCommands> list = new LinkedList<>();
    int beginCount = 0;

    public DatabaseCommands begin(DatabaseCommands command) {
        list.add(command);
        beginCount++;
        return new DatabaseCommands(command);
    }

    public DatabaseCommands rollback(DatabaseCommands command) {
        if (list.size() == 0 || beginCount == 0) {
            System.out.println("> NO TRANSACTION");
            return command;
        } else {
            DatabaseCommands last = list.pollLast();
            return last;
        }
    }

    public DatabaseCommands commit(DatabaseCommands command) {
        if (list.size() == 0 || beginCount == 0) {
            System.out.println("> NO TRANSACTION");
            return command;
        } else {
            list.clear();
            list.add(command);
            beginCount = 0;
            return command;
        }
    }
}