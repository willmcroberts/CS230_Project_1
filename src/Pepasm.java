import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Pepasm {
    public static void main(String[] args) throws FileNotFoundException {
        String file = args[0];
        StringBuilder result = translateCode(file);

        System.out.println(result);
    }

    public static StringBuilder translateCode(String file) throws FileNotFoundException {
        Map<String, String> opcodes = getStringMap();


        ArrayList<String> pepcodeRaw = readFile(file);
        StringBuilder result = new StringBuilder();

        for (String line : pepcodeRaw) {
            String[] parts = splitLine(line);

            if (parts[0].isEmpty()) {
                break;
            } else if (parts.length < 2) {
                reviseLength(parts, result, opcodes);
            } else if (parts[0].contains(":")){
                containsColon(line, result, opcodes);
            } else if (parts[0].contains("BRNE")){
                breakNotEqualHardCode(line, result, opcodes);
            } else {
                runBasic(line, result, opcodes);
            }
        }
        return result;
    }

    public static void runBasic(String line, StringBuilder result, Map<String, String> opcodes) {
        String opcode = concatenateOpcode(line);
        String hexOpcode = opcodes.get(opcode);
        String hex = getHex(line);
        result.append(hexOpcode);
        result.append(" ");
        result.append(hex);
        result.append(" ");
    }

    public static void breakNotEqualHardCode(String line, StringBuilder result, Map<String, String> opcodes) {
        String opcode = concatenateOpcode(line);
        String hexOpcode = opcodes.get(opcode);
        result.append(hexOpcode);
    }

    public static void reviseLength(String[] parts, StringBuilder result, Map<String, String> opcodes) {
        parts[0] = parts[0].trim();
        result.append(opcodes.get(parts[0]));
        result.append(" ");

    }

    public static void containsColon(String line, StringBuilder result, Map<String, String> opcodes) {
        line = line.substring(line.indexOf(":") + 1).trim();
        String opcode = concatenateOpcode(line);
        String hexOpcode = opcodes.get(opcode);
        String hex = getHex(line);
        result.append(hexOpcode);
        result.append(" ");
        result.append(hex);
        result.append(" ");

    }

    public static Map<String, String> getStringMap() {
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
        opcodes.put("BRNEi", "1A 00 03 ");
        opcodes.put(".END", "");
        return opcodes;
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
        return line.split("\\s+");
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
