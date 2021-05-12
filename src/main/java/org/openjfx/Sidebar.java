package org.openjfx;

import com.dao.AdminDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Sidebar {
    private static String page;

    @FXML
    public BorderPane main;
    public Label user;
    public Button logout;

    @FXML
    private void initialize() throws IOException {
        if (page != null) {
            load(page);
        } else {
            load("routes");
        }
        user.setText(AdminDao.getAdmin());
    }

    @FXML
    public void home() throws IOException {
        load("routes");
    }

    @FXML
    public void routes() throws IOException {
        load("routes");
    }

    @FXML
    public void drivers() throws IOException {
        load("manage_driver");
    }

    public void load(String fxml) throws IOException {
        main.setCenter(App.loadFXML(fxml));
    }

    //Setters
    public static void setPage(String page) {
        Sidebar.page = page;
    }

    public void logout() throws IOException {
        AdminDao.setAdmin(null);
        App.setRoot("start_screen");
    }
}
