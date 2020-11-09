package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    Window window = new Window();
    @Override
    public void start(Stage primaryStage) throws Exception{
        window.createWindow("selectProfile.fxml", true, primaryStage, false, 520, 400);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
