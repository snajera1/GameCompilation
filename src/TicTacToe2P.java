import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe2P extends TicTacToe implements ComOpponent{ //Extends the class TicTacToe to allow for playing against the computer

    protected TicTacToe2P(Stage primaryStage){
        super(primaryStage);
    }

    private boolean playerTurn = true; //Boolean to track if it is the player's turn or not

    @Override
    public void start(Stage primaryStage){ //Overrides the start method, calls the start method from the superclass
        super.start(primaryStage);
    }

    @Override
    protected void buttonClicked(Button button) { //Method that handles the button click
        if (button.getText().isEmpty() && playerTurn){ //Makes sure the button is unused and assigns it the player's letter
            button.setText("X");
            if (checkWin()) {
                System.out.println("Player wins!");
                showMessage("Player wins!", "You won!");
                StatTracker.p1Win();
                StatTracker.endTime();
                StatTracker.addWin("Player 1", "Tic-Tac-Toe");
                returnMain();
            } else if (checkFull()) {
                System.out.println("It's a draw!");
                showMessage();
                StatTracker.endTime();
                returnMain();
            } else {
                playerTurn = false;
                comMove();
            }
        }
    }

    private ArrayList<int[]> getPossibleMoves(){ //Method that gets all the possible moves the computer could make
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) { //Loops through the gameboard and saves all the empty space indexes
            for (int j = 0; j < 3; j++){
                if (boardButtons[i][j].getText().isEmpty()){
                    possibleMoves.add(new int[]{i, j});
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public void comMove(){ //Method that randomly generates a move for the computer
        ArrayList<int[]> possibleMoves = getPossibleMoves(); //Gets the possible moves and picks a random one to make
        Random random = new Random();
        int[] move = possibleMoves.get(random.nextInt(possibleMoves.size()));
        int row = move[0];
        int col = move[1];
        boardButtons[row][col].setText("O");
        playerTurn = true;

        if (checkWin()){
            showMessage("Computer wins!", "The computer has won!");
            System.out.println("Computer wins!");
            StatTracker.comWin();
            StatTracker.endTime();
            StatTracker.addWin("The Computer", "Tic-Tac-Toe");
            returnMain();
        }else if(checkFull()){
            System.out.println("It's a draw!");
            returnMain();
        }
            
    }
}
