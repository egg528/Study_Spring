import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);

        Greeter g = ctx.getBean("greeter", Greeter.class);
        Greeter g1 = ctx.getBean("greeter1", Greeter.class);
        System.out.println(g == g1);
        ctx.close();
    }
}
