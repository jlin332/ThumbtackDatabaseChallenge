import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class DatabaseTester {

    private static HashMap<String, Integer> map;
    public static void main(String[] args) {
        try {
            File file = new File("src/database.txt");
            Scanner scan = new Scanner(file);
            map = new HashMap<>();
            LinkedList<Transaction> linkedList = new LinkedList<>();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] separated = line.split(" ");
                String first = separated[0].trim();
                if (separated.length == 1) {
                    if (first.equals("BEGIN")) {
                        //Start of a Transaction
                        linkedList.add(new Transaction(new Database(map)));
                    } else if (first.equals("END")) {
                        //End of a Transaction
                        linkedList.pollLast();
                    } else if (first.equals("COMMIT")) {
                        //Commit Transaction
                        if (linkedList.size() == 0) {
                            System.out.println("NO TRANSACTION");
                        } else {
                            Transaction trans = linkedList.peekLast();
                            trans.commit();
                            System.out.println(" ");
                        }
                    } else if (first.equals("ROLLBACK")) {
                        //Rollback Transaction
                        if (linkedList.size() == 0) {
                            System.out.println("NO TRANSACTION");
                        } else {
                            Transaction trans = linkedList.pollLast();
                            Database test = trans.rollback();
                            System.out.println(test.get("a"));
                        }
                    }
                } else if (first.equals("SET")) {
                    String name = separated[1];
                    String value = separated[2];
                    Transaction trans = linkedList.peekLast();
                    trans.data.set(name, Integer.valueOf(value));
                    System.out.println(" ");
                } else if (first.equals("GET")) {
                    String name = separated[1];
                    if (linkedList.peekLast() == null) {
                        System.out.println("NULL");
                    } else {
                        Transaction trans = linkedList.peekLast();
                        int result = trans.data.get(name);
                        System.out.println(result);
                    }
                } else if (first.equals("UNSET")) {
                    String name = separated[1];
                    Transaction trans = linkedList.peekLast();
                    trans.data.unset(name);
                    System.out.println(" ");
                } else if (first.equals("NUMEQUALTO")) {
                    String value = separated[1];
                    int val = Integer.getInteger(value);
                    Transaction trans = linkedList.peekLast();
                    int result = trans.data.numEqualTo(val);
                    System.out.println(result);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
    }
}
