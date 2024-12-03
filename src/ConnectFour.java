import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectFour extends Application implements Game{ //Class to run a Connect Four game, extends Application for JavaFX

    protected static final int rows = 6;
    protected static final int cols = 7;
    protected int[][] boardStatus = new int[6][7];
    protected boolean player = true;
    protected GridPane board;
    protected Stage primaryStage;

    public ConnectFour(Stage primaryStage){ //Constructor to allow for the primary scene to be passed around
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage){ //Start method, sets up the node tree and displays the scene

        VBox main = drawBoard();
        StatTracker.startTime();
        Scene scene = new Scene(main, 650, 650);
        scene.getStylesheets().add(getClass().getResource("styleButtons.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Connect Four Game");
        primaryStage.show();

    }

    private VBox drawBoard(){ //Method to draw the gameboard and set up the buttons
        board = makeBoard();
        board.setAlignment(Pos.TOP_CENTER);
        HBox buttons = makeButtons();
        VBox main = new VBox(20);
        main.setAlignment(Pos.TOP_CENTER);
        Button btnReturn = new Button("Return to main menu");
        btnReturn.setOnAction(e -> returnMain());
        main.getChildren().addAll(board, buttons, btnReturn);
        return main;
    }

    private GridPane makeBoard(){ //Method to make the grid of circles that make up the connect four board
        GridPane board = new GridPane();
        board.setVgap(10);
        board.setHgap(10);
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Circle circle = new Circle(40);
                circle.setFill(Color.LIGHTGRAY);
                board.add(circle, col, row);
                boardStatus[row][col] = 0;
            }
        }
        return board;
    }

    private HBox makeButtons(){ //Method that makes the buttons the player uses to drop the pieces
        HBox buttons = new HBox(45);
        buttons.setAlignment(Pos.CENTER);
        for(int col = 0; col < cols; col++){
            Button button = new Button("Drop");
            int column = col;
            button.setOnAction(e -> dropPiece(column));
            buttons.getChildren().add(button);
        }
        return buttons;
    }

    protected void dropPiece(int col){ //Method that drops a piece in the row the user selected and 
        
        for(int row = rows - 1; row >= 0; row--){ //Checks the column the player chose and checks for the furthest down empty spot to place the piece
            if(boardStatus[row][col] == 0){
                if(player){
                    boardStatus[row][col] = 1;
                }else{
                    boardStatus[row][col] = 2;
                }

                Circle piece = new Circle(40); 
                if(player){ //Checks whose turn it is and drops the corresponding piece
                    piece.setFill(Color.RED);
                }else{
                    piece.setFill(Color.YELLOW);
                }
                board.add(piece, col, row);

                if(checkWin(row, col)){ //Checks if the droppied piece was a winning piece
                    if(player){
                        showMessage("You win!", "Red has won!");
                        StatTracker.p1Win();
                        StatTracker.endTime();
                        StatTracker.addWin("Player 1", "Connect Four");
                    }else{
                        showMessage("You win!", "Yellow has won!");
                        StatTracker.p2Win();
                        StatTracker.endTime();
                        StatTracker.addWin("Player 2", "Connect Four");
                    }
                    returnMain();
                }else if(checkFull()){
                    showMessage();
                    StatTracker.endTime();
                    returnMain();
                }else{
                    player = !player;
                }
                break;
            }
        }

    }

    protected boolean checkFull(){ //Checks if the board is full
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (boardStatus[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkWin(int row, int col){ //Method that checks if a given piece is a winning piece

        int piece = boardStatus[row][col];
        int count = 1;

        for(int i = col - 1; i >= 0 && boardStatus[row][i] == piece; i--){ //Checks both horizontal directions from the piece
            count++;
        }
        for(int i = col + 1; i < 7 && boardStatus[row][i] == piece; i++){
            count++;
        }
        if(count >= 4){
            return true;
        }

        count = 1;
        for(int i = row - 1; i >= 0 && boardStatus[i][col] == piece; i--){ //Checks both vertical directions from the piece
            count++;
        }
        for(int i = row + 1; i < 6 && boardStatus[i][col] == piece; i++){
            count++;
        }
        if(count >= 4){
            return true;
        }

        count = 1;
        for(int i = 1; row - i >= 0 && col - i >= 0 && boardStatus[row - i][col - i] == piece; i++){ //Checks the bottom left and top right diagonals from the piece
            count++;
        }
        for(int i = 1; row + i < 6 && col + i < 7 && boardStatus[row + i][col + i] == piece; i++){
            count++;
        }
        if(count >= 4){
            return true;
        }

        count = 1;
        for(int i = 1; row + i < 6 && col - i >= 0 && boardStatus[row + i][col - i] == piece; i++){ //Checks the top left to bottom right diagonals from the piece
            count++;
        }
        for(int i = 1; row - i >= 0 && col + i < 7 && boardStatus[row - i][col + i] == piece; i++){
            count++;
        }
        if(count >= 4){
            return true;
        }

        return false;
    }

    @Override
    public void showMessage(String title, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        Image imgCF = new Image(getClass().getResourceAsStream("/resources/connectfour.png"));
        ImageView imgvCF = new ImageView(imgCF);
        imgvCF.setFitHeight(100);
        imgvCF.setPreserveRatio(true);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(imgvCF);
        alert.showAndWait();
    }

    public void showMessage(){
        Alert alert = new Alert(AlertType.INFORMATION);
        Image imgCF = new Image(getClass().getResourceAsStream("/resources/connectfour.png"));
        ImageView imgvCF = new ImageView(imgCF);
        imgvCF.setFitHeight(100);
        imgvCF.setPreserveRatio(true);
        alert.setHeaderText("It's a tie!");
        alert.setContentText("The board is full. Try again next time!");
        alert.setGraphic(imgvCF);
        alert.showAndWait();
    }

    public void returnMain(){
        MainMenu m = new MainMenu();
        m.start(primaryStage);
    }

}
