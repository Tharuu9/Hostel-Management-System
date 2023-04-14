package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.RoomBO;
import lk.ijse.hostel.bo.custom.StudentBO;
import lk.ijse.hostel.bo.custom.UserBO;
import lk.ijse.hostel.entity.UserLogin;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public AnchorPane LoginFormContest;
    public JFXButton btnLogin;
    public CheckBox CheckBoxPassword;
    public JFXTextField txtPasswordHidden;
    public JFXPasswordField txtPassword;

    UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String name = txtUserName.getText();
        String password  = txtPassword.getText();

        UserLogin user=userBO.searchUser(name);

        if (user!=null) {
            if (txtPassword.getText().equals(user.getPassword())) {
                setUI("DashBoardForm");
            }else {
                new Alert(Alert.AlertType.ERROR, "Incorrect Password..!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Incorrect User ID..!").show();
        }
    }

    private void setUI(String location) throws IOException {
        Stage stage=(Stage) LoginFormContest.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void changeVisibility(MouseEvent mouseEvent) {
        if (CheckBoxPassword.isSelected()){
            txtPasswordHidden.setText(txtPassword.getText());
            txtPasswordHidden.setVisible(true);
            txtPassword.setVisible(false);
            return;
        }
        txtPasswordHidden.setText(txtPassword.getText());
        txtPassword.setVisible(true);
        txtPasswordHidden.setVisible(false);
    }
}
