import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class A {
    public static void main(String[] args) {
        List<String> tokens = getTokens("(+ (* 2 3) 4)");
        System.out.println("tokens: " + tokens);
        Object expr = parseOne(tokens);
        System.out.println("expr: " + expr);
        Object result = eval(expr);
        System.out.println("result of eval: " + result);
    }

    private static Object eval(Object expr) {
        if (expr instanceof Integer) { return expr; }

        List<Object> list = (List<Object>) expr;
        String op = (String) list.remove(0);
        List<Object> args = list;
        if (op.equals("+"))  { return ((Integer) eval(args.get(0))) + ((Integer) eval(args.get(1))); }
        if (op.equals("*"))  { return ((Integer) eval(args.get(0))) * ((Integer) eval(args.get(1))); }

        throw new RuntimeException("?");
    }

    private static Object parseOne(List<String> tokens) {
        String token = tokens.remove(0); // "(", ")", and others
        if (token.equals("(")) {
            List<Object> tmp = new ArrayList<>();
            while (true) {
                if (tokens.get(0).equals(")")) {
                    tokens.remove(0);
                    return tmp;
                }
                tmp.add(parseOne(tokens));
            }
        } else if (token.equals(")")) {
            throw new RuntimeException("found ')' but no '('");
        } else {
            try {
                return Integer.parseInt(token);
            } catch (Exception e) {
                return token;
            }
        }
    }

    private static List<String> getTokens(String line) {
        String[] tokens = line.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        return new LinkedList<String>(Arrays.asList(tokens)); // for remove()
    }
}
