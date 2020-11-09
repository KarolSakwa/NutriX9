package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Window {

    public void createWindow(String fxmlFile, Boolean isDecorated, Stage stage, Boolean inNewWindow, Integer width, Integer height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        if (isDecorated)
            stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }
}
