import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Test1_Token {
    public static void main(String[] args) {
        String line = "(+ (* 2 3) 4)";
        List<String> tokens = getTokens(line);
        System.out.println("tokens: " + tokens);
    }

    private static LinkedList<String> getTokens(String line) {
        String[] tmp = line.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        return new LinkedList<String>(Arrays.asList(tmp));
    }
}
