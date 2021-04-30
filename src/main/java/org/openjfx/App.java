package org.openjfx;

import com.sun.istack.Nullable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login", null));

        stage.setTitle("Nerdy Delivery");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml, null));
    }

    static void setRoot(String fxml, Object data) throws IOException {
        scene.setRoot(loadFXML(fxml, data));
    }

    public static Scene getScene() {
        return scene;
    }

    private static Parent loadFXML(String fxml, @Nullable Object data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org.openjfx/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}