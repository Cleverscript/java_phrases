import java.util.List;
import java.util.Random;

public class Phrase {
    private static final List<String> PHRASES = List.of(
        "The jorney of a thousad miles begins with a single step.",
        "The early bird catches the worm.",
        "Never give up on your dreams.",
        "A positive atitude can change everything.",
        "Your potential is limites."
    );

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        while (true) {
            String randomPhrase = PHRASES.get(RANDOM.nextInt(PHRASES.size()));
            System.out.println(randomPhrase);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}