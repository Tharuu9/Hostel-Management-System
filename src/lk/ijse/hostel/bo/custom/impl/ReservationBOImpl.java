package lk.ijse.hostel.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.hostel.bo.custom.ReservationBO;
import lk.ijse.hostel.dao.DAOFactory;
import lk.ijse.hostel.dao.DAOType;
import lk.ijse.hostel.dao.custom.ReservationDAO;
import lk.ijse.hostel.dao.custom.RoomDAO;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.view.tdm.KeyMoneyTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {

    ReservationDAO reservationDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION);

    @Override
    public ArrayList<ReservationDTO> getAllReserveDetails() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ReservationDTO> searchReserveDetails(String enteredText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<KeyMoneyTM> getRemainKeyMnyStudent() throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.getRemainKeyMoney();
    }
}
