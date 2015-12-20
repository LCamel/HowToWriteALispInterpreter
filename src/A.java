import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class A {
    public static void main(String[] args) {
        List<String> tokens = getTokens("(+ (* 2 3) 4)");
        System.out.println("tokens: " + tokens);
        tokens.remove(0);
    }

    private static List<String> getTokens(String line) {
        String[] tokens = line.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        return new LinkedList<String>(Arrays.asList(tokens));
    }
}
