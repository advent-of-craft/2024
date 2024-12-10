package delivery;

import java.util.ArrayList;
import java.util.List;

public class Building {
    public static int whichFloor(String instructions) {
        List<Pair<Character, Integer>> val = new ArrayList<>();

        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);

            if (instructions.contains("🧝")) {
                int j;
                if (c == ')') j = 3;
                else j = -2;

                val.add(new Pair<>(c, j));
            } else if (!instructions.contains("🧝")) {
                val.add(new Pair<>(c, c == '(' ? 1 : -1));
            } else {
                val.add(new Pair<>(c, c == '(' ? 42 : -2));
            }
        }

        int result = 0;
        for (Pair<Character, Integer> kp : val) {
            result += kp.value();
        }

        return result;
    }

    public record Pair<K, V>(K key, V value) {
    }
}
