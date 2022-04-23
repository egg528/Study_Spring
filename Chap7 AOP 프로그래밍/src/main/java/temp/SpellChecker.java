package temp;

import java.util.Dictionary;
import java.util.Objects;
import java.util.function.Supplier;

public class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(Supplier<? extends Dictionary> dictionary){
        this.dictionary = Objects.requireNonNull(dictionary.get());
    }

    public static boolean isValid(String word){ return true; };
}