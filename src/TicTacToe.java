import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToe extends Application implements Game{

    protected Button[][] boardButtons = new Button[3][3];
    protected boolean player = true;
    protected Stage primaryStage;

    public TicTacToe(Stage primaryStage){ //Constructor to allow the primary stage to be passed around
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage){ //Start method establishes the scene and calls the method to draw the game board

        GridPane gameBoard = drawBoard();
        Scene scene = new Scene(gameBoard, 650, 650);
        scene.getStylesheets().add(getClass().getResource("styleButtons.css").toExternalForm());
        StatTracker.startTime();
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    protected GridPane drawBoard(){ //Method to draw the game board

        GridPane gameBoard = new GridPane(); //Establishes a gridpane to hold all the buttons
        gameBoard.setVgap(20);
        gameBoard.setHgap(20);
        gameBoard.setAlignment(Pos.TOP_CENTER);
        
        for(int i = 0; i < 3; i++){ //Nested for loops place buttons in a 3x3 grid on the gridpane
            for (int j = 0; j < 3; j++){
                Button gameSpace = new Button(); //Creates the button and associates the method handling its click event
                gameSpace.setPrefSize(200, 200);
                gameSpace.setFont(new Font(50));
                gameSpace.setOnAction(e -> buttonClicked(gameSpace));
                boardButtons[i][j] = gameSpace;
                gameBoard.add(gameSpace, j, i);
                
            }
        }

        Button btnReturn = new Button("Return to main menu"); //Creates the button to return to the main menu
        btnReturn.setOnAction(e -> returnMain());
        gameBoard.add(btnReturn, 1, 3);
        btnReturn.setPrefWidth(200);

        return gameBoard;

    }

    protected void buttonClicked(Button button){ //Handles the button input
        if(button.getText().isEmpty()){ //Makes sure the button hasn't already been used and sets the text to match the respective player's turn
            if(player) {
                button.setText("X");
            }else{
                button.setText("O");
            }
            player = !player; //Changes the turn
        }

        if(checkWin()){ //Checks if either player got a win, inputs the statistics to StatTracker and shows an alert based on who won
            if(player == false){
                showMessage("You win!", "Player X won!");
                StatTracker.p1Win();
                StatTracker.endTime();
                StatTracker.addWin("Player 1", "Tic-Tac-Toe");
            }else if(player == true){
                showMessage("You win!", "Player O has won!");
                StatTracker.p2Win();
                StatTracker.endTime();
                StatTracker.addWin("Player 2", "Tic-Tac-Toe");
            }
            
            returnMain();

        }else if(checkFull()){ //Checks if the board is full and shows an alert
            System.out.println("It's a draw!");
            showMessage();
            StatTracker.endTime();
            returnMain();
        }
    }   

    @Override //Overrides checkWin from interface Game
    public boolean checkWin(){ //Checks all possible combinations a player could win for three of the same sign in a row
        for(int i = 0; i < 3; i++){ //Checks the horizontal and vertical directions
            if(boardButtons[i][0].getText().equals(boardButtons[i][1].getText()) && boardButtons[i][1].getText().equals(boardButtons[i][2].getText()) && !boardButtons[i][0].getText().isEmpty()){
                return true;
            }
            if(boardButtons[0][i].getText().equals(boardButtons[1][i].getText()) && boardButtons[1][i].getText().equals(boardButtons[2][i].getText()) && !boardButtons[0][i].getText().isEmpty()){
                return true;
            }
        } //Checks the two diagonals
        if(boardButtons[0][0].getText().equals(boardButtons[1][1].getText()) && boardButtons[1][1].getText().equals(boardButtons[2][2].getText()) && !boardButtons[0][0].getText().isEmpty()){
            return true;
        }
        if(boardButtons[0][2].getText().equals(boardButtons[1][1].getText()) && boardButtons[1][1].getText().equals(boardButtons[2][0].getText()) && !boardButtons[0][2].getText().isEmpty()){
            return true;
        }        

        return false;
    }

    public boolean checkFull(){ //Checks if every button on the gameboard is full
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(boardButtons[i][j].getText().isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void showMessage(String title, String message){ //Method to show an alert with whatever is given
        Alert alert = new Alert(AlertType.INFORMATION);
        Image imgTTT = new Image(getClass().getResourceAsStream("/resources/tictactoe.png"));
        ImageView imgvTTT = new ImageView(imgTTT);
        imgvTTT.setFitHeight(100);
        imgvTTT.setPreserveRatio(true);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(imgvTTT);
        alert.showAndWait();
    }

    public void showMessage(){ //Overloaded showAlert method letting the user know the board is full
        Alert alert = new Alert(AlertType.INFORMATION);
        Image imgTTT = new Image(getClass().getResourceAsStream("/resources/tictactoe.png"));
        ImageView imgvTTT = new ImageView(imgTTT);
        imgvTTT.setFitHeight(100);
        imgvTTT.setPreserveRatio(true);
        alert.setHeaderText("It's a tie!");
        alert.setContentText("The board is full. Try again next time!");
        alert.setGraphic(imgvTTT);
        alert.showAndWait();
    }

    public void returnMain(){ //Returns to the main menu
        MainMenu m = new MainMenu();
        m.start(primaryStage);
    }
}