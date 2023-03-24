package rockpaperscissors;

import java.io.*;
import java.util.*;

public class Main {

    private static final List<String> options = List.of("rock", "fire", "scissors", "snake", "human",
            "tree", "wolf", "sponge", "paper", "air", "water", "dragon", "devil", "lightning", "gun");
    private static final Map<String, List<String>> map = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Integer> scores = new HashMap<>();
    private static final String filename = "rating.txt";
    private static final List<String> choices = new ArrayList<>();

    public static void main(String[] args) {
        fillMap();
        loadScores();
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.println("Hello, " + userName);
        getChoices();
        start(userName);
    }

    private static void start(String userName) {
        System.out.println("Okay, let's start");
        if (!scores.containsKey(userName)) scores.put(userName, 0);
        String userChoice;
        while (!(userChoice = scanner.nextLine()).equals("!exit")) {
            if (choices.contains(userChoice) || userChoice.equals("!rating")) {
                Collections.shuffle(choices);
                String comChoice = choices.get(0);
                grade(userChoice, comChoice, userName);
            } else System.out.println("Invalid input");
        }
        System.out.println("Bye!");
    }

    private static void getChoices() {
        String choice = scanner.nextLine();
        if (choice.length() < 3) choice="rock,paper,scissors";
        Collections.addAll(choices, choice.split(","));
    }

    public static void fillMap() {
        for (int i = 0; i < options.size(); i++) {
            int pos = i;
            int count = 7;
            List<String> l = new ArrayList<>();
            while ((--count) >= 0) {
                pos = pos == options.size() - 1 ? 0 : pos + 1;
                l.add(options.get(pos));
            }
            map.put(options.get(i), l);
        }
    }

    public static void grade(String userChoice, String comChoice, String userName) {
        if (userChoice.equals("!rating")) System.out.printf("Your rating: %d\n", scores.get(userName));
        else if (userChoice.equals(comChoice)) {
            System.out.printf("Draw: There is a draw (%s);\n", userChoice);
            scores.put(userName, scores.get(userName) + 50);
        }
        else if (map.get(comChoice).contains(userChoice))
            System.out.printf("Loss: Sorry, but the computer chose %s\n", comChoice);
        else {
            System.out.printf("Win: Well done. The computer chose %s and failed\n", comChoice);
            scores.put(userName, scores.get(userName) + 100);
        }

    }

    public static void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                scores.put(arr[0], Integer.parseInt(arr[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
