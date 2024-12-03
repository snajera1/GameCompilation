import java.util.Random;

import javafx.stage.Stage;

public class HangmanHard extends Hangman{
    
    protected HangmanHard(Stage primaryStage){
        super(primaryStage);
    }

    @Override
    public void start(Stage primaryStage){ //Overrides the start method, calls the start method from the superclass
        super.start(primaryStage);
    }

    @Override
    protected String getWord(){ //Method to get a random word from the preset list for the game
        Random random = new Random();
        int wordIndex = random.nextInt(words.length);
        String word = words[wordIndex];
        String reversedWord = reverseWord(word);
        return reversedWord;
    }

    protected String reverseWord(String word){ //Method to recursively reverse a string
        if(word.length() <= 1){
            return word;
        }
        int count = word.length();
        Character letter = word.charAt(count - 1);
        return letter + reverseWord(word.substring(0, count - 1));
    }

}
