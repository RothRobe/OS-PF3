import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.nio.file.Files.*;

public class WordCount {
    public static String map(String input){
        Scanner scanner = new Scanner(input);
        String result = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim().replaceAll("[^\\p{L}\\p{N}\\s-]", "");
            String[] words = line.split("\\s+");
            for (String word : words) {
                result += word + " 1\n";
            }
        }
        scanner.close();
        return result;
    }

    public static Map<String, Integer> reduce(String input){
        Map<String, Integer> counter = new HashMap<>();
        Scanner sc = new Scanner(input);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] pp = line.trim().split("\\s+");
            if (pp.length > 0) {
                String word = pp[0];
                int count = Integer.parseInt(pp[1]);
                counter.put(word, counter.getOrDefault(word, 0) + count);
            }
        }
        sc.close();

        return counter;
    }

    public static Map<String, Integer> mergeMaps(Map<String, Integer>... maps) {
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Integer> map : maps) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                result.put(key, result.getOrDefault(key, 0) + value);
            }
        }
        return result;
    }

    public static void printMap(Map<String, Integer> map){
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    public static void main(String[] args) {
        String input = "";
        try {
            input = new String(readAllBytes(Paths.get("input.txt")));
        } catch (IOException e) {
            System.exit(-1);
        }
        Map<String, Integer> result = reduce(map(input));
        Map<String, Integer> result2 = reduce(map(input));

        printMap(mergeMaps(result, result2));
    }
}
