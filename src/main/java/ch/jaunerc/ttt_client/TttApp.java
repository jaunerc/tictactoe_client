package ch.jaunerc.ttt_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TttApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ttt_view.fxml"));
        primaryStage.setTitle("Tic Tac Toe Client");
        primaryStage.setScene(new Scene(root, 600, 420));
        primaryStage.show();
    }
}
