import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Test9_EnvEnv {
    private static class Env extends HashMap<String, Object> {
        private Env outer;
        public Env(Env outer) { this.outer = outer; }
        @Override
        public Object get(Object key) {
            Object tmp = super.get(key);
            return tmp != null ? tmp : outer.get(key);
        }
    }
    private interface Func {
        Object run(List<Object> args);
    }
    public static void main(String[] args0) {
        Env env = new Env(null);
        env.put("pi", 3);
        env.put("+", (Func) (args -> ((Integer) args.get(0)) + ((Integer) args.get(1))));
        env.put("*", (Func) (args -> ((Integer) args.get(0)) * ((Integer) args.get(1))));
        Env env2 = new Env(env);
        env2.put("+", "+++++");
        System.out.println(env2.get("+"));
        System.out.println(env2.get("*"));
        System.exit(1);

        //repl(env);
        String line = "(+ (* 2 pi) 4)";
        List<String> tokens = getTokens(line);
        System.out.println("tokens: " + tokens);
        Object exp = parseOne(tokens);
        System.out.println("exp: " + exp);
        Object result = eval(exp, env);
        System.out.println("result: " + result);
    }

    private static void repl(Env env) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            System.out.println("==> " + eval(parseOne(getTokens(line)), env));
        }
    }

    private static Object eval(Object exp, Env env) {
        if (exp instanceof Integer) { return exp; }
        if (exp instanceof String) { return env.get(exp); }

        // List
        List<Object> tmp = (List<Object>) exp;
        String op = (String) tmp.get(0);

        if (op.equals("define")) {
            env.put((String) tmp.get(1), eval(tmp.get(2), env));
            return null;
        }

        List<Object> evaled = tmp.stream().map(x -> eval(x, env)).collect(Collectors.toList());
        Func func = (Func) evaled.get(0);
        List<Object> args = evaled.subList(1, tmp.size());
        return func.run(args);
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
