package ProjectAkhirFinal;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;

public class LauncherProgram extends Application {
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("TampilanUtama.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Shopping List Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}