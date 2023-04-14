package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.UserBO;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.view.tdm.UserTM;

import java.io.IOException;
import java.util.List;


public class LoginDetailsFormController {
    public AnchorPane LoginDetailsContent;
    public JFXButton btnAddNewUser;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<UserTM> tblLoginDetails;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colPassword;

    UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);

    public void initialize(){
        tblLoginDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblLoginDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblLoginDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("password"));

        initUI();

        tblLoginDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnDelete.setDisable(false);

            if(newValue != null){
                txtUserId.setText(newValue.getUserId());
                txtUserName.setText(newValue.getUserName());
                txtPassword.setText(newValue.getPassword());


                txtUserId.setDisable(false);
                txtUserName.setDisable(false);
                txtPassword.setDisable(false);


                btnSave.setDisable(false);
            }
        });

        loadAllUsers();
        generateNewId();
        clear();
    }

    private void loadAllUsers(){

        tblLoginDetails.getItems().clear();
        try {
            List<UserDTO> userLoginDTOS = userBO.getAllUser();
            for(UserDTO u : userLoginDTOS) {
                tblLoginDetails.getItems().add(new UserTM(
                        u.getUserId(),
                        u.getUserName(),
                        u.getPassword()
                ));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }

    }

    private void clear(){
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
    }
    private void initUI(){
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        txtUserId.setEditable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }


    private void generateNewId() {
        try {
            String lastUserId = userBO.generateUserId();
            int newId = Integer.parseInt(lastUserId.substring(1, 4))+1;
            if (newId < 10) {
                txtUserId.setText("U00"+newId);
            }else if (newId < 100) {
                txtUserId.setText("U0"+newId);
            }else {
                txtUserId.setText("U"+newId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        txtUserId.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtUserId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblLoginDetails.getSelectionModel().clearSelection();
    }
    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        if (!id.matches("^(U00)[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID..!").show();
            txtUserId.requestFocus();
            return;

        }else if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name..!").show();
            txtUserName.requestFocus();
            return;
        } else if (!password.matches("^[A-z]{3,8}[0-9]{4}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long..!").show();
            txtPassword.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("Save")) {

            if (userBO.saveUser(new UserDTO(id, name, password)))
            {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..!").show();
                tblLoginDetails.getItems().add(new UserTM(id, name, password));
                loadAllUsers();
                clear();
            }else{
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong..!").show();

            }
        } else {
            try {
                if (userBO.updateUser(new UserDTO(id, name, password))){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated..!").show();
                    loadAllUsers();
                    clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong..!").show();
            }
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id=tblLoginDetails.getSelectionModel().getSelectedItem().getUserId();
        try {
            userBO.deleteUser(id);
            tblLoginDetails.getItems().remove(tblLoginDetails.getSelectionModel().getSelectedItem());
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted..!").show();

            tblLoginDetails.getSelectionModel().clearSelection();
            clear();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Try again..!").show();
        }
    }
}
