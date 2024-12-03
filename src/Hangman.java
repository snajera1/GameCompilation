import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class Hangman extends Application implements Game{

    //Establishes most of the nodes and variables outside of methods so that they can be modified easily within methods 
    protected static final String[] words = {"final", "abstract", "extends", "implements", "class", "interface", "static", "public"};
    protected static int attemptsLeft;
    protected Text attemptsDisplay;
    protected TextArea hangman;
    protected Stage primaryStage;
    protected TextField guess;
    protected Character letter;
    protected String word;
    protected int hangmanStage;
    protected static final String[] hangmanStages = {
        "+---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="};
    //Source for hangmanStages ascii art: https://github.com/juliedlevine/Hangman-ascii
    protected ArrayList<Character> guessList;
    protected ArrayList<Character> guessCorrect;
    protected Text wordDisplay;
    

    protected Hangman(Stage primaryStage){ //Constructor to allow the primary stage to be passed around
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage){ //Runs runs the methods that gets the word and generates the play area
        word = getWord();
        attemptsLeft = 6;
        hangmanStage = 1;
        guessList = new ArrayList<>();
        guessCorrect = new ArrayList<>();
        StatTracker.startTime();
        drawBoard(word);
    }

    protected void drawBoard(String word){ //Draws the playing space
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        wordDisplay = new Text(); //Creates the text displays and shows them through their associated methods
        attemptsDisplay = new Text();
        showAttempts();
        showWord(wordDisplay);
        guess = new TextField(); //Sets up the text field for user input
        guess.setPromptText("Enter a guess!");
        guess.setMaxWidth(100);
        Button btnReturn = new Button("Return to main menu"); //Creates the button to return to the main menu
        btnReturn.setOnAction(e -> returnMain());
        btnReturn.setPrefSize(200, 50);
        btnReturn.setFont(new Font(15));
        hangman = new TextArea(); //Sets the text area that shows the hangman stages ascii art
        hangman.setText(hangmanStages[0]);
        hangman.setEditable(false);
        hangman.setMaxWidth(100);
        hangman.setMaxHeight(150);
        Button btnGuess = new Button("Guess!"); //Creates the button to return to submit user guess
        btnGuess.setOnAction(e -> guessClick());
        btnGuess.setPrefSize(150, 50);
        btnGuess.setFont(new Font(25));
        root.getChildren().addAll(hangman, wordDisplay, guess, attemptsDisplay, btnGuess, btnReturn);

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("styleButtons.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    protected String getWord(){ //Method to get a random word from the preset list for the game
        Random random = new Random();
        int wordIndex = random.nextInt(words.length);
        String word = words[wordIndex];
        return word;
    }

    protected void showWord(Text text){ //Method to show a word/the amount of spaces that corresponds to the unguessed letters
        StringBuilder w = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            Character letter = word.charAt(i);
            if(guessCorrect.contains(letter)){
                w.append(letter).append(" ");
            }else{
                w.append("_ ");
            }
        }
        text.setText(w.toString());
    }

    protected void showHangman(){ //Advances the stage of the hangman ascii art
        hangman.setText(hangmanStages[hangmanStage]);
        hangmanStage++;
    }

    protected void showAttempts(){ //Shows how many attempts remain
        attemptsDisplay.setText("Attempts left: " + attemptsLeft);
    }
    
    protected void guessClick(){ //Method to check if the user entered a valid guess
        String guessed = guess.getText().toLowerCase();
        if(guessed.length() != 1 || !Character.isLetter(guessed.charAt(0))){ //Validates that the input is just a single letter
            showMessage("Invalid input", "Please enter a single letter");
            guess.clear();
        }else if(guessList.contains(guessed.charAt(0))){
            showMessage("Invalid input", "Please enter a letter you haven't guessed before");
            guess.clear();
        }else{
            letter = guessed.charAt(0); //Gets the user input and checks if it is a correct guess
            guessList.add(letter);
            boolean correct = checkLetter(letter);
            if(!correct){
                attemptsLeft -= 1;
                showAttempts();
                showHangman();
            }
            showWord(wordDisplay);
            if(checkWin()){
                showMessage("You Win!", "You figured out the word!");
                StatTracker.p1Win();
                StatTracker.endTime();
                StatTracker.addWin("Player 1", "Hangman");
                returnMain();
            }else if(attemptsLeft <= 0){
                showMessage("You Lose!", "You ran out of attempts!");
                StatTracker.endTime();
                returnMain();
            }
            showWord(wordDisplay);
            guess.clear();
        }  
    }

    protected boolean checkLetter(Character letter){ //Checks if a letter is in the word
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) == letter){
                guessCorrect.add(letter);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkWin(){ //Checks if the user has won
        for(int i = 0; i < word.length(); i++){
            Character letter = word.charAt(i);
            if(!guessList.contains(letter)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void showMessage(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        Image imgH = new Image(getClass().getResourceAsStream("/resources/hangman.png"));
        ImageView imgvH = new ImageView(imgH);
        imgvH.setFitHeight(100);
        imgvH.setPreserveRatio(true);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(imgvH);
        alert.showAndWait();
    }

    @Override
    public void returnMain(){
        MainMenu m = new MainMenu();
        m.start(primaryStage);
    }
}
