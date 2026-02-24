import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, String> opcodes = new HashMap<>();
        opcodes.put("STBAd", "F1");
        opcodes.put("LDBAi", "D0");
        opcodes.put("LDBAd", "D1");
        opcodes.put("STWAd", "E1");
        opcodes.put("LDWAi", "C0");
        opcodes.put("LDWAd", "C1");
        opcodes.put("ADDAi", "60");
        opcodes.put("ANDAd", "81");
        opcodes.put("ASLA", "0A");
        opcodes.put("ASRA", "0C");
        opcodes.put("STOP", "00");
        opcodes.put("CPBAi", "B0");
        opcodes.put("CPBAd", "B1");
        opcodes.put("BRNE", "1A");
        opcodes.put(".END", "zz");
        String file = args[0];

        ArrayList<String> pepcodeRaw = readFile(file);
        StringBuilder result = new StringBuilder();

        for (String line : pepcodeRaw) {
            String[] parts = splitLine(line);

            if (parts[0].isEmpty()) {
                continue;
            }

            if (parts.length < 2) {
                parts[0] = parts[0].trim();
                result.append(opcodes.get(parts[0]));
                result.append(" ");
            } else {
                String opcode = concatenateOpcode(line);
                String hexOpcode = opcodes.get(opcode);
                String hex = getHex(line);
                result.append(hexOpcode);
                result.append(" ");
                result.append(hex);
                result.append(" ");
            }
        }
        System.out.println(result);
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

    public static String[] splitLine(String line){
        line = line.trim();
        return line.split(" ");
    }

    public static String concatenateOpcode(String line) {
        String[] parts = splitLine(line);
        return parts[0] + parts[2];
    }

    public static String getHex(String line) {
        String[] parts = splitLine(line);

        StringBuilder finalHex = new StringBuilder(parts[1]);
        finalHex.delete(0, 2);
        finalHex.deleteCharAt(finalHex.length() - 1);

        if (finalHex.length() < 4) {
            for (int i = 0; i < (5 - finalHex.length()); i++) {
                finalHex.insert(0, "0");
            }
        }
        finalHex.insert(2, " ");
        return finalHex.toString();
    }

}
