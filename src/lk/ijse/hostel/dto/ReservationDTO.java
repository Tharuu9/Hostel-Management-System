package lk.ijse.hostel.dto;

import lk.ijse.hostel.entity.Room;
import lk.ijse.hostel.entity.Student;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private String resId;
    private LocalDate date;
    private Student studentId;
    private Room roomId;
    private double key_Money;
    private String status;
    private int qty;

}
