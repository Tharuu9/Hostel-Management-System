package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.StudentBO;
import lk.ijse.hostel.bo.custom.UserBO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.view.tdm.StudentTM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ManageStudentFormController {
    public JFXButton btnAddNewStudent;
    public JFXTextField txtStudentId;
    public JFXTextField txtContactNo;
    public JFXTextField txtStudentName;
    public JFXTextField txtAddress;
    public JFXComboBox<String> cmbGender;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colName;
    public TableColumn colConNo;
    public TableColumn colAddress;
    public TableColumn colDOB;
    public TableColumn colGender;
    public DatePicker txtDob;

    StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() throws IOException {
        cmbGender.getItems().addAll("Male" , "Female");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stId"));
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stName"));
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stAddress"));
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnDelete.setDisable(false);

            if(newValue != null){
                txtStudentId.setText(newValue.getStId());
                txtStudentName.setText(newValue.getStName());
                txtAddress.setText(newValue.getStAddress());
                txtContactNo.setText(newValue.getContactNo());
                txtDob.setValue(newValue.getDob());
                cmbGender.setValue(newValue.getGender());

                txtStudentId.setDisable(false);
                txtStudentName.setDisable(false);
                txtAddress.setDisable(false);
                txtContactNo.setDisable(false);
                txtDob.setDisable(false);
                cmbGender.setDisable(false);

                btnSave.setDisable(false);
            }
        });

        loadAllStudents();
        clear();
    }

    private void loadAllStudents() {
        tblStudent.getItems().clear();
        try {
            List<StudentDTO> allStudents = studentBO.getAllStudent();
            for (StudentDTO s : allStudents) {
                tblStudent.getItems().add(new StudentTM(
                        s.getStId(),
                        s.getStName(),
                        s.getStAddress(),
                        s.getContactNo(),
                        s.getDob(),
                        s.getGender()));
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR , e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }
    private void clear(){
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtDob.setValue(null);
        cmbGender.setValue(null);
    }

    private void initUI() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtStudentId.setDisable(true);
        txtStudentName.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNo.setDisable(true);
        txtDob.setDisable(true);
        cmbGender.setDisable(true);
        txtStudentId.setEditable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void AddNewStudentOnAction(ActionEvent actionEvent) {
        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNo.setDisable(false);
        txtDob.setDisable(false);
        cmbGender.setDisable(false);
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtStudentId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void SaveStudentOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtAddress.getText();
        String contact_no = txtContactNo.getText();
        LocalDate dob = txtDob.getValue();
        String gender = cmbGender.getValue();

        if (!id.matches("^(S00)[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID..!").show();
            txtStudentId.requestFocus();
            return;

        } else if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name..!").show();
            txtStudentName.requestFocus();
            return;
        } else if (!address.matches("^[A-z0-9 ,/]{4,20}$")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long..!").show();
            txtAddress.requestFocus();
            return;
        } else if (!contact_no.matches("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid city..!").show();
            txtContactNo.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("Save")) {
            if (studentBO.saveStudent(new StudentDTO(id, name, address, contact_no, dob, gender))) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved..!").show();
                tblStudent.getItems().add(new StudentTM(id, name, address, contact_no, dob, gender));
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();

            }
        } else {
            try {
                if (studentBO.updateStudent(new StudentDTO(id, name, address, contact_no, dob, gender))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
                    loadAllStudents();
                    clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong..!").show();
            }
        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) throws IOException {
        StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
        if (studentBO.deleteStudent(selectedItem.getStId())){
            new Alert(Alert.AlertType.CONFIRMATION , "Deleted Successfully..!").show();

        }

    }
}
