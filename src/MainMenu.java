import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenu extends Application{
    
    @Override
    public void start(Stage primaryStage) { //Draws the main menu UI

        primaryStage.setTitle("Quick 'n' Fun Games!");

        Button btnTicTacToe = new Button("Tic-Tac-Toe");
        Button btnConnectFour = new Button("Connect Four");
        Button btnHangman = new Button("Hangman");

        HBox root = new HBox(30);
        root.setAlignment(Pos.CENTER);
        VBox vTicTacToe = new VBox(10);
        VBox vConnectFour = new VBox(10);
        VBox vHangman = new VBox(10);
        
        ToggleGroup tgTicTacToe = new ToggleGroup();
        ToggleGroup tgConnectFour = new ToggleGroup();
        ToggleGroup tgHangman = new ToggleGroup();

        RadioButton rbTicTacToe1 = new RadioButton("1 Player");
        RadioButton rbTicTacToe2 = new RadioButton("2 Players");
        RadioButton rbConnectFour1 = new RadioButton("1 Player");
        RadioButton rbConnectFour2 = new RadioButton("2 Players");
        RadioButton rbHangman1 = new RadioButton("Normal");
        RadioButton rbHangman2 = new RadioButton("Hard");

        Image imgTicTacToe = new Image(getClass().getResourceAsStream("/resources/tictactoe.png"));
        Image imgConnectFour = new Image(getClass().getResourceAsStream("/resources/connectfour.png"));
        Image imgHangman = new Image(getClass().getResourceAsStream("/resources/hangman.png"));

        ImageView imgvTicTacToe = new ImageView(imgTicTacToe);
        imgvTicTacToe.setFitHeight(100);
        imgvTicTacToe.setPreserveRatio(true);

        ImageView imgvConnectFour = new ImageView(imgConnectFour);
        imgvConnectFour.setFitHeight(100);
        imgvConnectFour.setPreserveRatio(true);

        ImageView imgvHangman = new ImageView(imgHangman);
        imgvHangman.setFitHeight(100);
        imgvHangman.setPreserveRatio(true);

        rbTicTacToe1.setToggleGroup(tgTicTacToe);
        rbTicTacToe2.setToggleGroup(tgTicTacToe);
        rbTicTacToe1.setSelected(true);
        rbConnectFour1.setToggleGroup(tgConnectFour);
        rbConnectFour2.setToggleGroup(tgConnectFour);
        rbConnectFour1.setSelected(true);
        rbHangman1.setToggleGroup(tgHangman);
        rbHangman2.setToggleGroup(tgHangman);
        rbHangman1.setSelected(true);

        root.getChildren().addAll(vTicTacToe, vConnectFour, vHangman);
        vTicTacToe.getChildren().addAll(imgvTicTacToe, btnTicTacToe, rbTicTacToe1, rbTicTacToe2);
        vConnectFour.getChildren().addAll(imgvConnectFour, btnConnectFour, rbConnectFour1, rbConnectFour2);
        vHangman.getChildren().addAll(imgvHangman, btnHangman, rbHangman1, rbHangman2);

        btnTicTacToe.setOnAction(e -> { //Checks which radio button is checked and opens the corresponding class or subclass
            if(rbTicTacToe2.isSelected()){
                startTicTacToe(primaryStage);
            }else if(rbTicTacToe1.isSelected()){
                startTicTacToe2P(primaryStage);
            }
        });

        btnConnectFour.setOnAction(e -> {
            if(rbConnectFour1.isSelected()){
                startConnectFour2P(primaryStage);
            }else if(rbConnectFour2.isSelected()){
                startConnectFour(primaryStage);
            }
        });

        btnHangman.setOnAction(e -> {
            if(rbHangman1.isSelected()){
                startHangman(primaryStage);
            }else if(rbHangman2.isSelected()){
                startHangmanHard(primaryStage);
            }
        });

        Button btnStats = new Button("Check stats");
        btnStats.setOnAction(e -> showStats());
        btnStats.setAlignment(Pos.CENTER);
        BorderPane border = new BorderPane();
        border.setCenter(root);
        border.setBottom(btnStats);
        Scene mainMenu =  new Scene(border, 600, 300);

        mainMenu.getStylesheets().add(getClass().getResource("styleMainMenu.css").toExternalForm());

        primaryStage.setScene(mainMenu);
        primaryStage.show();

    }

    public void showStats(){ //Method to pull up the stats menu
        Stage stats = new Stage();
        Text p1Stats = new Text();
        Text p2Stats = new Text();
        Text comStats = new Text();
        stats.setTitle("Game Stats");

        p1Stats.setText("Player 1's win count: " + StatTracker.getP1Wins().toString());
        p2Stats.setText("Player 2's win count: " + StatTracker.getP2Wins().toString());
        comStats.setText("The computer's win count: " + StatTracker.getComWins().toString());
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(p1Stats, p2Stats, comStats);
        Scene scene = new Scene(root, 300, 200);

        stats.setScene(scene);
        stats.show();
    } 

    public void startTicTacToe(Stage primaryStage){ //Methods to start the different games
        TicTacToe ttt = new TicTacToe(primaryStage);
        ttt.start(primaryStage);
    }

    public void startTicTacToe2P(Stage primaryStage){
        TicTacToe2P ttt = new TicTacToe2P(primaryStage);
        ttt.start(primaryStage);

    }

    public void startConnectFour(Stage primaryStage){
        ConnectFour cf = new ConnectFour(primaryStage);
        cf.start(primaryStage);
    }

    public void startConnectFour2P(Stage primaryStage){
        ConnectFour2P cf = new ConnectFour2P(primaryStage);
        cf.start(primaryStage);
    }

    public void startHangman(Stage primaryStage){
        Hangman h = new Hangman(primaryStage);
        h.start(primaryStage);
    }

    public void startHangmanHard(Stage primaryStage){
        HangmanHard h = new HangmanHard(primaryStage);
        h.start(primaryStage);
    }

    public static void main(String args[]){
        launch(args);
    }

    
}
