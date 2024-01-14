import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<String> wordList;
    private static String[] hangman = {
            "  -----\n  |   |\n      |\n      |\n      |\n      |\n=========",
            "  -----\n  |   |\n  😂  |\n      |\n      |\n      |\n=========",
            "  -----\n  |   |\n  🙂  |\n  |   |\n      |\n      |\n=========",
            "  -----\n  |   |\n  😑  |\n /|   |\n      |\n      |\n=========",
            "  -----\n  |   |\n  🙁  |\n /|\\  |\n      |\n      |\n=========",
            "  -----\n  |   |\n  😱  |\n /|\\  |\n /    |\n      |\n=========",
            "  -----\n  |   |\n  💀  |\n /|\\  |\n / \\  |\n      |\n=========",
            "------------------------------------------------------------------"
    };

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("Welcome to Hangman! Please input your letter guess! (only letters, no numbers or symbols please)");
            readFile("words.txt");
            String unknown = getRandomWord();
            System.out.println("Word to guess: " + "_".repeat(unknown.length()));
            StringBuilder guessedWord = new StringBuilder();
            List<Character> guessedLetters = new ArrayList<>();
            int guesses = 0;

            for (int i = 0; i < unknown.length(); i++) {
                guessedWord.append("-");
            }
            // set the guessed word to blanks//
            while (true) {
                System.out.println("Guess a letter: ");
                String input = scanner.nextLine();
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Invalid input, please enter a single letter.");
                    continue;
                }
                char guess = Character.toUpperCase(input.charAt(0));

                if (guessedLetters.contains(guess)) {
                    System.out.println("You already guessed that letter. Try again.");
                    continue;
                }

                guessedLetters.add(guess);

                if (unknown.indexOf(guess) != -1) {
                    for (int i = 0; i < unknown.length(); i++) {
                        if (unknown.charAt(i) == guess) {
                            guessedWord.setCharAt(i, guess);
                        }
                    }
                } else {
                    guesses++;
                }
                //print out the guessed word//
                System.out.println("Guessed word: " + guessedWord.toString());
                System.out.println("Incorrect guesses: " + guesses);

                if (guessedWord.toString().equals(unknown)) {
                    System.out.println("Congratulations! You guessed the word.");
                    break;
                }
                //check if lives are all used up//
                if (guesses >= 7) {
                    System.out.println("Game over! You ran out of guesses.");
                    System.out.println("The word was: " + unknown);
                    System.out.println(hangman[guesses]);
                    break;
                }
                System.out.println(hangman[guesses]);
                //print out the hangman after each turn//
            }
            System.out.println("Do you want to play again? (Y/N)");
            String playAgainInput = scanner.nextLine().toUpperCase();
            playAgain = playAgainInput.equals("Y");
            //boolean setup playagain//
        }
    }
    //read the file into a wordlist//
    private static void readFile(String filename) {
        wordList = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine();
                wordList.add(word);
            }
            fileScanner.close();
        } catch (FileNotFoundException x) {
            System.out.println("File not found: " + filename);
        }
    }
    private static String getRandomWord() {
        Random random = new Random();
        int randomWord = random.nextInt(wordList.size());
        return wordList.get(randomWord);
    }
    //get a random word from the wordlist//
}