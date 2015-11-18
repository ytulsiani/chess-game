package boardview;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import gamecontrol.AIChessController;
import gamecontrol.ChessController;
import gamecontrol.GameController;
import gamecontrol.NetworkedChessController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
/**
 * Main class for the chess application
 * Sets up the top level of the GUI
 * @author Gustavo
 * @version
 */
public class ChessFX extends Application {

    private GameController controller;
    private BoardView board;
    private Text state;
    private Text sideStatus;
    private VBox root;

    @Override
    public void start(Stage stage) throws UnknownHostException {
        HBox infoPane = new HBox();
        HBox infoPaneRight = new HBox();
        HBox networkPane = new HBox();
        VBox mainPane = new VBox();
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Chess");
        Text state = new Text();
        Text sideStatus = new Text();
        this.board = new BoardView(new ChessController(), state, sideStatus);
        infoPaneRight.getChildren().addAll(state, sideStatus);
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event ->
            {
                this.board.reset(new ChessController());
            });
        Button playComputerButton = new Button("Play AI");
        playComputerButton.setOnAction(event ->
            {
                this.board.reset(new AIChessController());
            });
        Button host = new Button("Host");
        host.setOnMouseClicked(makeHostListener());
        TextField ipInput = new TextField();
        ipInput.setPromptText("Enter IP address");
        Button join = new Button("Join");
        join.disableProperty().bind(
            Bindings.isEmpty(ipInput.textProperty()));
        join.setOnMouseClicked(makeJoinListener(ipInput));
        Label ipNetworkLabel = new Label(InetAddress.getLocalHost().toString());
        networkPane.getChildren().addAll(host, ipNetworkLabel, ipInput, join);
        infoPane.getChildren().addAll(resetButton, playComputerButton,
            infoPaneRight);
        mainPane.getChildren().addAll(this.board.getView(), infoPane,
            networkPane);
        HBox.setHgrow(infoPaneRight, Priority.ALWAYS);
        infoPaneRight.setAlignment(Pos.CENTER_RIGHT);
        stage.show();

    }

    private EventHandler<? super MouseEvent> makeHostListener() {
        return event -> {
            board.reset(new NetworkedChessController());
        };
    }

    private EventHandler<? super MouseEvent> makeJoinListener(TextField input) {
        return event -> {
            try {
                InetAddress addr = InetAddress.getByName(input.getText());
                GameController newController
                    = new NetworkedChessController(addr);
                board.reset(newController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
    }
}
