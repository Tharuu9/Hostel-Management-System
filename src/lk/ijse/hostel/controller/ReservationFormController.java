package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.PurchaseReserveBO;
import lk.ijse.hostel.bo.custom.RoomBO;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;
import lk.ijse.hostel.view.tdm.ReservationTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFormController {
    public Label lblRoomId;
    public Label lblRoomType;
    public TableView<ReservationTM> tblReservation;
    public TableColumn colReservationID;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colStudentQty;
    public TableColumn colKeyMoney;
    public TableColumn colStatus;
    public TableColumn colDelete;
    public JFXTextField txtStudentName;
    public JFXComboBox<String> cmbStudentID;
    public JFXComboBox<String> cmbRoomID;
    public Label lblReserveID;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomQty;
    public TextField txtStatus;
    public TextField txtStudentQty;
    public JFXButton btnReserve;
    public JFXButton btnUpdate;
    public JFXButton btnAddToRemain;
    public Label lblRoomQty;
    public AnchorPane ReservationFormContent;
    String reservationId;
    int preQty;
    ArrayList<RoomDTO> allrooms;

    PurchaseReserveBO purchaseReserveBO = BOFactory.getInstance().getBO(BOType.PURCHASE_RESERVE);
    RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);

    public void initialize() {

        RF();

        tblReservation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("reserveID"));
        tblReservation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomID"));
        tblReservation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        tblReservation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("studentQty"));
        tblReservation.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblReservation.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<ReservationTM, Button> lastCol = (TableColumn<ReservationTM, Button>) tblReservation.getColumns().get(6);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                if(tblReservation.getSelectionModel().getSelectedItem()!=null){
                    try {
                        if(purchaseReserveBO.deleteReservation(reservationId)){
                            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.....").show();
                            System.out.println("qry room: "+preQty);

                            RoomDTO roomDTO1 = purchaseReserveBO.searchRooms((String) cmbRoomID.getValue());

                            roomDTO1.setRoomQty(preQty);

                            roomBO.updateRoom(roomDTO1);

                            tblReservation.getItems().remove(param.getValue());
                            tblReservation.getSelectionModel().clearSelection();
                            clear();

                        }else {

                            new Alert(Alert.AlertType.ERROR,"Try Again.....").show(); ;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Please Select Row....").show(); ;
                }
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        try {
            loadAllReservation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadStudentIds();
        loadRoomIds();

        cmbStudentID.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                StudentDTO studentDTO = purchaseReserveBO.searchStudent((String) newValue);
                txtStudentName.setText(studentDTO.getStName());
            } catch (SQLException exception) {
                exception.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        cmbRoomID.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                RoomDTO roomDTO = purchaseReserveBO.searchRooms((String) newValue);
                lblRoomId.setText(roomDTO.getRoomId());
                lblRoomType.setText(roomDTO.getRoomType());
                lblRoomQty.setText(String.valueOf(roomDTO.getRoomQty()));
                txtRoomType.setText(roomDTO.getRoomType());
                txtRoomQty.setText((String.valueOf(roomDTO.getRoomQty())));
                txtKeyMoney.setText(roomDTO.getKeyMoney());
            } catch (SQLException exception) {
                exception.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    private void loadAllReservation() throws Exception {
        ObservableList<ReservationTM> observableList = FXCollections.observableArrayList();
        List<ReservationDTO> list = purchaseReserveBO.getAllReservation();

        for (ReservationDTO r : list) {
            String reserveID = r.getResId();
            Room room = r.getRoomId();
            String roomID = room.getRoomId();
            String roomType = room.getType();
            int studentQty = r.getQty();
            double keyMoney = r.getKey_Money();
            String status = r.getStatus();

            ReservationTM reservationTM = new ReservationTM(reserveID, roomID, roomType, studentQty, keyMoney, status);
            observableList.add(reservationTM);
            tblReservation.setItems(observableList);
        }
    }

    private void loadRoomIds() {
        try {
            cmbRoomID.getItems().addAll(purchaseReserveBO.getRoomIds());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentIds() {
        try {
            cmbStudentID.getItems().addAll(purchaseReserveBO.getStudentIds());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void RF(){

        reservationId=generateNewOrderId();
        lblReserveID.setText(reservationId);

    }
    public void updateRoomQty(String id) throws SQLException, IOException, ClassNotFoundException {
        RoomDTO roomDTO = purchaseReserveBO.searchRooms(id);
        int newqty=roomDTO.getRoomQty()-Integer.parseInt(txtStudentQty.getText());

        roomDTO.setRoomQty(newqty);
        roomBO.updateRoom(roomDTO);
    }


    private String generateNewOrderId() {
        try {
            return purchaseReserveBO.generateNewOrderID();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id...").show();

        return "R001";
    }

    private void clear(){
        cmbStudentID.setValue(null);
        txtStudentName.clear();
        cmbRoomID.setValue(null);
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtRoomQty.clear();
        txtStatus.clear();
        txtStudentQty.clear();
    }

    public void ReserveOnAction(ActionEvent actionEvent) throws Exception {
        String res_id = lblReserveID.getText();
        LocalDate date = DashBoardFormController.date;
        StudentDTO studentDTO = purchaseReserveBO.searchStudent((String) cmbStudentID.getValue());
        Student student = new Student(studentDTO.getStId(), studentDTO.getStName(), studentDTO.getStAddress(), studentDTO.getContactNo(), studentDTO.getDob(), studentDTO.getGender());
        RoomDTO roomDTO = purchaseReserveBO.searchRooms((String) cmbRoomID.getValue());
        Room room = new Room(roomDTO.getRoomId(), roomDTO.getRoomType(), roomDTO.getKeyMoney(), roomDTO.getRoomQty());
        double key_money = Double.parseDouble(txtKeyMoney.getText());
        String status = txtStatus.getText();
        int qty = Integer.parseInt(txtStudentQty.getText());


        ReservationDTO reservationDTO = new ReservationDTO(res_id, date, student, room, key_money, status, qty);
        if(purchaseReserveBO.purchaseReserveSave(reservationDTO)){
            updateRoomQty((String) cmbRoomID.getValue());
            loadAllReservation();
            RF();
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.......").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again.......").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws Exception {
        String res_id = lblReserveID.getText();
        LocalDate date = DashBoardFormController.date;
        StudentDTO studentDTO = purchaseReserveBO.searchStudent((String) cmbStudentID.getValue());
        Student student = new Student(studentDTO.getStId(), studentDTO.getStName(), studentDTO.getStAddress(), studentDTO.getContactNo(), studentDTO.getDob(), studentDTO.getGender());
        RoomDTO roomDTO = purchaseReserveBO.searchRooms(cmbRoomID.getValue());
        Room room = new Room(roomDTO.getRoomId(), roomDTO.getRoomType(), roomDTO.getKeyMoney(), roomDTO.getRoomQty());
        double key_money = Double.parseDouble(txtKeyMoney.getText());
        String status = txtStatus.getText();
        int qty = Integer.parseInt(txtStudentQty.getText());


        ReservationDTO reservationDTO = new ReservationDTO(res_id, date, student, room, key_money, status, qty);

        if(purchaseReserveBO.UpdateReservation(reservationDTO)){
            loadAllReservation();
            int b=preQty-Integer.parseInt(txtStudentQty.getText());
            RoomDTO roomDTO1=new RoomDTO(room.getRoomId(),room.getType(),room.getKeyMoney(),b);

            roomBO.updateRoom(roomDTO1);

            new Alert(Alert.AlertType.CONFIRMATION,"Updated.....!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again.....!").show();
        }
    }

    public void AddToRemainOnAction(ActionEvent actionEvent) throws IOException {
        ReservationFormContent.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/RemainKeyMoneyForm.fxml"));
        ReservationFormContent.getChildren().add(parent);
    }

    public void reservationTableClicked(MouseEvent mouseEvent) throws SQLException, IOException, ClassNotFoundException {
        if (tblReservation.getSelectionModel().getSelectedItem() != null) {
            ReservationTM selectedItem = tblReservation.getSelectionModel().getSelectedItem();
            reservationId = selectedItem.getReserveID();

            ReservationDTO reservationDTO = purchaseReserveBO.searchReservation(reservationId);
            Student student = reservationDTO.getStudentId();
            Room roomID = reservationDTO.getRoomId();
            preQty=roomID.getQty()+selectedItem.getStudentQty();
            cmbStudentID.setValue(student.getStName());
            txtStudentName.setText(student.getStName());

            lblReserveID.setText(selectedItem.getReserveID());

            cmbRoomID.setValue(selectedItem.getReserveID());
            txtRoomType.setText(selectedItem.getRoomType());
            txtKeyMoney.setText(String.valueOf(selectedItem.getKeyMoney()));
            txtRoomQty.setText(String.valueOf(roomID.getQty()));

            txtStatus.setText(selectedItem.getStatus());
            txtStudentQty.setText(String.valueOf(selectedItem.getStudentQty()));
        }
    }
}
