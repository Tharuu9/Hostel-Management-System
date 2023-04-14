package lk.ijse.hostel.bo.custom.impl;

import lk.ijse.hostel.bo.custom.PurchaseReserveBO;
import lk.ijse.hostel.dao.DAOFactory;
import lk.ijse.hostel.dao.DAOType;
import lk.ijse.hostel.dao.custom.ReservationDAO;
import lk.ijse.hostel.dao.custom.RoomDAO;
import lk.ijse.hostel.dao.custom.StudentDAO;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Reservation;
import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseReservationBOImpl implements PurchaseReserveBO {

    RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);
    StudentDAO studentDAO= DAOFactory.getInstance().getDAO(DAOType.STUDENT);
    ReservationDAO reservationDAO=DAOFactory.getInstance().getDAO(DAOType.RESERVATION);

    @Override
    public boolean purchaseReserveSave(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.save(new Reservation(
                dto.getResId(),
                dto.getDate(),
                dto.getKey_Money(),
                dto.getQty(),
                dto.getStatus(),
                dto.getRoomId(),
                dto.getStudentId()
        ));

    }

    @Override
    public boolean UpdateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.update(new Reservation(
                dto.getResId(),
                dto.getDate(),
                dto.getKey_Money(),
                dto.getQty(),
                dto.getStatus(),
                dto.getRoomId(),
                dto.getStudentId()
        ));
    }

    @Override
    public boolean deleteReservation(String id) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.delete(id);
    }

    @Override
    public RoomDTO searchRooms(String id) throws SQLException, ClassNotFoundException, IOException {
        Room room = roomDAO.search(id);
        return new RoomDTO(room.getRoomId(),room.getType(),room.getKeyMoney(),room.getQty());
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException, IOException {
        Student s =studentDAO.search(id);

        return new StudentDTO(s.getStId(),s.getStName(),s.getStAddress(),s.getContactNo(),s.getDob(),s.getGender());

    }

    @Override
    public ReservationDTO searchReservation(String id) throws SQLException, ClassNotFoundException, IOException {
        Reservation reservation=reservationDAO.search(id);
        return new ReservationDTO(reservation.getResId(), reservation.getDate(),reservation.getStudent(), reservation.getRoom(), reservation.getKeyMoney(), reservation.getStatus(), reservation.getQty());
    }

    @Override
    public boolean checkRoomIsAvailable(String id) throws SQLException, ClassNotFoundException, IOException {
        return roomDAO.find(id);
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.find(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.generateNewID();
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<Student> all = studentDAO.getAll();
        ArrayList<StudentDTO> allStudents = new ArrayList<>();

        for(Student s: all){
            allStudents.add(new StudentDTO(
                    s.getStId(),
                    s.getStName(),
                    s.getStAddress(),
                    s.getContactNo(),
                    s.getDob(),
                    s.getGender()));
        }

        return allStudents;
    }

    @Override
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> allRooms = new ArrayList<>();

        for(Room r: all){
            allRooms.add(new RoomDTO(
                    r.getRoomId(),
                    r.getType(),
                    r.getKeyMoney(),
                    r.getQty()
            ));
        }

        return allRooms;
    }

    @Override
    public List<ReservationDTO> getAllReservation() throws Exception {
        List<Reservation> all = reservationDAO.getAll();
        ArrayList<ReservationDTO> allReservation = new ArrayList<>();

        for(Reservation r: all){
            allReservation.add(new ReservationDTO(
                    r.getResId(),
                    r.getDate(),
                    r.getStudent(),
                    r.getRoom(),
                    r.getKeyMoney(),
                    r.getStatus(),
                    r.getQty()
            ));
        }

        return allReservation;
    }

    @Override
    public List getStudentIds() throws IOException {
        return studentDAO.getStudentIds();
    }

    @Override
    public List getRoomIds() throws IOException {
        return roomDAO.getRoomIds();
    }
}
