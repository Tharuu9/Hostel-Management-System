package lk.ijse.hostel.bo.custom.impl;

import lk.ijse.hostel.bo.custom.StudentBO;
import lk.ijse.hostel.dao.DAOFactory;
import lk.ijse.hostel.dao.DAOType;
import lk.ijse.hostel.dao.custom.StudentDAO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.entity.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOType.STUDENT);

    @Override
    public List<StudentDTO> getAllStudent() throws IOException {
            List<Student> all = studentDAO.getAll();
            ArrayList<StudentDTO> allStudents = new ArrayList<>();

            for(Student s: all){
                allStudents.add(new StudentDTO(
                        s.getStId(),
                        s.getStName(),
                        s.getStAddress(),
                        s.getContactNo(),
                        s.getDob(),
                        s.getGender()
                ));
            }

            return allStudents;
    }

    @Override
    public boolean saveStudent(StudentDTO dto) throws IOException {
        return studentDAO.save(new Student(
                dto.getStId(),
                dto.getStName(),
                dto.getStAddress(),
                dto.getContactNo(),
                dto.getDob(),
                dto.getGender()
        ));
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws IOException {
        return studentDAO.update(new Student(
                dto.getStId(),
                dto.getStName(),
                dto.getStAddress(),
                dto.getContactNo(),
                dto.getDob(),
                dto.getGender()
        ));
    }

    @Override
    public boolean deleteStudent(String id) throws IOException {
        return studentDAO.delete(id);
    }
}
