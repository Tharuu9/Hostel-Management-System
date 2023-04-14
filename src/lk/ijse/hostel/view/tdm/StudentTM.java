package lk.ijse.hostel.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTM {
    private String stId;
    private String stName;
    private String stAddress;
    private String contactNo;
    private LocalDate dob;
    private String gender;
}
