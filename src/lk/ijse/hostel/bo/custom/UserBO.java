package lk.ijse.hostel.bo.custom;

import lk.ijse.hostel.bo.SuperBO;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.entity.UserLogin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    List<UserDTO> getAllUser() throws IOException;

    boolean saveUser(UserDTO dto) throws IOException;

    boolean updateUser(UserDTO dto) throws IOException;

    boolean deleteUser(String id) throws IOException;

    UserLogin searchUser(String id) throws IOException, SQLException, ClassNotFoundException;

    String generateUserId()throws Exception;


}
