import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseTester {
    public static void main(String[] args) {
        Scanner scan = new Scanner("database.txt");
        Map<String, Integer> map = new HashMap<>();
        Database base = new Database(map);
        while(scan.nextLine() != null) {
            String line = scan.next();
            String[] separated = line.split(" ");
            String first = separated[0];
            if (separated.length == 1) {
                if (first == "BEGIN") {
                    //Start of a Transaction
                } else if (first == "END") {
                    //End of a Transaction
                } else if (first == "COMMIT") {
                    //Commit Transaction
                } else if (first == "ROLLBACK") {
                    //Rollback Transaction
                }
            } else if (first == "SET") {
                String name = separated[1];
                String value = separated[2];
                base.set(name, Integer.valueOf(value));
                System.out.println(" ");
            } else if (first == "GET") {
                String name = separated[1];
                int result = base.get(name);
                System.out.println(result);
            } else if (first == "UNSET") {
                String name = separated[1];
                System.out.println(" ");
            } else if (first == "NUMEQUALTO") {
                String value = separated[1];
                int val = Integer.getInteger(value);
                int result = base.numEqualTo(val);
                System.out.println(result);
            }
        }
    }
}
