package temp;

import java.util.Dictionary;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "wow";
            }
        };
    }
}