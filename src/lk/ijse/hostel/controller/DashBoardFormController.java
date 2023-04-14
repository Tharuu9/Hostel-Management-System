package lk.ijse.hostel.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class DashBoardFormController {
    public static LocalDate date;
    public AnchorPane DashBoardContent;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane DashBoardContent2;

    public void initialize() throws SQLException, ClassNotFoundException {
        RunningTime();
        loadDate();
        //loadLabel();
    }

    public void loadDate() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    private void RunningTime() {
        final Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(time);
                });
            }
        });
        thread.start();
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContent2.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/LoginDetailsForm.fxml"));
        DashBoardContent2.getChildren().add(parent);
    }

    public void ManageStudentOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContent2.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ManageStudentForm.fxml"));
        DashBoardContent2.getChildren().add(parent);
    }

    public void ManageRoomOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContent2.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ManageRoomForm.fxml"));
        DashBoardContent2.getChildren().add(parent);
    }

    public void ReservationOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContent2.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ReservationForm.fxml"));
        DashBoardContent2.getChildren().add(parent);
    }

    public void RemainKeyMoneyOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContent2.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/KeyMoneyForm.fxml"));
        DashBoardContent2.getChildren().add(parent);
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Logout?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            URL resource = getClass().getResource("lk/ijse/hostel/view/LoginForm.fxml");
            Parent load = FXMLLoader.load(resource);
            DashBoardContent.getChildren().clear();
            DashBoardContent.getChildren().add(load);
        }
    }
}
