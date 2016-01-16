
public class B {
    private interface Foo {
        public Object f(Object arg0);
    }
    private static Foo f1() {
        String s = "sss";
        Foo tmp = arg0 -> arg0 + s;
        return tmp;
    }
    public static void main(String[] args) {
        Foo foo = f1();
        System.out.println(foo.f("xxx"));
    }
}
