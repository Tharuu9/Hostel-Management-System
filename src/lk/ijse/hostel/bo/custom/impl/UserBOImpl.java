package lk.ijse.hostel.bo.custom.impl;

import lk.ijse.hostel.bo.custom.UserBO;
import lk.ijse.hostel.dao.DAOFactory;
import lk.ijse.hostel.dao.DAOType;
import lk.ijse.hostel.dao.custom.UserDAO;
import lk.ijse.hostel.dto.UserDTO;
import lk.ijse.hostel.entity.UserLogin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOType.USER);

    @Override
    public List<UserDTO> getAllUser() throws IOException {
        List<UserLogin> all = userDAO.getAll();
        ArrayList<UserDTO> allUser = new ArrayList<>();

        for(UserLogin u: all){
            allUser.add(new UserDTO(
                    u.getUserId(),
                    u.getUserName(),
                    u.getPassword()
            ));
        }

        return allUser;
    }

    @Override
    public boolean saveUser(UserDTO dto) throws IOException {
        return userDAO.save(new UserLogin(
                dto.getUserId(),
                dto.getUserName(),
                dto.getPassword()
        ));
    }
    @Override
    public boolean updateUser(UserDTO dto) throws IOException {
        return userDAO.update(new UserLogin(
                dto.getUserId(),
                dto.getUserName(),
                dto.getPassword()
        ));
    }

    @Override
    public boolean deleteUser(String id) throws IOException {
        return userDAO.delete(id);
    }

    @Override
    public UserLogin searchUser(String id) throws IOException, SQLException, ClassNotFoundException {
        return userDAO.search(id);
    }

    @Override
    public String generateUserId() throws Exception {
        String id= userDAO.generateNewID();
        return id;
    }
}
