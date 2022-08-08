import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static String[][] boardEasy = {{"X", "X", "X", "X"},
            {"X", "X", "X", "X"}};
    public static String[][] boardHard = {{"X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X"}};
    public static String randomWord;

    public static int first = 0;
    public static int second = 0;
    public static int counterEasy = 10;
    public static int counterHard = 15;
    public static long startTime;
    public static long endTime;
    public static long seconds;
    public static ArrayList<String> randomlyWords = new ArrayList<>();
    public static ArrayList<String> randomlyWordsWithoutDuplicates = new ArrayList<>();
    public static ArrayList<String> pairsOfWords = new ArrayList<>();
    public static ArrayList<String> correctPairsOfWords = new ArrayList<>();
    public static String gameStatus;

    public static void main(String[] args) throws IOException {

        String difficultyLevel;

        do {
            System.out.println("Choose a difficulty level (easy or hard):");
            difficultyLevel = scanner.nextLine().toLowerCase();

            System.out.println(System.lineSeparator().repeat(100));

            if (difficultyLevel.equals("easy") || difficultyLevel.equals("hard")) {

                startTime = System.nanoTime();

                if (difficultyLevel.equals("easy")) {
                    shuffleWords(difficultyLevel);
                } else {
                    shuffleWords(difficultyLevel);
                }
                informationAtTheEndOfTheGame(difficultyLevel);
                return;
            }
        } while (true);
    }

    private static void informationAtTheEndOfTheGame(String difficultyLevel) throws IOException {
        while (true) {
            if (difficultyLevel.equals("easy")) {
                printBoard(difficultyLevel);
                if (correctPairsOfWords.size() == 8 && counterEasy >= 1) {
                    endTime = System.nanoTime() - startTime;
                    seconds = TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS);
                    System.out.println("You solved the memory game after " + (10 - counterEasy) + " chances. It took you " + seconds + " seconds");
                    information();
                    counterEasy = 10;

                    if (continuationOfTheGame(difficultyLevel, boardEasy)) return;
                } else if (counterEasy == 0) {
                    counterEasy = 10;
                    if (announcementOfLoss(difficultyLevel, boardEasy)) return;
                }
                chooseWord();
            } else {
                printBoard(difficultyLevel);
                if (correctPairsOfWords.size() == 16 && counterHard >= 1) {
                    endTime = System.nanoTime() - startTime;
                    seconds = TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS);
                    System.out.println("You solved the memory game after " + (15 - counterHard) + " chances. It took you " + seconds + " seconds");
                    information();
                    counterHard = 15;

                    if (continuationOfTheGame(difficultyLevel, boardHard)) return;
                } else if (counterHard == 0) {
                    counterHard = 15;
                    if (announcementOfLoss(difficultyLevel, boardHard)) return;
                }
                chooseWord();
            }
        }
    }

    static boolean announcementOfLoss(String difficultyLevel, String[][] boardHard) {
        System.out.println("You Lost :(");
        System.out.println("Do you want to play again ? (Choose yes or no):");
        gameStatus = scanner.nextLine().toLowerCase();
        if (gameStatus.equals("yes")) {
            pairsOfWords.clear();
            correctPairsOfWords.clear();
            for (String[] strings : boardHard) {
                Arrays.fill(strings, "X");
            }
            printBoard(difficultyLevel);
            chooseWord();
        } else return gameStatus.equals("no");
        return false;
    }

    private static boolean continuationOfTheGame(String difficultyLevel, String[][] boardEasy) throws FileNotFoundException {
        startTime = System.nanoTime();

        if (gameStatus.equals("yes")) {
            for (String[] strings : boardEasy) {
                Arrays.fill(strings, "X");
            }
            printBoard(difficultyLevel);

            shuffleWords(difficultyLevel);

        } else return gameStatus.equals("no");
        return false;
    }

    private static void information() {
        System.out.println("Do you want to play again ? (Choose yes or no):");

        gameStatus = scanner.nextLine().toLowerCase();

        System.out.println(System.lineSeparator().repeat(100));

        pairsOfWords.clear();
        correctPairsOfWords.clear();
        randomlyWords.clear();
        randomlyWordsWithoutDuplicates.clear();

        startTime = 0;
        endTime = 0;
        seconds = 0;
        first = 0;
        second = 0;
    }

    private static void shuffleWords(String difficultyLevel) throws FileNotFoundException {

        if (difficultyLevel.equals("easy")) {
            while (randomlyWordsWithoutDuplicates.size() < 4) {
                randomWord = choose(new File("Words.txt"));
                randomlyWords.add(randomWord);
                for (String element : randomlyWords) {
                    if (!(randomlyWordsWithoutDuplicates.contains(element))) {
                        randomlyWordsWithoutDuplicates.add(element);
                    }
                }
            }

        } else {
            while (randomlyWordsWithoutDuplicates.size() < 8) {
                randomWord = choose(new File("Words.txt"));
                randomlyWords.add(randomWord);
                for (String element : randomlyWords) {
                    if (!(randomlyWordsWithoutDuplicates.contains(element))) {
                        randomlyWordsWithoutDuplicates.add(element);
                    }
                }
            }

        }
        randomlyWordsWithoutDuplicates.addAll(randomlyWordsWithoutDuplicates);
        Collections.shuffle(randomlyWordsWithoutDuplicates);
    }

    private static void chooseWord() {

        System.out.println("Choose a word:");
        String word = scanner.nextLine();

        System.out.println(System.lineSeparator().repeat(100));

        first = (int) word.charAt(0) - 64;
        second = (int) word.charAt(1) - 48;
    }
    private static void printBoard(String difficultyLevel) {

        if (difficultyLevel.equals("easy")) {

            if (first != 0 && second != 0) {
                boardEasy[first - 1][second - 1] = randomlyWordsWithoutDuplicates.get((first - 1) * 4 + second - 1);
                pairsOfWords.add(randomlyWordsWithoutDuplicates.get((first - 1) * 4 + second - 1));
            }

            System.out.println("------------------------------------");
            System.out.println("\t\tLevel: easy");
            System.out.println("\t\tGuess chances: " + counterEasy);
            System.out.println();
            System.out.print("\t\t\s\s");

            for (int i = 1; i <= 4; i++) {
                System.out.print(i + " ");
            }

            boardNumbering(boardEasy);

            if ((pairsOfWords.size() == 1 && correctPairsOfWords.contains(pairsOfWords.get(0)))) {
                pairsOfWords.clear();
            }

            if ((pairsOfWords.size() == 2 && correctPairsOfWords.contains(pairsOfWords.get(1)))) {
                pairsOfWords.remove(1);
            }

            if (pairsOfWords.size() == 2) {
                if (pairsOfWords.contains(correctPairsOfWords)) {
                    pairsOfWords.clear();
                }
                if (pairsOfWords.get(0).equals(pairsOfWords.get(1)) && !(correctPairsOfWords.containsAll(pairsOfWords))) {
                    correctPairsOfWords.addAll(pairsOfWords);
                    pairsOfWords.clear();
                } else {
                    for (int i = 0; i < boardEasy.length; i++) {
                        for (int j = 0; j < boardEasy[i].length; j++) {
                            if (!(correctPairsOfWords.contains(boardEasy[i][j]))) {
                                boardEasy[i][j] = "X";
                            }
                        }
                    }
                    --counterEasy;
                    pairsOfWords.clear();
                }
            }

        } else {

            if (first != 0 && second != 0) {
                boardHard[first - 1][second - 1] = randomlyWordsWithoutDuplicates.get((first - 1) * 8 + second - 1);
                pairsOfWords.add(randomlyWordsWithoutDuplicates.get((first - 1) * 8 + second - 1));
            }

            System.out.println("------------------------------------");
            System.out.println("\t\tLevel: hard");
            System.out.println("\t\tGuess chances: " + counterHard);
            System.out.println();
            System.out.print("\t\t\s\s");

            for (int i = 1; i <= 8; i++) {
                System.out.print(i + " ");
            }

            boardNumbering(boardHard);

            if (pairsOfWords.size() == 1 && correctPairsOfWords.contains(pairsOfWords.get(0))) {
                pairsOfWords.clear();
            }

            if (pairsOfWords.size() == 2 && correctPairsOfWords.contains(pairsOfWords.get(1))) {
                pairsOfWords.remove(1);
            }

            if (pairsOfWords.size() == 2) {
                if (pairsOfWords.contains(correctPairsOfWords)) {
                    pairsOfWords.clear();
                }
                if (pairsOfWords.get(0).equals(pairsOfWords.get(1)) && pairsOfWords.get(0).equals(pairsOfWords.get(1)) && !(correctPairsOfWords.containsAll(pairsOfWords))) {
                    correctPairsOfWords.addAll(pairsOfWords);
                    pairsOfWords.clear();
                } else {
                    for (int i = 0; i < boardHard.length; i++) {
                        for (int j = 0; j < boardHard[i].length; j++) {
                            if (!(correctPairsOfWords.contains(boardHard[i][j]))) {
                                boardHard[i][j] = "X";
                            }
                        }
                    }
                    --counterHard;
                    pairsOfWords.clear();
                }
            }
        }
    }

    private static void boardNumbering(String[][] boardEasy) {
        System.out.println();
        System.out.print("\t\t");

        for (int i = 0; i < boardEasy.length; i++) {
            if (i == 0) {
                System.out.print("A ");
            } else {
                System.out.print("B ");
            }
            for (int j = 0; j < boardEasy[i].length; j++) {
                System.out.print(boardEasy[i][j] + " ");
            }
            System.out.println();
            if (i == 0) {
                System.out.print("\t\t");
            }
        }
        System.out.println("------------------------------------");
    }

    public static String choose(File f) throws FileNotFoundException {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(f); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }
}
