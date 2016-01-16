import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Test2_Parse {
    public static void main(String[] args) {
        String line = "(+ (* 2 3) 4)";
        List<String> tokens = getTokens(line);
        System.out.println("tokens: " + tokens);
        Object exp = parseOne(tokens);
        System.out.println("exp: " + exp);
    }

    private static Object parseOne(List<String> tokens) {
        while (true) {
            String token = tokens.remove(0);
            if (token.equals("(")) {
                List<Object> tmp = new ArrayList<Object>();
                while (true) {
                    if (tokens.get(0).equals(")")) {
                        tokens.remove(0);
                        return tmp;
                    } else {
                        tmp.add(parseOne(tokens));
                    }
                }
            } else if (token.equals(")")) {
                throw new RuntimeException("bad )");
            } else {
                try {
                    return Integer.parseInt(token);
                } catch (Exception e) {
                    return token;
                }
            }
        }
    }


    private static LinkedList<String> getTokens(String line) {
        String[] tmp = line.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        return new LinkedList<String>(Arrays.asList(tmp));
    }
}
