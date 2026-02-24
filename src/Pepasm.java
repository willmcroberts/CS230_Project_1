import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Pepasm {
    public static void main(String[] args) throws FileNotFoundException {
        String file = args[0];
        System.out.println(readFile(file));
    }

    public static ArrayList<String> readFile(String file) throws FileNotFoundException {

        File f = new File(file);
        ArrayList<String> pepcodeRaw = new ArrayList<>();
        Scanner scanner;

        scanner = new Scanner(f);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            pepcodeRaw.add(line);
        }
        return pepcodeRaw;
    }

}
