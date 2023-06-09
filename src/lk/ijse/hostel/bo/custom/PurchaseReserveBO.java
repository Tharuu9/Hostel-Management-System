package lk.ijse.hostel.bo.custom;

import lk.ijse.hostel.bo.SuperBO;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PurchaseReserveBO extends SuperBO {
    boolean purchaseReserveSave(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException;

    boolean UpdateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException;

    boolean deleteReservation(String  id) throws SQLException, ClassNotFoundException, IOException;

    RoomDTO searchRooms(String id) throws SQLException, ClassNotFoundException, IOException;

    StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException, IOException;

    ReservationDTO searchReservation(String id) throws SQLException, ClassNotFoundException, IOException;

    boolean checkRoomIsAvailable(String id) throws SQLException, ClassNotFoundException, IOException;

    boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException, IOException;

    String generateNewOrderID() throws SQLException, ClassNotFoundException, IOException;

    List<StudentDTO> getAllStudents() throws Exception;

    List<RoomDTO> getAllRooms() throws Exception;

    List<ReservationDTO> getAllReservation() throws Exception;


    List getStudentIds() throws IOException;

    List getRoomIds() throws IOException;
}
