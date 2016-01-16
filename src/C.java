import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class C {
    private static class Env extends HashMap<String, Object> {
    }
    public static void main(String[] args) {
        //String line = "(+ (* 2 pi) 4)";
        Env env = new Env();
        env.put("pi", 3);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            List<String> tokens = getTokens(line);
            Object exp = parseOne(tokens);
            System.out.println("exp: " + exp);
            Object result = eval(exp, env);
            System.out.println("result: " + result);
        }

    }

    private static Object eval(Object exp, Env env) {
        if (exp instanceof Integer) return exp;
        if (exp instanceof String) return env.get(exp);

        // list
        List<Object> tmp = (List<Object>) exp;
        if (tmp.get(0).equals("define")) { // (define x (+ 1 2))
            env.put((String) tmp.get(1), eval(tmp.get(2), env));
            return null;
        }
        String op = (String) tmp.get(0);
        List<Object> args = tmp.subList(1, tmp.size());
        if (op.equals("+")) { return ((Integer) eval(args.get(0), env)) + ((Integer) eval(args.get(1), env)); }
        if (op.equals("*")) { return ((Integer) eval(args.get(0), env)) * ((Integer) eval(args.get(1), env)); }
        throw new RuntimeException("what?");
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

    private static List<String> getTokens(String line) {
        String[] tmp = line.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        return new ArrayList<String>(Arrays.asList(tmp));
    }
}
