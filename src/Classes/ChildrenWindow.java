package Classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ChildrenWindow {
    public static void create(String fxmlPath, Object className, Stage stage, Boolean modality, Integer width, Integer height) {
        try {
            FXMLLoader loader = new FXMLLoader(ChildrenWindow.class.getResource(fxmlPath));
            loader.setController(className);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(loader.load(), width, height));
            if (modality)
                stage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
