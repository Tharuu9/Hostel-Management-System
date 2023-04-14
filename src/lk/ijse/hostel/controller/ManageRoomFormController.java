package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.RoomBO;
import lk.ijse.hostel.bo.custom.StudentBO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.view.tdm.RoomTM;

import java.io.IOException;
import java.util.List;

public class ManageRoomFormController {

    public JFXButton btnAddNewRoom;
    public JFXTextField txtRoomQty;
    public JFXTextField txtKeyMoney;
    public JFXComboBox<String> cmbRoomID;
    public JFXComboBox<String> cmbRoomType;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<RoomTM> tblRoom;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colRoomQty;

    RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);

    public void initialize() throws IOException{
        cmbRoomID.getItems().addAll("RM-1324","RM-5467","RM-7896","RM-0093");
        cmbRoomType.getItems().addAll("Non-AC\",\"Non-Ac/Food\",\"AC\",\"AC/Food");

        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("roomQty"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnDelete.setDisable(false);

            if(newValue != null){
                cmbRoomID.setValue(newValue.getRoomId());
                cmbRoomType.setValue(newValue.getRoomType());
                txtKeyMoney.setText(newValue.getKeyMoney());
                txtRoomQty.setText(String.valueOf(newValue.getRoomQty()));

                cmbRoomID.setDisable(false);
                cmbRoomType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtRoomQty.setDisable(false);


                btnSave.setDisable(false);
            }
        });

        loadAllRooms();
        clear();
    }

    private void initUI() {
            cmbRoomID.setValue(null);
            cmbRoomType.setValue(null);
            txtKeyMoney.clear();
            txtRoomQty.clear();
            cmbRoomID.setDisable(true);
            cmbRoomType.setDisable(true);
            txtKeyMoney.setDisable(true);
            txtRoomQty.setDisable(true);
            cmbRoomID.setEditable(true);
            btnSave.setDisable(true);
            btnDelete.setDisable(true);
        }
        private void clear(){
        cmbRoomID.setValue(null);
        cmbRoomType.setValue(null);
        txtKeyMoney.clear();
        txtRoomQty.clear();

        }

    private void loadAllRooms() {
        tblRoom.getItems().clear();
        try {
            List<RoomDTO> allrooms = roomBO.getAllRooms();
            for(RoomDTO r : allrooms) {
                tblRoom.getItems().add(new RoomTM(
                        r.getRoomId(),
                        r.getRoomType(),
                        r.getKeyMoney(),
                        r.getRoomQty()
                ));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void AddNewRoomOnAction(ActionEvent actionEvent) {
        cmbRoomID.setDisable(false);
        cmbRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtRoomQty.setDisable(false);
        cmbRoomID.setValue(null);
        cmbRoomType.setValue(null);
        txtKeyMoney.clear();
        txtRoomQty.clear();
        cmbRoomID.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    public void SaveRoomOnAction(ActionEvent actionEvent) throws IOException {
        String id =cmbRoomID.getValue();
        String type = cmbRoomType.getValue();
        String keymoney = txtKeyMoney.getText();
        int qty = Integer.valueOf(txtRoomQty.getText());

        if (!keymoney.matches("^[0-9]{3,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Key_Money..!").show();
            txtKeyMoney.requestFocus();
            return;
        } else if (!txtRoomQty.getText().matches("^[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qty..! ").show();
            txtRoomQty.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("Save")) {

            if (roomBO.saveRoom(new RoomDTO(id, type, keymoney, qty)))
            {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                tblRoom.getItems().add(new RoomTM(id, type, keymoney, qty));
                clear();
            }else{
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong..!").show();

            }


        } else {
            try {
                if (roomBO.updateRoom(new RoomDTO(id, type,keymoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated..!").show();
                    loadAllRooms();
                    clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong..!").show();
            }
        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        String id=tblRoom.getSelectionModel().getSelectedItem().getRoomId();
        try {
            roomBO.deleteRoom(id);
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted..!").show();
            tblRoom.getSelectionModel().clearSelection();
            clear();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Try Again..!").show();
        }

    }
}
