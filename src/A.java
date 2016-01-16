import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class A {
    private static class Env extends HashMap<String, Object> {
        private Env outer;
        public Env(Env outer) {
            this.outer = outer;
        }
        @Override
        public Object get(Object key) {
            Object tmp = super.get(key);
            return tmp != null ? tmp : outer.get(key);
        }
    }
    private interface Func {
        public Object run(List<Object> args);
    }
    public static void main(String[] args0) {
        Env env = new Env(null);
        env.put("pi", 3);
        env.put("+", (Func) (args -> ((Integer) args.get(0)) + ((Integer) args.get(1))));
        env.put("*", (Func) (args -> ((Integer) args.get(0)) * ((Integer) args.get(1))));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            System.out.println(evalLine(line, env));
        }
    }

    private static Object evalLine(String line, Env env) {
        List<String> tokens = getTokens(line);
        System.out.println("tokens: " + tokens);
        Object expr = parseOne(tokens);
        System.out.println("expr: " + expr);

        Object result = eval(expr, env);
        return result;
    }

    private static Object eval(Object expr, Env env) {
        if (expr instanceof Integer) { return expr; }
        if (expr instanceof String) { return env.get(expr); }

        List<Object> list = (List<Object>) expr;
        if (list.get(0).equals("define")) { // special form
            env.put((String) list.get(1), eval(list.get(2), env));
            return null;
        }
        if (list.get(0).equals("lambda")) { // special form
            // (lambda (w h) (* w h))  params / body
            final List<Object> params = (List<Object>) list.get(1);
            final Object body = list.get(2);
            final Env origEnv = env;
            return new Func() {
                @Override
                public Object run(List<Object> args) {
                    // System.out.println("====");
                    // System.out.println("run: " + args);
                    // System.out.println("params: " + params);
                    // System.out.println("body: " + body);
                    // System.out.println("origEnv: " + origEnv);
                    Env newEnv = new Env(origEnv);
                    for (int i = 0; i < args.size(); i++) {
                        newEnv.put((String) params.get(i), args.get(i));
                    }
                    return eval(body, newEnv);
                }
            };
        }

        List<Object> evaled = list.stream().map(x -> eval(x, env)).collect(Collectors.toList());
        Func func = (Func) evaled.remove(0);
        return func.run(evaled);
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
