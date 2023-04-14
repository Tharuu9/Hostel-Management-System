package lk.ijse.hostel.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostel.bo.BOFactory;
import lk.ijse.hostel.bo.BOType;
import lk.ijse.hostel.bo.custom.ReservationBO;
import lk.ijse.hostel.view.tdm.KeyMoneyTM;

import java.io.IOException;
import java.sql.SQLException;

public class KeyMoneyFormController {
    public AnchorPane KeyMoneyContent;
    public TableView<KeyMoneyTM> tblKeyMoney;
    public TableColumn colStId;
    public TableColumn colStName;
    public TableColumn colStatus;

    ReservationBO reservationBO = BOFactory.getInstance().getBO(BOType.RESERVATION);

    public void initialize() throws SQLException, IOException, ClassNotFoundException {

        tblKeyMoney.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentID"));
        tblKeyMoney.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        tblKeyMoney.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));

        loadRemainKeyMoneyStudent();
    }
    private void loadRemainKeyMoneyStudent() throws SQLException, IOException, ClassNotFoundException {

        ObservableList<KeyMoneyTM> remainKeyMnyStudent = reservationBO.getRemainKeyMnyStudent();
        tblKeyMoney.setItems(remainKeyMnyStudent);

    }
}
