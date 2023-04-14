package lk.ijse.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String stId;
    private String stName;
    private String stAddress;
    private String contactNo;
    private LocalDate dob;
    private String gender;
}
