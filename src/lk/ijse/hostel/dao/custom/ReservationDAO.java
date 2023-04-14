package lk.ijse.hostel.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.hostel.dao.CrudDAO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.view.tdm.KeyMoneyTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationDAO extends CrudDAO<Reservation,String> {
    public ArrayList<Reservation> searchReservation(String enteredText) throws SQLException, ClassNotFoundException;

    public ObservableList<KeyMoneyTM> getRemainKeyMoney() throws SQLException, ClassNotFoundException, IOException;
}
