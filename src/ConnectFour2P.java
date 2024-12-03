import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectFour2P extends ConnectFour implements ComOpponent{

    protected ConnectFour2P(Stage primaryStage){
        super(primaryStage);
    }

    private boolean playerTurn = true; //Boolean to track if it's the player's turn or computer's turn

    @Override
    public void start(Stage primaryStage){ //Overrides the start method, calls the start method from the superclass
        super.start(primaryStage);
    }

    @Override
    protected void dropPiece(int col){ //Method to drop the piece 
        if (playerTurn){
            for (int row = rows - 1; row >= 0; row--){ //Checks the row the user chose and drops one of their pieces in the furthest spot down possible
                if(boardStatus[row][col] == 0){
                    boardStatus[row][col] = 1;
                    Circle piece = new Circle(40);
                    piece.setFill(Color.RED);
                    board.add(piece, col, row);

                    if(checkWin(row, col)){ //Checks if the player won or there was a draw
                        showMessage("You win!", "Red has won!");
                        StatTracker.p1Win();
                        StatTracker.endTime();
                        StatTracker.addWin("Player 1", "Connect Four");
                        returnMain();
                    }else if(checkFull()){
                        showMessage();
                        returnMain();
                    }else{
                        playerTurn = false;
                        comMove();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void comMove(){ //Generates a random move for the game to make 
        Random random = new Random();
        int selectCol = -1;

        while(selectCol == -1){ //Gets a random column and sees if it can place a piece there
            int randomPiece = random.nextInt(cols);
            for(int i = rows - 1; i >= 0; i--){
                if(boardStatus[i][randomPiece] == 0){
                    selectCol = randomPiece;
                    break;
                }
            }
        }

        for(int row = rows - 1; row >= 0; row--){ //Checks the randomly generated column and checks for the furthest down empty spot to place the piece
            if(boardStatus[row][selectCol] == 0){
                boardStatus[row][selectCol] = 2;
                Circle piece = new Circle(40);
                piece.setFill(Color.YELLOW);
                board.add(piece, selectCol, row);

                if(checkWin(row, selectCol)){ //Checks if the computer won or the board is full
                    showMessage("You Lose!", "Computer wins!");
                    StatTracker.comWin();
                    StatTracker.endTime();
                    StatTracker.addWin("The Computer", "Connect Four");
                    returnMain();
                }else if(checkFull()){
                    showMessage();
                    returnMain();
                }else{
                    playerTurn = true; //Switches turns
                }
                break;
            }
        }

    }
    
}
